package com.plaquesolaire.entity;
import java.text.DecimalFormat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

public class Utils {
    public static <T> double somme(List<T> objects, String fieldGetter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = objects.get(0).getClass().getMethod(fieldGetter);
        double somme = 0;
        for (Object object: objects) {
            //if(object instanceof Integer){
                Double temp = Double.valueOf(m.invoke(object) + "");
                somme += temp;
            
        }
        return somme;
    }
    public static int getDayOfWeek(long timestamp) {
       Calendar calendar = Calendar.getInstance();
       calendar.setTimeInMillis(timestamp);
       return calendar.get(Calendar.DAY_OF_WEEK);
   }
   public static String format(double valeur){
        DecimalFormat df = new DecimalFormat("#.00");
        String formattedNum = df.format(valeur);
        return formattedNum;
   }
   public static void main(String[] args) {
    System.out.println(Utils.format(34324.342423));
   }
}
