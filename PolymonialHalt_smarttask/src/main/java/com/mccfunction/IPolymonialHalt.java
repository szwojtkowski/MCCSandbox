package com.mccfunction;

import task.ISharedResource;

public interface IPolymonialHalt extends ISharedResource<PolymonialHaltRequest, PolymonialHaltResponse> {
    PolymonialHaltResponse process(PolymonialHaltRequest request);
}
