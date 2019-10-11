package com.automationtasks;

import com.thoughtworks.xstream.XStream;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BnmWebService {
    private static final String BNM_URL = "https://www.bnm.md/en/official_exchange_rates?get_xml=1&date=";

    private static String getBnmUrl() {
        return BNM_URL;
    }

    private static String generateRequestLink(String date) {
        return getBnmUrl() + date;
    }

    private static InputStream getHttpContent(String link) {
        CloseableHttpClient httpClient;
        HttpGet getRequest;
        HttpResponse response;
        InputStream httpContent;

        httpContent = null;

        try {
            httpClient = HttpClientBuilder.create().build();
            getRequest = new HttpGet(link);
            getRequest.addHeader("accept", "application/xml");
            response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatusLine().getStatusCode());

            httpContent = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpContent;
    }

    //Task #4
    public static ValCurs getValCurs(LocalDate date) {
        String requestLink;
        XStream xstream;
        ValCurs valCurs;

        requestLink = generateRequestLink(DatesHelper.transformLocalDateToString(date));
        xstream = new XStream();
        xstream.processAnnotations(ValCurs.class);

        valCurs = (ValCurs) xstream.fromXML(getHttpContent(requestLink));

        return valCurs;
    }

    //Task #5
    public static Valute getValute(LocalDate date, String charCode) {
        String requestLink;
        XStream xstream;
        Valute valute;

        requestLink = generateRequestLink(DatesHelper.transformLocalDateToString(date));
        xstream = new XStream();
        xstream.processAnnotations(ValCurs.class);

        List<Valute> listOfValute = ((ValCurs) xstream.fromXML(getHttpContent(requestLink))).getValuteList();

        valute = listOfValute
                .stream()
                .filter(val -> charCode.equals(val.getCharCode()))
                .findAny()
                .orElse(null);

        return valute;
    }

    //Task #6
    public static Valute getMaxValute(LocalDate startDate, LocalDate endDate, String charCode) {
        String requestLink;
        XStream xstream;
        Valute valute, maxValute;
        List<String> listOfDates;
        List<Valute> listOfValute;
        List<Valute> listOfRequiredValute = new ArrayList<>();

        listOfDates = DatesHelper.listOfDatesBetweenStartAndEnd(startDate, endDate);
        xstream = new XStream();
        xstream.processAnnotations(ValCurs.class);

        for (String date : listOfDates) {
            requestLink = generateRequestLink(date);

            listOfValute = ((ValCurs) xstream.fromXML(getHttpContent(requestLink))).getValuteList();
            valute = listOfValute
                    .stream()
                    .filter(val -> charCode.equals(val.getCharCode()))
                    .findAny()
                    .orElse(null);

            if (valute != null) {
                listOfRequiredValute.add(valute);
            }
        }
        maxValute = listOfRequiredValute
                .stream()
                .max(Comparator.comparing(Valute::getValue))
                .get();

        return maxValute;
    }
}
