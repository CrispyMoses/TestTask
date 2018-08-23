package service;

import com.jayway.jsonpath.JsonPath;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class JsonRequestService {

    private static final String OPENEXCHANGE_KEY = "openexchange";

    private Properties properties;

    public JsonRequestService() throws IOException {
       properties = new Properties();
       ClassLoader loader = Thread.currentThread().getContextClassLoader();
       InputStream inputStream = loader.getResourceAsStream("keys.properties");
       properties.load(inputStream);
    }


    public String getUsdPrice(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String link = String.format("https://openexchangerates.org/api/historical/%s.json?app_id=%s",
                sdf.format(date), properties.getProperty(OPENEXCHANGE_KEY));

        return getPriceByLink(link);
    }

    public String getUsdPrice() throws Exception {
        String link = "https://openexchangerates.org/api/latest.json?app_id=" + properties.getProperty(OPENEXCHANGE_KEY);

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

        return JsonPath.read(json.toString(), "$.rates.RUB").toString();
    }
}
