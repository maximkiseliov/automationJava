package com.automationtasks;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlWork {
    private static final String PART_NAME = "BNM_";
    private static final String XML_EXTENSION = ".xml";

    public static String getPartName() {
        return PART_NAME;
    }

    public static String getXmlExtension() {
        return XML_EXTENSION;
    }

    public static List<String> getListOfCreatedXmlFiles (Map<String, String> mapReceivedData) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        String fileName;
        List<String> listOfCreatedXmlFiles;
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        DOMSource source;
        StreamResult result;

        // Parse the given input
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();

        listOfCreatedXmlFiles = new ArrayList<>();
        for (Map.Entry<String, String> entry : mapReceivedData.entrySet()) {
            fileName = PART_NAME + entry.getKey() + XML_EXTENSION;

            Document doc = builder.parse(new InputSource(new StringReader(entry.getValue())));

            // Write the parsed document to an xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            source = new DOMSource(doc);

            result = new StreamResult(new File(fileName));
            transformer.transform(source, result);
            listOfCreatedXmlFiles.add(fileName);
        }

        return listOfCreatedXmlFiles;
    }
}
