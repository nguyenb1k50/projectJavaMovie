package com.myclass.customException;

import java.lang.reflect.Field;

public class ReflectionUtils {
	public static <T> Object getPropertyValue(T obj, String fieldName) {
        Object result = null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            result = field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }

        return result;
    }
}
