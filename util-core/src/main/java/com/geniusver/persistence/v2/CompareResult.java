package com.geniusver.persistence.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * CompareResult
 *
 * @author GeniusV
 */
public class CompareResult<T> {
    private final List<T> newList;
    private final List<OldNew<T>> modifiedList;

    public CompareResult(List<T> newList, List<OldNew<T>> modifiedList) {
        this.newList = newList == null ? new ArrayList<>() : newList;
        this.modifiedList = modifiedList == null ? new ArrayList<>() : modifiedList;
    }

    public CompareResult<T> toInsert(Consumer<T> insertOperation) {
        newList.forEach(insertOperation);
        return this;
    }

    public CompareResult<T> toUpdate(BiConsumer<T, T> updateOperation) {
        modifiedList.forEach(oldNew -> updateOperation.accept(oldNew.getOldObject(), oldNew.getNewObject()));
        return this;
    }

    public List<T> toInsertList() {
        return newList;
    }

    public List<OldNew<T>> toUpdateList() {
        return modifiedList;
    }
}
