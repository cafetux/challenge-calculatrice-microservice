package com.arolla.jam.calculatrice.infra.rest;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class CalculatorConfiguration extends Configuration {


    @NotEmpty
    private String moduleName = "Calculator";

    @JsonProperty
    public String getModuleName() {
        return moduleName;
    }

    @JsonProperty
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}