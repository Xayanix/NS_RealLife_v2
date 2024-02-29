package net.xayanix.nssv.reallife.commands;

import com.intellectualcrafters.plot.flag.Flag;
import com.intellectualcrafters.plot.flag.FlagManager;
import com.intellectualcrafters.plot.object.Plot;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.ball.BallManager;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.configuration.Configuration;
import net.xayanix.nssv.reallife.job.firefighter.FireManager;
import net.xayanix.nssv.reallife.jail.JailUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.lotto.LottoManager;
import net.xayanix.nssv.reallife.mine.MineManager;
import net.xayanix.nssv.reallife.mongodb.MongoConnection;
import net.xayanix.nssv.reallife.payday.PaydayManager;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.stamina.StaminaManager;
import net.xayanix.nssv.reallife.trash.TrashManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import net.xayanix.nssv.reallife.wantedlevel.WantedLevelManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AdminCommand extends BukkitCommand {

    public AdminCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length == 0 || !arg0.hasPermission("admin")){
            return false;
        }

        String name = arg3[0];
        User user = UserManager.getUser(arg0.getName());

       if(name.equalsIgnoreCase("przystanek")) {
           Bukkit.dispatchCommand(arg0, "npc create &6Busiarz");
           Bukkit.dispatchCommand(arg0, "npcmd add noperms opengui bus");
           arg0.sendMessage("Gotowe.");
       } else if(name.equalsIgnoreCase("payday")) {
           PaydayManager.getInstance().realize();
       } else if(name.equalsIgnoreCase("renameneararmorstands")){
           Player player = (Player) arg0;
           for(Entity entity : player.getNearbyEntities(3, 3, 3)) {
               if(entity.getType() == EntityType.ARMOR_STAND) {
                   ArmorStand armorStand = (ArmorStand) entity;
                   player.sendMessage("Renaming " + armorStand.getCustomName());
                   armorStand.setCustomName("SKLEPAUTPOJAZD");
               }
           }
       } else if(name.equalsIgnoreCase("removeneararmorstands")){
           Player player = (Player) arg0;
           for(Entity entity : player.getNearbyEntities(3, 3, 3)) {
               if(entity.getType() == EntityType.ARMOR_STAND) {
                    entity.remove();
               }
           }
       } else if(name.equalsIgnoreCase("mongodrop")){
           MongoConnection.getInstance().getMongoDatabase().getCollection("users").drop((result, t) ->  System.out.println("Done"));
           arg0.sendMessage("Done.");
       } else if(name.equalsIgnoreCase("saveme")){
           UserManager.getUser(arg0.getName()).synchronize();
           arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("synctest")){
           UserManager.getUser(arg0.getName()).synchronize();
           UserManager.getUsers().clear();
           MongoConnection.getInstance().load();
           arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("findtest")) {
           UserManager.getUser(arg0.getName()).find();
       } else if(name.equalsIgnoreCase("setflags")) {
           RegionUtil.setPvpFlags();
           arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("fireup")){
           ((Player) arg0).teleport(FireManager.getInstance().fire());
       } else if(name.equalsIgnoreCase("renewmine")) {
           if(MineManager.getInstance().getBukkitTask() != null)
               MineManager.getInstance().getBukkitTask().cancel();
           MineManager.getInstance().run();
       } else if(name.equalsIgnoreCase("changeowners")) {
           PlotManager.changeOwner((Player) arg0);
       } else if(name.equalsIgnoreCase("dropplot")) {
           PlotManager.removePlots((Player) arg0);
       } else if(name.equalsIgnoreCase("addflag")) {
           for(Plot plot : PlotManager.getApi().getPlayerPlots((Player) arg0)){
               Flag flag = FlagManager.getFlag("use");
               plot.setFlag(flag, flag.parseValue("0:0"));
               arg0.sendMessage(".");
           }

           arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("spawnball")) {
           BallManager.getInstance().spawn(((Player) arg0).getLocation());
       } else if(name.equalsIgnoreCase("workstamina")) {
           StaminaManager.getInstance().work(UserManager.getUser(arg0.getName()));
       } else if(name.equalsIgnoreCase("jail")) {
           JailUtil.jail(Bukkit.getPlayerExact(arg3[1]), Integer.valueOf(arg3[2]));
       } else if(name.equalsIgnoreCase("kolornicku")) {
           user = UserManager.getUser(arg3[1]);
           if(user != null){
               user.getUserConfiguration().setNicknameColor(ChatUtil.fixColors(arg3[2]));
               user.getUserConfiguration().setNicknameExpire(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(Integer.valueOf(arg3[3])));
               user.synchronize();
               arg0.sendMessage("OK");
           }
       } else if(name.equalsIgnoreCase("tracker")) {
           int tracker = Integer.parseInt(arg3[1]);
           Configuration.getInstance().setPlayerTrackerDistance(tracker);
           for(Player player : Bukkit.getOnlinePlayers())
               PlayerUtil.enablePlayerTracker(player);

           arg0.sendMessage("OK " + tracker);
       } else if(name.equalsIgnoreCase("trashclear")) {
           TrashManager.getInstance().getTrashLocation().clear();
           arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("skasujfirme")) {
            CompanyManager.deleteCompany(CompanyManager.getCompany(arg3[1]));
            arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("getallarmorstands")){
           for(Entity entity : UserManager.getUser(arg0.getName()).getPlayer().getWorld().getEntities()) {
               if(entity.getType() == EntityType.ARMOR_STAND){
                   ArmorStand armorStand = (ArmorStand) entity;
                   if(armorStand.getCustomName() != null) {
                       ChatUtil.sendMessage(arg0, armorStand.getCustomName() + " ");
                       System.out.println(armorStand.getCustomName());
                   }
               }
           }

           ChatUtil.broadcast(UserManager.getUser(arg0.getName()).getPlayer().getWorld().getEntities().size() + " ogolem");
       } else if(name.equalsIgnoreCase("setreward")) {
           user.getCharacter().getJob().setEarned(100);
           user.getCharacter().getJob().setLevel(5 + RandomUtil.getRandomNumber(6));

           arg0.sendMessage("OK");
       } else if(name.equalsIgnoreCase("prace")){
           for(JobType jobType : JobType.values()){
               int online = 0;
               for(User user1 : UserManager.getUsers().values())
                   if(user1.getCharacter().getJob().getType() == jobType)
                       online++;

               ChatUtil.sendMessage(arg0, jobType.getFullName() + ": " + online);
           }
       } else if(name.equalsIgnoreCase("praceo")){
           for(JobType jobType : JobType.values()){
               int online = 0;
               for(User user1 : UserManager.getUsersOnline())
                   if(user1.getCharacter().getJob().getType() == jobType)
                       online++;

               ChatUtil.sendMessage(arg0, jobType.getFullName() + ": " + online);
           }
       } else if(name.equalsIgnoreCase("deluser")){

           MongoConnection.getInstance().getMongoDatabase().getCollection("user").deleteMany(new Document("name", arg3[1]), (deleteResult, throwable) -> {
               arg0.sendMessage(deleteResult.getDeletedCount() + "");
           });

          // User user1 = UserManager.getUser(arg3[1]);
          // user1.getCharacter().getMoney().removeFromWallet(user1.getCharacter().getMoney().getWallet());
          // user1.getCharacter().getMoney().removeFromBank(user1.getCharacter().getMoney().getBank());
          // user1.delete();

           //UserManager.getUsers().remove(user1.getName().toLowerCase());
           //if(user1.getPlayer() != null)
               //user1.getPlayer().kickPlayer("");

           //arg0.sendMessage("Skasowany.");

       } else if(name.equalsIgnoreCase("lotto"))
           arg0.sendMessage("Aktualna nagroda: " + LottoManager.getInstance().getWinNumber());
       else if(name.equalsIgnoreCase("bank")) {
           User user1 = UserManager.getUser(arg3[1]);
           user1.getCharacter().getMoney().addToBank(Integer.valueOf(arg3[2]));

           arg0.sendMessage("OK");
           user1.synchronize();
       } else if(name.equalsIgnoreCase("wallet")) {
           User user1 = UserManager.getUser(arg3[1]);
           user1.getCharacter().getMoney().addToWallet(Integer.valueOf(arg3[2]));

           arg0.sendMessage("OK");
           user1.synchronize();
       } else if(name.equalsIgnoreCase("wl")) {
           WantedLevelManager.addWantedLevel(user, "ACMD", 1);
       }

        return true;
    }
}
