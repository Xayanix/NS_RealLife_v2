package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.offer.OfferType;
import net.xayanix.nssv.reallife.region.RegionUtil;
import net.xayanix.nssv.reallife.speedometer.SpeedometerType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RepairCarCommand extends BukkitCommand {

    public RepairCarCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length != 2){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /naprawauto <gracz> <cena>");
            return false;
        }

        Player player = Bukkit.getPlayerExact(arg3[0]);
        User user = UserManager.getUser(arg0.getName());

        if(!StringUtil.isInteger(arg3[1])){
            ChatUtil.sendMessage(arg0, "&8#&7 " + arg3[1] + "&c nie jest prawidlowa liczba.");
            return false;
        }

        int price = Integer.valueOf(arg3[1]);
        if(price < 2000 || price > 8000){
            ChatUtil.sendMessage(arg0, "&8#&c Cena musi zawierac sie w przedziale &e2000-8000 zlotych&c.");
            return false;
        }

        if(player == arg0){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /naprawauto <gracz> <cena>");
            return false;
        }

        if(user.getCharacter().getJob().getType() != JobType.PRACOWNIK_STACJI) {
            ChatUtil.sendMessage(arg0, "&8#&c Nie jestes mechanikiem.");
            return false;
        }

        if(player == null){
            ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza o tym nicku.");
            return false;
        }

        User target = UserManager.getUser(player);

        if(PlayerUtil.distanceBetweenPlayers((Player) arg0, player) > 8){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz znajduje sie za daleko od Ciebie.");
            return false;
        }

        if(target.getUserTempData().getSpeedometer() == null){
            ChatUtil.sendMessage(arg0, "&8#&c Gracz nie siedzi w pojezdzie.");
            return false;
        }


        if(target.getUserTempData().getSpeedometer().getType() == SpeedometerType.OLD){
            ChatUtil.sendMessage(arg0, "&8#&c Pojazd nie wymaga naprawy.");
            return false;
        } else if(target.getUserTempData().getSpeedometer().getType() == SpeedometerType.NEW){
            if(target.getUserTempData().getSpeedometer().getVehicleNew().getHealth() == target.getUserTempData().getSpeedometer().getVehicleNew().getType().getMaxHealth()){
                ChatUtil.sendMessage(arg0, "&8#&c Pojazd nie wymaga naprawy.");
                return false;
            }
        }


        if(!target.isCooldownFinished("naprawauto", TimeUnit.SECONDS.toMillis(15))){
            ChatUtil.sendMessage(arg0, "&8#&c Naprawiac mozesz co 15 sekund.");
            return false;
        }

        target.getUserTempData().addOffer(new Offer(arg0.getName(), price, OfferType.REPAIR));
        target.sendMessage("&8#&e " + arg0.getName() + "&b proponuje Ci naprawienie pojazdu za oplata &e" + price + " zlotych&b.");
        target.sendMessage("&8#&b Jesli akceptujesz propozycje wpisz &e/akceptuj " + OfferType.REPAIR + " " + arg0.getName() + "&b.");
        user.sendMessage("&8#&a Zaproponowales&e " + target.getName() + "&a naprawienie jego pojazdu za &e" + price + " zlotych&a.");

        return true;
    }
}
