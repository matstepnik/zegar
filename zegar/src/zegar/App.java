package zegar;

import processing.core.PApplet;

public class App extends PApplet{
	
	private Zegar zWarszawa;
	private Slonce sWarszawa;
	private Zegar zNowyJork;
	private Slonce sNowyJork;
	private Zegar zJerozolima;
	private Slonce sJerozolima;

	public static void main(String[] args) {
		PApplet.main("zegar.App");
	}
	
	public void settings(){
		size(1200, 450);
	}
	
	public void setup() {	
		background(100);
		
		this.zWarszawa = new Zegar(200, 200, 150, "Europe/Warsaw");
		this.sWarszawa = new Slonce(this, this.zWarszawa.getCyferblat(), 53.2322, 21.0083, "Europe/Warsaw");
		this.zNowyJork = new Zegar(600, 200, 150, "America/New_York");
		this.sNowyJork = new Slonce(this, this.zNowyJork.getCyferblat(), 40.7166, -74.0000, "America/New_York");
		this.zJerozolima = new Zegar(1000, 200, 150, "Asia/Jerusalem");
		this.sJerozolima = new Slonce(this, this.zJerozolima.getCyferblat(), 31.7786, 35.2294, "Asia/Jerusalem");
		
		System.out.println(this.sWarszawa.getSunriseSunset().toString());
	}
	
	public void draw() {
		
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
		text("Warszawa", 200, 430);
		text("Nowy Jork", 600, 430);
		text("Jerozolima", 1000, 430);
		popStyle();
		
		
	}
	

}
