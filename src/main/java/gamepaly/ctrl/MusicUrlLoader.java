package gamepaly.ctrl;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Music;

import db.MusicDAO;

@WebServlet("/MusicUrlLoader")
public class MusicUrlLoader extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MusicUrlLoader() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idSt = request.getParameter("id");
		String all = request.getParameter("all");
		
		if(all != null && all.equals("true")){
			Vector<Music> list = MusicDAO.getAllMusic();
			request.getSession().setAttribute("musicList", list);
		} else {
			int id = -1;
			if(!(idSt == null || idSt.equals(""))) {
				id = Integer.parseInt(idSt);
			}
			
			Music music = MusicDAO.getMusicByID(id);
			request.getSession().setAttribute("musicSingle", music);
		}
		
		response.getWriter().print(true);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
