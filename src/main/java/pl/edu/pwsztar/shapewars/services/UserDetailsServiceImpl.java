//package pl.edu.pwsztar.shapewars.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import pl.edu.pwsztar.shapewars.entities.User;
//import pl.edu.pwsztar.shapewars.repositories.UserRepository;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Transactional
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//    //
//    public UserDetails loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException(
//                    "No user found with username: "+ email);
//        }
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNonExpired = true;
//        boolean accountNonLocked = true;
//        return  new org.springframework.security.core.userdetails.User
//                (user.getEmail(),
//                        user.getPassword().toLowerCase(), enabled, accountNonExpired,
//                        credentialsNonExpired, accountNonLocked,
//                        getAuthorities(new ArrayList<>()));
//    }
//
//    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;
//    }
//}
