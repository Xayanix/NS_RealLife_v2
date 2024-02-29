package net.xayanix.nssv.reallife.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static Boolean isCharName(String name){
        if(name.length() < 3 || name.length() > 16) return false;
        String patternString = "[a-zA-Z]*";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()) return false;

        return true;
    }

}
