package com.automationtasks;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebService {
    private static final String BNM_URL = "https://www.bnm.md/en/official_exchange_rates?get_xml=1&date=";

    public static String getBnmUrl() {
        return BNM_URL;
    }

    public static Map<String, String> getContent(List<String> listOfDates) throws IOException{
        Map<String, String> mapReceivedData;
        CloseableHttpClient httpClient;
        HttpGet getRequest;
        HttpResponse response;
        HttpEntity httpEntity;
        String entityAsString;

        httpClient = HttpClientBuilder.create().build();
        mapReceivedData = new HashMap<>();
        for (String date : listOfDates) {
            getRequest = new HttpGet(BNM_URL + date);
            getRequest.addHeader("accept", "application/xml");
            response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatusLine().getStatusCode());

            httpEntity = response.getEntity();
            entityAsString = EntityUtils.toString(httpEntity);
            mapReceivedData.put(date, entityAsString);
        }

        return mapReceivedData;
    }
}
