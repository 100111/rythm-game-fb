package gamepaly.ctrl;

import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Music;
import model.MusicLevel;

import com.google.gson.Gson;

import db.MusicDAO;
import db.MusicLevelDAO;

@WebServlet("/MusicLoader")
public class MusicLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MusicLoader() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idSt = request.getParameter("id");
		String lvSt = request.getParameter("lv");
		
		int id = 1;
		int level = 2;
		if(!(idSt == null || idSt.equals(""))) {
			id = Integer.parseInt(idSt);
		}
		if(!(lvSt == null || lvSt.equals(""))) {
			level = Integer.parseInt(lvSt);
		}
		
		//Get from database
		MusicLevel mslv = MusicLevelDAO.getMusicLv(id,level);
		
		//Set bien ID danh cho cac servlet xu ly khac
		request.getSession().setAttribute("idMusicLv", new Integer(mslv.getId()));
		
		/*
		mslv.processJsonData();
		Gson gson = new Gson();
		String json = gson.toJson(mslv.getData());
		*/
		
		response.setContentType("application/json");
		response.getWriter().print(mslv.getJsonData());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
