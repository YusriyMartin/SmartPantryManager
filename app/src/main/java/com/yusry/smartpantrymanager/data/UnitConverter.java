package com.yusry.smartpantrymanager.data;

// Created initially to convert - workaround to troubleshoot strcit macth error
public class UnitConverter {

    // Converts all wieght to grams
    public static double toGrams(double qty, String unit){
        unit = unit.toLowerCase().trim();
        switch(unit){
            case "kg": return qty * 1000;
            case "g": return qty;
            case "lb": case "lbs": return qty * 453.6;
            default: return qty; // non-units unaffected
        }
    }

    // Chedk if its a weight measurement, either kg, g, lb, lbs
    public static boolean isWeight(String u){
        u = u.toLowerCase().trim();
        return u.equals("kg") || u.equals("g") || u.equals("lb") || u.equals("lbs");
    }

    public static boolean canCompare(String u1, String u2){
        return (isWeight(u1) && isWeight(u2)) || u1.equals(u2);
    }
}
