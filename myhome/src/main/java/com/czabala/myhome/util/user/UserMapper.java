package com.czabala.myhome.util.user;

import com.czabala.myhome.domain.model.User;
import com.czabala.myhome.domain.model.dto.UserDTO;

import java.lang.reflect.Field;

public class UserMapper {

    public static void copyNonNullFields(UserDTO source, User destination) throws IllegalAccessException {
        Class<?> sourceClass = source.getClass();
        Class<?> destinationClass = destination.getClass();

        Field[] fields = sourceClass.getDeclaredFields();

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
    }

}
