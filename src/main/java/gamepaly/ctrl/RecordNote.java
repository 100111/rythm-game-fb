package gamepaly.ctrl;

import java.io.IOException;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.MusicLevel;

import util.GeneralUtil;

import com.google.gson.Gson;

import db.MusicLevelDAO;

@WebServlet("/RecordNote")
public class RecordNote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RecordNote() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = GeneralUtil.processJsonRequest(request);
		Gson gson = new Gson();
		
		TreeMap<Long, String> map = gson.fromJson(json, TreeMap.class);
		
		for (Entry<Long, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " - " + entry.getValue());
		}
		
		MusicLevel musicLevel = new MusicLevel(1, 1, json);
		//MusicLevelDAO.addMusicLevl(1, musicLevel);
		MusicLevelDAO.updateMusicLevl(1, musicLevel);
		
		System.out.println("Called");
	}

}
