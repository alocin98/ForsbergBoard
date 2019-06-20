package ch.philosoph.forsbergboard;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RemoruchService {

    @GET("/")
    Call<String> getWebsite();
}
