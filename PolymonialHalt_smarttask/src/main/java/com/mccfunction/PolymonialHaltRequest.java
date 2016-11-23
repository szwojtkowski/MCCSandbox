package com.mccfunction;

import task.SmartRequest;

public class PolymonialHaltRequest implements SmartRequest {
    public double a;
    public double b;
    public double c;
    public double d;
    public PolymonialHaltRequest(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
