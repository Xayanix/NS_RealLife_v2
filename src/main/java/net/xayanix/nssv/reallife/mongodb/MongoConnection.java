package net.xayanix.nssv.reallife.mongodb;


import com.google.gson.Gson;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoDatabase;
import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.family.Family;
import net.xayanix.nssv.reallife.family.FamilyManager;
import net.xayanix.nssv.reallife.logger.Logger;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.user.UserTempData;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import sun.rmi.runtime.Log;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Getter
public class MongoConnection implements Runnable {

    @Getter
    private static MongoConnection instance;
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private Gson gson;
    private File backupFile;

    @Getter
    private static boolean loading = true;


    public MongoConnection(){
        instance = this;
        gson = new Gson();
        mongoClient = MongoClients.create();
        mongoDatabase = mongoClient.getDatabase("REALLIFE");

        File folder = new File("backup");
        if(!folder.exists())
            folder.mkdirs();


        try {
            this.backupFile = new File("backup/" + TimeUtil.formatDate(System.currentTimeMillis(), "dd_MM_yy") + ".backup");
            this.backupFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Logger.info("Polaczono z MongoDB.");
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 20 * 30, 20 * 30);
    }

    public void backup(Document document){
        try {
            Files.write(backupFile.toPath(), (document.toString() + ".,.newline.,.").getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load(){
        FindIterable<Document> iterable = this.mongoDatabase.getCollection("user").find();
        iterable.forEach(document -> {
            this.backup(document);
            try {
                User user = MongoConnection.getInstance().getGson().fromJson(this.gson.toJson(document), User.class);
                user.loaded();
            } catch (Exception e) { Logger.info("Problem with deserialization: " + document.toJson()); e.printStackTrace(); }
        }, (result, t) -> {
            if(t != null)
                t.printStackTrace();

            CompanyManager.load();
            FamilyManager.load();

            loading = false;
        });

    }

    @Override
    public void run() {
        for(User user : UserManager.getUsersOnline())
            user.synchronize();

        for(Company company : CompanyManager.getCompany())
            if(company.getLastOnline() != 0)
                company.synchronize();

        for(Family family : FamilyManager.getFamillies().values())
            if(family.getLastOnline() != 0)
                family.synchronize();
    }
}
