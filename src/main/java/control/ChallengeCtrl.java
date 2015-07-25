package control;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.GeneralUtil;
import util.RestFBUtil;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.User;

import db.ChallengeDAO;
import db.UserDAO;

import model.Challenge;
import model.ChallengeSumRecord;
import model.UserFB;

@WebServlet("/ChallengeCtrl")
public class ChallengeCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ChallengeCtrl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idMusicLevel = 0;
		int senderScore = 0;
		try {
			idMusicLevel = Integer.parseInt(request.getParameter("idMusicLevel"));
			senderScore = Integer.parseInt(request.getParameter("score"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.getWriter().print("Missing Par");
			return;
		}
		
		boolean isInvite = false;
		if (request.getParameter("isInvite") != null) {
			isInvite = Boolean.parseBoolean(request.getParameter("invite"));
		}
		
		String idUserSent = request.getParameter("sender");
		String idUserRec = request.getParameter("reciver");
		if(GeneralUtil.isEmpty(idUserSent) || GeneralUtil.isEmpty(idUserRec)) return;
		
		registerChallenge(idUserSent, idUserRec, idMusicLevel, isInvite, senderScore);
		
		response.getWriter().print("Done sending.");
	}

	public static void registerChallenge(String idUserSent, String idUserRec, int idMusicLv, boolean isInvite, int senderScore) {
		Challenge challenge = new Challenge(0, idUserSent, new Date());
		challenge.setIdUserRec(idUserRec);
		challenge.setIdMusic(idMusicLv);
		challenge.setResult(0);
		challenge.setInvite(isInvite);
		challenge.setSenderScore(senderScore);
		
		ChallengeDAO.registerChallenge(challenge);
	}
	
	public static int updateChallengeResult(Challenge challenge, int rivalScore) {
		int result = 0;
		if (challenge.getSenderScore() >= rivalScore) {
			//Nguoi gui thang
			result = 1;
		} else {
			//Nguoi nhan thang
			result = 2;
		}
		
		challenge.setResult(result);
		ChallengeDAO.updateChallengeInfo(challenge);
		
		return result;
	}
	
	/**
	 * Tao danh sach ket qua tu challenge list cu 1 user xác định
	 * @param idUser id cua nguoi dung, tuy co the ko can thiet, nhung nen truyen de ko bi loi
	 * @param challengeList danh sach thanh dau
	 * @return
	 */
	public static Vector<ChallengeSumRecord> createResultHistoryFromChallenge(String idUser, Vector<Challenge> challengeList) {
		//IUser - Ty so (n-m)
		Vector<ChallengeSumRecord> result = new Vector<ChallengeSumRecord>();
	
		for (Challenge challenge : challengeList) {
			if(challenge.getIdUserSent().equals(idUser)) {
				//Xy ly khi current user la nguoi gui
				ChallengeSumRecord record = ChallengeSumRecord.findRecordIn(idUser, challenge.getIdUserRec(), result);
				if(record != null){
					result.remove(record);
				} else {
					record = new ChallengeSumRecord(idUser, challenge.getIdUserRec());
				}
				
				switch (challenge.getResult()) {
				case 0:
					record.setStatus(1);
					break;
				case 1:
					record.setWin(record.getWin() + 1);
					break;
				case 2:
					record.setLose(record.getLose() + 1);
					break;
				}
				
				result.add(record);
				
			} else if(challenge.getIdUserRec().equals(idUser)){
				//Xy ly khi current user la nguoi nhan
				ChallengeSumRecord record = ChallengeSumRecord.findRecordIn(idUser, challenge.getIdUserSent(), result);
				
				if(record != null){
					result.remove(record);
				} else {
					record = new ChallengeSumRecord(idUser, challenge.getIdUserSent());
				}
				
				switch (challenge.getResult()) {
				case 0:
					record.setStatus(-1);
					break;
				case 1:
					record.setLose(record.getLose() + 1);
					break;
				case 2:
					record.setWin(record.getWin() + 1);
					break;
				}
				
				result.add(record);
			}
		}
		
		return result;
	}
	

	/**
	 * Lay nguoi dung tu database, dung de hien thi trong he thong Challenge
	 * @param num
	 * @return
	 */
	public static Vector<UserFB> getRandomUser(int num){		
		return UserDAO.getRandomUserFromDatabase(num);
	}
	
	/**
	 * Lay random ban be cua nguoi dum, dung de hien thi trong he thong Challenge
	 * @param num so luong tra ve, neu <=0 nghi la lay tat ca 
	 * @param checkDB so sanh voi database, neu = true, se bo qua random khi co so bien "num"
	 * @param session 
	 * @return
	 */
	public static Vector<UserFB> getRandomFriend(String userID, int num, boolean checkDB, HttpSession session){
		Vector<UserFB> result = new Vector<UserFB>();
		Vector<String> idList = new Vector<String>();
		
		//Lay thong tin friend tu fb
		FacebookClient client = RestFBUtil.getRestFBClient(session);	
		Connection<User> friends = client.fetchConnection(userID+"/friends", User.class);
	
		for (List<User> friendConnection : friends) {
			for (User user : friendConnection) {
				idList.add(user.getId());
				result.add(new UserFB(user));
			}
		}
		
		//Co kiem tra database hay ko
		if(checkDB){
			Vector<String> idOfFriendWhoRegistered = UserDAO.checkIfUserInDB(idList);
			Vector<UserFB> result2 = new Vector<UserFB>();
			
			//Neu num <= 0 thi chuyen thanh gia tri lon nhat, de de lam
			if(num <= 0) num = Integer.MAX_VALUE;
			
			//Add vao ket qua tat ca cac ban be co trong database
			int counter = 0;
			for (UserFB userFB : result) {
				if(counter == num) break;
				
				if (idOfFriendWhoRegistered.contains(userFB.getId())){
					result2.add(userFB);
					counter++;
				}
			}

			//Neu nhu tat ca cac ban van chưa đủ so lượng cần thì
			if(counter < num) {
				result.removeAll(result2); //Xoa tat ca cac du lieu da dc loc
				for (UserFB userFB : result) {
					if(counter == num) break;
					result2.add(userFB);
					counter++;
				}
			}
			
			return result2;
		} else {
			//Co random hay khong
			if (num <= 0){
				return result;
			} else {
				Vector<UserFB> result2 = new Vector<UserFB>();
				for (int i = 0; i < num; i++) {
					int random = (int) Math.round(Math.random()*(result.size() - 1));
					result2.add(result.get(random));
				}
				System.gc();
				return result2;
			}
		}
	}
}
