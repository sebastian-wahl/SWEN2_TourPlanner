package at.fhtw.swen2_tourplanner.frontend.service.tour;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.Tour;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourAPI {
    @GET("tour/get")
    Call<List<Tour>> getAllTours();

    @GET("tour/get/{tourId}")
    Call<Optional<Tour>> getTourById(@Path("tourId") UUID tourId);

    @DELETE("tour/delete/{tourId}")
    Call<Void> deleteTour(@Path("tourId") UUID tourId);

    @POST("tour/create")
    Call<Optional<Tour>> addTour(@Body Tour tour);

    @POST("tour/import-tours")
    Call<List<Tour>> importTours(@Body List<Tour> tourList);

    @PUT("tour/update")
    Call<Optional<Tour>> updateTour(@Body Tour tour);
}
