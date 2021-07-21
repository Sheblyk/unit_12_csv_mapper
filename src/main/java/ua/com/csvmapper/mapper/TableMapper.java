package ua.com.csvmapper.mapper;

import ua.com.csvmapper.annotation.Column;
import ua.com.csvmapper.table.CSVTable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TableMapper {

    public <T> List<T> map(CSVTable csvTable, Class<T> tClass) {
        try {
            List<T> result = new ArrayList<>();
            Constructor<T> constructor = tClass.getConstructor();
            Field[] fields = tClass.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
            }
            for (int i = 0; i < csvTable.getValues().size(); i++) {
                T instance = constructor.newInstance();
                for (Field f : fields) {
                    if (f.isAnnotationPresent(Column.class)) {
                        String columnValue = f.getAnnotation(Column.class).value();
                        String valueToInsert = csvTable.getElementByName(i + 1, columnValue);
                        convertStringTo(instance, f, valueToInsert);
                    }
                }
                result.add(instance);
            }
            return result;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void convertStringTo(Object toInit, Field field, String toInsert) throws IllegalAccessException {
        Class<?> type = field.getType();
        if (type.equals(Integer.class) || type == int.class) {
            field.set(toInit, Integer.parseInt(toInsert));
        } else if (type.equals(Long.class) || type == long.class) {
            field.set(toInit, Long.parseLong(toInsert));
        } else if (type.equals(Boolean.class) || type == boolean.class) {
            field.set(toInit, Boolean.parseBoolean(toInsert));
        } else if (type.equals(Byte.class) || type == byte.class) {
            field.set(toInit, Byte.parseByte(toInsert));
        } else if (type.equals(Short.class) || type == short.class) {
            field.set(toInit, Short.parseShort(toInsert));
        } else if (type.equals(Double.class) || type == double.class) {
            field.set(toInit, Double.parseDouble(toInsert));
        } else if (type.equals(Float.class) || type == float.class) {
            field.set(toInit, Float.parseFloat(toInsert));
        } else if (type.equals(String.class)) {
            field.set(toInit, toInsert);
        } else if (type.equals(Character.class) || type == char.class) {
            field.set(toInit, toInsert.charAt(0));
        } else if (type.isEnum()) {
            Object[] constants = type.getEnumConstants();
            for (Object constant : constants) {
                if (constant.toString().equalsIgnoreCase(toInsert)) {
                    field.set(toInit, constant);
                }
            }
        }
    }
}
