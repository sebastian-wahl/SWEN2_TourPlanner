package at.fhtw.swen2_tourplanner.backend.tourlog.util;

import at.fhtw.swen2_tourplanner.backend.tourlog.dto.TourLogDTO;

import java.util.List;

public class ComputedValues {
    private final int popularity;
    private final double childFriendliness;
    private static final double MAX_RATING = 3;
    private static final double MID_RATING = 2;
    private static final double MIN_RATING = 1;

    public ComputedValues(List<TourLogDTO> tourLogs) {
        this.popularity = calculatePopularity(tourLogs.size());
        this.childFriendliness = calculateChildFriendliness(tourLogs);
    }

    private int calculatePopularity(int tourCount) {
        if (tourCount <= 5) {
            return 1;
        } else if (tourCount <= 15) {
            return 2;
        } else {
            return 3;
        }
    }

    private double calculateChildFriendliness(List<TourLogDTO> tourLogs) {
        if (!tourLogs.isEmpty()) {
            final double avgTime = tourLogs.stream().mapToInt(a -> a.getDateTime() != null ? a.getDateTime().getHour() * 60 + a.getDateTime().getMinute() : 0).average().orElse(0);
            final double avgDistance = tourLogs.stream().mapToDouble(TourLogDTO::getDistance).average().orElse(0);
            final double avgDifficulty = tourLogs.stream().mapToDouble(TourLogDTO::getDifficulty).average().orElse(0);
            return (calculateTimeRating(avgTime) + calculateDistanceRating(avgDistance) + calculateDifficultyRating(avgDifficulty)) / MAX_RATING;
        }
        return 1;
    }

    private double calculateDifficultyRating(double avgDifficulty) {
        return MAX_RATING - avgDifficulty + 1;
    }

    private double calculateDistanceRating(double avgDistance) {
        if (avgDistance <= 10) {
            return MAX_RATING;
        } else if (avgDistance <= 30) {
            return MID_RATING;
        } else {
            return MIN_RATING;
        }
    }

    private double calculateTimeRating(double avgTime) {
        if (avgTime <= 120) {
            return MAX_RATING;
        } else if (avgTime <= 240) {
            return MID_RATING;
        } else {
            return MIN_RATING;
        }
    }


}
