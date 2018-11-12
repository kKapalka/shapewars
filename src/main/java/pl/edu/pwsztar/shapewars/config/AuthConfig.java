//package pl.edu.pwsztar.shapewars.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import pl.edu.pwsztar.shapewars.security.services.UserDetailsServiceImpl;
//
//@Configuration
//public class AuthConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsServiceImpl userDetailsService;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//}
