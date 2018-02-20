package com.anupv.dojomadness.oversumo.factories;

import com.anupv.dojomadness.oversumo.services.OverwatchQueryService;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class OverwatchQueryServiceFactory {

    public OverwatchQueryService getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://overwatch-api.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(OverwatchQueryService.class);
    }
}
