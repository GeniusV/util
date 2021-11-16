package com.geniusver.persistence.v2;

import java.util.ArrayList;
import java.util.List;

/**
 * CompareResult
 *
 * @author GeniusV
 */
public class CompareResult<T> {
    private final List<T> toInsert;
    private final List<T> toUpdate;

    public CompareResult(List<T> toInsert, List<T> toUpdate) {
        this.toInsert = toInsert == null ? new ArrayList<>() : toInsert;
        this.toUpdate = toUpdate == null ? new ArrayList<>() : toUpdate;
    }

    public List<T> toInsertList() {
        return toInsert;
    }

    public List<T> toUpdateList() {
        return toUpdate;
    }
}
