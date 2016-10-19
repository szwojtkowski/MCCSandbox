package com.mccfunction;

public class PolymonialHaltResponse {
    private boolean status;
    PolymonialHaltResponse(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
