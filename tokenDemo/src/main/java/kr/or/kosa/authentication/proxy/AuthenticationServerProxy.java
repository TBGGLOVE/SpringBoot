package kr.or.kosa.authentication.proxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kr.or.kosa.model.User;

@Component
public class AuthenticationServerProxy {

	@Autowired
	private  RestTemplate restTemplate;

	@Value("${auth.server.base.url}")
	private String baseUrl;
	
	
	public User sendAuth(String username, String password) {
		String url = baseUrl + "/user/auth";
		var body = User.builder().username(username).password(password).build();
		var request = new HttpEntity<>(body);
		var response = restTemplate.postForEntity(url, request, User.class);
		return response.getBody();
	}
	
}
