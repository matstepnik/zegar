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
 
	private LocalDate date; //2017-06-06
	private double lat; //53.2322
	private double lon; //21.0083
	private ZoneId strefa_czasowa; //"Europe/Warsaw"
	
	private ZonedDateTime sunrise;	//"2017-06-06T02:10:47+00:00"
	private ZonedDateTime sunset;	//"2017-06-06T18:58:46+00:00"
	private ZonedDateTime solar_noon;	//"2017-06-06T10:34:46+00:00"
	/*
	private Duration day_length;	//60479
	private ZonedDateTime civil_twilight_begin;	//"2017-06-06T01:20:17+00:00"
	private ZonedDateTime civil_twilight_end;	//"2017-06-06T19:49:15+00:00"
	private ZonedDateTime nautical_twilight_begin;	//"2017-06-05T23:56:08+00:00"
	private ZonedDateTime nautical_twilight_end;	//"2017-06-06T21:13:24+00:00"
	private ZonedDateTime astronomical_twilight_begin;	//"1970-01-01T00:00:01+00:00"
	private ZonedDateTime astronomical_twilight_end;	//"1970-01-01T00:00:01+00:00"
	*/
	
	
	/** Inicjalizuje dla dzisiejszej daty dla Warszawy */
	SunriseSunset(PApplet p){
		this(p, LocalDate.now());
	}
	
	/** Inicjalizuje dla wybranej daty dla Warszawy */
	SunriseSunset(PApplet p, LocalDate date){
		this(p, date, 53.2322, 21.0083, "Europe/Warsaw");
	}
	
	/**Inicjalizuje dla dzisiejszej daty dla dowolnej lokalizacji */
	SunriseSunset(PApplet p, double lat, double lon, String strefa_czasowa){
		this(p, LocalDate.now(), lat, lon, strefa_czasowa);
	}
	
	/** Inicjalizuje dla wybranej daty dla dowolnej lokalizacji */
	SunriseSunset(PApplet p, LocalDate date, double lat, double lon, String strefa_czasowa){
		this.lat = lat;
		this.lon = lon;
		this.date = date;
		this.strefa_czasowa = ZoneId.of(strefa_czasowa);
		
		this.sunrise = parseToZonalTime(p, "sunrise", this.strefa_czasowa);
		this.sunset = parseToZonalTime(p, "sunset", this.strefa_czasowa);
		this.solar_noon = parseToZonalTime(p, "solar_noon", this.strefa_czasowa);
	}
	
	/** Zwraca czas w odpowiedniej strefie czasowej dla field={sunrise, sunset, solar_nood} */
	private ZonedDateTime parseToZonalTime(PApplet p, String field, ZoneId strefa_czasowa){
		String fileName = "https://api.sunrise-sunset.org/json?lat="+this.lat+"&lng="+this.lon+"&date="+this.date+"&formatted=0";
		JSONObject sunrise_sunset = p.loadJSONObject(fileName);
		JSONObject results = sunrise_sunset.getJSONObject("results");
		ZonedDateTime zdt_utc = ZonedDateTime.parse(results.getString(field));
		//ZoneId warsaw = ZoneId.of("Europe/Warsaw");
		ZonedDateTime zdt_zone = zdt_utc.withZoneSameInstant(strefa_czasowa);
		//LocalTime lt_warsaw = zdt_warsaw.toLocalTime();
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
		return this.strefa_czasowa;
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
