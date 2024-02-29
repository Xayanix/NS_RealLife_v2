package net.xayanix.nssv.reallife.family;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.mongodb.async.client.FindIterable;
import lombok.Getter;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.mongodb.MongoConnection;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

public class FamilyManager {

	@Getter
	private static ConcurrentHashMap<String, Family> famillies = new ConcurrentHashMap<String, Family>();
	public static List<FamilyRank> ranks = new ArrayList<FamilyRank>();
	
	public static Family getFamily(String name){
		return famillies.get(name.toLowerCase());
	}
	
	public static void delete(String name){
		famillies.remove(name.toLowerCase());
	}
	
	public static void load(){
		FindIterable<Document> iterable = MongoConnection.getInstance().getMongoDatabase().getCollection("family").find();
		iterable.forEach(document -> {
			MongoConnection.getInstance().backup(document);
			Family family = MongoConnection.getInstance().getGson().fromJson(MongoConnection.getInstance().getGson().toJson(document), Family.class);
			family.loaded();

			famillies.put(family.getName().toLowerCase(), family);
		}, (result, t) -> {
			if(t != null)
				t.printStackTrace();
		});
	}
	
}
