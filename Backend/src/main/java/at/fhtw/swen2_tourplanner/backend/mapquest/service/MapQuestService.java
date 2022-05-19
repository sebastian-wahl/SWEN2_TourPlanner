package at.fhtw.swen2_tourplanner.backend.mapquest.service;

import at.fhtw.swen2_tourplanner.backend.mapquest.model.MapQuestResponse;

import java.util.Optional;

public interface MapQuestService {

    MapQuestResponse getTimeAndDistance(String from, String to);

    byte[] getImage(String start, String end);

    Optional<String> validateLocation(String address);
}
