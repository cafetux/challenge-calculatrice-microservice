package com.arolla.jam.calculatrice.infra.rest;

import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/calcul")
@Produces(MediaType.APPLICATION_JSON)
public class CalculatorResource {
    private final String template;
    private final String defaultName;

    public CalculatorResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
    }

    @GET
    @Timed
    public Calcul calcul(@QueryParam("calcul") Optional<String> name) {

        final String value = String.format(template, name.orElse(defaultName));

        return new Calcul(name.orElse(null),null);
    }
}