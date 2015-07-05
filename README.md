# coordinate-generator
This algorithm generates a set of coordinates for building smaller circles to cover a given larger circle.
It is typically used in Instagram or Weibo crawling, when the target crawling area is bigger than the one that the API supports.

Usage example:

	public static void main(String[] args) {
		//the center of the target circle
		double lat = 30.657938; //the center of the target circle
		double longi = 104.065590;
		
		double R = 11132*4; //the radius of the target circle area, in meters
		double r = 11132; //the radius of the small circle area, in meters
		
		//the parameter for coverage, 0<interval<=r, the smaller interval, the more small circles it will generate, but better coverage, vice versa
		double interval = r; //default = r
		
		//initialize the algorithm
		CoodinatesGenerator cg = new CoodinatesGenerator(lat, longi, R, r,
				interval);
		cg.process();
		
		//the result, a set of coordinates with radius, is delivered by a List<Coordinate>
		List<Coordinate> list = cg.getList();
		cg.iterateList(list); //just print the result
	}

Have fun!
