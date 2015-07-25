package model;

import java.util.Date;

public class Challenge {
	private int id;
	private String idUserSent;
	private String idUserRec;
	private Date date;
	private int result;
	private int idMusic;
	private boolean isInvite;
	private int senderScore;
	private String idRequestFB;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdUserSent() {
		return idUserSent;
	}
	public void setIdUserSent(String idUserSent) {
		this.idUserSent = idUserSent;
	}
	public String getIdUserRec() {
		return idUserRec;
	}
	public void setIdUserRec(String idUserRec) {
		this.idUserRec = idUserRec;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getIdMusic() {
		return idMusic;
	}
	public void setIdMusic(int idMusic) {
		this.idMusic = idMusic;
	}
	public boolean isInvite() {
		return isInvite;
	}
	public void setInvite(boolean isInvite) {
		this.isInvite = isInvite;
	}
	public int getSenderScore() {
		return senderScore;
	}
	public void setSenderScore(int senderScore) {
		this.senderScore = senderScore;
	}
	public String getIdRequestFB() {
		return idRequestFB;
	}
	public void setIdRequestFB(String idRequestFB) {
		this.idRequestFB = idRequestFB;
	}
	public Challenge() {
	}
	public Challenge(int id, String idUserSent, Date date) {
		this.id = id;
		this.idUserSent = idUserSent;
		this.date = date;
		this.isInvite = false;
		
	}
}
