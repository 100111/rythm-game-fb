package model;

import java.util.Date;

public class Score {
	private String idUser;
	private int idMusicLevel;
	private int score;
	private Date date;
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public int getIdMusicLevel() {
		return idMusicLevel;
	}
	public void setIdMusicLevel(int idMusicLevel) {
		this.idMusicLevel = idMusicLevel;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Score() {
	}
	public Score(String idUser, int idMusicLevel, int score) {
		this.idUser = idUser;
		this.idMusicLevel = idMusicLevel;
		this.score = score;
	}
	
	
}
