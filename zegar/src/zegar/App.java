package zegar;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.time.ZoneId;

import controlP5.*;

public class App extends PApplet{
	
	private Clock z1;
	private Clock z2;
	private Clock z3;
	private SolarDayMomentsClock s1;
	private SolarDayMomentsClock s2;
	private SolarDayMomentsClock s3;
	
	
	private SunriseSunset[] ssArr;
	
	private ControlP5 cp5;
	private JSONArray cities;
	private String[] items;
	
	private DropdownList ddl1;
	private DropdownList ddl2;
	private DropdownList ddl3;
	
	private SunriseSunset ss1;
	private SunriseSunset ss2;
	private SunriseSunset ss3;
	
	private String name1;
	private String name2;
	private String name3;

	public static void main(String[] args) {
		PApplet.main("zegar.App");
	}
	
	public void settings(){
		size(1200, 450);
	}
	
	public void setup() {		
		cp5 = new ControlP5(this);
		ddl1 = cp5.addDropdownList("ddl1");
		ddl2 = cp5.addDropdownList("ddl2");
		ddl3 = cp5.addDropdownList("ddl3");
		getCityData();
		customizeDropdownList(ddl1, 100, 0);
		customizeDropdownList(ddl2, 500, 0);
		customizeDropdownList(ddl3, 900, 0);
		
		
		this.ssArr = new SunriseSunset[cities.size()];
		//create SunriseSunset[] for all cities
		for (int i=0; i<cities.size();i++){
			this.ssArr[i] = new SunriseSunset(this, 
					(Double)((JSONObject)this.cities.get(i)) .get("lat"),
					(Double)((JSONObject)this.cities.get(i)) .get("lon"),
					(String)((JSONObject)this.cities.get(i)) .get("zoneid"));
		}
		
				
		//initializing
		ss1 = this.ssArr[0];
		ss2 = this.ssArr[0];
		ss3 = this.ssArr[0];
		name1 = "Warszawa";
		name2 = "Warszawa";
		name3 = "Warszawa";
		
		/*
		this.zNowyJork = new Clock(600, 200, 150, ZoneId.of("America/New_York"));
		this.sNowyJork = new SolarDayMomentsClock(this, this.zNowyJork.getClockDial(), this.ssArr[2]);
		this.zJerozolima = new Clock(1000, 200, 150, ZoneId.of("Asia/Jerusalem"));
		this.sJerozolima = new SolarDayMomentsClock(this, this.zJerozolima.getClockDial(), this.ssArr[1]);
		*/
		//System.out.println(this.sWarszawa.getSunriseSunset().toString());
		
		
	}
	
	public void draw() {
		System.out.println("KB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
		background(100);
		
		this.z1 = new Clock(200, 200, 150, ss1.getZone());
		this.z2 = new Clock(600, 200, 150, ss2.getZone());
		this.z3 = new Clock(1000, 200, 150, ss3.getZone());
		this.s1 = new SolarDayMomentsClock(this, this.z1.getClockDial(), ss1);
		this.s2 = new SolarDayMomentsClock(this, this.z2.getClockDial(), ss2);
		this.s3 = new SolarDayMomentsClock(this, this.z3.getClockDial(), ss3);
		
		z1.draw(this);
		z2.draw(this);
		z3.draw(this);
		s1.draw(this);
		s2.draw(this);
		s3.draw(this);
		
		pushStyle();
		fill(255, 255, 0);
		textAlign(CENTER);
		textSize(36);
		text(name1, 200, 430);
		text(name2, 600, 430);
		text(name3, 1000, 430);
		popStyle();
		
		
	}
	
	private void customizeDropdownList(DropdownList ddl, float x, float y){
		//ddl.setBackgroundColor(color(190));
		ddl.setPosition(x, y);
		ddl.setItemHeight(30);
		ddl.setBarHeight(30);
		ddl.close();
		ddl.getCaptionLabel().set("Miasto");
		//ddl.getCaptionLabel().getStyle().marginTop = 3;
		//ddl.getCaptionLabel().getStyle().marginLeft = 3;
		//ddl.getValueLabel().getStyle().marginTop = 3;
		//ddl.addItem("Image One", 0);
		//ddl.addItem("Image Two", 1);
		ddl.addItems(items);
		//ddl.setColorBackground(color(60));
		//ddl.setColorActive(color(255,128));
	}
	
	private void getCityData(){
		String fileName = "miasta.json";
		this.cities = this.loadJSONArray(fileName);
		this.items = new String[this.cities.size()];
		for (int i=0; i<this.cities.size(); i++){
			String cityName = (String) ((JSONObject)cities.get(i)) .get("name");
			this.items[i] = cityName;
			System.out.println(cityName);
		}
	}
	
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isGroup()) {
			// not used in this sketch but has to be included
		} 
		else if(theEvent.isController()) {
			if (theEvent.getController().getName().equals("ddl1")){
				int index = (int) theEvent.getController().getValue();
				
				this.ss1 = this.ssArr[index];
				this.name1 = (String)((JSONObject)this.cities.get(index)) .get("name");
			}
			if (theEvent.getController().getName().equals("ddl2")){
				int index = (int) theEvent.getController().getValue();
				
				this.ss2 = this.ssArr[index];
				this.name2 = (String)((JSONObject)this.cities.get(index)) .get("name");
			}
			if (theEvent.getController().getName().equals("ddl3")){
				int index = (int) theEvent.getController().getValue();
				
				this.ss3 = this.ssArr[index];
				this.name3 = (String)((JSONObject)this.cities.get(index)) .get("name");
			}
		}
	}

}
