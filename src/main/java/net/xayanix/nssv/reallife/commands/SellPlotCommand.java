package net.xayanix.nssv.reallife.commands;

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
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.dhl.PackTarget;
import net.xayanix.nssv.reallife.job.gui.JobInventory;
import net.xayanix.nssv.reallife.license.gui.LicenseInventory;
import net.xayanix.nssv.reallife.misc.ItemContainer;
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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SellPlotCommand extends BukkitCommand {

    public SellPlotCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if (arg3.length != 1) {
            ChatUtil.sendMessage(arg0, "&8#&c Prawidlowe uzycie komendy:&7 /sprzedajdzialke <cena>");
            return false;
        }

        Player player = (Player) arg0;
        player.chat("/plot flag set price " + arg3[0]);
        ChatUtil.sendMessage(player, "&8#&3 Dzialka wystawiona na sprzedaz.");

        return true;
    }
}
