package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.restfb.FacebookClient;
import com.restfb.types.User;

import model.UserFB;

import db.UserDAO;

import util.RestFBUtil;

@WebServlet("/LoginUser")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginUser() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = request.getParameter("user");
		String token = request.getParameter("token");
		HttpSession session = request.getSession();
		
		//init RestFB API
		RestFBUtil.initRestFB(token,session);
		
		//Check user
		UserFB user = UserDAO.findUser(userID);
		if (user == null) {
			FacebookClient client = RestFBUtil.getRestFBClient(session);
			User userRs = client.fetchObject("me", User.class);
			user = new UserFB(userRs);
			UserDAO.registerUser(user);
			
			System.out.println("New user: "+user.getId());
		} else {
			System.out.println("Old user: "+user.getId());
		}
		
		session.setAttribute("user", user);
		
		response.setContentType("text/plain");
		response.getWriter().print("index2.jsp");
	}

	

}
