package com.eliottvincent.lingo.Helper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;

/**
 * Created by eliottvct on 23/05/17.
 */
public class CSVHelper {

	public CSVHelper() {

	}


	public Iterable<CSVRecord> readCSV(String path) {

		Reader in = null;
		try {
			in = new FileReader(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Iterable<CSVRecord> records = null;
		try {
			records = CSVFormat.EXCEL.parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (CSVRecord record : records) {
			String lastName = record.get("Last Name");
			String firstName = record.get("First Name");
		}
		return records;
	}

}
