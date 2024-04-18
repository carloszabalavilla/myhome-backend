package com.czabala.myhome.util.user;

import com.czabala.myhome.util.exception.ApiException;

import java.lang.reflect.Field;

public class MapperDTOtoDAO {

    public static void copyNonNullFields(Object source, Object destination) {
        Class<?> sourceClass = source.getClass();
        Class<?> destinationClass = destination.getClass();

        Field[] fields = sourceClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);

                Object value = field.get(source);

                if (value != null) {
                    Field destinationField;
                    try {
                        destinationField = destinationClass.getDeclaredField(field.getName());
                    } catch (NoSuchFieldException e) {
                        continue;
                    }
                    destinationField.setAccessible(true);
                    destinationField.set(destination, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new ApiException("Error al copiar los campos no nulos de un objeto a otro: " + sourceClass.getName() + " -> " + destinationClass.getName()) ;
        }
    }

}
