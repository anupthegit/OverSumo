package com.anupv.dojomadness.oversumo.endpoints;

import com.anupv.dojomadness.oversumo.factories.OverwatchQueryServiceFactory;
import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.Hero;
import com.anupv.dojomadness.oversumo.models.OverwatchHeroesResponse;
import com.anupv.dojomadness.oversumo.repositories.AbilityRepository;
import com.anupv.dojomadness.oversumo.repositories.HeroRepository;
import com.anupv.dojomadness.oversumo.services.OverwatchQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DataImportEndpointTest {

    @Mock
    private AbilityRepository abilityRepository;

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private OverwatchQueryServiceFactory serviceFactory;

    @Mock
    private OverwatchQueryService service;

    @InjectMocks
    private DataImportEndpoint endpoint;

    @Test
    public void loadDataShouldReturnServerErrorIfOverwatchListHerosCallFails() throws Exception {
        when(serviceFactory.getInstance()).thenReturn(service);
        when(service.listHeroes()).thenThrow(IOException.class);

        Response response = endpoint.loadData();

        assertEquals(500, response.getStatus());
    }

    @Test
    public void loadDataShouldReturnServerErrorIfOverwatchGetHeroCallFails() throws Exception {
        when(serviceFactory.getInstance()).thenReturn(service);
        OverwatchHeroesResponse overwatchHeroesResponse = new OverwatchHeroesResponse();
        Hero hero = new Hero();
        int hero_id = 0;
        hero.setId(hero_id);
        hero.setName("herozero");
        hero.setShield(100);
        hero.setHealth(50);
        hero.setArmour(0);
        hero.setRealName("The real hero");
        overwatchHeroesResponse.setData(Arrays.asList(hero));
        Call<OverwatchHeroesResponse> heroListCall = mock(Call.class);
        when(service.listHeroes()).thenReturn(heroListCall);
        when(heroListCall.execute()).thenReturn(retrofit2.Response.success(overwatchHeroesResponse));
        when(heroRepository.exists(hero_id)).thenReturn(true);
        when(service.getHero(hero_id)).thenThrow(IOException.class);

        Response response = endpoint.loadData();

        assertEquals(500, response.getStatus());
    }

    @Test
    public void loadDataShouldSaveHerosAndAbilitiesIntoRepositoriesFromServiceCallResponse() throws Exception {
        when(serviceFactory.getInstance()).thenReturn(service);
        OverwatchHeroesResponse overwatchHeroesResponse = new OverwatchHeroesResponse();
        Hero hero = new Hero();
        int hero_id = 0;
        hero.setId(hero_id);
        hero.setName("herozero");
        hero.setShield(100);
        hero.setHealth(50);
        hero.setArmour(0);
        hero.setRealName("The real hero");
        Ability ability = new Ability();
        int ability_id = 1;
        ability.setId(ability_id);
        ability.setUltimate(true);
        ability.setName("ab1");
        ability.setDescription("first ability");
        hero.setAbilities(Sets.newSet(ability));
        overwatchHeroesResponse.setData(Arrays.asList(hero));
        Call<OverwatchHeroesResponse> heroListCall = mock(Call.class);
        when(service.listHeroes()).thenReturn(heroListCall);
        when(heroListCall.execute()).thenReturn(retrofit2.Response.success(overwatchHeroesResponse));
        when(heroRepository.exists(hero_id)).thenReturn(false);
        Call heroCall = mock(Call.class);
        when(service.getHero(hero_id)).thenReturn(heroCall);
        when(heroCall.execute()).thenReturn(retrofit2.Response.success(hero));
        when(abilityRepository.exists(ability_id)).thenReturn(false);

        endpoint.loadData();

        verify(heroRepository, times(1)).save(hero);
        verify(abilityRepository, times(1)).save(ability);
    }
}