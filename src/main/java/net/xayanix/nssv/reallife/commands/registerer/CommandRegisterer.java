package net.xayanix.nssv.reallife.commands.registerer;

import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;

public class CommandRegisterer {

    public static void register(String command, BukkitCommand bukkitCommand){
        ((CraftServer) RealLife.getInstance().getServer()).getCommandMap().register(command, bukkitCommand);
    }

}
