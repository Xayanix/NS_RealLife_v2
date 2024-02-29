package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.family.Family;
import net.xayanix.nssv.reallife.family.FamilyManager;
import net.xayanix.nssv.reallife.family.FamilyRank;
import net.xayanix.nssv.reallife.family.FamilyUtil;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.offer.OfferType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.xayanix.nssv.core.utils.ChatUtil;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class FamilyCommand extends BukkitCommand {

	public FamilyCommand(String name, String... aliases) {
		super(name);
		this.description = "Default command.";
		this.setAliases(Arrays.asList(aliases));
	}

	@Override
	public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
		if(arg3.length == 0){
			ChatUtil.sendMessage(arg0, "&8&m---------------&8[&9 RODZINA&8 ]&m---------------");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina zaloz <nazwa>&7 - zaklada rodzine");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina dodaj <nick>&7 - wysyla zaproszenie do rodziny");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina wyrzuc <nick>&7 - wyrzuca z rodziny");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina info <nazwa>&7 - informacje o rodzinie");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina dolacz <nazwa>&7 - dolacza do rodziny");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina zmienrange <gracz>&7 - zmienia range w rodzinie");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina opusc&7 - opuszcza rodzine");
			ChatUtil.sendMessage(arg0, "&8 »&9 /rodzina usun&7 - usuwa rodzine");
			ChatUtil.sendMessage(arg0, "&8 »&9 Czat rodziny:&7 @wiadomosc");
			ChatUtil.sendMessage(arg0, "&8&m---------------&8[&9 RODZINA&8 ]&m---------------");
			return false;
		}
		
		String ar1 = arg3[0];
		User user = UserManager.getUser(arg0.getName());
	
		if(arg3.length == 2){
			String ar2 = arg3[1];
			if(ar1.equalsIgnoreCase("zaloz")){
				if(user.getCharacter().getFamily() != null){
					ChatUtil.sendMessage(arg0, "&8#&c Masz juz rodzine!");
					return false;
				}
				
				if(!FamilyUtil.isVaildName(ar2)){
					ChatUtil.sendMessage(arg0, "&8#&c Nazwa rodziny jest nieprawidlowa, musi skladac sie z znakow od A do Z oraz posiadac min. 4 znaki a maks. 11.");
					return false;
				}
				
				if(FamilyManager.getFamily(ar2) != null) {
					ChatUtil.sendMessage(arg0, "&8#&c Istnieje juz rodzina o tej nazwie.");
					return false;
				}

				if(!user.isCooldownFinished("rodzinazaloz",TimeUnit.MINUTES.toMillis(1))) {
					user.sendMessage("&8#&c Rodzine mozesz zakaldac raz na minute.");
					return false;
				}

				if(!EconomyManager.getInstance().takeWallet(user,200)){
					return false;
				}
				
				new Family(ar2, user);
				ChatUtil.broadcast("&8#&7 " + user.getName() + "&a zalozyl rodzine &7" + ar2 + "&a.");
				
				
			} else if(ar1.equalsIgnoreCase("dodaj")){
				Player player = Bukkit.getPlayerExact(ar2);
				User target = UserManager.getUser(ar2);
				
				if(player == null || target == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
					return false;
				}
				
				if(user.getCharacter().getFamily().getMembers().size() >= (arg0.hasPermission("vip") ? 30 : 25)){
					ChatUtil.sendMessage(arg0, "&8#&c Osiagnieto maksymalna liczbe czlonkow.");
					return false;
				}
				
				if(user.getCharacter().getFamily() == null || !user.getCharacter().getFamily().isOwner(user)){
					ChatUtil.sendMessage(arg0, "&8#&c Nie masz rodziny lub nie jestes jej zalozycielem.");
					return false;
				}
				
				if(target.getCharacter().getFamily() != null){
					ChatUtil.sendMessage(arg0, "&8#&c Gracz ma rodzine.");
					return false;
				}
				
				ChatUtil.sendMessage(arg0, "&8#&a Zaproszenie zostalo wyslane.");
				ChatUtil.sendMessage(player, "&8#&a Otrzymales zaproszenie do rodziny. Wpisz &7/akceptuj " + OfferType.FAMILY + " " + user.getName() + "&a, aby dolaczyc.");

				target.getUserTempData().addOffer(new Offer(arg0.getName(), 0, OfferType.FAMILY));
				
				
			} else if(ar1.equalsIgnoreCase("info")){
				Family family = FamilyManager.getFamily(ar2);
				
				if(family == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono rodziny.");
					return false;
				}
				
				ChatUtil.sendMessage(arg0, "&8&m---------------&8[&9 RODZINA&8 ]&m---------------");
				ChatUtil.sendMessage(arg0, "&8 »&9 Nazwa rodziny:&7 " + family.getName());
				ChatUtil.sendMessage(arg0, "&8 »&9 Czlonkowie rodziny:&7 " + family.getFormattedMembers());
				ChatUtil.sendMessage(arg0, "&8&m---------------&8[&9 RODZINA&8 ]&m---------------");
				
			}
			else if(ar1.equalsIgnoreCase("wyrzuc")){
				User target = UserManager.getUser(ar2);
				
				if(target == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
					return false;
				}

				if(user.getCharacter().getFamily() == null || !user.getCharacter().getFamily().isOwner(user)){
					ChatUtil.sendMessage(arg0, "&8#&c Nie masz rodziny lub nie jestes jej zalozycielem.");
					return false;
				}
				
				if(target.getCharacter().getFamily() == null || target.getCharacter().getFamily() != user.getCharacter().getFamily()){
					ChatUtil.sendMessage(arg0, "&8#&c Gracz nie ma rodziny lub jest w innej.");
					return false;
				}
				
				if(target == user){
					ChatUtil.sendMessage(arg0, "&8#&c Nie mozesz wyrzucic siebie.");
					return false;
				}
				
				ChatUtil.sendMessage(arg0, "&8#&c Gracz zostal wyrzucony.");
				target.getCharacter().setFamily(null);
				
			} else if(ar1.equalsIgnoreCase("zmienrange")){
				
				Player player = Bukkit.getPlayerExact(ar2);
				User target = UserManager.getUser(ar2);
				
				if(player == null || target == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
					return false;
				}
				
				if(user.getCharacter().getFamily() == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie masz rodziny!");
					return false;
				}
				
				if(!user.getCharacter().getFamily().isOwner(user) && target != user){
					ChatUtil.sendMessage(arg0, "&8#&c Nie jestes zalozycielem.");
					return false;
				}
				
				if(target == user && user.getCharacter().getFamily().isOwner(user)){
					ChatUtil.sendMessage(arg0, "&8#&c Nie mozesz zmienic swojej rangi, gdy jestes glowa rodziny.");
					return false;
				}
				
				if(target.getCharacter().getFamily() == null || target.getCharacter().getFamily() != user.getCharacter().getFamily()){
					ChatUtil.sendMessage(arg0, "&8#&c Gracz nie ma rodziny lub nie nalezy do Twojej.");
					return false;
				}
				
				
				final Player receipient = (Player) arg0;
				final ComponentBuilder message = new ComponentBuilder(ChatUtil.fixColors("&8#&c Wybierz range (&7kliknij na nia&c): "));
				for (FamilyRank rank : FamilyManager.ranks) {
				    message.append(ChatColor.GRAY + rank.getColor() + ChatUtil.fixColors("&l") + rank.getDescription().toUpperCase() + ", ");
				    message.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rodzina ranga " + ar2 + " " + rank.toString()));
				    message.event(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(ChatUtil.fixColors("&7Kliknij, aby przyznac rodzinna range &6" + rank.getDescription() + "&7."))));
				}
				receipient.spigot().sendMessage(message.create());
			} else ChatUtil.sendMessage(arg0, "&8#&c Nieprawidlowa komenda lub uzyta w niewlasciwy sposob.");
			
			return true;
		} else if(arg3.length == 3){
			String ar2 = arg3[1];
			String ar3 = arg3[2];
			
			if(ar1.equalsIgnoreCase("ranga")){
				Player player = Bukkit.getPlayerExact(ar2);
				User target = UserManager.getUser(ar2);
				
				if(player == null || target == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
					return false;
				}
				
				if(user.getCharacter().getFamily() == null){
					ChatUtil.sendMessage(arg0, "&8#&c Nie masz rodziny!");
					return false;
				}
				
				if(!user.getCharacter().getFamily().isOwner(user) && target != user){
					ChatUtil.sendMessage(arg0, "&8#&c Nie jestes zalozycielem.");
					return false;
				}
				
				if(target == user && user.getCharacter().getFamily().isOwner(user)){
					ChatUtil.sendMessage(arg0, "&8#&c Nie mozesz zmienic swojej rangi, gdy jestes glowa rodziny.");
					return false;
				}
				
				if(target.getCharacter().getFamily() == null || target.getCharacter().getFamily() != user.getCharacter().getFamily()){
					ChatUtil.sendMessage(arg0, "&8#&c Gracz nie ma rodziny lub nie nalezy do Twojej.");
					return false;
				}
				
				try{
					FamilyRank rank = FamilyRank.valueOf(ar3);
					if(rank == FamilyRank.OWNER){
						ChatUtil.sendMessage(arg0, "&8#&c Nie mozesz mianowac kogos glowa rodziny.");
						return false;
					}
					target.getCharacter().setFamilyRank(rank);
					ChatUtil.sendMessage(arg0, "&8#&a Ranga gracza zostala zmieniona.");
				} catch(Exception e) { ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono takiej rangi."); }
				return true;
				
			} else ChatUtil.sendMessage(arg0, "&8#&c Nieprawidlowa komenda lub uzyta w niewlasciwy sposob.");
			
			return true;
			
		}
		
		if(ar1.equalsIgnoreCase("opusc")){
			if(user.getCharacter().getFamily() != null){
				if(user.getCharacter().getFamily().isOwner(user)){
					ChatUtil.sendMessage(arg0, "&8#&c Glowa rodziny nie moze jej opuscic.");
					return false;
				}
				ChatUtil.broadcast("&8#&c " + arg0.getName() + " &7opuscil rodzine &c" + user.getCharacter().getFamily().getName() + "&7.");
				user.getCharacter().setFamily(null);
			} else ChatUtil.sendMessage(arg0, "&8#&c Nie masz rodziny.");
		} else if(ar1.equalsIgnoreCase("usun")){
			if(user.getCharacter().getFamily() != null){
				if(user.getCharacter().getFamily().isOwner(user)){
					ChatUtil.broadcast("&8#&c " + arg0.getName() + " &7usunal rodzine &c" + user.getCharacter().getFamily().getName() + "&7.");
					user.getCharacter().getFamily().delete();
				} else ChatUtil.sendMessage(arg0, "&8#&c Nie jestes zalozycielem.");
			} else ChatUtil.sendMessage(arg0, "&8#&c Nie masz rodziny.");
		} else ChatUtil.sendMessage(arg0, "&8#&c Nieprawidlowa komenda lub uzyta w niewlasciwy sposob.");
		
		
		return true;
	}

}
