package net.xayanix.nssv.reallife.license;

import net.xayanix.nssv.reallife.user.User;

public class LicenseManager {

    public static boolean hasLicense(User user, License license){
        return user.getCharacter().getLicenses().contains(license);
    }

    public static void giveLicense(User user, License license){
        if(!hasLicense(user, license))
            user.getCharacter().getLicenses().add(license);
    }

    public static void takeLicense(User user, License license){
        user.getCharacter().getLicenses().remove(license);
    }

}
