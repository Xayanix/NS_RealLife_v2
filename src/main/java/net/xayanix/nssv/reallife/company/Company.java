package net.xayanix.nssv.reallife.company;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.core.basic.Logger;
import net.xayanix.nssv.core.enums.LoggerType;
import net.xayanix.nssv.reallife.mongodb.SerializeObject;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Company extends SerializeObject {

    private String name;

    @Setter
    private String description;

    private CompanyType companyType;

    @Setter
    private long bank;

    @Setter
    private long earned;

    @Setter
    private double paydayPercent;

    private List<String> owners;

    private String homeWorld;
    private double x;
    private double y;
    private double z;

    private transient Location home;

    @Setter
    private transient long lastOnline = 0;

    public Company(String name, String owner, Location home) {
        super("company", "name", name);
        this.name = name;
        this.description = "Brak opisu";
        this.bank = 0;
        this.paydayPercent = 0.5;
        this.owners = new CopyOnWriteArrayList<>();
        this.x = home.getX();
        this.y = home.getY();
        this.z = home.getZ();
        this.home = home;
        this.homeWorld = home.getWorld().getName();
        this.companyType = CompanyType.UNKNOWN;

        this.owners.add(owner);
        CompanyManager.getCompany().add(this);
    }

    public void loaded(){
        this.setHome();
        if(this.paydayPercent == 0.0)
            this.paydayPercent = 0.5;

        if(this.paydayPercent > 1)
            this.paydayPercent = 0.5;

        for(User user : UserManager.getUsers().values())
            if(user.getCharacter().getCompanyName() != null && user.getCharacter().getCompanyName().equalsIgnoreCase(this.getName()))
                user.getCharacter().setCompany(this);
    }

    public boolean isOwner(String name){
        return this.owners.contains(name);
    }

    public boolean isOwner(User user){
        return this.owners.contains(user.getName());
    }

    public void addOwner(User user){
        if(!isOwner(user))
            this.owners.add(user.getName());
    }

    public void removeOwner(User u){
        this.owners.remove(u.getName());
    }

    public String getBoss(){
        return this.owners.toString().replace("[", "").replace("]", "");
    }

    public List<User> getMembers(){
        List<User> users = new ArrayList<>();
        for(User user : UserManager.getUsers().values())
            if(user.getCharacter().getCompany() == this)
                users.add(user);

        return users;
    }

    public List<User> getMembersOnline(){
        List<User> users = new ArrayList<>();
        for(User user : UserManager.getUsersOnline())
            if(user.getCharacter().getCompany() == this)
                users.add(user);

        return users;
    }

    public void broadcast(String message){
        for(User user : this.getMembersOnline())
            user.sendMessage(message);
    }

    public void setHome(){
        this.home = new Location(Bukkit.getWorld(this.homeWorld), this.x, this.y, this.z);
    }

    public void setHome(Location home){
        this.x = home.getX();
        this.y = home.getY();
        this.z = home.getZ();
        this.home = home;
        this.homeWorld = home.getWorld().getName();
    }

    public void synchronize(){
        super.synchronize();
    }



}
