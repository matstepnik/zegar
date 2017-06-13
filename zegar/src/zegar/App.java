package zegar;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.time.ZoneId;

import controlP5.*;

public class App extends PApplet{
	
	private Clock zWarszawa;
	private SolarDayMomentsClock sWarszawa;
	private Clock zNowyJork;
	private SolarDayMomentsClock sNowyJork;
	private Clock zJerozolima;
	private SolarDayMomentsClock sJerozolima;
	
	private SunriseSunset[] ssArr;
	
	private ControlP5 cp5;
	private DropdownList ddl1;
	private JSONArray cities;
	private String[] items;
	
	private SunriseSunset ss1;
	
	private String name1;

	public static void main(String[] args) {
		PApplet.main("zegar.App");
	}
	
	public void settings(){
		size(1200, 450);
	}
	
	public void setup() {		
		cp5 = new ControlP5(this);
		ddl1 = cp5.addDropdownList("WyborMiasta");
		getCityData();
		customizeDropdownList(ddl1);
		
		
		this.ssArr = new SunriseSunset[cities.size()];
		//create SunriseSunset[] for all cities
		for (int i=0; i<cities.size();i++){
			this.ssArr[i] = new SunriseSunset(this, 
					(Double)((JSONObject)this.cities.get(i)) .get("lat"),
					(Double)((JSONObject)this.cities.get(i)) .get("lon"),
					(String)((JSONObject)this.cities.get(i)) .get("zoneid"));
		}
		for (int i=0; i<this.ssArr.length;i++){
			System.out.println(this.ssArr[i].toString());
		}
				
		//initializing
		ss1 = this.ssArr[0];
		name1 = "Warszawa";
		
		
		this.zNowyJork = new Clock(600, 200, 150, ZoneId.of("America/New_York"));
		this.sNowyJork = new SolarDayMomentsClock(this, this.zNowyJork.getClockDial(), this.ssArr[2]);
		this.zJerozolima = new Clock(1000, 200, 150, ZoneId.of("Asia/Jerusalem"));
		this.sJerozolima = new SolarDayMomentsClock(this, this.zJerozolima.getClockDial(), this.ssArr[1]);
		
		//System.out.println(this.sWarszawa.getSunriseSunset().toString());
		
		
	}
	
	public void draw() {
		background(100);
		
		this.zWarszawa = new Clock(200, 200, 150, ss1.getZone());
		this.sWarszawa = new SolarDayMomentsClock(this, this.zWarszawa.getClockDial(), ss1);
		
		zWarszawa.draw(this);
		sWarszawa.draw(this);
		zNowyJork.draw(this);
		sNowyJork.draw(this);
		zJerozolima.draw(this);
		sJerozolima.draw(this);
		
		pushStyle();
		fill(255, 255, 0);
		textAlign(CENTER);
		textSize(36);
		text(name1, 200, 430);
		text("Nowy Jork", 600, 430);
		text("Jerozolima", 1000, 430);
		popStyle();
		
		
	}
	
	private void customizeDropdownList(DropdownList ddl){
		//ddl.setBackgroundColor(color(190));
		ddl.setPosition(100, 0);
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
			// not used in this sketch, but has to be included
		} else if(theEvent.isController()) {
			if (theEvent.getController().getName().equals("WyborMiasta")){
				//selectedImage = (int)(theEvent.getController().getValue());
				int index = (int) theEvent.getController().getValue();
				
				this.ss1 = this.ssArr[index];
				/*
				this.lat1 = (Double)((JSONObject)this.cities.get(index)) .get("lat");
				this.lon1 = (Double)((JSONObject)this.cities.get(index)) .get("lon");
				this.zone1 = (String)((JSONObject)this.cities.get(index)) .get("zoneid");
				*/
				this.name1 = (String)((JSONObject)this.cities.get(index)) .get("name");
				System.out.println(name1+" "+ss1.toString());
				
				
			}
		}
	}

}
