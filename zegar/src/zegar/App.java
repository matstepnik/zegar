package zegar;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;
import controlP5.*;

public class App extends PApplet{
	
	private Zegar z1;
	private Slonce s1;
	private Zegar zNowyJork;
	private Slonce sNowyJork;
	private Zegar zJerozolima;
	private Slonce sJerozolima;
	
	private ControlP5 cp5;
	private DropdownList ddl1;
	private JSONArray cities;
	private String[] items;
	
	private double lon1;
	private double lat1;
	private String zone1;
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
		
		//initializing
		zone1 = "America/New_York";
		lon1 = 40.7166;
		lat1 = -74.0000;
		name1 = "Nowy Jork";
						
		this.z1 = new Zegar(200, 200, 150, zone1);
		this.s1 = new Slonce(this, this.z1.getCyferblat(), lon1, lat1, zone1);
		this.zNowyJork = new Zegar(600, 200, 150, "America/New_York");
		this.sNowyJork = new Slonce(this, this.zNowyJork.getCyferblat(), 40.7166, -74.0000, "America/New_York");
		this.zJerozolima = new Zegar(1000, 200, 150, "Asia/Jerusalem");
		this.sJerozolima = new Slonce(this, this.zJerozolima.getCyferblat(), 31.7786, 35.2294, "Asia/Jerusalem");
		
		//System.out.println(this.s1.getSunriseSunset().toString());
		
		
	}
	
	public void draw() {
		background(100);
		
		
		
		z1.draw(this);
		s1.draw(this);
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
			//System.out.println(cityName);
		}
	}
	
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isGroup()) {
			// not used in this sketch, but has to be included
		} else if(theEvent.isController()) {
			if (theEvent.getController().getName().equals("WyborMiasta")){
				//selectedImage = (int)(theEvent.getController().getValue());
				int index = (int) theEvent.getController().getValue();
				
				this.lat1 = (Double)((JSONObject)this.cities.get(index)) .get("lat");
				this.lon1 = (Double)((JSONObject)this.cities.get(index)) .get("lon");
				this.zone1 = (String)((JSONObject)this.cities.get(index)) .get("zoneid");
				this.name1 = (String)((JSONObject)this.cities.get(index)) .get("name");
				System.out.println(name1+" "+lat1+" "+lon1+" "+zone1);
				
			}
		}
	}

}
