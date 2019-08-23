package com.automationtasks;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main {

        public static void main(String[] args) throws IOException, TransformerException, SAXException, ParserConfigurationException {
            List<String> listOfDates = CsvWork.getListOfDates();
            Map<String, String> receivedData = WebService.getContent(listOfDates);
            List<String> listOfCreatedXmlFiles = XmlWork.getListOfCreatedXmlFiles(receivedData);
            List<ValCurs> listOfValCursObjects = Deserialize.getDeserialized(listOfCreatedXmlFiles);
            XlsxWork.createXlsxFile(listOfValCursObjects);
        }
}
