package com.mccfunction;

import task.SmartResponse;

public class PolymonialHaltResponse implements SmartResponse{
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
