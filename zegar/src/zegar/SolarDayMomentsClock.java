package zegar;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import processing.core.PApplet;
import processing.core.PConstants;

public class SolarDayMomentsClock {

	private ClockDial clockDial;
	private ZonedDateTime[] momentsTime = new ZonedDateTime[3];
	float angularCoordinate[] = new float[3];
	float markerCoordinates[][] = new float[3][2];
	String[] momentName = {"wschód", "zachód", "po³udnie"};
		
	SolarDayMomentsClock(PApplet p, ClockDial clockDial, SunriseSunset ss){
		this.momentsTime[0] = ss.getSunrise();
		this.momentsTime[1] = ss.getSunset();
		this.momentsTime[2] = ss.getSolarNoon();
		this.clockDial = clockDial;
		for (int i=0; i<momentsTime.length;i++){
			this.angularCoordinate[i] = angularCoordinate(this.momentsTime[i]);
			this.markerCoordinates[i] = markerCoordinates(this.angularCoordinate[i]);
		}
	}
		
	public void draw(PApplet p){
		p.pushStyle();
		p.stroke(255, 0, 0);
		p.fill(255, 0, 0);
		p.textSize(14);
		
		for (int i=0; i<momentsTime.length;i++){
			p.line(0.85f*markerCoordinates[i][0]+clockDial.getXOrigin(),
					0.85f*markerCoordinates[i][1]+clockDial.getYOrigin(),
					markerCoordinates[i][0]+clockDial.getXOrigin(),
					markerCoordinates[i][1]+clockDial.getYOrigin());
			drawText(p, momentName[i], momentsTime[i], angularCoordinate[i]);
		}
		
		drawDate(p);
		p.popStyle();
	}
	
	private void drawDate(PApplet p){
		p.text(momentsTime[2].toLocalDate().toString(),
				clockDial.getXOrigin(),
				clockDial.getYOrigin()+0.1f*clockDial.getRadius());
	}
	
	private void drawText(PApplet p, String momentName, ZonedDateTime momentTime, float angularCoordinate){
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		int k = kFactor(angularCoordinate);
		p.pushMatrix();		
		rotateText(p, angularCoordinate);
		p.text(momentName+"\n" + momentTime.format(format), k*0.6f*clockDial.getRadius(), 0);
		p.popMatrix();
	}
	
	private void rotateText(PApplet p, float angularCoordinate){
		float rotationAngle = rotationAngle(angularCoordinate);
		
		p.translate(clockDial.getXOrigin(), clockDial.getYOrigin());
		p.rotate(rotationAngle);
	}
	
	private float rotationAngle(float angularCoordinate){
		float rotationAngle = 0;
		if (isRightHalf(angularCoordinate)){ 
			rotationAngle = -angularCoordinate;
		}
		if (isLeftHalf(angularCoordinate)){
			rotationAngle = -angularCoordinate - (float)Math.PI;
		}
		return rotationAngle;
	}
	
	//wspó³czynnik k, zale¿ny od k¹ta obrotu(wsp. k¹towej), przerzuca tekst wzglêdem œrodka tarczy
	private int kFactor(float angularCoordinate){
		int k = 1;
		if (isRightHalf(angularCoordinate)){ 
			k = 1;
		}
		if (isLeftHalf(angularCoordinate)){
			k = -1;
		}
		return k;
	}
	
	private boolean isLeftHalf(float angularCoordinate){
		boolean left = false;
		if (angularCoordinate>Math.PI/2 && angularCoordinate<=3*Math.PI/2){
			left = true;
		}
		return left;
	}
	
	private boolean isRightHalf(float angularCoordinate){
		boolean right = false;
		if ((angularCoordinate>=0 && angularCoordinate<=Math.PI/2) 
				|| (angularCoordinate>(3/2)*Math.PI && angularCoordinate<2*Math.PI)){
			right = true;
		}
		return right;
	}
	
	private  float[] markerCoordinates(double angularCoordinate){
		float[] markerCoordinates = new float[2];
		markerCoordinates[0] = (float)(clockDial.getRadius()*Math.cos(angularCoordinate));
		markerCoordinates[1] = (float)(-clockDial.getRadius()*Math.sin(angularCoordinate));
		return markerCoordinates;
	}
	
	private float angularCoordinate(ZonedDateTime zonedTime){
		double hourWithMinutes = zonedTime.getHour() + (double)zonedTime.getMinute()/60;
		if (hourWithMinutes >= 12){ //hourWithMinutes=<0,12)
			hourWithMinutes = hourWithMinutes - 12;
		}
		float angularCoordinate = (float)(Math.PI/2 - (2*Math.PI * hourWithMinutes/12));
		if (angularCoordinate < 0){
			angularCoordinate = angularCoordinate + (float)(2*Math.PI);
		}
		return angularCoordinate;
	}
	
}
