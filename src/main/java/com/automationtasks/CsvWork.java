package com.automationtasks;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvWork {
    private final static String SCV_EXTENSION = ".csv";

    public static String getScvExtension() {
        return SCV_EXTENSION;
    }

    public static List<String> getListOfDates() throws IOException {
        Scanner sc;
        List<String> listOfDates;
        String fileName;
        Reader reader;
        CSVParser csvParser;

        listOfDates = new ArrayList<>();
        sc = new Scanner(System.in);
        System.out.print("Introduce file name (without extension) were dates are stored\n-> ");
        fileName = sc.next();

        reader = Files.newBufferedReader(Paths.get(fileName + SCV_EXTENSION));
        csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        for (CSVRecord date : csvParser) {
            listOfDates.add(date.get(0));
        }

        return listOfDates;
    }
}
