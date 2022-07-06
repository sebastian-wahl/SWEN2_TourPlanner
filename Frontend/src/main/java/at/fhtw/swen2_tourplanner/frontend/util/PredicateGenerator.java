package at.fhtw.swen2_tourplanner.frontend.util;

import at.fhtw.swen2_tourplanner.frontend.cellObject.converter.CustomLocalDateTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.cellObject.converter.CustomLocalTimeStringConverter;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;

import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

public class PredicateGenerator {
    public static Predicate<TourLog> getTourLogPredicate(String searchText) {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern(CustomLocalDateTimeStringConverter.DATE_TIME_FORMAT);
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern(CustomLocalTimeStringConverter.TIME_FORMAT);
        Predicate<TourLog> startDatePredicate = tourLog -> tourLog.getDateTime() != null && formatterDate.format(tourLog.getDateTime()).contains(searchText);

        Predicate<TourLog> durationPredicate = tourLog -> tourLog.getTotalTime() != null && formatterTime.format(tourLog.getTotalTime()).contains(searchText);

        Predicate<TourLog> distancePredicate = tourLog -> ("" + tourLog.getDistance()).contains(searchText);

        Predicate<TourLog> difficultyPredicate = tourLog -> ("" + tourLog.getDifficulty()).contains(searchText);

        Predicate<TourLog> ratingPredicate = tourLog -> ("" + tourLog.getRating()).contains(searchText);

        Predicate<TourLog> commentPredicate = tourLog -> tourLog.getComment() != null && tourLog.getComment().contains(searchText);

        return startDatePredicate.or(durationPredicate)
                .or(difficultyPredicate)
                .or(distancePredicate)
                .or(ratingPredicate)
                .or(commentPredicate);
    }

    public static Predicate<Tour> getTourPredicate(String searchText, boolean isFavorite) {
        Predicate<Tour> tourPredicate1 = Tour::isFavorite;
        Predicate<Tour> tourPredicate2 = tour -> tour.getName().toLowerCase().contains(searchText.toLowerCase());

        if (isFavorite) {
            return tourPredicate1.and(tourPredicate2);
        }
        return tourPredicate2;
    }
}
