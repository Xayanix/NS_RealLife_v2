package net.xayanix.nssv.reallife.commands;

import es.pollitoyeye.vehicles.interfaces.VehicleManager;
import net.xayanix.nssv.core.objects.ItemBuilder;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.backpack.BackPack;
import net.xayanix.nssv.reallife.bank.BankAction;
import net.xayanix.nssv.reallife.bank.gui.BankInventory;
import net.xayanix.nssv.reallife.character.CharacterInfoInventory;
import net.xayanix.nssv.reallife.compass.CompassUtil;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.gunshop.GunShopInventory;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.dhl.PackTarget;
import net.xayanix.nssv.reallife.job.gui.JobInventory;
import net.xayanix.nssv.reallife.license.License;
import net.xayanix.nssv.reallife.license.LicenseManager;
import net.xayanix.nssv.reallife.license.gui.LicenseInventory;
import net.xayanix.nssv.reallife.misc.ItemContainer;
import net.xayanix.nssv.reallife.petshop.PetShopInventory;
import net.xayanix.nssv.reallife.speedometer.SpeedometerType;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.gui.WarpInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class GuiCommand extends BukkitCommand {

    public GuiCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if (arg3.length == 0)
            return false;

        if (!(arg0 instanceof Player))
            return false;

        Player player = (Player) arg0;
        String gui = arg3[0];
        User user = UserManager.getUser(player);

        if(!user.containsCooldown("guiCommandAllow")){
            arg0.sendMessage(" : ) ");
            return false;
        }

        if(!user.isCooldownFinished("opengui", TimeUnit.SECONDS.toMillis(1))) {
            user.sendActionBar("&bOdczekaj chwile przed ponownym uzyciem.");
            return false;
        }

        if (gui.equalsIgnoreCase("mychar")) {
            CharacterInfoInventory.display(user, player);
        } else if (gui.equalsIgnoreCase("jobslist")) {
            JobInventory.display(player);
        } else if (gui.equalsIgnoreCase("licenselist")) {
            LicenseInventory.display(player);
        } else if (gui.equalsIgnoreCase("tutorial")) {
            if(user.getCharacter().getLevel() >= 3){
                user.sendMessage("&8#&c Nie jestes nowym graczem.");
                return false;
            }

            if (!user.isCooldownFinished("tutorial", TimeUnit.HOURS.toMillis(1))) {
                ChatUtil.sendMessage(player, "&8#&c Ksiazke z poradnikiem mozesz otrzymywac raz na godzine.");
                return false;
            }

            player.getInventory().addItem(ItemContainer.TUTORIAL_BOOK);
            player.getInventory().addItem(new ItemBuilder(Material.COMPASS)
                .setName("&aPrzyda Ci sie w nawigacji po miescie!")
                .toItemStack());
            user.sendTitle("", "&aOtrzymales serwerowy poradnik.", 20, 20, 20);

        } else if (gui.equalsIgnoreCase("bus")) {
            if(user.getCharacter().getJob().getType() == JobType.KURIER && user.getUserTempData().getPackTarget() != null) {
                user.sendMessage("&8#&c Nie mozesz jezdzic autobusem, gdy dostarczasz paczki.");
                user.sendMessage("&8#&c Aby moc to zrobic dostarcz ja, lub wyrzuc komenda &7/wyrzucpaczke&c.");
                return false;
            }

            WarpInventory.display(player);
        } else if (gui.equalsIgnoreCase("bankdeposit")) {
            BankInventory.display(player, BankAction.DEPOSIT);
        } else if (gui.equalsIgnoreCase("bankwithdraw")) {
            BankInventory.display(player, BankAction.WITHDRAW);
        } else if(gui.equalsIgnoreCase("bankinfo")) {
            user.sendMessage("&8#&7 Pracownik banku:&f Witamy w banku, u obslugi po lewej stronie mozecie Panstwo wyplacic gotowke z konta, a po prawej wplacic.");
        } else if(gui.equalsIgnoreCase("kuppaliwo")){
            if(user.getUserTempData().getSpeedometer() == null){
                user.sendMessage("&8# &cNie znajdujesz sie w pojezdzie.");
                return false;
            }

            if(EconomyManager.getInstance().takeWallet(user, 1500)) {
                user.sendTitle("", "&aTankowanie pojazdu...", 20, 60, 0);
                Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> {
                    if(user.getUserTempData().getSpeedometer() != null) {
                        if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.OLD)
                            user.getUserTempData().getSpeedometer().getVehicleOld().setFuel(1000);
                        else if(user.getUserTempData().getSpeedometer().getType() == SpeedometerType.NEW)
                            user.getUserTempData().getSpeedometer().getVehicleNew().setFuel(100);
                        user.sendMessage("&8#&a Pojazd zatankowany.");
                    }
                }, 60);
            }
        } else if(gui.equalsIgnoreCase("adoptujpsa")) {
            if(EconomyManager.getInstance().takeWallet(user, 50)){
                Wolf entityWolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);
                entityWolf.setOwner(player);
                entityWolf.setCustomName(ChatUtil.fixColors("&7Piesek &f(" + player.getName() + ")"));
                entityWolf.setCustomNameVisible(true);

            }
        } else if(gui.equalsIgnoreCase("kupvipmine")) {
            if(user.isVip()){
                user.sendMessage("&8#&c VIP nie musi kupowac dostepu do tej kopalni.");
                return false;
            }

            if(user.getCharacter().getVipMine() > System.currentTimeMillis()){
                user.sendMessage("&8#&a Posiadasz juz dostep do kopalni VIP do:&e " + TimeUtil.formatDate(user.getCharacter().getVipMine()));
                return false;
            }

            int price = Bukkit.getOnlinePlayers().size() * 100;

            if(!user.isCooldownFinished("kupvipmine", TimeUnit.SECONDS.toMillis(15))) {
                if(EconomyManager.getInstance().takeWallet(user, price)) {
                    user.sendTitle("&6&lKopalnia VIP", "&7Zakupiles dostep na 2 godziny.", 20, 20, 20);
                    user.getCharacter().setVipMine(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(2));
                }
            }

            user.sendMessage("&8#&c Dostep do kopalni VIP kosztuje w tej chwili okolo &6" + price + " zlotych&c.");
            user.sendMessage("&8#&c Kliknij na tego NPC jeszcze raz, aby potwierdzic zakup.");

        } else if(gui.equalsIgnoreCase("kupplecak")){
            if(user.getCharacter().getBackPack() != null){
                int freeSlots = 0;
                for(ItemStack is : user.getCharacter().getBackPack().getBackPackInventory().getContents())
                    if(is == null || is.getType() == Material.AIR)
                        freeSlots++;

                if(freeSlots != user.getCharacter().getBackPack().getBackPackInventory().getSize()) {
                    user.sendMessage("&8#&c Oproznij stary plecak zanim kupisz nowy!");
                    return false;
                }

            }

            if(!user.isCooldownFinished("backpackbuy", TimeUnit.SECONDS.toMillis(15))) {
                int price = 8500;
                if(EconomyManager.getInstance().takeWallet(user, price)) {
                    user.getCharacter().setBackPack(new BackPack(user));
                    user.sendMessage("&8#&a Otrzymales plecak.");
                    user.sendMessage("&8#&a Wpisz &e/plecak&a, aby go otworzyc.");
                    return true;
                }
            }

        } else if(gui.equalsIgnoreCase("wezpaczke")){
            if(user.getCharacter().getJob().getType() != JobType.KURIER) {
                user.sendMessage("&8#&c Nie jestes kurierem.");
                return false;
            }

            if(user.getUserTempData().getPackTarget() != null){
                ChatUtil.sendMessage(user.getPlayer(), "&8#&c Masz juz paczke, ktora musisz ja dostarczyc do &7sklepu " + user.getUserTempData().getPackTarget().getDesc() + "&c.");
                return false;
            }

            PackTarget packTarget = PackTarget.values()[RandomUtil.getRandom().nextInt(PackTarget.values().length)];
            user.getUserTempData().setPackTarget(packTarget);
            CompassUtil.setTarget(user.getPlayer(), packTarget.getLocation());

            ChatUtil.sendMessage(user.getPlayer(), "&8#&c Wziales paczke i musisz ja dostarczyc do &7sklepu " + user.getUserTempData().getPackTarget().getDesc() + "&c.");


        } else if(gui.equalsIgnoreCase("givevehicle")) {
            String manager = arg3[1];
            String vehicle = arg3[2];
            int price = Integer.valueOf(arg3[3]);

            try {
                if(EconomyManager.getInstance().takeWallet(user, price)){
                    VehicleManager vehicleManager = (VehicleManager) Class.forName("es.pollitoyeye.vehicles.vehiclemanagers." + manager + "Manager").newInstance();
                    vehicleManager.giveItem(user.getPlayer(), vehicle);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if(gui.equalsIgnoreCase("gunshop")) {
            if(!LicenseManager.hasLicense(user, License.LICENCJA_NA_BRON)) {
                user.sendMessage("&8#&c Nie posiadasz licencji na bron.");
                return false;
            }

            GunShopInventory.display(player);
        } else if(gui.equalsIgnoreCase("kupwedke")) {
            if(EconomyManager.getInstance().takeWallet(user, 250)){
                user.getPlayer().getInventory().addItem(new ItemStack(Material.FISHING_ROD, 1));
            }
        } else if(gui.equalsIgnoreCase("petshop"))
            PetShopInventory.display(player);

        return true;
    }
}
