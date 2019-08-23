package com.automationtasks;

import com.thoughtworks.xstream.XStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Deserialize {


    public static List<ValCurs> getDeserialized(List<String> listOfXmlFiles) throws FileNotFoundException {
        XStream xstream;
        List<ValCurs> listValCursObjects;
        FileInputStream exampleCurs;
        ValCurs convertedValute;

        xstream = new XStream();
        xstream.processAnnotations(ValCurs.class);

        listValCursObjects = new ArrayList<>();
        for (String xmlFileName : listOfXmlFiles) {
            exampleCurs = new FileInputStream(xmlFileName);
            convertedValute = (ValCurs) xstream.fromXML(exampleCurs);
            listValCursObjects.add(convertedValute);
        }
        return listValCursObjects;
    }
}
