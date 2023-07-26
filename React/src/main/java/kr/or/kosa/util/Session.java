package kr.or.kosa.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// 세션을 받아오는 함수

public class Session{
	
	public String username;
	public int sessionId;
	
	
	public Session(HttpServletRequest request) {
		HttpSession session = request.getSession();
		RandomId randomId = new RandomId();
//		System.out.println(session);
//		session.setAttribute("username", "JohnDoe");
//		session.setAttribute("sessionId", randomId.randomNumber);
		
		this.username = (String)session.getAttribute("username"); 
		this.sessionId = (int) session.getAttribute("sessionId");
	}
	
//static public String getSession(HttpServletRequest request) {
//    // getSession() 메서드를 사용하여 세션을 가져옵니다.
//    HttpSession session = request.getSession();
//
//    // 세션에 데이터를 저장하거나 가져올 수 있습니다.
//    // 예를 들어, 세션에 'username'이라는 속성을 저장하고 가져옵니다.
//    String username = (String) session.getAttribute("username");
//
//    // 세션에 데이터를 저장할 때는 setAttribute() 메서드를 사용합니다.
//    session.setAttribute("username", "JohnDoe");
//
//    // 세션에 저장된 데이터를 삭제할 때는 removeAttribute() 메서드를 사용합니다.
//    session.removeAttribute("username");
//
//    // 세션을 무효화(삭제)할 때는 invalidate() 메서드를 사용합니다.
//    session.invalidate();
//    return username;
//}
}
