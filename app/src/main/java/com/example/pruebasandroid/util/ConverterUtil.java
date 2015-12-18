package com.example.pruebasandroid.util;

/**
 * Created by Jordi on 11/12/2015.
 */
public class ConverterUtil {

    public static float convertCelsiusToFahrenheit(float degrees)
    {
        return degrees*9/5+32;
    }

    public static float convertFahrenheitToCelsius(float fahrenheit)
    {
        return (fahrenheit-32)*5/9;
    }
}
