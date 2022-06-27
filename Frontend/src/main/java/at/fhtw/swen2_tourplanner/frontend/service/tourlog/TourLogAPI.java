package at.fhtw.swen2_tourplanner.frontend.service.tourlog;

import at.fhtw.swen2_tourplanner.frontend.viewmodel.modelobjects.TourLog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TourLogAPI {
    @GET("tour-log/get/{tourId}")
    Call<List<TourLog>> getAllLogs(@Path("tourId") UUID tourId);

    @DELETE("tour-log/delete/{tourLogId}")
    Call<Void> deleteTourLog(@Path("tourLogId") UUID tourLogId);

    @POST("tour-log/create")
    Call<Optional<TourLog>> addTourLog(@Body TourLog tourLog);

    @PUT("tour-log/update")
    Call<Optional<TourLog>> updateTourLog(@Body TourLog tourLog);

    @GET("tour-log/get-tour-report/{tourId}")
    Call<ResponseBody> getTourReport(@Path("tourId") UUID tourId);

    @GET("tour-log/get-summary-report")
    Call<ResponseBody> getTourSummary();
}
