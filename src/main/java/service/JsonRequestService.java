package service;

import com.jayway.jsonpath.JsonPath;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class JsonRequestService {

    private static final String PATH_TO_PROPERTIES = "src/main/resources/keys.properties";
    private static final String FIXER_KEY = "fixer";

    private Properties properties;

    public JsonRequestService() throws IOException {
       properties = new Properties();
       properties.load(new FileInputStream(PATH_TO_PROPERTIES));
    }


    public String getUsdPriceByDate(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String link = String.format("http://data.fixer.io/api/%s?access_key=%s&symbols=USD",
                sdf.format(date), properties.getProperty(FIXER_KEY));

        return getPriceByLink(link);
    }

    public String getLatestUsdPrice() throws Exception {
        String link = "http://data.fixer.io/api/latest?" +
                "access_key=" + properties.getProperty(FIXER_KEY) +
                "&symbols=USD";

        return getPriceByLink(link);
    }

    private String getPriceByLink(String link) throws Exception {
        URL url = new URL(link);
        URLConnection uc = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        StringBuilder json = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            json.append(line);
        }

        return JsonPath.read(json.toString(), "$.rates.USD").toString();
    }
}
