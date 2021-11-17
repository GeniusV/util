package com.geniusver.persistence.v2;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * RepositoryUtil
 *
 * @author GeniusV
 */
public class RepositoryUtil {
    /**
     * @param newDataObjects   new data object collections to compare
     * @param clazz            new data object type
     * @param getId            get id from a data object
     * @param copyVersion      copy version from old to new
     * @param saveNewOperation perform save new operation and return new one.
     * @param updateOperation  perform update operation and return new one
     * @param context          context contains old data objects to compare
     * @param <T>              data object type
     */
    public static <T> void performCompareAndSave(Collection<T> newDataObjects,
                                                 Class<T> clazz,
                                                 Function<T, Object> getId,
                                                 BiConsumer<T, T> copyVersion,
                                                 Function<T, T> saveNewOperation,
                                                 Function<T, T> updateOperation,
                                                 DataObjectContext context) {
        CompareResult<T> compareResult = CompareUtil.compare(context.getAll(clazz),
                newDataObjects,
                getId);
        compareResult.toInsert(obj -> {
            T inserted = saveNewOperation.apply(obj);
            context.put(inserted, clazz, getId);
        });
        compareResult.toUpdate((oldDo, newDo) -> {
            copyVersion.accept(oldDo, newDo);
            T updated = updateOperation.apply(newDo);
            context.put(updated, clazz, getId);
        });
    }
}
