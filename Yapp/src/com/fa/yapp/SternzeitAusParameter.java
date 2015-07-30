package com.fa.yapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SternzeitAusParameter {
	public SternzeitAusParameter(double längengrad, double breitengrad,
			int year, int mon, int day, int h, int min, int sec,
			double zeitzone, boolean sommerzeit) {

	}

	public int getSternzeit(double längengrad, double breitengrad, int year,
			int mon, int day, int h, int min, int sec, double zeitzone,
			boolean sommerzeit) {
		Calendar hilfeCalendar = new GregorianCalendar(); // erzeuge gregorian
															// objekt
		hilfeCalendar.set(year, mon - 1, day, h, min, sec); // Vorsicht Jan ist
															// 0
		System.out.println("hilfeCalendar: " + hilfeCalendar);
		long hilfeTime = hilfeCalendar.getTimeInMillis(); // umrechnung in
															// millisekunden
															// vergangen nach
															// 1.1.1970.
															// 00.00.00
															// .getTime().getTime();
															// funktioniert
															// nicht
		System.out.println("hilfeTime: " + hilfeTime);
		hilfeTime = hilfeTime / 1000; // umrechnung in sekunden
		System.out.println("hilfeTime durch 1000: " + hilfeTime);
		sekundenFormatierenHMinSec(hilfeTime);
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
		System.out.println("hilfeTime nach Schleife: " + hilfeTime);
		System.out.println("tag: " + tag);
		int tageNach1970 = ((int) hilfeTime / 86400) - tag;
		System.out.println("tageNach1970: " + tageNach1970);
		double jd = tageNach1970 + 2440587.5;
		System.out.println("jd: " + jd);
		int uhrzeit = (int) hilfeTime - (tageNach1970 * 86400);
		System.out.println("uhrzeit: " + uhrzeit);
		sekundenFormatierenHMinSec(uhrzeit);

		double t = (jd - 2451545.0) / 36525; // t ermitteln
		 System.out.println("t=" + t);
		double gmst = 24110.548 + (8640184.812866 * t) + (0.093104 * t * t)
				- (0.0000062 * t * t * t); // gmst in sec
		gmst = gmst + (uhrzeit * 1.00273790935); // Vergangene Zeit nach
													// Mitternacht hinzu
													// addieren mit
													// Sternzeitfaktor
		gmst = gmst + ((längengrad / 15)) * 3600;// Zeitverschiebung mit
													// einberechnen
		int sternzeit = ((int) gmst) % 86400;
		 sekundenFormatierenHMinSec((int) gmst / 1);

		return sternzeit;
	}

	static void sekundenFormatierenHMinSec(int zeit) {
		int h = zeit / 3600;
		int min = (zeit - h * 3600) / 60;
		int sec = zeit - (h * 3600) - (min * 60);
		System.out.println(zeit + " Sekunden entsprechen " + h % 24 + "h:"
				+ min + "min:" + sec + "sek");
		if (h >= 24) {
			System.out.println("nach Reduktion der Stunden");
		}
	}

	static void sekundenFormatierenHMinSec(long zeit) {
		long h = zeit / 3600;
		long min = (zeit - h * 3600) / 60;
		long sec = zeit - (h * 3600) - (min * 60);
		System.out.println(zeit + " Sekunden entsprechen " + h % 24 + "h:"
				+ min + "min:" + sec + "sek");
		if (h >= 24) {
			System.out.println("nach Reduktion der Stunden");
		}
	}
}
