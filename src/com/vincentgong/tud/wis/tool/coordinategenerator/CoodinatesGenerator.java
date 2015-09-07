package com.vincentgong.tud.wis.tool.coordinategenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vincentgong
 */
public class CoodinatesGenerator {

	public static void main(String[] args) {
		double lat = 30.657938;
		double longi = 104.065590;
		double R = 11132 * 4;
		double r = 11132;
		double interval = r;
		CoodinatesGenerator cg = new CoodinatesGenerator(lat, longi, R, r,
				interval);
		cg.process();
		List<Coordinate> list = cg.getList();
		cg.iterateList(list);
	}

	private double lat;
	private double longi;
	private double R;
	private double r;
	private double interval;
	public static double LATITUDE_SECOND_PER_METER = 1 / 30.9;
	private static double EARTH_RADIUS = 6378.137 * 1000;
	private static final double PI = 3.14159265;
	private List<Coordinate> list;

	public CoodinatesGenerator(double lat, double longi, double R, double r,
			double interval) {
		this.lat = lat;
		this.longi = longi;
		this.R = R;
		this.r = r;
		this.interval = interval;
		this.list = new ArrayList<Coordinate>();
	}

	public void process() {
		
		if (this.R <= this.r) {// if the given R is shorter than r
			Coordinate coord = new Coordinate(this.lat, this.longi, this.R);
			this.list.add(coord);
			return;
		}

		CircleCalculator cc = new CircleCalculator(R, r, interval);
		List<Coord> basicCoordsList = cc.process();
		for (Coord c : basicCoordsList) {
			double x = c.x;
			double y = c.y;
			double rad = c.radius;

			// step1: Coordinate Y transformation & translation, from y to
			// latitude
			double absLat = transCoordinateY(y);

			// step2: Coordinate X transformation & translation, from x to
			// longitude
			double absLongi = transCoordinateX(x, absLat);

			// finale: adding to the list
			Coordinate coordinate = new Coordinate(absLat, absLongi, rad);
			this.list.add(coordinate);

			// System.out.println(absLat + "," + absLongi);
		}
	}

	private double transCoordinateX(double x, double lat) {
		double radLat = rad(lat);
		double LongitudeSecondPerMeter = (360 * 3600)
				/ (2 * CoodinatesGenerator.PI
						* CoodinatesGenerator.EARTH_RADIUS * Math.cos(radLat));
		double relativeLongitude = (x * LongitudeSecondPerMeter) / 3600;
		double absLongitude = relativeLongitude + this.longi;
		return absLongitude;
	}

	private double transCoordinateY(double y) {
		double relativeLat = (y * CoodinatesGenerator.LATITUDE_SECOND_PER_METER) / 3600;
		double absLat = relativeLat + lat;
		return absLat;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public void iterateList() {
		for (Coordinate c : list) {
			System.out.println(c.toString());
		}
	}

	public void iterateList(List<Coordinate> list) {
		for (Coordinate c : list) {
			System.out.println(c.toString());
		}
	}

	public List<Coordinate> getList() {
		return list;
	}
}
