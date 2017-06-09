package zegar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import processing.core.PApplet;

public class Wskazowka {

	private Cyferblat cyferblat;
	private String rodzaj; //{godz, min, sek}
	private float l; //D³ugoœæ wskazówki
	private ZoneId strefa_czasowa; //strefa czasowa
	
	/** Rodzaj wskazówki: godz, min, sek */
	Wskazowka(String rodzaj, Cyferblat cyferblat, String strefa_czasowa){
		this.rodzaj = rodzaj;
		this.cyferblat = cyferblat;
		this.strefa_czasowa = ZoneId.of(strefa_czasowa);
		switch (this.rodzaj){
		case "godz":
			this.l = (float)0.4*cyferblat.getR();
			break;
		case "min":
			this.l = (float)0.75*cyferblat.getR();
			break;
		case "sek":
			this.l = (float)0.75*cyferblat.getR();
			break;
		}
	}
	
		
	/** Zwraca wspó³rzêdne grota wskazówki w uk³adzie zegara*/
	private float[] grot(){
		float[] ret = new float[2];
		
		
		float phi = this.liczPhi();
		ret[0] = (float)(this.l*Math.cos(phi)); //x
		ret[1] = (float)(-this.l*Math.sin(phi)); //y
		return ret;
		
	}
	
	/** Zwraca k¹t zawarty miêdzy godzin¹ 9:00 a wskazówk¹ zegara */
	private float liczPhi(){
		LocalTime time_local = LocalTime.now();
		LocalDate date = LocalDate.now();
		//TODO pobraæ strefê czasow¹ z komputera 
		ZonedDateTime time_zone = ZonedDateTime.of(date, time_local, ZoneId.of("Europe/Warsaw")); //przy za³o¿eniu, ¿e komputer jest w Polsce
		ZonedDateTime time = time_zone.withZoneSameInstant(this.strefa_czasowa);
		
		float phi = 0;
		switch (this.rodzaj){
		case "godz":
			double hour = time.getHour() + (double)time.getMinute()/60;
			if (hour > 12){
				hour = hour - 12;
			}
			phi = (float)(Math.PI/2 - (2*Math.PI * hour/12));
			break;
		case "min":
			phi = (float)(Math.PI/2 -(2*Math.PI*time.getMinute()/60));
			break;
		case "sek":
			phi = (float)(Math.PI/2 -(2*Math.PI*time.getSecond()/60));
			break;
		}
		return phi;
	}
	
	/** Rysuje wskazówkê */
	public void draw(PApplet p){
		p.pushStyle();
		p.stroke(0);
		
		float[] grot = grot();
		p.line(cyferblat.getXO(), cyferblat.getYO(), grot[0]+cyferblat.getXO(), grot[1]+cyferblat.getYO());
		p.popStyle();
	}
}
