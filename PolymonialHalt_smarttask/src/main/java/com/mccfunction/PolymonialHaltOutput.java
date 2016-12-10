package com.mccfunction;

import task.SmartOutput;

public class PolymonialHaltOutput implements SmartOutput {
    private boolean status;
    PolymonialHaltOutput(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
