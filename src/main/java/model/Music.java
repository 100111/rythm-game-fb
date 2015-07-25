package model;

import java.util.List;

public class Music {
	private int id;
	private String name;
	private String fileurl;
	private String imageurl;
	private List<MusicLevel> levels;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public List<MusicLevel> getLevels() {
		return levels;
	}
	public void setLevels(List<MusicLevel> levels) {
		this.levels = levels;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public Music() {
	}
	public Music(int id, String name, String fileurl) {
		this.id = id;
		this.name = name;
		this.fileurl = fileurl;
	} 
	

}
