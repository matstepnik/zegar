package zegar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import processing.core.PApplet;
import processing.core.PConstants;

public class Slonce {

	private Cyferblat cyferblat;
	private SunriseSunset ss;
	
	Slonce(PApplet p, Cyferblat cyferblat, double lat, double lon, String strefa_czasowa){
		this.ss = new SunriseSunset(p, lat, lon, strefa_czasowa);
		this.cyferblat = cyferblat;
	}
	
	public void draw(PApplet p){
		p.pushStyle();
		p.stroke(255, 0, 0);
		p.fill(255, 0, 0);
		p.textSize(14);
				
		float[] wsp1 = wsp(ss.getSunrise());	
		p.line((float)(0.85*wsp1[0]+cyferblat.getXO()), (float)(0.85*wsp1[1]+cyferblat.getYO()), wsp1[0]+cyferblat.getXO(), wsp1[1]+cyferblat.getYO());
		
		float[] wsp2 = wsp(ss.getSunset());	
		p.line((float)(0.85*wsp2[0]+cyferblat.getXO()), (float)(0.85*wsp2[1]+cyferblat.getYO()), wsp2[0]+cyferblat.getXO(), wsp2[1]+cyferblat.getYO());
		
		float[] wsp3 = wsp(ss.getSolarNoon());	
		p.line((float)(0.85*wsp3[0]+cyferblat.getXO()), (float)(0.85*wsp3[1]+cyferblat.getYO()), wsp3[0]+cyferblat.getXO(), wsp3[1]+cyferblat.getYO());

		
		//Rysuje tekst
		drawText(p, "wschód");
		drawText(p, "zachód");
		drawText(p, "po³udnie");
		
		//Data
		p.text(ss.getDate().toString(), cyferblat.getXO(), cyferblat.getYO()+0.1f*cyferblat.getR());
		
		p.popStyle();
	}
	
	private void drawText(PApplet p, String s){ //s={wschód, zachód, po³udnie}
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		ZonedDateTime czas = null;
		
		switch (s){
		case "wschód":
			czas = ss.getSunrise();
			break;
		case "zachód":
			czas = ss.getSunset();
			break;
		case "po³udnie":
			czas = ss.getSolarNoon();
			break;
		}
		
		float[] wsp = wsp(czas);
		float ang = 0; //k¹t obrotu
		int k = 1;
		
		if ((wsp[2]>=0 && wsp[2]<=Math.PI/2) || (wsp[2]>(3/2)*Math.PI && wsp[2]<2*Math.PI)){
			ang = -wsp[2];
		}
		if (wsp[2]>Math.PI/2 && wsp[2]<=3*Math.PI/2){
			ang = -wsp[2] - (float)Math.PI;
			k = -1;
		}
		
		p.pushMatrix();
		p.translate(this.cyferblat.getXO(), this.cyferblat.getYO());
		p.rotate(ang);
		p.text(s+"\n" + czas.format(format), k*0.6f*cyferblat.getR(), 0);
		p.popMatrix();
	}
	
	/** Zwraca wspó³rzêdne znacznika */
	private  float[] wsp(ZonedDateTime czas){
		float[] ret = new float[3];
				
		double hour = czas.getHour() + (double)czas.getMinute()/60;
		if (hour >= 12){ //hour=<0,12)
			hour = hour - 12;
		}
		
		ret[2] = (float)(Math.PI/2 - (2*Math.PI * hour/12)); //phi	
		if (ret[2] < 0){
			ret[2] = ret[2] + (float)(2*Math.PI);
		}
		ret[0] = (float)(cyferblat.getR()*Math.cos(ret[2])); //x
		ret[1] = (float)(-cyferblat.getR()*Math.sin(ret[2])); //y
		return ret;
		
	}
	
	public SunriseSunset getSunriseSunset(){
		return this.ss;
	}
}
