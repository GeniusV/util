package com.geniusver.persistence.v2;

import cn.hutool.core.lang.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CompareUtil
 *
 * @author GeniusV
 */
public class CompareUtil {
    public static <T> CompareResult<T> compare(Collection<T> oldList, Collection<T> newList, Function<T, Object> idFunction) {
        Assert.notEmpty(newList, "new list cannot be empty");
        Assert.notNull(idFunction, "id function cannot be null");
        if (oldList == null || oldList.isEmpty()) {
            return new CompareResult<>(new ArrayList<>(newList), null);
        }

        // id -> T
        Map<Object, T> oldMap = oldList.stream().collect(Collectors.toMap(idFunction, obj -> obj));

        List<T> insertList = new ArrayList<>();
        List<OldNew<T>> updateList = new ArrayList<>();

        for (T newObject : newList) {
            Object id = idFunction.apply(newObject);
            Assert.notNull(id, "id for {} is null", newObject);

            T oldObject = oldMap.get(id);

            if (oldObject == null) {
                insertList.add(newObject);
                continue;
            }

            if (!oldObject.equals(newObject)) {
                updateList.add(new OldNew<>(oldObject, newObject));
            }
        }

        return new CompareResult<>(insertList, updateList);
    }
}
