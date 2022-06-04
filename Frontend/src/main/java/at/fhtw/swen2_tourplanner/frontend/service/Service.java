package at.fhtw.swen2_tourplanner.frontend.service;

import at.fhtw.swen2_tourplanner.frontend.util.ConfigLoader;

public interface Service {
    String REST_URL = ConfigLoader.getValue("backend.url");
}
