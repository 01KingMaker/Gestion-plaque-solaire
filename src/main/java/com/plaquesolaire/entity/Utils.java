package com.plaquesolaire.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static double somme(List<Object> objects, String fieldGetter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = objects.get(0).getClass().getMethod(fieldGetter);
        double somme = 0;
        for (Object object: objects) {
            double temp = (double) m.invoke(object);
            somme += temp;
        }
        return somme;
    }
}
