package kr.or.kosa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}

/*
 *  //로그인 
 * 	//테스트 명령 : curl -H "username:masungil" -H "password:1234" http://localhost:8100/login
 * */
