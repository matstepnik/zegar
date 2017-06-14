package zegar;

import processing.core.PApplet;
import processing.data.JSONArray;
import java.util.ArrayList;
import java.util.Random;
import controlP5.*;

public class App extends PApplet{
	
	private int clockNumbers = 3;
	
	private ControlP5 cp5;
	private JSONArray cities;
	private String[] itemsForDdl;
	private SunriseSunset[] ssOfAll;
	
	private ArrayList<Drawable> drawable;
	private ArrayList<DropdownList> ddl;
	private ArrayList<SunriseSunset> ss;
	private ArrayList<String> cityName;
	
	public static void main(String[] args) {
		PApplet.main("zegar.App");
	}
	
	public void settings(){
		size(400*clockNumbers, 450);
	}
	
	public void setup() {		
		//czy te metody powinny zwracaæ wartoœæ?
		addCityData();
		addItemsForDdl();
		addSunriseSunsetData();
		
		addDropdownLists();			
		initialize();
	}
	
	public void draw() {
		memoryCheck();
		
		background(100);
		buildDrawable();
		
		for (int i=0; i<drawable.size(); i++){
			drawable.get(i).draw(this);
		}
				
		drawText();
	}
	
	private void addCityData(){
		String fileName = "miasta.json";
		this.cities = this.loadJSONArray(fileName);
	}
	
	private void addItemsForDdl(){
		this.itemsForDdl = new String[this.cities.size()];
		for (int i=0; i<this.cities.size(); i++){
			String cityName = (String) getCityProperty(i,"name");
			this.itemsForDdl[i] = cityName;
		}
	}
	
	private void addSunriseSunsetData(){
		this.ssOfAll = new SunriseSunset[cities.size()];
		for (int i=0; i<cities.size();i++){
			this.ssOfAll[i] = new SunriseSunset(this, 
					(Double)getCityProperty(i,"lat"),
					(Double)getCityProperty(i,"lon"),
					(String)getCityProperty(i,"zoneid"));
		}
	}
	
	private void addDropdownLists(){
		cp5 = new ControlP5(this);
		ddl = new ArrayList<>();
		for (int i=0; i<clockNumbers; i++){
			ddl.add(cp5.addDropdownList("ddl"+i));
			customizeDropdownList(ddl.get(i), 100+400*i, 0);
		}
	}
	
	private void initialize(){
		ss = new ArrayList<>();
		cityName = new ArrayList<>();
		Random random = new Random();
		for (int i=0; i<clockNumbers; i++){
			int j = random.nextInt(clockNumbers);
			ss.add(ssOfAll[j]);
			cityName.add(itemsForDdl[j]);
		}
	}
	
	private void buildDrawable(){
		drawable = new ArrayList<>();
		for (int i=0; i<clockNumbers; i++){
			drawable.add(new Clock(200+400*i, 200, 150, ss.get(i).getZone()));
		}
		for (int i=0; i<clockNumbers; i++){
			drawable.add(new SolarDayMomentsClock(this, ((Clock)drawable.get(i)).getClockDial(), ss.get(i)));
		}
	}
	
	private void drawText(){
		pushStyle();
		fill(255, 255, 0);
		textAlign(CENTER);
		textSize(36);
		for (int i=0; i<clockNumbers; i++){
			text(cityName.get(i), 200+400*i, 430);
		}
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
		ddl.addItems(itemsForDdl);
		//ddl.setColorBackground(color(60));
		//ddl.setColorActive(color(255,128));
	}
	
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isGroup()) {
			// not used in this sketch but has to be included
		} 
		else if(theEvent.isController()) {
			for (int i=0; i<clockNumbers; i++){
				if (theEvent.getController().getName().equals("ddl"+i)){
					int index = (int) theEvent.getController().getValue();
					ss.set(i, this.ssOfAll[index]);
					cityName.set(i, itemsForDdl[index]);
				}
			}
		}
	}
	
	//czy mo¿e nazwa tej metody zaczynaæ siê od "get"?
	private Object getCityProperty(int index, String nameOfProperty){
		return this.cities.getJSONObject(index).get(nameOfProperty);
	}

	private void memoryCheck(){
		System.out.println("KB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
		
	}
}
