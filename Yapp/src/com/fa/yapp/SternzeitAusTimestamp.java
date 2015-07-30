package com.fa.yapp;

public class SternzeitAusTimestamp {
	
	double[] params = new double[5]; //unsere parameter, bei initialisierung alle 0.0
	double timestamp;
	int timestampMitternacht;
	static double jd;
	static int uhrzeit;
	private int sternzeit;
	double zeitzone;
	String asc;
	double winkel;
	public boolean isSetLat;
	public boolean isSetLong;
	public boolean isSetDateTime;
	
	
	
	static double längengrad =  11.58333; //unnütz
	static double breitengrad = 48.13333; //unnütz
	static double schiefeDerEkliptik = 23.43864; 
    
	
	public SternzeitAusTimestamp() {

		setTimestamps();
		setJd();
		setAktuelleUhrzeitUmMitternacht();
		setSternzeitAusJd();
		setWinkelInAszendent();
		getAsc();

	}
	
	public SternzeitAusTimestamp(double längengrad, double breitengrad,
			long ts, double zeitzone, boolean sommerzeit) { // zweiter Konstruktor mit parameterübergabe
		setCustomTimestamp(längengrad, breitengrad, ts,  zeitzone,	sommerzeit);
		
		this.längengrad = längengrad;
		this.breitengrad = breitengrad;
		/*this.längengrad = 12.368;
		this.breitengrad = 47.850;*/
		this.zeitzone = zeitzone;
		
		setWinkelInAszendent();
		getAsc();

	}

	private void setJd() {
		jd = timestampMitternacht + 2440587.5;
	}
	
	private int getOffsetInSecs(boolean summertime, double offsetFromGmt){
		if (summertime){
			return (int) offsetFromGmt/1000 +3600;
		}
		
		return (int) offsetFromGmt/1000 ;
	}
	
	public void setCustomTimestamp(double längengrad, double breitengrad, long ts, double zeitzone,
			boolean sommerzeit) {
		
		long hilfeTime = ts; // umrechnung in
														// millisekunden
														// vergangen nach
														// 1.1.1970.
														// 00.00.00
														// .getTime().getTime();
														// funktioniert
														// nicht
		//System.out.println("hilfeTime: " + hilfeTime);
		hilfeTime = hilfeTime / 1000; // umrechnung in sekunden
		//System.out.println("hilfeTime durch 1000: " + hilfeTime);
		//sekundenFormatierenHMinSec(hilfeTime);
		//	double hilfeDouble = 3600 * zeitzone;
		//System.out.println("hilfeDouble: " + hilfeDouble);
		//long hilfeLong = (long) hilfeDouble;
		//System.out.println("hilfeLong: " + hilfeLong);
		//hilfeTime = hilfeTime - (hilfeLong); // unixzeit nach greenwich
		//	System.out.println("hilfeTime - hilfeLong: " + hilfeTime);
		//	if (sommerzeit == true) {
		//		hilfeTime = hilfeTime - 3600;
		//	}
		//	System.out.println("hilfeTime Sommerzeit: " + hilfeTime);
		int tag = 0;
		while (hilfeTime < 0) {
			tag = tag + 1;
			hilfeTime = hilfeTime + 86400;
			if (tag > 2440587) {
				System.exit(-1); // behindert vor jd zeitrechnung
			}
		}
		
		//System.out.println("hilfeTime nach Schleife: " + hilfeTime);
		//System.out.println("tag: " + tag);
		int tageNach1970 = ((int) hilfeTime / 86400) - tag; 
		//System.out.println("tageNach1970: " + tageNach1970);
		jd = tageNach1970 + 2440587.5;
		//System.out.println("jd: " + jd);
		//System.out.print("zeitzone  "+zeitzone+"       \n");
		uhrzeit = (int) hilfeTime - (tageNach1970 * 86400) + 0;//getOffsetInSecs(sommerzeit, zeitzone); //TODO Zeitzone & Sommertezit
		//System.out.println("uhrzeit: " + uhrzeit);
		//sekundenFormatierenHMinSec(uhrzeit);

		double t = (jd - 2451545.0) / 36525; // t ermitteln
		// System.out.println("t//das sind tageVor1970=" + t);
		double gmst = 24110.548 + (8640184.812866 * t) + (0.093104 * t * t)
				- (0.0000062 * t * t * t); // gmst in sec
		gmst = gmst + (uhrzeit * 1.00273790935); // Vergangene Zeit nach
													// Mitternacht hinzu
													// addieren mit
													// Sternzeitfaktor
		gmst = gmst + ((längengrad / 15)) * 3600;// Zeitverschiebung mit
													// einberechnen
		sternzeit = ((int) gmst) % 86400;
		// sekundenFormatierenHMinSec((int) gmst / 1);
		
		
		//return sternzeit;
		
		
	}
	
	private void setTimestamps() {
		timestamp = (System.currentTimeMillis() / 1000);
		// System.out.println("Timestamp=" + timestamp);
		timestampMitternacht = (int) timestamp / 86400;
	}

	private void setAktuelleUhrzeitUmMitternacht() {
		// System.out.println("Julianisches Datum=" + jd);
		uhrzeit = (int) timestamp - (timestampMitternacht * 86400);
		// sekundenFormatierenHMinSec(uhrzeit);
	}

	/*
	 * static void sternzeitInAszendent() { // +90 grad astronomisch noch nicht
	 * // geprüft // System.out.println(sternzeit); double winkel = ((double)
	 * sternzeit / 86400) * 360; // System.out.println(winkel); if (winkel >=
	 * 118.8 && winkel < 143.5) { System.out.println("Widder"); } if (winkel >=
	 * 143.5 && winkel < 180.2) { System.out.println("Stier"); } if (winkel >=
	 * 180.2 && winkel < 208.1) { System.out.println("Zwillinge"); } if (winkel
	 * >= 208.1 && winkel < 228.2) { System.out.println("Krebs"); } if (winkel
	 * >= 228.2 && winkel < 263.9) { System.out.println("Löwe"); } if (winkel
	 * >= 263.9 && winkel < 308.0) { System.out.println("Jungfrau"); } if
	 * (winkel >= 308.0 && winkel < 331.0) { System.out.println("Waage"); } if
	 * (winkel >= 331.0 && winkel < 337.7) { System.out.println("Skorpion"); }
	 * if (winkel >= 337.7 && winkel < 356.3) {
	 * System.out.println("Schlangenträger"); } if (winkel >= 346.3 || winkel <
	 * 29.7) { System.out.println("Schütze"); } if (winkel >= 29.7 && winkel <
	 * 57.6) { System.out.println("Steinock"); } if (winkel >= 57.6 && winkel <
	 * 81.6) { System.out.println("Wassermann"); } if (winkel >= 81.6 && winkel
	 * < 118.8) { System.out.println("Fische"); } }
	 * 
	 * /* static void neusternzeitInAszendent() { // astrologisch
	 * 
	 * // System.out.println(sternzeit); double sternzeitwinkel = ((double)
	 * sternzeit / 86400) * 360; System.out.println(sternzeitwinkel); double
	 * zähler=(-Math.cos(Math.toRadians(sternzeitwinkel))); double
	 * nenner=(Math.sin
	 * (Math.toRadians(sternzeitwinkel))*Math.cos(Math.toRadians(
	 * schiefeDerEkliptik
	 * )))+(Math.tan(Math.toRadians(breitengrad))*Math.sin(Math
	 * .toRadians(schiefeDerEkliptik))); double
	 * winkel=Math.atan(zähler/nenner); winkel=Math.toDegrees( winkel );
	 * System.out.println("winkel="+winkel);
	 * 
	 * if (winkel >= 0.0 && winkel < 30.0) { System.out.println("Krebs"); } if
	 * (winkel >= 30.0 && winkel < 60.0) { System.out.println("Löwe"); } if
	 * (winkel >= 60.0 && winkel < 90.0) { System.out.println("Jungfrau"); } if
	 * (winkel >= 90.0 && winkel < 120.0) { System.out.println("Waage"); } if
	 * (winkel >= 120.0 && winkel < 150.0) { System.out.println("Skorpion"); }
	 * if (winkel >= 150.0 && winkel < 180.0) { System.out.println("Schütze");
	 * } if (winkel >= 180.0 && winkel < 210.0) {
	 * System.out.println("Steinock"); } if (winkel >= 210.0 && winkel < 240.0)
	 * { System.out.println("Wassermann"); } if (winkel >= 240.0 && winkel <
	 * 270.0) { System.out.println("Fische"); } if (winkel >= 270.0 && winkel <
	 * 300.0) { System.out.println("Widder"); } if (winkel >= 300.0 && winkel <
	 * 330.0) { System.out.println("Stier"); } if (winkel >= 330.0 && winkel <
	 * 360.0) { System.out.println("Zwillinge"); } }
	 */// Alles Müll

	private void setSternzeitAusJd() {
		double t = (jd - 2451545.0) / 36525; // t ermitteln
		// System.out.println("t=" + t);
		double gmst = 24110.548 + (8640184.812866 * t) + (0.093104 * t * t)
				- (0.0000062 * t * t * t); // gmst in sec
		gmst = gmst + (uhrzeit * 1.00273790935); // Vergangene Zeit nach
													// Mitternacht hinzu
													// addieren mit
													// Sternzeitfaktor
		gmst = gmst + ((längengrad / 15)) * 3600;// Zeitverschiebung mit
													// einberechnen
		sternzeit = ((int) gmst) % 86400;
		// sekundenFormatierenHMinSec((int) gmst / 1);

	}

	/*
	 * static void test(){ double
	 * zähler=Math.cos(Math.toRadians(261.1276))*(-1); double
	 * nenner=Math.sin(Math
	 * .toRadians(261.1276))*Math.cos(Math.toRadians(23.43864
	 * ))+(Math.tan(Math.toRadians(48))*Math.sin(Math.toRadians(23.43864)));
	 * double tanLamda=zähler/nenner; System.out.println("tanLamda="+tanLamda);
	 * double lamda=Math.atan(tanLamda); lamda=lamda+Math.PI;
	 * lamda=Math.toDegrees(lamda); System.out.println("lamda="+lamda); double
	 * a=Math.PI/2; //System.out.println("sin("+a+")="+Math.sin(a));
	 * //System.out.println(Math.toRadians(90)); }
	 */

	private void setWinkelInAszendent() { // astrologisch

		// System.out.println(sternzeit);
		double sternzeitwinkel = ((double) sternzeit / 86400) * 360;
		// System.out.println(sternzeitwinkel);
		double zähler = (Math.cos(Math.toRadians(sternzeitwinkel)) * (-1));
		double nenner = (Math.sin(Math.toRadians(sternzeitwinkel)) * Math
				.cos(Math.toRadians(schiefeDerEkliptik)))
				+ (Math.tan(Math.toRadians(breitengrad)) * Math.sin(Math
						.toRadians(schiefeDerEkliptik)));
		double tanLamda = zähler / nenner;
		winkel = Math.atan(tanLamda);

		if (winkel < 0) {
			winkel = winkel + Math.PI;
		}

		if (sternzeitwinkel < 90 || sternzeitwinkel >= 270) {
			winkel = Math.toDegrees(winkel);
			if (winkel >= 360) {
				winkel = winkel - 360;
			}
		}

		if (sternzeitwinkel >= 90 && sternzeitwinkel < 270) {
			winkel = Math.toDegrees(winkel);
			winkel = winkel + 180;
			if (winkel >= 360) {
				winkel = winkel - 360;
			}
		}
		// System.out.println("winkel=" + winkel);
		// return winkel;

	}

	public String getAsc() {
		StringBuffer asc = new StringBuffer("");
		if (winkel >= 0.0 && winkel < 30.0) {
			// System.out.println("Widder");
			asc.append("Widder");
		}
		if (winkel >= 30.0 && winkel < 60.0) {
			// System.out.println("Stier");
			asc.append("Stier");
		}
		if (winkel >= 60.0 && winkel < 90.0) {
			// System.out.println("Zwillinge");
			asc.append("Zwillinge");
		}
		if (winkel >= 90.0 && winkel < 120.0) {
			// System.out.println("Krebs");
			asc.append("Krebs");
		}
		if (winkel >= 120.0 && winkel < 150.0) {
			// System.out.println("Löwe");
			asc.append("Löwe");
		}
		if (winkel >= 150.0 && winkel < 180.0) {
			// System.out.println("Jungfrau");
			asc.append("Jungfrau");
		}
		if (winkel >= 180.0 && winkel < 210.0) {
			// System.out.println("Waage");
			asc.append("Waage");

		}
		if (winkel >= 210.0 && winkel < 240.0) {
			// System.out.println("Skorpion");
			asc.append("Skorpion");
		}
		if (winkel >= 240.0 && winkel < 270.0) {
			// System.out.println("Schütze");
			asc.append("Schütze");
		}
		if (winkel >= 270.0 && winkel < 300.0) {
			// System.out.println("Steinbock");
			asc.append("Steinbock");
		}
		if (winkel >= 300.0 && winkel < 330.0) {
			// System.out.println("Wassermann");
			asc.append("Wassermann");
		}
		if (winkel >= 330.0 && winkel < 360.0) {
			// System.out.println("Fische");
			asc.append("Fische");
		}
		
		
		return asc.toString();

	}
	
	public String getAscAstronomisch() {
		StringBuffer asc = new StringBuffer("");
		if (winkel >= 28.8 && winkel < 53.5) {
			// System.out.println("Widder");
			asc.append("Widder");
			return asc.toString();
		}
		if (winkel >= 53.5 && winkel < 90.2) {
			// System.out.println("Stier");
			asc.append("Stier");
			return asc.toString();
		}
		if (winkel >= 90.2 && winkel < 118.1) {
			// System.out.println("Zwillinge");
			asc.append("Zwillinge");
			return asc.toString();
		}
		if (winkel >= 118.1 && winkel < 138.2) {
			// System.out.println("Krebs");
			asc.append("Krebs");
			return asc.toString();
		}
		if (winkel >= 138.2 && winkel < 173.9) {
			// System.out.println("Löwe");
			asc.append("Löwe");
			return asc.toString();
		}
		if (winkel >= 173.9 && winkel < 218.0) {
			// System.out.println("Jungfrau");
			asc.append("Jungfrau");
			return asc.toString();
		}
		if (winkel >= 218.0 && winkel < 241.0) {
			// System.out.println("Waage");
			asc.append("Waage");
			return asc.toString();
		}
		if (winkel >= 241.0 && winkel < 247.7) {
			// System.out.println("Skorpion");
			asc.append("Skorpion");
			return asc.toString();
		}
		if (winkel >= 247.7 && winkel < 266.3) {
			// System.out.println("Schlangentr�ger");
			asc.append("Schlangenträger");
			return asc.toString();
		}
		if (winkel >= 266.3 && winkel < 299.7) {
			// System.out.println("Schütze");
			asc.append("Schütze");
			return asc.toString();
		}
		if (winkel >= 299.7 && winkel < 327.6) {
			// System.out.println("Steinbock");
			asc.append("Steinbock");
			return asc.toString();
		}
		if (winkel >= 327.6 && winkel < 351.6) {
			// System.out.println("Wassermann");
			asc.append("Wassermann");
			return asc.toString();
		}
		if (winkel >= 351.6 || winkel < 28.8) {
			// System.out.println("Fische");
			asc.append("Fische");
			return asc.toString();
			
		}

		return asc.toString();

	}

	// GETTER

	public int getSternzeit() {
		return sternzeit;
	}

	/*
	 * public int getZeitStunde(int zeit) { int h=zeit/3600; h=h%24; return h; }
	 * 
	 * public int getZeitMinute(int zeit) { int h = zeit / 3600; int min = (zeit
	 * - h * 3600) / 60; return min; }
	 * 
	 * public int getZeitSekunde(int zeit) { int h = zeit / 3600; int min =
	 * (zeit - h * 3600) / 60; int sec = zeit - (h * 3600) - (min * 60); return
	 * sec; }
	 */

	public String formatSekunden(int sekunden) {
		int h = sekunden / 3600;
		int min = (sekunden - h * 3600) / 60;
		int sec = sekunden - (h * 3600) - (min * 60);
		String zeit = new String("");
		if (h < 10) {
			zeit = "0";
		}
		zeit = zeit + String.valueOf(h) + ":";
		if (min < 10) {
			zeit = zeit + "0";
		}
		zeit = zeit + String.valueOf(min) + ":";
		if (sec < 10) {
			zeit = zeit + "0";
		}
		zeit = zeit + String.valueOf(sec);
		return zeit;
	}

	public double getTs() {
		return timestamp;
	}

	public int getUhrzeit() {
		return uhrzeit;
	}

	public double getJD() {
		return jd;
	}

	public double getLängengrad() {
		return längengrad;
	}

	public double getBreitengrad() {
		return breitengrad;
	}
	
	public double getWinkel() {
		return winkel;
	}

	// DEBUG über Konsole

	void sekundenFormatierenHMinSec(int zeit) {	  
		int h = zeit / 3600;
		int min = (zeit - h * 3600) / 60;
		int sec = zeit - (h * 3600) - (min * 60);
		System.out.println(zeit + " Sekunden entsprechen " + h % 24 + "h:"
				+ min + "min:" + sec + "sek");
		if (h >= 24) {
			System.out.println("nach Reduktion der Stunden");
		}
	}

	static void aktuelleJd() {
		double timestamp = System.currentTimeMillis() / 1000;
		System.out.println("Timestamp=" + timestamp);
		double jetzt = timestamp / 86400 + 2440587.5;
		System.out.println("jetzt=" + jetzt); // Stimmt alles

	}
}
