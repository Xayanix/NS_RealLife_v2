package net.xayanix.nssv.reallife.listeners;

import com.intellectualcrafters.plot.object.Plot;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.bank.BankAction;
import net.xayanix.nssv.reallife.bank.gui.BankInventory;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.job.firefighter.FireManager;
import net.xayanix.nssv.reallife.plotdev.PlotManager;
import net.xayanix.nssv.reallife.trash.TrashManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.TimeUnit;

public class PlayerInteractListener implements Listener {

    public PlayerInteractListener() {
        Bukkit.getPluginManager().registerEvents(this, RealLife.getInstance());
    }

    @EventHandler
    public void onEvent(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player);
        Block block = event.getClickedBlock();

        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(user.getCharacter().getJob().getType() == JobType.SPRZATACZ) {
                if (user.isCooldownFinished("trashclean", TimeUnit.SECONDS.toMillis(15)))
                    TrashManager.getInstance().clean(user, block.getLocation());
            } else if(user.getCharacter().getJob().getType() == JobType.STRAZAK)
                FireManager.getInstance().extenguish(user, block.getLocation());

        } else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(user.getCharacter().getJail() != null)
                event.setCancelled(true);

            /*switch (block.getType()) {
                case SILVER_SHULKER_BOX:
                case RED_SHULKER_BOX:
                case BLUE_SHULKER_BOX:
                case CYAN_SHULKER_BOX:
                case GRAY_SHULKER_BOX:
                case LIME_SHULKER_BOX:
                case PINK_SHULKER_BOX:
                case BLACK_SHULKER_BOX:
                case BROWN_SHULKER_BOX:
                case GREEN_SHULKER_BOX:
                case WHITE_SHULKER_BOX:
                case ORANGE_SHULKER_BOX:
                case PURPLE_SHULKER_BOX:
                case YELLOW_SHULKER_BOX:
                case MAGENTA_SHULKER_BOX:
                case LIGHT_BLUE_SHULKER_BOX:
                    event.setCancelled(true);
                    ChatUtil.sendMessage(player, "&8#&c Shulker boxy sa zablokowane na tym serwerze.");
                    return;
            }*/

            if(event.getItem() != null){
                switch (event.getItem().getType()){
                    case ARMOR_STAND:
                        Plot plot = PlotManager.getApi().getPlot(block.getLocation());

                        if(!player.hasPermission("admin"))
                            if(plot != null && (!plot.isOwner(player.getUniqueId()) && !plot.isAdded(player.getUniqueId())))
                                event.setCancelled(true);
                        break;
                }
            }

        }

        if(block == null || (block.getType() != Material.SIGN && block.getType() != Material.WALL_SIGN && block.getType() != Material.SIGN_POST))
            return;

        Sign sign = (Sign) block.getState();
        String shop = sign.getLine(0);

        if(shop.equalsIgnoreCase(ChatUtil.fixColors("&9[Bankomat]")))
            BankInventory.display(player, BankAction.WITHDRAW);

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void groundVehiclePlacing(final PlayerInteractEvent blockPlaceEvent) {
        /*
        if(blockPlaceEvent.getClickedBlock() == null)
            return;

        final ItemStack itemInHand = blockPlaceEvent.getPlayer().getItemInHand();
        if (itemInHand != null && itemInHand.getType() == Material.CHEST && itemInHand.hasItemMeta()) {
            final ItemMeta itemMeta = itemInHand.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                final String displayName = itemMeta.getDisplayName();
                final VehiclesMain plugin = VehiclesMain.getPlugin();
                VehicleType vehicleType = null;
                VehicleSubType vehicleSubType = null;
                for (final VehicleType vehicleType2 : plugin.vehicleSubTypesMap.keySet()) {
                    for (final VehicleSubType vehicleSubType2 : plugin.vehicleSubTypesMap.get(vehicleType2)) {
                        if (vehicleSubType2.getDisplayName().equals(displayName)) {
                            if (vehicleType2.getPlaceLocation() == PlaceLocation.GROUND) {
                                vehicleSubType = vehicleSubType2;
                                break;
                            }
                            if (vehicleType2.getPlaceLocation() == PlaceLocation.WATER) {
                                blockPlaceEvent.setCancelled(true);
                                blockPlaceEvent.getPlayer().sendMessage(plugin.getLang().getValue(String.valueOf(vehicleType2.getConfigName().toLowerCase()) + "-onlyWater"));
                            }
                            else {
                                if (vehicleType2.getPlaceLocation() != PlaceLocation.AIR) {
                                    continue;
                                }
                                blockPlaceEvent.setCancelled(true);
                                blockPlaceEvent.getPlayer().sendMessage(plugin.getLang().getValue(String.valueOf(vehicleType2.getConfigName().toLowerCase()) + "-wrongPlace"));
                            }
                        }
                    }
                    if (vehicleSubType != null) {
                        vehicleType = vehicleType2;
                        break;
                    }
                }
                if (vehicleSubType != null) {
                    final Player player = blockPlaceEvent.getPlayer();
                    blockPlaceEvent.setCancelled(true);
                    if (plugin.allowedWorlds.contains(blockPlaceEvent.getPlayer().getWorld().getName()) || blockPlaceEvent.getPlayer().hasPermission(String.valueOf(vehicleType.getConfigName()) + "s.overrideWorld")) {
                        if (vehicleSubType.getPermission() != null && !player.hasPermission(vehicleSubType.getPermission())) {
                            player.sendMessage(plugin.getLang().getValue(String.valueOf(vehicleType.getConfigName().toLowerCase()) + "-use-nopermission"));
                            return;
                        }
                        final Location clone = blockPlaceEvent.getClickedBlock().getLocation().add(0.0, 1.0, 0.0).clone();
                        final VehiclePlaceEvent vehiclePlaceEvent = new VehiclePlaceEvent(player, clone, vehicleType, vehicleSubType.getName());
                        Bukkit.getPluginManager().callEvent(vehiclePlaceEvent);
                        if (!vehiclePlaceEvent.isCancelled()) {
                            if (blockPlaceEvent.getHand() == EquipmentSlot.OFF_HAND) {
                                player.getInventory().setItemInOffHand(new ItemStack(Material.AIR));
                            }
                            else {
                                player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                            }
                            player.updateInventory();
                            final ArmorStand spawn = vehicleType.getVehicleManager().spawn(clone, player.getUniqueId().toString(), vehicleSubType.getName());
                            if (plugin.vehicleHealthEnabled) {
                                spawn.setMaxHealth(vehicleSubType.getMaxHealth());
                                spawn.setHealth(vehicleSubType.getMaxHealth());
                                try {
                                    for (final String s : itemInHand.getItemMeta().getLore()) {
                                        if (s.startsWith(plugin.getLang().getValue("package-health-prefix"))) {
                                            spawn.setHealth(Double.parseDouble(s.replace(plugin.getLang().getValue("package-health-prefix"), "").split("/")[0].replace(",", ".")));
                                            break;
                                        }
                                    }
                                }
                                catch (Exception ex) {}
                            }
                            if (plugin.fuelEnabled) {
                                try {
                                    for (final String s2 : itemInHand.getItemMeta().getLore()) {
                                        if (s2.startsWith(plugin.getLang().getValue("package-fuel-prefix"))) {
                                            EntityUtils.setCurrentFuel(spawn, Double.parseDouble(s2.replace(plugin.getLang().getValue("package-fuel-prefix"), "").split("/")[0].replace(",", ".")));
                                            break;
                                        }
                                    }
                                }
                                catch (Exception ex2) {
                                    EntityUtils.setCurrentFuel(spawn, 0.0);
                                }
                            }
                        }
                    }
                    else {
                        player.sendMessage(plugin.getLang().getValue(String.valueOf(vehicleType.getConfigName().toLowerCase()) + "-worldDisabled"));
                    }
                }
            }
        }
        */
    }
}
