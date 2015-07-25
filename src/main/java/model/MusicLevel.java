package model;

import java.util.TreeMap;

import util.GeneralUtil;

public class MusicLevel {
	private int id;
	private int level;
	private int idMusic;
	private String jsonData;
	private TreeMap<Long, String> data;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public TreeMap<Long, String> getData() {
		return data;
	}

	public void setData(TreeMap<Long, String> data) {
		this.data = data;
	}
	
	public int getIdMusic() {
		return idMusic;
	}

	public void setIdMusic(int idMusic) {
		this.idMusic = idMusic;
	}

	public MusicLevel() {
	}
	
	public MusicLevel(int id, int level, String jsonData) {
		super();
		this.id = id;
		this.level = level;
		this.jsonData = jsonData;
	}

	public static TreeMap<Long, String> gennerateOneString(int time){
		TreeMap<Long, String> result = new TreeMap<Long, String>();
		
		long ran = 0;
		for (int i = 0; i < time; i++) {
			ran += (Math.random() * 2000);
			if(Math.random() > 0.5) {
				if(Math.random() > 0.5) {
					result.put(ran, "a");
				} else {
					result.put(ran, "s");
				}
			} else {
				if(Math.random() > 0.5) {
					result.put(ran, "d");
				} else {
					result.put(ran, "f");
				}
			}
			
		}
		
		return result;
	}
	
	public void processJsonData(){
		this.data = GeneralUtil.processJsonData(this.jsonData);
	}
	
}
