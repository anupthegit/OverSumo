package com.anupv.dojomadness.oversumo.endpoints;

import com.anupv.dojomadness.oversumo.models.Ability;
import com.anupv.dojomadness.oversumo.models.ShortInfo;
import com.anupv.dojomadness.oversumo.repositories.AbilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@Path("/abilities")
public class AbilityEndpoints {

    private final AbilityRepository abilityRepository;

    @Autowired
    public AbilityEndpoints(AbilityRepository abilityRepository) {
        this.abilityRepository = abilityRepository;
    }

    @GET
    @Produces("application/json")
    public Response getAbilities(){
        List<ShortInfo> shortInfos = StreamSupport.stream(abilityRepository.findAll().spliterator(), true)
                .map(ability -> new ShortInfo(ability.getId(), ability.getName()))
                .collect(Collectors.toList());
        return Response.ok().entity(shortInfos).build();
    }

    @GET
    @Path("/{ability_id}")
    @Produces("application/json")
    @Transactional
    public Response getAbility(@PathParam("ability_id") int id){
        if(!abilityRepository.exists(id)) {
            return Response.status(404).entity("").build();
        }
        Ability ability = abilityRepository.findOne(id);
        return Response.ok().entity(ability).build();
    }
}
