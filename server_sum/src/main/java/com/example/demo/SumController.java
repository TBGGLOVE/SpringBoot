package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.ServletContext;

@RestController
@CrossOrigin("http://localhost:3000")
public class SumController {
	
	@Autowired
	private ServletContext application;
	
	
	@GetMapping("/api/createId")
	public Map<String, Object> createId() {
		String uuid = UUID.randomUUID().toString();
		application.setAttribute("loop_" + uuid, true);

		Map<String, Object> result = new HashMap<>();
		result.put("status", 1);
		result.put("uuid", uuid);
		
		return result;
	}
	
	@GetMapping("/api/sum")
	public Map<String, Object> sum(
			@RequestParam(name = "uuid", required = true)
			String uuid) {
		boolean existUUID = application.getAttribute("loop_" + uuid) != null;
		if (uuid == null || uuid.length() == 0 || !existUUID) {
			Map<String, Object> result = new HashMap<>();
			result.put("status", -1);
			result.put("result", 0);
			result.put("message", "uuid가 존재하지 않습니다");

			return result;
		}
		int result = 0;
		String message = "1 ~ 1000 까지의 합계를 구함";

		application.setAttribute("loop_" + uuid, true);
		for (int i=1;i<=1000;i++) {
			Boolean loop = (Boolean)application.getAttribute("loop_" + uuid);
			System.out.println("loop->" + loop);
			if (loop != null && !loop) {
				message = "취소되었습니다";
				break;
			}
			result += i;
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				
			}
//			System.out.println("합계 구할 때 uuid = " + uuid + "     i = " + i);
		}
		Map<String, Object> resultMap = new HashMap<>();
		Boolean loop = (Boolean)application.getAttribute("loop_" + uuid); 
		
		resultMap.put("status", loop != null && loop ? 1 : -2);
		resultMap.put("result", result);
		resultMap.put("message", message);

		application.removeAttribute("loop_" + uuid);
		return resultMap;
	}
	
	@GetMapping("/api/sumCancel")
	public Map<String, Object> sumCancel(
			@RequestParam(name = "uuid", required = true)
			String uuid) {
		boolean existUUID = application.getAttribute("loop_" + uuid) != null;
		if (uuid == null || uuid.length() == 0 || !existUUID) {
			Map<String, Object> result = new HashMap<>();
			result.put("status", -1);
			result.put("result", 0);
			result.put("message", "uuid가 존재하지 않습니다");

			return result;
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("status", 1);
		resultMap.put("message", "취소를 설정하였습니다");

		return resultMap;
	}

}
