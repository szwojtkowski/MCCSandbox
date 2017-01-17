package com.mccfunction;

public class PolymonialHalt implements IPolymonialHalt {

    @Override
    public PolymonialHaltOutput process(PolymonialHaltInput request) {
        double a = request.a;
        double b = request.b;
        double c = request.c;
        double d = request.d;
        try {
            Thread.sleep((long) (a * a * a + b * b + c * c + d));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new PolymonialHaltOutput(false);
        }
        return new PolymonialHaltOutput(true);
    }
}

