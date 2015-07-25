package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import util.GeneralUtil;

import model.Score;

public class ScoreDAO {
	public static Vector<Score> getScoreList(String userID) {
		Connection con = DBConnect.getConnection();
		Vector<Score> result = new Vector<Score>();
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM score " +
					"WHERE idUser like '"+userID+"'");
			ResultSet ketqua = cauTruyVan.executeQuery();
			while (ketqua.next()) {
				Score score = new Score();
				score.setIdMusicLevel(ketqua.getInt("idMusicLevel"));
				score.setIdMusicLevel(ketqua.getInt("score"));
				score.setIdUser(userID);
				score.setDate(ketqua.getTimestamp("date"));
				result.add(score);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static void registerScore(Score score) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(
					"INSERT INTO score (idUser, idMusicLevel, score, date)" +
					"VALUES (?, ?, ?, ?)");
			preparedStatement.setString(1, score.getIdUser());
			preparedStatement.setInt(2, score.getIdMusicLevel());
			preparedStatement.setInt(3, score.getScore());
			preparedStatement.setString(4, GeneralUtil.formatDatetime(score.getDate()));
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
