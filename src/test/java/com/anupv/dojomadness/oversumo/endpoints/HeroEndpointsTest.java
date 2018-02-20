package com.anupv.dojomadness.oversumo.endpoints;

import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.Hero;
import com.anupv.dojomadness.oversumo.models.ShortInfo;
import com.anupv.dojomadness.oversumo.repositories.HeroRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HeroEndpointsTest {

    @Mock
    private HeroRepository repository;

    @InjectMocks
    private HeroEndpoints endpoints;

    @Test
    public void getHeroesShouldReturnShortInfoForAllHerosInRepository() throws Exception {
        Hero hero = new Hero();
        hero.setId(0);
        hero.setName("herozero");
        hero.setShield(100);
        hero.setHealth(50);
        hero.setArmour(0);
        hero.setRealName("The real hero");
        when(repository.findAll()).thenReturn(Arrays.asList(hero));

        Response response = endpoints.getHeroes();

        assertEquals(200, response.getStatus());
        assertTrue(response.getEntity() instanceof List);
        List<ShortInfo> infoList = (List<ShortInfo>) response.getEntity();
        assertEquals(1, infoList.size());
        assertEquals(hero.getId(), infoList.get(0).getId());
        assertEquals(hero.getName(), infoList.get(0).getName());
    }

    @Test
    public void getHeroShouldReturn404StatusCodeIfHeroDoesNotExistInRepository() throws Exception {
        int id = 1;
        when(repository.exists(id)).thenReturn(false);

        Response response = endpoints.getHero(id);

        assertEquals(404, response.getStatus());
    }

    @Test
    public void getHeroShouldReturnHeroDetailsIfHeroExists() throws Exception {
        int id = 1;
        Hero hero = new Hero();
        hero.setId(id);
        hero.setName("herozero");
        hero.setShield(100);
        hero.setHealth(50);
        hero.setArmour(0);
        hero.setRealName("The real hero");
        hero.setAbilities(Collections.emptySet());
        when(repository.exists(id)).thenReturn(true);
        when(repository.findOne(id)).thenReturn(hero);

        Response heroResponse = endpoints.getHero(id);

        assertEquals(200, heroResponse.getStatus());
        assertTrue(heroResponse.getEntity() instanceof Hero);
        assertEquals(hero, heroResponse.getEntity());
    }

    @Test
    public void getHeroAbilitiesShouldReturn404StatusCodeIfHeroDoesNotExistInRepository() throws Exception {
        int id = 1;
        when(repository.exists(id)).thenReturn(false);

        Response response = endpoints.getHeroAbilities(id);

        assertEquals(404, response.getStatus());
    }

    @Test
    public void getHeroAbilitiesShouldReturnAbilitiesForHero() throws Exception {
        Ability ability1 = new Ability();
        ability1.setId(1);
        ability1.setUltimate(true);
        ability1.setName("ab1");
        ability1.setDescription("first ability");

        Ability ability2 = new Ability();
        ability2.setId(2);
        ability2.setUltimate(false);
        ability2.setName("ab2");
        ability2.setDescription("second ability");

        int hero_id = 0;
        Hero hero = new Hero();
        hero.setId(hero_id);
        hero.setAbilities(Sets.newSet(ability1, ability2));
        when(repository.exists(hero_id)).thenReturn(true);
        when(repository.findOne(hero_id)).thenReturn(hero);

        Response response = endpoints.getHeroAbilities(hero_id);

        assertEquals(200, response.getStatus());
        assertTrue(response.getEntity() instanceof List);
        List<Ability> abilities = (List<Ability>) response.getEntity();
        assertEquals(ability1, abilities.get(0));
        assertEquals(ability2, abilities.get(1));
    }
}