package com.anupv.dojomadness.oversumo.services;

import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.Hero;
import com.anupv.dojomadness.oversumo.models.OverwatchAbilitiesResponse;
import com.anupv.dojomadness.oversumo.models.OverwatchHeroesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OverwatchQueryService {

    @GET("api/v1/hero/")
    Call<OverwatchHeroesResponse> listHeroes();

    @GET("api/v1/hero/{hero_id}")
    Call<Hero> getHero(@Path("hero_id") int id);

    @GET("api/v1/ability/")
    Call<OverwatchAbilitiesResponse> listAbilities();

    @GET("api/v1/ability/{ability_id}")
    Call<Ability> getAbility(@Path("ability_id") int id);

}
