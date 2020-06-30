package com.oieskova.employers2.api;

import com.oieskova.employers2.data.Employers;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {
    @GET("testTask.json")
   Observable<Employers> getEmployers();
}
