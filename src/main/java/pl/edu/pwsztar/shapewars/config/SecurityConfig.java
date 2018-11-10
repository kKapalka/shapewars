package pl.edu.pwsztar.shapewars.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(70)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public SecurityConfig(){
        super();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf().disable();
//                .authorizeRequests()
//                .antMatchers("/login*","/login*", "/logout*", "/signin/**", "/signup/**", "/customLogin",
//                        "/user/register*", "/registrationConfirm*", "/expiredAccount*", "/register*",
//                        "/badUser*", "/user/resendRegistrationToken*" ,"/forgetPassword*", "/user/resetPassword*",
//                        "/user/changePassword*", "/emailError*", "/resources/**","/old/user/registration*","/successRegister*","/qrcode*").permitAll()
//                .antMatchers("/invalidSession*").anonymous()
//                .antMatchers("/user/updatePassword*","/user/savePassword*","/updatePassword*").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
//                .anyRequest().hasAuthority("READ_PRIVILEGE")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/homepage.html")
//                .failureUrl("/login?error=true")
//                .permitAll()
//                .and()
//                .sessionManagement()
//                .invalidSessionUrl("/invalidSession.html")
//                .sessionFixation().none()
//                .and()
//                .logout()
//                .invalidateHttpSession(false)
//                .logoutSuccessUrl("/logout.html?logSucc=true")
//                .deleteCookies("JSESSIONID")
//                .permitAll()
//                .and()
//                .a;
        // @formatter:on
    }

}
