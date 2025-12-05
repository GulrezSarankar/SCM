package com.scm.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.scm.ServiceImpl.SecurityCustomUserDetailService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	AuthFailtureHandler authFailtureHandler;
	
	@Autowired
	private OAuthAuthenicationSuccessHandler handler;
	
	@Autowired
	private SecurityCustomUserDetailService userDetailService;
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		UserDetails user1 = User
//				.withDefaultPasswordEncoder()
//				.username("admin123")
//		
//				.password("admin123")
//		
//				.roles("admin","User")
//		
//				.build();
//		
//		
//		
//		var inMemoryUserDetailsManager= new InMemoryUserDetailsManager(user1);
//		return  inMemoryUserDetailsManager;
//		
//
//}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user detail service ka object:
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/register", "/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
		
		
		
		http.formLogin(formLogin ->{
			formLogin.loginPage("/login");
			formLogin.loginProcessingUrl("/authenticate");
			formLogin.successForwardUrl("/user/dashboard");
			formLogin.failureForwardUrl("/login?error=true");
			
			formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
//            formLogin.failureHandler(new AuthenticationFailureHandler() {
//				
//				@Override
//				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
//						AuthenticationException exception) throws IOException, ServletException {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//            
//            formLogin.successHandler(new AuthenticationSuccessHandler() {
//				
//				@Override
//				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//						Authentication authentication) throws IOException, ServletException {
//					// TODO Auto-generated method stub
//					
//				}
//			});
            
            formLogin.failureHandler(authFailtureHandler);
            
		});
		
		http.csrf(AbstractHttpConfigurer::disable);
		http.logout(logoutform ->{
			logoutform.logoutUrl("/logout");
			logoutform.logoutSuccessUrl("/login?error=true");

			
		});
		
//		oauth configuration
		http.oauth2Login(oauth->{
			oauth.loginPage("/login");
			oauth.successHandler(handler);
		});
		
		
		
		return http.build();
		
	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	


	

}
