package com.example.lib.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author Administrator
 */
public class EmptyUtil {

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof Collection) {
            return isEmpty((Collection<?>) object);
        } else if (object instanceof Map) {
            return isEmpty((Map<?, ?>) object);
        }

        String str = object.toString();
        return isEmpty(str);
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

}
