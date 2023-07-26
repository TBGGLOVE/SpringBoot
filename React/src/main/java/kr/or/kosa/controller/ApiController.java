/*
package kr.or.kosa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.context.annotation.RequestScope;
//import org.springframework.stereotype.Component;

import kr.or.kosa.util.*;

@RestController
@RequestMapping("*")
public class ApiController {
	
	
	@GetMapping("getSum")
	public ResponseEntity<?> getSum(HttpSession session) {
		
		State state = new State();
		RandomId randomid = new RandomId();
		session.setAttribute("loop", true);
		session.setAttribute("key", randomid.getRandomNumber());
		int key = (int)session.getAttribute("key");
		state.setKey(key); 
		
	
		int sum = 0;
		for (int i = 1; i <= 10; i++) {
			boolean result = (boolean) session.getAttribute("loop");
			System.out.println(i+"번째"+result);
			if (result == true ) {
				
				sum += i;
				System.out.println(sum);
				state.setSum(sum);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
			} else {
				break;
			}

		}

		return new ResponseEntity<State>(state, HttpStatus.OK);

	}
	

	@GetMapping("getCancel")
	public ResponseEntity<?> getCancel(HttpSession session) {
		int Cancelkey = (int)session.getAttribute("key");
		System.out.println(Cancelkey);
		
		
//		if(Cancelkey == sumkey) { //계산하기 눌렀을때 설정된 식별값이 현재 멤버필드와 값이 같다면.
		session.setAttribute("loop", false);
//		}
		
		return new ResponseEntity<>("안녕", HttpStatus.OK);
		

	}
}
*/

 package kr.or.kosa.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("*")
public class ApiController {

    @GetMapping("createTaskId")
    public ResponseEntity<?> createTaskId(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String taskId = UUID.randomUUID().toString(); // 고유한 작업 ID 생성
        session.setAttribute("loop-" + taskId, true); // 해당 작업 ID를 세션에 저장

        return ResponseEntity.ok(taskId); // 클라이언트에게 작업 ID 반환
    }
    

    @GetMapping("getSum")
    public ResponseEntity<?> getSum(@RequestParam("taskId") String taskId, HttpServletRequest request) {
    	System.out.println("****************************");
    	  HttpSession session = request.getSession();
        //String taskId = UUID.randomUUID().toString(); // 고유한 작업 ID 생성
        session.setAttribute("loop-" + taskId, true); // 해당 작업 ID를 세션에 저장
        System.out.println("실행 : " + session.getId() + ", 작업 ID: " + taskId);
        int result = 0;
        long delayInMilliseconds = 1000; // 0.01초를 밀리초로 변환
//        session.setAttribute("loop", true);
        for (int i = 1; i <= 10; i++) {
            System.out.println((Boolean) session.getAttribute("loop"));
            // 여기서 작업을 수행
            // System.out.println("현재 변수 값: " + i);
            result += i;
            // 0.01초마다 딜레이를 줌
            try {
                Thread.sleep(delayInMilliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!(Boolean) session.getAttribute("loop-" + taskId)) {
                break;
            }
        }
        return ResponseEntity.ok(result); // 클라이언트에게 연산결과 반환
    }

    @GetMapping("cancel")
    public ResponseEntity<?> cancel(@RequestParam("taskId") String taskId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Boolean isCancelled = (Boolean) session.getAttribute("loop-" + taskId);
        if (isCancelled != null && !isCancelled) {
            // 작업이 이미 취소되었을 경우
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 취소된 작업입니다.");
        }

        session.setAttribute("loop-" + taskId, false); // 작업 ID에 해당하는 작업 취소
        System.out.println("취소 : " + session.getId() + ", 작업 ID: " + taskId);
        System.out.println("작업 취소 결과: " + session.getAttribute("loop-" + taskId));

        return ResponseEntity.ok("작업 취소 완료");
    }
}
 







