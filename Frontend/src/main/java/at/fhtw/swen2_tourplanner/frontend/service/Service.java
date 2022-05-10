package at.fhtw.swen2_tourplanner.frontend.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class Service {
    protected static final String REST_URL = "http://localhost:8080";

    protected static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
    }

    protected static HttpURLConnection getConnection(String stringUrl) {
        try {
            URL url = new URL(stringUrl);
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();

            return con;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
