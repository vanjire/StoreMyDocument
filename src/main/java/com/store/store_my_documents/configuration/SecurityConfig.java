package com.store.store_my_documents.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 private final JwtAuthenticationFilter jwtAuthenticationFilter;
	 private final JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
	    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,JwtAuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
	        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	        this.jwtAuthenticationSuccessHandler=jwtAuthenticationSuccessHandler;
	    }
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	 @Bean
	 public AuthenticationManager authenticationManager(
	         AuthenticationConfiguration configuration) throws Exception {

	     return configuration.getAuthenticationManager();
	 }
	 
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	     http
	         .csrf(csrf -> csrf.disable())
	         .authorizeHttpRequests(auth -> auth
	             .requestMatchers("/auth/register").permitAll()
	             .anyRequest().authenticated()
	         )
	         .formLogin(form -> form
	        		    //.loginPage("/login")
	        		   // .loginProcessingUrl("/login")
	        		    .failureUrl("/login?error=true")
	        		    .successHandler(jwtAuthenticationSuccessHandler)
	        		    .permitAll()
	        		).
	         sessionManagement(session ->
	         session.sessionCreationPolicy(
	             SessionCreationPolicy.STATELESS
	         )
	     ).
	        		 addFilterBefore(
	        			        jwtAuthenticationFilter,
	        			        UsernamePasswordAuthenticationFilter.class
	        			);

	     return http.build();
	 }
}
