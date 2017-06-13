package zegar;

import processing.core.PApplet;
import processing.core.PConstants;

public class ClockDial implements Drawable{

	private float xOrigin;
	private float yOrigin;
	private float radius;
	
	private float[][] markersCoordinates = new float[12][2];
	
	ClockDial(float xOrigin, float yOrigin, float radius){
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.radius = radius;
		for (int hourNumber=1; hourNumber<=12; hourNumber++){
			markersCoordinates[hourNumber-1] = markerCoordinates(hourNumber);
		}
	}
	
	/** Liczy wspó³rzêdne znacznika godziny, na obrze¿u cyferblatu */
	private float[] markerCoordinates(int hourNumber){
		double angularCoordinate = Math.PI/2 -(2*Math.PI*hourNumber/12);
		float[] markerCoordinates = new float[2];
		markerCoordinates[0] = (float)(radius*Math.cos(angularCoordinate));
		markerCoordinates[1] = (float)(-radius*Math.sin(angularCoordinate));
		return markerCoordinates;
	}
	
	public void draw(PApplet p){
		p.pushStyle();
		p.fill(255);
		p.ellipse(xOrigin, yOrigin, radius*2, radius*2);
		drawMarkers(p);
		p.popStyle();
	}
	
	private void drawMarkers(PApplet p){
		p.stroke(0);
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		p.textSize(18);
		for (int hourNumber=1; hourNumber<=12; hourNumber++){
			p.line(0.8f*markersCoordinates[hourNumber-1][0]+xOrigin, 
					0.8f*markersCoordinates[hourNumber-1][1]+yOrigin, 
					markersCoordinates[hourNumber-1][0]+xOrigin, 
					markersCoordinates[hourNumber-1][1]+yOrigin);
			p.text(hourNumber, 
					1.1f*markersCoordinates[hourNumber-1][0]+xOrigin, 
					1.1f*markersCoordinates[hourNumber-1][1]+yOrigin);
		}
	}
	
	public float getXOrigin(){
		return this.xOrigin;
	}
	
	public float getYOrigin(){
		return this.yOrigin;
	}
	
	public float getRadius(){
		return this.radius;
	}
}
