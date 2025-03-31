package com.QuizApp.config;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
        		.requestMatchers("/articles/**").hasAuthority("ROLE_ADMIN")
        		.requestMatchers("/quiz/admin/**").hasAuthority("ROLE_ADMIN") // Only ADMIN can access
        		.requestMatchers("/quiz/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER") // Both ADMIN and USER can access
        		.requestMatchers("/question/addQuestion").hasAuthority("ROLE_ADMIN")
        		.requestMatchers("/question/update/{id}").hasAuthority("ROLE_ADMIN")
        		.requestMatchers("/question/{id}").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
        		.requestMatchers("/result/generate").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
        		.anyRequest().authenticated() // All other requests require authentication
        		)
        .oauth2ResourceServer(oauth2 -> oauth2
        		.jwt(jwt -> jwt
        				.jwtAuthenticationConverter(jwtAuthenticationConverter())
        				)
        		);
        return http.build();
        
//            .authorizeHttpRequests(authorize -> authorize
//            		.requestMatchers("/articles/**").hasAuthority("ROLE_ADMIN")
//            		.requestMatchers("/quiz/create").hasAuthority("ROLE_ADMIN")
//            		.anyRequest().authenticated())
//            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
    }
    
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter());
        return converter;
    }

    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("ROLE_"); // Add prefix to roles
        converter.setAuthoritiesClaimName("roles"); // Map roles from the JWT claim
        return converter;
    }
	
//    @Bean
//    GrantedAuthoritiesMapper userAuthoritiesMapper() {
//		return (authorities) -> {
//			Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
//
//			authorities.forEach(authority -> {
//				
//				System.out.println("authority: "+authority);
//				
//				if (OidcUserAuthority.class.isInstance(authority)) {
//					OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
//
//					OidcIdToken idToken = oidcUserAuthority.getIdToken();
//					OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
//					System.out.println("userInfo: "+userInfo);
//					// Map the claims found in idToken and/or userInfo
//					// to one or more GrantedAuthority's and add it to mappedAuthorities
//
//				} else if (OAuth2UserAuthority.class.isInstance(authority)) {
//					OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
//					
//					Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
//
//					System.out.println("userAttributes: "+userAttributes.get("roles"));
//					
//					Collection<String> roles = (Collection<String>) userAttributes.get("roles");
//					
//					System.out.println("roles: "+roles);
//					
//
//					
//					 roles.forEach(role -> mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role)));
//
//					
//					 System.out.println("mappedAuthorities: "+mappedAuthorities);
//					
//					
//					// Map the attributes found in userAttributes
//					// to one or more GrantedAuthority's and add it to mappedAuthorities
//
//				}
//			});
//
//			return mappedAuthorities;
//		};
//	}
    
}