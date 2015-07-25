package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import model.Music;

public class MusicDAO {
	
	public static Vector<Music> getAllMusic() {
		Vector<Music> musicList = new Vector<Music>();
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement( "SELECT * FROM music");
//			"SELECT music.id as musicID, musiclevel.id as musicLevelID, name, jsondata, fileurl" +
//			"FROM music, musiclevel where music.id = musiclevel.idmusic");
			ResultSet ketqua = cauTruyVan.executeQuery();
			int idMusic;
			String name;
			String url;
			String image;
			while (ketqua.next()) {
				idMusic = ketqua.getInt("id");
				name = ketqua.getString("name");
				url = ketqua.getString("fileurl");
				image = ketqua.getString("imageurl");
				Music music = new Music(idMusic, name, url);
				music.setImageurl(image);
				musicList.add(music);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return musicList;
	}
	
	public static Music getMusicByID(int id) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT *  FROM music WHERE id = "+id);
			ResultSet ketqua = cauTruyVan.executeQuery();
			String name;
			String url;
			while (ketqua.next()) {
				name = ketqua.getString("name");
				url = ketqua.getString("fileurl");
				Music music = new Music(id, name, url);
				return music;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String getMusicNameByID(int id) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT * FROM music WHERE id = "+id);
			ResultSet ketqua = cauTruyVan.executeQuery();
			while (ketqua.next()) {
				return ketqua.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public static String getMusicNameByMuiscLvID(int id) {
		Connection con = DBConnect.getConnection();
		
		try {
			PreparedStatement cauTruyVan = con.prepareStatement(
					"SELECT name FROM music, musiclevel WHERE musiclevel.id = ? AND musiclevel.id = music.id");
			cauTruyVan.setInt(1, id);
			ResultSet ketqua = cauTruyVan.executeQuery();
			while (ketqua.next()) {
				return ketqua.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	/*
	public ArrayList<Music> FindByName(String name){
		ArrayList<Music> listMusic = new ArrayList<Music>();
		Music Music = new Music();
		try {
			con = Connect.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement("SELECT*FROM Music WHERE TenMusic like(?)");
			//SELECT DeptNo, DeptName, Employee.Name
			//FROM Department, Employee
			//WHERE Department.Mgr = Employee.Empno
			preparedStatement.setString(1,"%" +name+"%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int MusicId = resultSet.getInt(("MusicId"));
				int nguoiDungId = resultSet.getInt("NguoiDungId");
				String tenMusic = resultSet.getString("TenMusic");
				int phi = resultSet.getInt("Phi");
				String loai = resultSet.getString("Loai");
				String link_Music = resultSet.getString("Link_Music");
				Music = new Music(MusicId, nguoiDungId, tenMusic, phi, loai, link_Music);
				listMusic.add(Music);
			}
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}


		return listMusic;
	}
	*/
	
	
	public void addMusic(Music Music){
		
//		con = Connect.getConnection();
//		try {
//			PreparedStatement preparedStatement = (PreparedStatement)con.prepareStatement("INSERT INTO Music VALUES(?,?,?,?,?)");
//					preparedStatement.setInt(1, Music.getNguoiDungId());
//					preparedStatement.setString(2, Music.getTenMusic());
//					preparedStatement.setInt(3, Music.getPhi());
//					preparedStatement.setString(4, Music.getLoai());
//					preparedStatement.setString(5, Music.getLink());
//					preparedStatement.executeUpdate();
//		} catch (Exception e) {
//		}
	}
	
	public String getFileurl(int idMusic){
		String link_Music = "";
		Connection con = DBConnect.getConnection();
		try {
			PreparedStatement preparedStatement = con.prepareStatement("SELECT*FROM Music WHERE id = (?)");
			preparedStatement.setInt(1, idMusic);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				link_Music = resultSet.getString("fileurl");
			}
			
			
		} catch (Exception e) {
		}
		return link_Music;
	}
}
