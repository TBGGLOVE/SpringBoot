package kr.or.kosa.authentication.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import kr.or.kosa.authentication.UsernamePasswordAuthentication;
import kr.or.kosa.authentication.proxy.AuthenticationServerProxy;

@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	final private AuthenticationServerProxy proxy;
	
	public UsernamePasswordAuthenticationProvider(AuthenticationServerProxy proxy) {
		this.proxy = proxy;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = String.valueOf(authentication.getCredentials());
	
		proxy.sendAuth(username, password);
		return new UsernamePasswordAuthenticationToken(username, password);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthentication.class.isAssignableFrom(authentication);
	}

}
