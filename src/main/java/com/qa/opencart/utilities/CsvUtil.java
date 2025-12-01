package com.qa.opencart.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CsvUtil {
	private static final String  FILE_PATH = "./src/test/resources/testData/";
	private static CSVReader reader;
	private static List<String[]> rows;
	private static Object[][] csvData;
	
	
	public static Object[][] getCSVData(String fileName) {
		
		String completeFilePath = FILE_PATH+fileName+".csv";
		
		try {
			reader = new CSVReader(new FileReader(completeFilePath));
			rows = reader.readAll();
			reader.close();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvException e) {
			e.printStackTrace();
		}
		csvData = new Object[rows.size()][];
		
		for(int i =0; i <rows.size();i++) {
			csvData[i] = rows.get(i);
		}
		
		return csvData;
	}

}
