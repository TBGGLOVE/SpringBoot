package kr.or.kosa.authentication.filter;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.kosa.authentication.UsernamePasswordAuthentication;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Value("${jwt.secret}")
	private String signingKey;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader("Authorization");
		SecretKey key = Keys.hmacShaKeyFor(signingKey.getBytes("UTF-8"));
		
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(jwt)
				.getBody();
		
		String username = String.valueOf(claims.get("username"));
		GrantedAuthority a = new SimpleGrantedAuthority("user");
		var auth = new UsernamePasswordAuthentication(username, null, List.of(a));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		// /login 경로를 제외하고 모든 필더 적용한다 
		return request.getServletPath().equals("/login");
	}

}
