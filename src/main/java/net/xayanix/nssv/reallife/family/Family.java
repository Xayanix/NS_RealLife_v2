package net.xayanix.nssv.reallife.family;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.mongodb.SerializeObject;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.xayanix.nssv.core.utils.ChatUtil;

public class Family extends SerializeObject {

	private String name;

	@Setter @Getter
	private transient long lastOnline = 0;

	public Family(String name, User owner){
		super("family", "name", name);
		this.name = name;

		owner.getCharacter().setFamily(this);
		owner.getCharacter().setFamilyRank(FamilyRank.OWNER);
		
		if(this.name.length() > 11)
			this.name.substring(0, 11);
		
		FamilyManager.getFamillies().put(this.name.toLowerCase(), this);
		super.insert();
	}
	
	public String getName(){
		return this.name;
	}
	
	public List<User> getMembers(){
		List<User> users = new CopyOnWriteArrayList<>();
		for(User user : UserManager.getUsers().values())
			if(user.getCharacter().getFamily() == this)
				users.add(user);

		return users;

	}

	public boolean isOwner(User user){
		return user.getCharacter().getFamilyRank() == FamilyRank.OWNER;
	}
	
	public String getFormattedMembers(){
		String members = "";

		for(User user : this.getMembers()){
			Player player = user.getPlayer();
			members = members + "&7, (" + user.getCharacter().getFamilyRank().getPrefix() + "&7) (" + (player != null ? "&aonline" : "&coffline") + "&7)&7 " + user.getName();
		}
		members = members.replaceFirst("&7, ", "");

		
		return members;
				
	}

	@Override
	public void delete(){
		FamilyManager.delete(this.name);
		super.delete();

		for(User member : this.getMembers())
			member.getCharacter().setFamily(null);

	}
	
	public void broadcast(String message){
		for(User user : UserManager.getUsersOnline()){
			if(user.getCharacter().getFamily() == this)
				user.sendMessage(message);
		}
	}


	public void loaded() {
		for(User user : UserManager.getUsers().values())
			if(user.getCharacter().getFamilyName() != null && user.getCharacter().getFamilyName().equalsIgnoreCase(this.getName())) {
				FamilyRank familyRank = user.getCharacter().getFamilyRank();
				user.getCharacter().setFamily(this);
				user.getCharacter().setFamilyRank(familyRank);
			}
	}
}
