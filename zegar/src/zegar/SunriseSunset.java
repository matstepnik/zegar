package zegar;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import processing.core.PApplet;
import processing.data.*;

/** SunriseSunset
 * Dostarcza czas wschodu i zachodu s³oñca, po³udnia s³onecznego dla wybranej lokalizacji.
 * Dane pochodz¹ z https://sunrise-sunset.org/
 * @author Mateusz
 *
 */
public class SunriseSunset {
 
	private LocalDate date;
	private double lat;
	private double lon;
	private ZoneId time_zone;
	
	private ZonedDateTime sunrise;
	private ZonedDateTime sunset;
	private ZonedDateTime solar_noon;
	
	/**Inicjalizuje dla dzisiejszej daty dla dowolnej lokalizacji */
	SunriseSunset(PApplet p, double lat, double lon, String time_zone){
		this(p, LocalDate.now(), lat, lon, time_zone);
	}
	
	/** Inicjalizuje dla wybranej daty dla dowolnej lokalizacji */
	SunriseSunset(PApplet p, LocalDate date, double lat, double lon, String time_zone){
		this.lat = lat;
		this.lon = lon;
		this.date = date;
		this.time_zone = ZoneId.of(time_zone);
		
		this.sunrise = parseToZonalTime(p, "sunrise", this.time_zone);
		this.sunset = parseToZonalTime(p, "sunset", this.time_zone);
		this.solar_noon = parseToZonalTime(p, "solar_noon", this.time_zone);
	}
	
	/** Zwraca czas w odpowiedniej strefie czasowej dla field={sunrise, sunset, solar_nood} */
	private ZonedDateTime parseToZonalTime(PApplet p, String field, ZoneId time_zone){
		String fileName = "https://api.sunrise-sunset.org/json?lat="+this.lat+"&lng="+this.lon+"&date="+this.date+"&formatted=0";
		JSONObject sunrise_sunset = p.loadJSONObject(fileName);
		JSONObject results = sunrise_sunset.getJSONObject("results");
		ZonedDateTime zdt_utc = ZonedDateTime.parse(results.getString(field));
		ZonedDateTime zdt_zone = zdt_utc.withZoneSameInstant(time_zone);
		return zdt_zone;
	}
	
	public double getLattitude(){
		return this.lat;
	}
	
	public double getLongitude(){
		return this.lon;
	}
	
	public LocalDate getDate(){
		return this.date;
	}
	
	public ZonedDateTime getSunrise(){
		return this.sunrise;
	}
	
	public ZonedDateTime getSunset(){
		return this.sunset;
	}
	
	public ZonedDateTime getSolarNoon(){
		return this.solar_noon;
	}
	
	public ZoneId getZone(){
		return this.time_zone;
	}
	
	public String toString(){
		DateTimeFormatter format = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
		String result = this.date.toString() + "; "
				+ this.lat + ", " + this.lon + "; "
				+ "wschód: " + this.sunrise.toLocalTime().format(format).toString() + "; "
				+ "zachód: " + this.sunset.toLocalTime().format(format).toString() + "; "
				+ "po³udnie: " + this.solar_noon.toLocalTime().format(format).toString();
		return result;
	}
	
}
