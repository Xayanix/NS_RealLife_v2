package net.xayanix.nssv.reallife.user;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.character.Character;
import net.xayanix.nssv.reallife.job.Job;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.mongodb.SerializeObject;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class User extends SerializeObject {

    private String name;
    private transient UserTempData userTempData;
    private Character character;
    private UserConfiguration userConfiguration;

    @Setter
    private transient Player player;

    @Setter
    private long lastActiveTime;
    private transient boolean isNew;

    @Setter
    private transient boolean isVip;

    private transient ConcurrentHashMap<String, Long> cooldownMap;

    public User(String name){
        super("user", "name", name);
        this.name = name;
        this.character = new Character(this, "Brak", "Brak", 0, new Job(JobType.BEZROBOTNY, 1, 0));
        this.cooldownMap = new ConcurrentHashMap<>();
        this.isNew = true;
        this.lastActiveTime = System.currentTimeMillis();
        this.userTempData = new UserTempData();
        this.isVip = false;
        this.userConfiguration = new UserConfiguration();

        UserManager.getUsers().put(this.name.toLowerCase(), this);
    }

    public void loaded(){
        this.userTempData = new UserTempData();
        this.isNew = false;
        this.cooldownMap = new ConcurrentHashMap<>();
        this.isVip = false;

        if(this.getCharacter().getBackPack() != null)
            this.getCharacter().getBackPack().deserialize();

        this.character.loaded();

        UserManager.getUsers().put(this.name.toLowerCase(), this);
    }

    public boolean isVip(){
        return (this.getPlayer() != null ? this.getPlayer().hasPermission("vip") : this.isVip);
    }

    public Player getPlayer(){
        return this.player;
    }

    public boolean isOnline(){
        return (this.getPlayer() != null);
    }

    public void sendMessage(String message){
        Player player = this.getPlayer();
        if(player != null)
            ChatUtil.sendMessage(player, message);
    }

    public void sendTitle(String title, String subtitle, int fadein, int stay, int fadeout){
        Player player = this.getPlayer();
        if(player != null)
            player.sendTitle(ChatUtil.fixColors(title), ChatUtil.fixColors(subtitle), fadein, stay, fadeout);
    }

    public void sendActionBar(String bar){
        Player player = this.getPlayer();
        if(player != null)
            player.sendActionBar(ChatUtil.fixColors(bar));
    }

    public boolean isCooldownFinished(String key, long cooldown, boolean put){
        if(!cooldownMap.containsKey(key)){
            cooldownMap.put(key, System.currentTimeMillis() + cooldown);
            return true;
        }

        if(cooldownMap.get(key) > System.currentTimeMillis())
            return false;

        if(put)
            cooldownMap.put(key, System.currentTimeMillis() + cooldown);
        return true;
    }

    public boolean isCooldownFinished(String key, long cooldown){
        return this.isCooldownFinished(key, cooldown, true);
    }

    public boolean containsCooldown(String key){
        return this.cooldownMap.containsKey(key);
    }

    public void cancelCooldown(String key){
        this.cooldownMap.remove(key);
    }

    @Override
    public void synchronize(){
        if(this.getCharacter().getBackPack() != null)
            this.getCharacter().getBackPack().serialize();

        super.synchronize();
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
