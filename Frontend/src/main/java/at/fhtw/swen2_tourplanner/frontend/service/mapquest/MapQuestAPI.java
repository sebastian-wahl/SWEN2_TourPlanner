package at.fhtw.swen2_tourplanner.frontend.service.mapquest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MapQuestAPI {
    @GET("map/validate/location/{address}")
    Call<Boolean> validateLocation(@Path("address") String address);
}
