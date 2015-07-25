package old.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.GeneralUtil;

import com.google.gson.Gson;

import model.Music;
import model.MusicLevel;

/**
 * Servlet implementation class TestJSON
 */
@WebServlet("/TestJSON")
public class TestJSON extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TestJSON() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numSt = request.getParameter("num");
	
		int num = 10;
		if(!(numSt == null || numSt.equals(""))) {
			num = Integer.parseInt(numSt);
		}
		
		TreeMap<Long, String> data = MusicLevel.gennerateOneString(num);
		Gson gson = new Gson();
		String json = gson.toJson(data);
		
		response.setContentType("application/json");
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
