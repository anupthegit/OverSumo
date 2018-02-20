package com.anupv.dojomadness.oversumo.configuration;

import com.anupv.dojomadness.oversumo.endpoints.AbilityEndpoints;
import com.anupv.dojomadness.oversumo.endpoints.DataImportEndpoint;
import com.anupv.dojomadness.oversumo.endpoints.HeroEndpoints;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(DataImportEndpoint.class);
        register(HeroEndpoints.class);
        register(AbilityEndpoints.class);
    }
}
