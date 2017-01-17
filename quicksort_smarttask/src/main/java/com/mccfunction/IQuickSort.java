

package com.mccfunction;

import task.ISharedResource;

public interface IQuickSort extends ISharedResource<QuickSortInput, QuickSortOutput> {
    QuickSortOutput process(QuickSortInput request);
}
