package kr.or.kosa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import kr.or.kosa.authentication.filter.InitialAuthenticationFilter;
import kr.or.kosa.authentication.filter.JwtAuthenticationFilter;
import kr.or.kosa.authentication.providers.UsernamePasswordAuthenticationProvider;



@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	
	@Autowired
	private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(usernamePasswordAuthenticationProvider);
    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/* @formatter:off */
		//해당 위치에서 AuthenticationManager 개체를 얻으려 하면 null 나옴 
		//반드시 커스텀 필터 구성정보를 설정할 수 있는 것을 추가하여 얻는다 
		AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);

		http.csrf(AbstractHttpConfigurer::disable);
		http.apply(new CustomFilter()); // 커스텀 필터 등록
		http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
		
		return http.build();
		/* @formatter:on */
	}

	//커스텀 필터 구성 정보 등록 
	public class CustomFilter extends AbstractHttpConfigurer<CustomFilter, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http.addFilterAt(new InitialAuthenticationFilter(authenticationManager), BasicAuthenticationFilter.class)
				.addFilterAt(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class);
		}
	}
	
}



