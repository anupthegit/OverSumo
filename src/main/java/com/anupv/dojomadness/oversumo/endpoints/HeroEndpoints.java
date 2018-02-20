package com.anupv.dojomadness.oversumo.endpoints;

import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.Hero;
import com.anupv.dojomadness.oversumo.models.ShortInfo;
import com.anupv.dojomadness.oversumo.repositories.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Path("/heros")
public class HeroEndpoints {

    private Logger LOGGER = LoggerFactory.getLogger(HeroEndpoints.class);

    private final HeroRepository heroRepository;

    @Autowired
    public HeroEndpoints(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @GET
    @Produces("application/json")
    public Response getHeroes(){
        List<ShortInfo> shortInfos = StreamSupport.stream(heroRepository.findAll().spliterator(), true)
                .map(hero -> new ShortInfo(hero.getId(), hero.getName()))
                .collect(Collectors.toList());
        return Response.ok().entity(shortInfos).build();
    }

    @GET
    @Path("/{hero_id}")
    @Produces("application/json")
    @Transactional
    public Response getHero(@PathParam("hero_id") int id){
        if(!heroRepository.exists(id)) {
            return Response.status(404).entity("").build();
        }
        Hero hero = heroRepository.findOne(id);
        hero.getAbilities().size();
        return Response.ok().entity(hero).build();
    }

    @GET
    @Path("/{hero_id}/abilities")
    @Produces("application/json")
    @Transactional
    public Response getHeroAbilities(@PathParam("hero_id") int id){
        if(!heroRepository.exists(id)) {
            return Response.status(404).entity("").build();
        }
        Hero hero = heroRepository.findOne(id);
        Set<Ability> heroAbilities = hero.getAbilities();
        return Response.ok().entity(new ArrayList<>(heroAbilities)).build();
    }

}
