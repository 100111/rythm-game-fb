package model;

import java.util.Vector;

public class ChallengeSumRecord {
	private String idCurrentUser;
	private String idOppement;
	private int win;
	private int lose;
	private int status; //0: Binh thuong, 1: Dang cho tra loi, -1: Dang dc mời, chuẩn bị chơi
	
	public String getIdCurrentUser() {
		return idCurrentUser;
	}
	public void setIdCurrentUser(String idCurrentUser) {
		this.idCurrentUser = idCurrentUser;
	}
	public String getIdOppement() {
		return idOppement;
	}
	public void setIdOppement(String idOppement) {
		this.idOppement = idOppement;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	/**
	 * @param status 0: Binh thuong, 1: Dang cho tra loi, -1: Dang dc mời, chuẩn bị chơi
 	 */
	public int getStatus() {
		return status;
	}
	/**
	 * @param status 0: Binh thuong, 1: Dang cho tra loi, -1: Dang dc mời, chuẩn bị chơi
 	 */
	public void setStatus(int status) {
		this.status = status;
	}
	public ChallengeSumRecord() {
	}
	public ChallengeSumRecord(String idCurrentUser) {
		this.idCurrentUser = idCurrentUser;
	}
	public ChallengeSumRecord(String idCurrentUser, String idOppement) {
		this.idCurrentUser = idCurrentUser;
		this.idOppement = idOppement;
		this.lose = 0;
		this.win = 0;
		this.status = 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChallengeSumRecord other = (ChallengeSumRecord) obj;
		if (idCurrentUser == null) {
			if (other.idCurrentUser != null)
				return false;
		} else if (!idCurrentUser.equals(other.idCurrentUser))
			return false;
		if (idOppement == null) {
			if (other.idOppement != null)
				return false;
		} else if (!idOppement.equals(other.idOppement))
			return false;
		if (lose != other.lose)
			return false;
		if (status != other.status)
			return false;
		if (win != other.win)
			return false;
		return true;
	}
	public static ChallengeSumRecord findRecordIn(String idCurrentUser, String idOppement, Vector<ChallengeSumRecord> recordList) {
		for (ChallengeSumRecord record : recordList) {
			if(record.getIdCurrentUser().equals(idCurrentUser) && record.getIdOppement().equals(idOppement)){
				return record;
			}
		}
		
		return null;
	}
}
