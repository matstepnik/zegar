package zegar;

import processing.core.PApplet;
import processing.core.PConstants;

public class Cyferblat {

	//Wspó³rzêdne œrodka cyferblatu
	private float xO;
	private float yO;
	//Promieñ
	private float r;
	
	Cyferblat(float xO, float yO, float r){
		this.xO = xO;
		this.yO = yO;
		this.r = r;
	}
	
	/** Liczy wspó³rzêdne znacznika godziny, na obrze¿u cyferblatu */
	float[] wspZnacznika(int cyfra){
		float[] wsp = new float[2];
		
		double phi = Math.PI/2 -(2*Math.PI*cyfra/12);
		wsp[0] = (float)(r*Math.cos(phi));
		wsp[1] = (float)(-r*Math.sin(phi));
		return wsp;
	}
	
	/** Rysuje cyferblat */
	public void draw(PApplet p){
		p.pushStyle();
		p.fill(255);
		p.ellipse(xO, yO, r*2, r*2);
		
		p.stroke(0);
		p.textAlign(PConstants.CENTER, PConstants.CENTER);
		p.textSize(18);
		for (int i=1; i<=12; i++){
			float[] wsp = wspZnacznika(i);
			
			//rysuje znaczniki
			p.line((float)(0.8*wsp[0]+xO), (float)(0.8*wsp[1]+yO), wsp[0]+xO, wsp[1]+yO);
			
			//rysuje cyfry
			p.text(i, (float)(1.1*wsp[0]+xO), (float)(1.1*wsp[1]+yO));
		}
		p.popStyle();
	}
	
	public float getXO(){
		return this.xO;
	}
	
	public float getYO(){
		return this.yO;
	}
	
	public float getR(){
		return this.r;
	}
}
