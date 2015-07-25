package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import control.ChallengeCtrl;

import util.GeneralUtil;

import model.Challenge;
import model.ChallengeSumRecord;

public class ChallengeDAO {
	public static Vector<Challenge> getAllChallengeForUser(String idUser) {
		Connection con = DBConnect.getConnection();
		Vector<Challenge> result = new Vector<Challenge>();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM challenge " +
					"WHERE userSent like ? OR userRec like ?");
			cauTruyVan.setString(1, idUser);
			cauTruyVan.setString(2, idUser);
			ResultSet ketqua = cauTruyVan.executeQuery();
			int id;
			String idUserSent;
			Date date;
			while (ketqua.next()) {
				id = ketqua.getInt("id");
				idUserSent = ketqua.getString("userSent");
				date = ketqua.getTimestamp("date");
				Challenge challenge = new Challenge(id, idUserSent, date);
				challenge.setIdUserRec(ketqua.getString("userRec"));
				challenge.setIdMusic(ketqua.getInt("idMusicLv"));
				challenge.setResult(ketqua.getInt("result"));
				challenge.setSenderScore(ketqua.getInt("senderScore"));
				challenge.setIdRequestFB(ketqua.getString("requestFB"));
				
				switch (ketqua.getInt("isInvite")) {
				case 0:
					challenge.setInvite(false);
					break;
				case 1:
					challenge.setInvite(true);
					break;
				}
				
				result.add(challenge);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static Vector<Challenge> getAllChallengeForUserSolo(String idUser) {
		Connection con = DBConnect.getConnection();
		Vector<Challenge> result = new Vector<Challenge>();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM challenge " +
					"WHERE userRec like ?");
			cauTruyVan.setString(1, idUser);
			ResultSet ketqua = cauTruyVan.executeQuery();
			int id;
			String idUserSent;
			Date date;
			while (ketqua.next()) {
				id = ketqua.getInt("id");
				idUserSent = ketqua.getString("userSent");
				date = ketqua.getTimestamp("date");
				Challenge challenge = new Challenge(id, idUserSent, date);
				challenge.setIdUserRec(ketqua.getString("userRec"));
				challenge.setIdMusic(ketqua.getInt("idMusicLv"));
				challenge.setResult(ketqua.getInt("result"));
				challenge.setSenderScore(ketqua.getInt("senderScore"));
				challenge.setIdRequestFB(ketqua.getString("requestFB"));
				
				switch (ketqua.getInt("isInvite")) {
				case 0:
					challenge.setInvite(false);
					break;
				case 1:
					challenge.setInvite(true);
					break;
				}
				
				result.add(challenge);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static Challenge findByID(int idChallenge) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM challenge " +
					"WHERE id = ?");
			cauTruyVan.setInt(1, idChallenge);
			ResultSet ketqua = cauTruyVan.executeQuery();
			int id;
			String idUserSent;
			Date date;
			while (ketqua.next()) {
				id = ketqua.getInt("id");
				idUserSent = ketqua.getString("userSent");
				date = ketqua.getTimestamp("date");
				Challenge challenge = new Challenge(id, idUserSent, date);
				challenge.setIdUserRec(ketqua.getString("userRec"));
				challenge.setIdMusic(ketqua.getInt("idMusicLv"));
				challenge.setResult(ketqua.getInt("result"));
				challenge.setSenderScore(ketqua.getInt("senderScore"));
				challenge.setIdRequestFB(ketqua.getString("requestFB"));
				
				switch (ketqua.getInt("isInvite")) {
				case 0:
					challenge.setInvite(false);
					break;
				case 1:
					challenge.setInvite(true);
					break;
				}
				
				return challenge;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static void registerChallenge(Challenge challenge) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO challenge (userSent, userRec, date, result, idMusicLv, isInvite, senderScore, requestFB)" +
					"VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, challenge.getIdUserSent());
			preparedStatement.setString(2, challenge.getIdUserRec());
			preparedStatement.setString(3, GeneralUtil.formatDatetime(challenge.getDate()));
			preparedStatement.setInt(4	, challenge.getResult());
			preparedStatement.setInt(5	, challenge.getIdMusic());
			if(challenge.isInvite()){
				preparedStatement.setInt(6	, 1);
			} else {
				preparedStatement.setInt(6	, 0);
			}
			preparedStatement.setInt(7	, challenge.getSenderScore());
			preparedStatement.setString(8	, challenge.getIdRequestFB());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void updateChallengeInfo(Challenge challenge) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"UPDATE challenge SET " +
					"result = ? ," +
					"idMusicLv = ? ," +
					"isInvite = ? " +
					"WHERE id = ? ");
			
			preparedStatement.setInt(1	, challenge.getResult());
			preparedStatement.setInt(2	, challenge.getIdMusic());
			if(challenge.isInvite()){
				preparedStatement.setInt(3	, 1);
			} else {
				preparedStatement.setInt(3	, 0);
			}
			preparedStatement.setInt(4	, challenge.getId());
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		Vector<ChallengeSumRecord> temp = ChallengeCtrl.createResultHistoryFromChallenge("100005547128193", getAllChallengeForUser("100005547128193"));
		for (ChallengeSumRecord record : temp) {
			System.out.println(record.getIdOppement()+": "+record.getWin()+" - "+record.getLose());
		}
	}
}
