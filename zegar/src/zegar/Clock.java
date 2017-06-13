package zegar;

import java.time.ZoneId;

import processing.core.PApplet;

public class Clock{

	private ClockDial ClockDial;
	private ClockHand hourHand;
	private ClockHand minuteHand;
	private ClockHand secondHand;
	
	Clock(float xOrigin, float yOrigin, float radius, ZoneId time_zone){
		this.ClockDial = new ClockDial(xOrigin, yOrigin, radius);
		this.hourHand = new ClockHand("hour", ClockDial, time_zone);
		this.minuteHand = new ClockHand("min", ClockDial, time_zone);
		this.secondHand = new ClockHand("sec", ClockDial, time_zone);
	}
	
	public void draw(PApplet p){
		p.pushStyle();
		ClockDial.draw(p);
		
		hourHand.draw(p);
		minuteHand.draw(p);
		secondHand.draw(p);
				
		p.popStyle();
	}
	
	public ClockDial getClockDial(){
		return this.ClockDial;
	}
}
