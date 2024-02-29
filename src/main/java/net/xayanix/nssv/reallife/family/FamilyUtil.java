package net.xayanix.nssv.reallife.family;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FamilyUtil {

	private static String patternString = "[^a-zA-Z]*";
	private static Pattern pattern = Pattern.compile(patternString);

	public static boolean isVaildName(String name) {
		Matcher matcher = pattern.matcher(name);
		if (matcher.matches())
			return false;
		if (name.length() < 4 || name.length() > 11)
			return false;

		return true;
	}

}
