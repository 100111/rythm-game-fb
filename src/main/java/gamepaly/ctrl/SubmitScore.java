package gamepaly.ctrl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;

import util.RestFBUtil;

import db.ScoreDAO;

import model.Score;
import model.UserFB;

@WebServlet("/SubmitScore")
public class SubmitScore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SubmitScore() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//HttpSession session = request.getSession();
		int idMusicLevel = 0,score = 0;
		try {
			idMusicLevel = Integer.parseInt(request.getParameter("idMusicLevel"));
			score = Integer.parseInt(request.getParameter("score"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().print("Missing Par");
			return;
		}
		
		String idUser = request.getParameter("idUser");
		
		saveScoreToDB(idUser , idMusicLevel, score);
		response.getWriter().print("Done score in DB");
	}
	
	public static void saveScoreToDB(String idUser, int idMusicLevel, int score) {
		Score scoreObj = new Score(idUser, idMusicLevel, score);
		scoreObj.setDate(new Date());
		
		ScoreDAO.registerScore(scoreObj);
		
		System.out.println("Done score in DB");
	}
	
	public static boolean postScoreToFB(int score, String idUser, HttpSession session) {
		//Post FB
        try {
        	
        	String token = (String)session.getAttribute("token");	
        	
        	HttpClient httpClient = new DefaultHttpClient();  
            HttpPost httpPost = new HttpPost("https://graph.facebook.com/"+idUser+"/scores");
            
            StringEntity entity = new StringEntity("{'score':"+score+",'access_token':'"+token+"'}");
            httpPost.setEntity(entity);
            
            HttpResponse response = httpClient.execute(httpPost);
            
            System.out.println("post to fb"+response.getAllHeaders());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		return false;
	}
	
	public static String postScoreToFBString(int score, String idUser, HttpSession session) {
		
		String token = (String)session.getAttribute("token");	
		
        String result = "https://graph.facebook.com/"+idUser+"/scores?score="+score+"&access_token="+token;
		
		return result;
	}
	
	
}
