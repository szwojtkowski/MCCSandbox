package com.example.smartmcc;

import com.amazonaws.regions.Regions;

public class ProxyFactoryConfiguration {
    public String identityPoolId;
    public Regions region;

    public ProxyFactoryConfiguration(String identityPoolId, Regions region) {
        this.identityPoolId = identityPoolId;
        this.region = region;
    }
}
