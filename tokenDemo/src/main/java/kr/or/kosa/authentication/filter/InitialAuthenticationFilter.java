package kr.or.kosa.authentication.filter;

import java.io.IOException;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.authentication.UsernamePasswordAuthentication;

public class InitialAuthenticationFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager;
	
	@Value("${jwt.secret}")
	private String signingKey;
	
	public InitialAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager; 
	}
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		String username = request.getHeader("username");
		String password = request.getHeader("password");
		
		Authentication a = new UsernamePasswordAuthentication(username, password);
		authenticationManager.authenticate(a);
		
		
		System.out.println("jwt.secret->" + signingKey);
		
		//token 생성 
		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes("UTF-8"));
		String jwt = Jwts.builder()
				.setClaims(Map.of("username", username))
				.signWith(key)
				.compact();
		response.setHeader("Authorization", jwt);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// /login 경로만 필더 적용한다 
		return !request.getServletPath().equals("/login");
	}

}
