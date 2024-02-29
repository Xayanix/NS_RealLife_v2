package net.xayanix.nssv.reallife.job;

import net.md_5.bungee.api.chat.TextComponent;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.upperlevel.spigot.book.BookUtil;

import java.util.concurrent.TimeUnit;

public class JobManager {

    public static void setJob(User user, JobType type){
        if(user.getCharacter().getJob().getType() == type){
            user.sendTitle("&c", "&cPracujesz juz jako &7" + type.getShortName() + "&c.", 20, 20, 30);
            return;
        }

        if(!user.isVip() && !user.isCooldownFinished("jobchange",TimeUnit.MINUTES.toMillis(1))) {
            user.sendMessage("&8#&c Prace mozesz zmieniac raz na minute.");
            return;
        }

        if(type.getJobCommands().size() > 0 && user.isCooldownFinished("poradnik." + type.getFullName(), TimeUnit.HOURS.toMillis(6))) {

            BookUtil.PageBuilder page = new BookUtil.PageBuilder()
                    .add(ChatUtil.fixColors("&lKOMENDY PRACY:"));

            for(String command : type.getJobCommands()) {
                page.newLine();
                page.add(command);
            }

            BookUtil.BookBuilder bookBuilder = BookUtil.writtenBook()
                    .author("NETHERSTORM")
                    .title(ChatUtil.fixColors("&aPoradnik pracy:&7 " + type.getFullName()))
                    .pages(page.build());

            user.getPlayer().getInventory().addItem(bookBuilder.build());
        }


        int earned = user.getCharacter().getJob().getEarned();

        user.getCharacter().getJobLevelMap().put(user.getCharacter().getJob().getType(), user.getCharacter().getJob().getLevel());
        user.getCharacter().setJob(new Job(type, 1, 0));

        if(user.getCharacter().getJobLevelMap().containsKey(type))
            user.getCharacter().getJob().setLevel(user.getCharacter().getJobLevelMap().get(type));
        user.getCharacter().getJob().setEarned(earned);

        user.sendMessage("&8#&7 Gratulacje wyboru nowej pracy.");
        user.sendMessage("&8#&7 Od teraz pracujesz jako &f" + type.getFullName() + "&7.");
        user.sendMessage("&8#&7 Mozesz sprawdzic postep swojej pracy w &f/praca&7.");
        user.getCharacter().setChangedJob(true);
        user.sendTitle("&a&l" + type.getFullName(), "&7Twoja nowa praca!", 20, 20, 30);
    }

    public static void broadcast(JobType jobType, TextComponent message){
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(user.getCharacter().getJob().getType() == jobType)
                user.getPlayer().sendMessage(message);
        }
    }

    public static void broadcast(JobType jobType, String message){
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(user.getCharacter().getJob().getType() == jobType)
                user.sendMessage(message);
        }
    }

    public static void sendActionBar(JobType jobType, String message){
        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);
            if(user.getCharacter().getJob().getType() == jobType)
                user.sendActionBar(message);
        }
    }

    public static int getPlayersArmountWithJob(JobType jobType){
        int players = 0;
        for(User user : UserManager.getUsersOnline())
            if(user.getCharacter().getJob().getType() == jobType)
                players++;

       return players;
    }

}
