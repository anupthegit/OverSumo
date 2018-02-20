package com.anupv.dojomadness.oversumo.endpoints;

import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.ShortInfo;
import com.anupv.dojomadness.oversumo.repositories.AbilityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbilityEndpointsTest {

    @Mock
    private AbilityRepository repository;

    @InjectMocks
    private AbilityEndpoints endpoints;

    @Test
    public void getAbilitiesShouldReturnShortInfoForAllAbilitiesInRepository() throws Exception {
        Ability ability = new Ability();
        ability.setId(1);
        ability.setUltimate(true);
        ability.setName("ab1");
        ability.setDescription("first ability");
        when(repository.findAll()).thenReturn(Arrays.asList(ability));

        Response response = endpoints.getAbilities();

        assertEquals(200, response.getStatus());
        assertTrue(response.getEntity() instanceof List);
        List<ShortInfo> infoList = (List<ShortInfo>) response.getEntity();
        assertEquals(1, infoList.size());
        assertEquals(ability.getId(), infoList.get(0).getId());
        assertEquals(ability.getName(), infoList.get(0).getName());
    }

    @Test
    public void getAbilityShouldReturn404StatusCodeIfAbilityDoesNotExistInRepository() throws Exception {
        int id = 1;
        when(repository.exists(id)).thenReturn(false);

        Response response = endpoints.getAbility(id);

        assertEquals(404, response.getStatus());
    }

    @Test
    public void getAbilityShouldReturnAbilityDetailsIfAbilityExists() throws Exception {
        Ability ability = new Ability();
        int id = 1;
        ability.setId(id);
        ability.setUltimate(true);
        ability.setName("ab1");
        ability.setDescription("first ability");
        when(repository.exists(id)).thenReturn(true);
        when(repository.findOne(id)).thenReturn(ability);

        Response response = endpoints.getAbility(id);

        assertEquals(200, response.getStatus());
        assertTrue(response.getEntity() instanceof Ability);
        assertEquals(ability, response.getEntity());
    }
}