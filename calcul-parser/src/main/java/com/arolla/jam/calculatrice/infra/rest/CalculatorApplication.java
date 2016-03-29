package com.arolla.jam.calculatrice.infra.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class CalculatorApplication extends Application<CalculatorConfiguration> {

    public static void main(String[] args) throws Exception {
        new CalculatorApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<CalculatorConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(CalculatorConfiguration configuration, Environment environment) {
        // nothing to do yet
    }

}