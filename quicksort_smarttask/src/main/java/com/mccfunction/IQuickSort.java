

package com.mccfunction;

import task.ISharedResource;

public interface IQuickSort extends ISharedResource<QuickSortRequest, QuickSortResponse> {
    QuickSortResponse process(QuickSortRequest request);
}
