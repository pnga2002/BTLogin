package java22.Fillter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "login",urlPatterns = {"/profile"})
public class LoginFillter implements Filter{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		chinh tieng viet
		response.setContentType("text/html; charset=UTF-8");
		
//		servletPath : lay ra link dang duoc goi
		// code xu ly khi filter dc kich hoat
		System.out.println("Da kich hoat filter");
//		PrintWriter print = response.getWriter();
//		print.write("Noi dung duoc tra tu filter");
//		print.close();
		
//		cho phep di vao file servlet ung voi link ma client goi
//		chain.doFilter(request, response);
		
//		Neu nguoi dung goi pade/loichao ma c truyen tham so hoten thi da ve login
//		nguoc lai neu co truyen tham so thi cho phep di vao page loi chao
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpServletResponse response2 = (HttpServletResponse) response;
		HttpSession session = request2.getSession();
		//lay duong dan link hien tai
		String path = request2.getServletPath();
		String context = request2.getContextPath();
		switch(path) {
		case "/profile":
			System.out.println(path);
			String email = (String) session.getAttribute("email");
			
			if(email==null||email.isEmpty()) {
				response2.sendRedirect(context+"/login");
			} else {
				chain.doFilter(request2, response2);
			}
			break;
		default:
			System.out.println(path);
		}
		
	}

}
