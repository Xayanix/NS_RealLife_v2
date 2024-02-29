package net.xayanix.nssv.reallife.company;

import com.mongodb.async.client.FindIterable;
import lombok.Getter;
import net.xayanix.nssv.reallife.logger.Logger;
import net.xayanix.nssv.reallife.mongodb.MongoConnection;
import net.xayanix.nssv.reallife.user.User;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompanyManager {

    @Getter
    private static List<Company> company = new CopyOnWriteArrayList<>();

    public static Company getCompany(String name){
        for(Company c : company)
            if(c.getName().equalsIgnoreCase(name))
                return c;

        return null;
    }

    public static void load(){
        FindIterable<Document> iterable = MongoConnection.getInstance().getMongoDatabase().getCollection("company").find();
        iterable.forEach(document -> {
            MongoConnection.getInstance().backup(document);
            Company company = MongoConnection.getInstance().getGson().fromJson(MongoConnection.getInstance().getGson().toJson(document), Company.class);
            company.loaded();

            if(company.getMembers().size() == 0) {
                company.delete();
                return;
            }

            CompanyManager.getCompany().add(company);
        }, (result, t) -> {
            if(t != null)
                t.printStackTrace();
        });
    }

    public static boolean isCompany(String name){
        return getCompany(name) != null;
    }

    public static void deleteCompany(Company c){
        c.delete();
        company.remove(c);

        for(User user : c.getMembers()){
            user.getCharacter().setCompany(null);
        }

    }

    public static int hasAnyMemberOnline(Company company){
        return company.getMembersOnline().size();
    }

    public static boolean isVaildCompanyName(String name){
        if(name.length() < 3 || name.length() > 16) return false;
        String patternString = "[a-zA-Z]*";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()) return false;

        return true;
    }

}
