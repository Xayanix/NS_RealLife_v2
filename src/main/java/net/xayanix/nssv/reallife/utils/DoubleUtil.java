package net.xayanix.nssv.reallife.utils;

import java.text.DecimalFormat;

public class DoubleUtil {

    private static DecimalFormat df = new DecimalFormat("#.##");

    public static String format(double doub){
        return df.format(doub);
    }

}
