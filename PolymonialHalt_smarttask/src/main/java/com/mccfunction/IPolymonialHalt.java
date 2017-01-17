package com.mccfunction;

import task.ISharedResource;

public interface IPolymonialHalt extends ISharedResource<PolymonialHaltInput, PolymonialHaltOutput> {
    PolymonialHaltOutput process(PolymonialHaltInput request);
}
