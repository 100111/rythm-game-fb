package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.MusicLevel;

public class MusicLevelDAO {
	
	public static MusicLevel finByID(int idMusicLv) {
		Connection con = DBConnect.getConnection();
		
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM musiclevel " +
					"WHERE id = ? ");
			cauTruyVan.setInt(1, idMusicLv);
			ResultSet ketqua = cauTruyVan.executeQuery();
			
			int idMusicLevel;
			int lv;
			String data;
			while (ketqua.next()) {
				idMusicLevel = ketqua.getInt("id");
				lv = ketqua.getInt("idLevel");
				data = ketqua.getString("jsondata");
				MusicLevel reulst = new MusicLevel(idMusicLevel, lv, data);
				reulst.setIdMusic(ketqua.getInt("idMusic"));
				return reulst;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static MusicLevel getMusicLv(int idMusic, int idLevel) {
		MusicLevel reulst = null;
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * " +
					"FROM musiclevel " +
					"WHERE idMusic = ? and idLevel = ?");
			cauTruyVan.setInt(1, idMusic);
			cauTruyVan.setInt(2, idLevel);
			ResultSet ketqua = cauTruyVan.executeQuery();
			
			int idMusicLevel;
			int lv;
			String data;
			while (ketqua.next()) {
				idMusicLevel = ketqua.getInt("id");
				lv = ketqua.getInt("idLevel");
				data = ketqua.getString("jsondata");
				reulst = new MusicLevel(idMusicLevel, lv, data);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reulst;
	}
	
	public static void addMusicLevl(int idMusic, MusicLevel lv){
		Connection con = DBConnect.getConnection();
		try {
			PreparedStatement preparedStatement = (PreparedStatement)con.prepareStatement(
					"INSERT INTO  musiclevel (idMusic, idLevel , jsondata )" +
					" VALUES (?,  ?,  ? )" );
			preparedStatement.setInt(1, idMusic);
			preparedStatement.setInt(2, lv.getLevel());
			preparedStatement.setString(3, lv.getJsonData());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updateMusicLevl(int idMusic, MusicLevel lv){
		Connection con = DBConnect.getConnection();
		try {
			PreparedStatement preparedStatement = (PreparedStatement)con.prepareStatement(
					"UPDATE musiclevel SET" +
					" idMusic = ?, idLevel = ?, jsondata = ? " +
					" WHERE id = ?" );
			preparedStatement.setInt(1, idMusic);
			preparedStatement.setInt(2, lv.getLevel());
			preparedStatement.setString(3, lv.getJsonData());
			preparedStatement.setInt(4, lv.getId());
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
