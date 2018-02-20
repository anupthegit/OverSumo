package com.anupv.dojomadness.oversumo.endpoints;

import com.anupv.dojomadness.oversumo.factories.OverwatchQueryServiceFactory;
import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.Hero;
import com.anupv.dojomadness.oversumo.models.OverwatchHeroesResponse;
import com.anupv.dojomadness.oversumo.repositories.AbilityRepository;
import com.anupv.dojomadness.oversumo.repositories.HeroRepository;
import com.anupv.dojomadness.oversumo.services.OverwatchQueryService;
import com.diffplug.common.base.Errors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Component
@Path("/loadData")
public class DataImportEndpoint {

    private Logger LOGGER = LoggerFactory.getLogger(DataImportEndpoint.class);

    private final AbilityRepository abilityRepository;

    private final HeroRepository heroRepository;

    private OverwatchQueryServiceFactory serviceFactory;

    @Autowired
    public DataImportEndpoint(AbilityRepository abilityRepository, HeroRepository heroRepository,
                              OverwatchQueryServiceFactory serviceFactory) {
        this.abilityRepository = abilityRepository;
        this.heroRepository = heroRepository;
        this.serviceFactory = serviceFactory;
    }

    @GET
    public Response loadData() {
        OverwatchQueryService service = serviceFactory.getInstance();

        try {
            retrofit2.Response<OverwatchHeroesResponse>  overwatchHeroesResponse = service.listHeroes().execute();
            List<Hero> heroList = overwatchHeroesResponse.body().getData();
            heroList.forEach(Errors.rethrow().wrap(hero -> {
                if(!heroRepository.exists(hero.getId())) {
                    heroRepository.save(hero);
                }
                retrofit2.Response<Hero> heroResponse = service.getHero(hero.getId()).execute();
                Set<Ability> abilities = heroResponse.body().getAbilities();
                abilities.forEach(ability -> {
                    if(!abilityRepository.exists(ability.getId())) {
                        ability.setHero(heroResponse.body());
                        abilityRepository.save(ability);
                    }
                });
            }));
        } catch (Exception e) {
            LOGGER.error("Error fetching data: ", e);
            return Response.serverError().entity("Downstream error in Overwatch API").build();
        }
        return Response.ok().entity("Data import successful!").build();
    }
}
