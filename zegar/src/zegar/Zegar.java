package zegar;

import java.time.ZoneId;

import processing.core.PApplet;

public class Zegar{

	private Cyferblat cyferblat;
	private Wskazowka godzinowa;
	private Wskazowka minutowa;
	private Wskazowka sekundowa;
	
	Zegar(float xO, float yO, float r, ZoneId strefa_czasowa){
		this.cyferblat = new Cyferblat(xO, yO, r);
		this.godzinowa = new Wskazowka("godz", cyferblat, strefa_czasowa);
		this.minutowa = new Wskazowka("min", cyferblat, strefa_czasowa);
		this.sekundowa = new Wskazowka("sek", cyferblat, strefa_czasowa);
	}
	
	/** Rysuje zegar */
	public void draw(PApplet p){
		p.pushStyle();
		//Rysuje cyferblat
		cyferblat.draw(p);
		
		//Rysuje wskazówki
		godzinowa.draw(p);
		minutowa.draw(p);
		sekundowa.draw(p);
				
		p.popStyle();
	}
	
	public Cyferblat getCyferblat(){
		return this.cyferblat;
	}
}
