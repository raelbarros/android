package br.senac.lojaandroid.util;

import java.text.DecimalFormat;

public class Util {

    public static String formatPreco(Double x){
        DecimalFormat format = new DecimalFormat("RS: 0.00");
        return format.format(x);
    }
}
