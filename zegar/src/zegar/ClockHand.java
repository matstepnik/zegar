 package zegar;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import processing.core.PApplet;

public class ClockHand implements Drawable{

	private ClockDial clockDial;
	//TODO wprowadziæ Enum
	private String type; //{hour, min, sec}
	private float length; 
	private ZoneId time_zone;
	
	ClockHand(String type, ClockDial clockDial, ZoneId time_zone){
		this.type = type;
		this.clockDial = clockDial;
		this.time_zone = time_zone;
		this.length = typeLength(this.type);
	}
	
	private float typeLength(String type){
		float length = 0;
		switch (type){
		case "hour":
			length = 0.4f*clockDial.getRadius();
			break;
		case "min":
			length = 0.75f*clockDial.getRadius();
			break;
		case "sec":
			length = 0.75f*clockDial.getRadius();
			break;
		}
		return length;
	}
	
	/** Zwraca wspó³rzêdne grota wskazówki w uk³adzie zegara*/
	private float[] topOfHandCoordinates(){
		float[] topOfHandCoordinates = new float[2];
		
		float angularCoordinate = this.angularCoordinate();
		topOfHandCoordinates[0] = (float)(this.length*Math.cos(angularCoordinate));
		topOfHandCoordinates[1] = (float)(-this.length*Math.sin(angularCoordinate));
		return topOfHandCoordinates;
	}
	
	/** Zwraca k¹t zawarty miêdzy godzin¹ 9:00 a wskazówk¹ zegara */
	private float angularCoordinate(){
		float angularCoordinate = 0;
		ZonedDateTime zonedTime = zonedTime();
		switch (this.type){
		case "hour":
			double hourWithMinutes = zonedTime.getHour() + (double)zonedTime.getMinute()/60;
			if (hourWithMinutes > 12){
				hourWithMinutes = hourWithMinutes - 12;
			}
			angularCoordinate = (float)(Math.PI/2 - (2*Math.PI * hourWithMinutes/12));
			break;
		case "min":
			angularCoordinate = (float)(Math.PI/2 -(2*Math.PI*zonedTime.getMinute()/60));
			break;
		case "sec":
			angularCoordinate = (float)(Math.PI/2 -(2*Math.PI*zonedTime.getSecond()/60));
			break;
		}
		return angularCoordinate;
	}
	
	private ZonedDateTime zonedTime(){
		ZonedDateTime zonedTimeOfComp = ZonedDateTime.now();
		ZonedDateTime zonedTime = zonedTimeOfComp.withZoneSameInstant(this.time_zone);
		return zonedTime;
	}
	
	public void draw(PApplet p){
		p.pushStyle();
		p.stroke(0);
		float[] topOfHandCoordinates = topOfHandCoordinates();
		p.line(clockDial.getXOrigin(), 
				clockDial.getYOrigin(), 
				topOfHandCoordinates[0]+clockDial.getXOrigin(), 
				topOfHandCoordinates[1]+clockDial.getYOrigin());
		p.popStyle();
	}
}
