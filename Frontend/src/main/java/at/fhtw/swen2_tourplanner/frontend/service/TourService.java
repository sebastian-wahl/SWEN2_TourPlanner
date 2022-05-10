package at.fhtw.swen2_tourplanner.frontend.service;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.dtoObjects.TourDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public class TourService extends Service {

    private static final String TOUR_URL = REST_URL + "/tour";

    private final ObjectMapper o = new ObjectMapper();


    public void getAllTours() {
        try {
            HttpURLConnection con = getConnection("get");
            con.setRequestMethod("GET");
            int status = con.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            System.out.println("Content: " + content);
            in.close();
            con.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        TourService t = new TourService();

        t.getAllTours();
    }

    public void getTourById(UUID id) {
        HttpURLConnection con = getConnection("get");
        con.setRequestProperty("Content-Type", "application/json");
        con.disconnect();
    }

    public void deleteTour(TourDTO tour) {
        HttpURLConnection con = getConnection("get");

        con.disconnect();
    }

    public void addTour(TourDTO tour) {

    }

    public void updateTour(TourDTO tour) {

    }
}
