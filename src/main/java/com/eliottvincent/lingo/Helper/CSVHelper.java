package com.eliottvincent.lingo.Helper;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.List;

/**
 * Created by eliottvct on 23/05/17.
 */
public class CSVHelper {

	/**
	 *
	 * @param path the path of the csv file
	 * @return
	 */
	public static List<String[]> readCSV(String path) {
		//Build reader instance
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(path), ',', '"', 1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Read all rows at once
		List<String[]> allRows = null;
		try {
			assert reader != null;
			allRows = reader.readAll();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Read CSV line by line and use the string array as you want
		assert allRows != null;
		return allRows;
	}

	/**
	 *
	 * @param path the path of the csv file
	 * @param record the record to be saved in the file
	 */
	public static void writeCSV(String path, String[] record) {
		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(path, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

		assert writer != null;
		writer.writeNext(record);

		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
