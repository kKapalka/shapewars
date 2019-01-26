package pl.edu.pwsztar.shapewars.controllers.rest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwsztar.shapewars.entities.User;
import pl.edu.pwsztar.shapewars.entities.dto.LoginDto;
import pl.edu.pwsztar.shapewars.entities.dto.PrivilegesDto;
import pl.edu.pwsztar.shapewars.entities.dto.UserDto;
import pl.edu.pwsztar.shapewars.entities.enums.FighterSlot;
import pl.edu.pwsztar.shapewars.messages.JwtResponse;
import pl.edu.pwsztar.shapewars.messages.ResponseMessage;
import pl.edu.pwsztar.shapewars.repositories.UserRepository;
import pl.edu.pwsztar.shapewars.security.jwt.JwtProvider;
import pl.edu.pwsztar.shapewars.services.FighterService;
import pl.edu.pwsztar.shapewars.services.MaintenanceLogService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    private FighterService fighterService;

    @PostMapping("/privileges")
    public ResponseEntity<?> grantPrivileges(@RequestBody PrivilegesDto dto){
        User issuer =
              userRepository.findByLoginEquals(jwtProvider.getUserNameFromJwtToken(dto.getIssuerToken().replaceAll("[\\x00-\\x09\\x0B\\x0C\\x0E-\\x1F\\x7F]", ""))).orElseThrow(
                    EntityNotFoundException::new);
        if(!issuer.isAdmin()){
            return new ResponseEntity<>(new ResponseMessage("Fail - you are not an admin to do this kind of things!"),
                  HttpStatus.UNAUTHORIZED);
        } else{
            User newAdmin =
                  userRepository.findByLoginEquals(dto.getSelectUsername()).orElseThrow(EntityNotFoundException::new);
            newAdmin.setAdmin(dto.isAdmin());
            userRepository.save(newAdmin);
            return new ResponseEntity<>(new ResponseMessage("Success! "+dto.getSelectUsername()+(dto.isAdmin()?" is ":
                                                            " is not ")+"an admin now!"),
                  HttpStatus.OK);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto signUpRequest) {
        if(signUpRequest.getLogin().length()>=9){
            if(signUpRequest.getLogin().substring(0,9).equals("BOT_LEVEL")){
                return new ResponseEntity<>(new ResponseMessage("Fail -> This username is reserved for BOTS ONLY!"),
                      HttpStatus.BAD_REQUEST);
            }
        }
        if (userRepository.findByLoginEquals(signUpRequest.getLogin()).isPresent()) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.findByEmail(signUpRequest.getEmail())!=null) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User();
        user.setLogin(signUpRequest.getLogin());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        List<User> currentUserList = userRepository.findAll();
        if(currentUserList.size()==0){
            user.setAdmin(true);
        } else{
            user.setAdmin(signUpRequest.isAdmin());
        }
        user.setLevel(1L);
        user.setExperiencePoints(0L);
        user = userRepository.save(user);
        if(!user.isAdmin()){
            user.setFighterList(Arrays.asList(fighterService.generateFighter(user, FighterSlot.SLOT_1),
                  fighterService.generateFighter(user,FighterSlot.SLOT_2),
                    fighterService.generateFighter(user,FighterSlot.SLOT_3),fighterService.generateFighter(user,
                        FighterSlot.SLOT_4)));
        }
        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
    @PostMapping("/reset-fighters/{login}")
    public ResponseEntity<?> resetFighters(@PathVariable String login){
        fighterService.resetFighterList(login);
        User user = userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new);
        user.setFighterList(Arrays.asList(fighterService.generateFighter(user, FighterSlot.SLOT_1),
              fighterService.generateFighter(user,FighterSlot.SLOT_2),
              fighterService.generateFighter(user,FighterSlot.SLOT_3),fighterService.generateFighter(user,
                    FighterSlot.SLOT_4)));
        return new ResponseEntity<>(new ResponseMessage("Fighter list reset successfully!"), HttpStatus.OK);
    }

    @PostMapping("/ban/{login}")
    public ResponseEntity<?> ban(@PathVariable String login, @RequestBody String token){
        User issuer =
              userRepository.findByLoginEquals(jwtProvider.getUserNameFromJwtToken(token.replaceAll("[\\x00-\\x09\\x0B" +
                                                                                                "\\x0C\\x0E-\\x1F\\x7F]", ""))).orElseThrow(
                    EntityNotFoundException::new);
        if(!issuer.isAdmin()){
            return new ResponseEntity<>(new ResponseMessage("Fail - you are not an admin to do this kind of things!"),
                  HttpStatus.UNAUTHORIZED);
        } else {
            User user = userRepository.findByLoginEquals(login).orElseThrow(EntityNotFoundException::new);
            userRepository.delete(user);
            return new ResponseEntity<>(new ResponseMessage("Success! " + login + " has been banned!"), HttpStatus.OK);
        }
    }
}
