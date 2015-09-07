package com.vincentgong.tud.wis.tool.coordinategenerator.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.vincentgong.tud.wis.tool.coordinategenerator.CoodinatesGenerator;
import com.vincentgong.tud.wis.tool.coordinategenerator.Coordinate;
import com.vincentgong.tud.wis.tool.utils.MyLineReader;
import com.vincentgong.tud.wis.tool.utils.MyLineWriter;

public class BatchProcess {

	public static void main(String[] args) {
		String inputFileName = "/Users/vincentgong/Desktop/crawldata/coordinate/inputcoords.txt";
		String outputFileName = "/Users/vincentgong/Desktop/crawldata/coordinate/outputcoords.txt";
		
		List<Coordinate> outputCoordsList = new ArrayList<Coordinate>();
		try {
			double lat;
			double longi;
			double R;
			double r = 11132;
			double interval = r;
			
			MyLineReader mlr = new MyLineReader(inputFileName);
			while(mlr.hasNextLine()){
				String line = mlr.nextLine();
				lat = Double.valueOf(line.split(",")[0].trim());
				longi = Double.valueOf(line.split(",")[1].trim());
				R = Double.valueOf(line.split(",")[2].trim());
				CoodinatesGenerator cg = new CoodinatesGenerator(lat, longi, R, r,
						interval);
				cg.process();
				List<Coordinate> coordList = cg.getList();
				for(Coordinate c: coordList){
					outputCoordsList.add(c);
				}
			}
			mlr.close();
			
			Iterator<Coordinate> it = outputCoordsList.iterator();
			while(it.hasNext()){
				Coordinate c = it.next();
				MyLineWriter.getInstance().writeLine(outputFileName, c.toString());
				System.out.println(c.toString());
			}
			System.out.println("done!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
