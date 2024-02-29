package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.casino.Casino;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.family.Family;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.misc.LocationList;
import net.xayanix.nssv.reallife.misc.Repairables;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.speedometer.SpeedometerType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class AcceptCommand extends BukkitCommand {

    public AcceptCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(arg3.length < 2){
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /akceptuj <akcja> <gracz>");
            return false;
        }

        User user = UserManager.getUser(arg0.getName());
        String name = arg3[1];
        Offer offer = user.getUserTempData().getOffer(name);

        if(offer == null){
            ChatUtil.sendMessage(arg0, "&8#&c Ten gracz nie zlozyl Ci zadnej oferty.");
            return false;
        }

        if(!offer.getType().getCommandArgument().equalsIgnoreCase(arg3[0])) {
            ChatUtil.sendMessage(arg0, "&8#&c Gracz nie zlozyl ci oferty &e'" + arg3[1] + "'&c.");
            return false;
        }

        if(offer.getOfferTime() < System.currentTimeMillis()){
            ChatUtil.sendMessage(arg0, "&8#&c Oferta wygasla, popros gracza &e" + offer.getOfferer() + "&c, zeby zlozyl ja jeszcze raz.");
            return false;
        }

        User offerer = UserManager.getUser(offer.getOfferer());
        if(!offerer.isOnline()){
            ChatUtil.sendMessage(arg0, "&8#&c Oferta zostala anulowana, gdyz oferujacy gracz jest offline.");
            return false;
        }

        user.getUserTempData().removeOffer(offerer.getName());

        switch (offer.getType()){
            case FREEDOM_FROM_JAIL:
                if(arg3.length < 3){
                    ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /akceptuj <akcja> <gracz> <captcha>");
                    return false;
                }

                if(!offerer.getUserTempData().getJobCaptcha().equals(arg3[2])){
                    ChatUtil.sendMessage(arg0, "&8#&c Kod &e" + arg3[2] + "&c jest nieprawidlowy, wpisz &e" + offerer.getUserTempData().getJobCaptcha() + "&c.");
                    return false;
                }

                if(offerer.getCharacter().getJob().getType() != JobType.PRAWNIK){
                    user.sendMessage("&8#&c Gracz skladajacy oferte zmienil prace i nie jest juz prawnikiem.");
                    return false;
                }

                //if(user.getCharacter().getMoney().getWallet() < offer.getPrice()){
                //    user.sendMessage("&8#&c Nie masz &e" + offer.getPrice() + " zlotych&c, aby to zrobic.");
                //    return false;
                //}

                //if(!EconomyManager.getInstance().takeWallet(offerer, 1000)){
                //    user.sendMessage("&8#&e " + offerer.getName() + "&c nie ma &e1000 zlotych&c na pokrycie kosztów uwolnienia.");
                //    return false;
                //}

                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())) {
                    return false;
                }

                user.getCharacter().setJail(null);
                user.getPlayer().teleport(LocationList.WIEZIENIE_WEJSCIE);

                int reward = offer.getPrice() + JobType.PRAWNIK.getReward();
                offerer.sendMessage("&8#&a Uwolniles &e" + user.getName() + "&a z wiezienia i zarobiles &e" + reward + " zlotych&a.");
                user.sendMessage("&8#&a Zostales uwolniony z wiezienia przez prawnika &e" + offerer.getName() + "&a.");

                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);
                offerer.getCharacter().getJob().addReward(offer.getPrice());

                return true;
            case REDUCE_WANTED_LEVEL:
                if(arg3.length < 3){
                    ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /akceptuj <akcja> <gracz> <captcha>");
                    return false;
                }

                if(!offerer.getUserTempData().getJobCaptcha().equals(arg3[2])){
                    ChatUtil.sendMessage(arg0, "&8#&c Kod &e" + arg3[2] + "&c jest nieprawidlowy, wpisz &e" + offerer.getUserTempData().getJobCaptcha() + "&c.");
                    return false;
                }

                if(offerer.getCharacter().getJob().getType() != JobType.PRAWNIK){
                    user.sendMessage("&8#&c Gracz skladajacy oferte zmienil prace i nie jest juz prawnikiem.");
                    return false;
                }

                //if(!EconomyManager.getInstance().takeWallet(offerer, 100)){
                //    user.sendMessage("&8#&e " + offerer.getName() + "&c nie ma &e100 zlotych&c na pokrycie kosztów zbicia WL.");
                //    return false;
                //}

                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())) {
                    return false;
                }

                user.getCharacter().setWantedlevel(0);
                reward = offer.getPrice() + JobType.PRAWNIK.getReward();

                offerer.sendMessage("&8#&a Zredukowales poziom poszukiwania gracza &e" + user.getName() + "&a i zarobiles &e" + reward + " zlotych&a.");
                user.sendMessage("&8#&a Twoj poziom poszukiwania zostal zredukowany do 0 przez &e" + offerer.getName() + "&a.");

                user.sendTitle("", "&aWL = 0", 0, 30, 20);
                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);
                offerer.getCharacter().getJob().addReward(offer.getPrice());

                return true;
            case CASINO:
                Bukkit.getScheduler().runTaskAsynchronously(RealLife.getInstance(), () -> {
                    Casino casino = new Casino(user, offerer, offer.getPrice());
                    if(casino.takeMoney())
                        casino.play();
                });

                return true;
            case FUEL:
                if(user.getUserTempData().getSpeedometer() == null){
                    user.sendMessage("&8#&c Nie znajdujesz sie w pojezdzie.");
                    return false;
                }

                if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.OLD){
                    if(user.getUserTempData().getSpeedometer().getVehicleOld().getFuel()/10 >= 80){
                        user.sendMessage("&8#&c Twój pojazd nie wymaga tankowania.");
                        return false;
                    }
                } else if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.NEW) {
                    if(user.getUserTempData().getSpeedometer().getVehicleNew().getFuel() >= 80){
                        user.sendMessage("&8#&c Twój pojazd nie wymaga tankowania.");
                        return false;
                    }
                }



                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())){
                    return false;
                }

                reward = offer.getPrice() + JobType.PRACOWNIK_STACJI.getReward();

                if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.OLD)
                    user.getUserTempData().getSpeedometer().getVehicleOld().setFuel(1000);
                else if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.NEW)
                    user.getUserTempData().getSpeedometer().getVehicleNew().setFuel(100);

                user.sendMessage("&8#&b Pracownik stacji &e" + offerer.getName() + "&b zatankowal Twój pojazd za &e" + offer.getPrice() + " zlotych&b.");
                offerer.sendMessage("&8#&b Zatankowales pojazd gracza &e" + user.getName() + "&b i zarobiles &e" + reward + " zlotych&b.");

                offerer.getCharacter().getJob().addReward(offer.getPrice());
                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);

                return true;
            case REPAIR:
                if(user.getUserTempData().getSpeedometer() == null){
                    user.sendMessage("&8#&c Nie znajdujesz sie w pojezdzie.");
                    return false;
                }

                if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.OLD){
                    user.sendMessage("&8#&c Twój pojazd nie wymaga naprawy.");
                    return false;
                } else if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.NEW) {
                    if(user.getUserTempData().getSpeedometer().getVehicleNew().getHealth() == user.getUserTempData().getSpeedometer().getVehicleNew().getType().getMaxHealth()){
                        ChatUtil.sendMessage(arg0, "&8#&c Pojazd nie wymaga naprawy.");
                        return false;
                    }
                }



                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())){
                    return false;
                }

                reward = offer.getPrice() + JobType.PRACOWNIK_STACJI.getReward();
                user.getUserTempData().getSpeedometer().getVehicleNew().setHealth((float) user.getUserTempData().getSpeedometer().getVehicleNew().getType().getMaxHealth());

                user.sendMessage("&8#&b Pracownik stacji &e" + offerer.getName() + "&b naprawil Twój pojazd za &e" + offer.getPrice() + " zlotych&b.");
                offerer.sendMessage("&8#&b Naprawiles pojazd gracza &e" + user.getName() + "&b i zarobiles &e" + reward + " zlotych&b.");

                offerer.getCharacter().getJob().addReward(offer.getPrice());
                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);

                return true;
            case ANVIL:
                if(arg3.length < 3){
                    ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /akceptuj <akcja> <gracz> <captcha>");
                    return false;
                }

                if(!offerer.getUserTempData().getJobCaptcha().equals(arg3[2])){
                    ChatUtil.sendMessage(arg0, "&8#&c Kod &e" + arg3[2] + "&c jest nieprawidlowy, wpisz &e" + offerer.getUserTempData().getJobCaptcha() + "&c.");
                    return false;
                }

                ItemStack itemStack = user.getPlayer().getInventory().getItemInMainHand();
                if(itemStack == null) {
                    user.sendMessage("&8#&c Nie posiadasz zadnego przedmiotu w rece.");
                    return false;
                }

                if(!Repairables.isRepairable(itemStack.getType())){
                    user.sendMessage("&8#&c Tego przedmiotu nie mozna naprawiac.");
                    return false;
                }

                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())){
                    return false;
                }

                itemStack.setDurability((short) 0);

                reward = offer.getPrice() + JobType.KOWAL.getReward();

                offerer.getUserTempData().setJobCaptcha(null);
                user.sendMessage("&8#&b Kowal &e" + offerer.getName() + "&b naprawil przedmiot &e" + itemStack.getType() + "&b za &e" + offer.getPrice() + " zlotych&b.");
                offerer.sendMessage("&8#&b Naprawiles przedmiot gracza &e" + user.getName() + "&b i zarobiles &e" + reward + " zlotych&b.");
                offerer.isCooldownFinished("kowal." + user.getName(), TimeUnit.MINUTES.toMillis(1));

                offerer.getCharacter().getJob().addReward(offer.getPrice());
                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);

                return true;
            case FAMILY:
                if(user.getCharacter().getFamily() != null){
                    ChatUtil.sendMessage(arg0, "&8#&c Masz juz rodzine!");
                    return false;
                }

                Family family = offerer.getCharacter().getFamily();
                if(family == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Rodzina nie istnieje.");
                    return false;
                }

                user.getCharacter().setFamily(family);
                ChatUtil.broadcast("&8#&7 " + user.getName() + "&a dolaczyl do rodziny &7" + family.getName() + "&a.");
                return true;
            case PROTECTION:
                if(arg3.length < 3){
                    ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /akceptuj <akcja> <gracz> <captcha>");
                    return false;
                }

                if(user.getUserTempData().getThiefProtect() > System.currentTimeMillis()){
                    ChatUtil.sendMessage(arg0, "&8#&c Posiadasz juz aktywna ochrone przed zlodziejami.");
                    return false;
                }


                if(!offerer.getUserTempData().getJobCaptcha().equals(arg3[2])){
                    ChatUtil.sendMessage(arg0, "&8#&c Kod &e" + arg3[2] + "&c jest nieprawidlowy, wpisz &e" + offerer.getUserTempData().getJobCaptcha() + "&c.");
                    return false;
                }

                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())){
                    return false;
                }

                reward = offer.getPrice() + JobType.OCHRONA.getReward();

                user.getUserTempData().setThiefProtect(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10));
                user.sendMessage("&8#&b Ochroniarz &e" + offerer.getName() + "&b sprzedal Ci ochrone przed zlodziejami za &e" + offer.getPrice() + " zlotych&b na &e10 minut&b.");
                offerer.sendMessage("&8#&b Sprzedales ochrone graczowi &e" + user.getName() + "&b i zarobiles &e" + reward + " zlotych&b.");
                offerer.getUserTempData().setJobCaptcha(null);

                offerer.getCharacter().getJob().addReward(offer.getPrice());
                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);

                return true;
            case MARRY:
                offerer.getCharacter().setMarry(user.getName());
                user.getCharacter().setMarry(offerer.getName());
                ChatUtil.broadcast("&8#&e " + offerer.getName() + "&b i &e" + user.getName() + "&b zawieraja zwiazek malzenski.");
                ChatUtil.broadcast("&8#&e Udaj sie do kosciola, aby uczcic razem z nimi ta uroczystosc.");
                return true;
            case HEAL:
                if(arg3.length < 3){
                    ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /akceptuj <akcja> <gracz> <captcha>");
                    return false;
                }

                if(!offerer.getUserTempData().getJobCaptcha().equals(arg3[2])){
                    ChatUtil.sendMessage(arg0, "&8#&c Kod &e" + arg3[2] + "&c jest nieprawidlowy, wpisz &e" + offerer.getUserTempData().getJobCaptcha() + "&c.");
                    return false;
                }

                if(!EconomyManager.getInstance().takeWallet(user, offer.getPrice())){
                    return false;
                }

                Player player = user.getPlayer();
                player.setHealth(player.getMaxHealth());
                player.removePotionEffect(PotionEffectType.SLOW);
                player.removePotionEffect(PotionEffectType.CONFUSION);
                player.removePotionEffect(PotionEffectType.WEAKNESS);

                offerer.getCharacter().getJob().addReward(offer.getPrice());
                offerer.getCharacter().getJob().addPoint(offerer.getPlayer(), true);
                offerer.getUserTempData().setJobCaptcha(null);
                offerer.isCooldownFinished("ulecz." + player.getName(), TimeUnit.MINUTES.toMillis(5));

                ChatUtil.sendMessage(offerer.getPlayer(), "&8#&b Gracz zostal uleczony.");
                ChatUtil.sendMessage(player, "&8#&b Zostales uleczony przez medyka " + arg0.getName() + ".");
                return true;
            case COMPANY:
                if(user.getCharacter().getCompany() != null){
                    ChatUtil.sendMessage(arg0, "&8#&c Posiadasz juz firme.");
                    return false;
                }

                user.getCharacter().setCompany(offerer.getCharacter().getCompany());
                if(user.getCharacter().getCompany() != null)
                    user.getCharacter().getCompany().broadcast("&8#&a " + arg0.getName() + " &7dolaczyl do Twojej firmy.");

                return true;
            default:
                ChatUtil.sendMessage(arg0, "&8#&c Blad. Skontaktuj sie z administracja.");
                return false;
        }

    }
}
