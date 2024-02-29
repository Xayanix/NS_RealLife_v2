package net.xayanix.nssv.reallife.scoreboard;

import eu.haelexuis.utils.xoreboard.XoreBoard;
import eu.haelexuis.utils.xoreboard.XoreBoardPlayerSidebar;
import eu.haelexuis.utils.xoreboard.XoreBoardUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.xayanix.nssv.core.manager.AuthorizeManager;
import net.xayanix.nssv.core.objects.StaffMember;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.RandomUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.prefix.PrefixManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.utils.DoubleUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

@Getter
public class ScoreboardManager implements Runnable {

    @Getter
    private static ScoreboardManager instance;

    public ScoreboardManager() {
        instance = this;
        XoreBoardUtil.setUpBelowNames(ChatUtil.fixColors("&7poziom postaci"));
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 20 * 3, 20 * 3);
    }

    public void display(User user, int time, String title, ScoreboardLine... lines){
        if(user.getUserTempData().getSpeedometer() != null)
            return;

        XoreBoard xoreBoard = user.getUserTempData().getXoreBoard();
        XoreBoardPlayerSidebar xoreBoardPlayerSidebar = xoreBoard.getSidebar(user.getPlayer());

        HashMap<String, Integer> hashMap = new HashMap<>();
        for(ScoreboardLine line : lines)
            hashMap.put(line.getText(), line.getValue());

        xoreBoardPlayerSidebar.clearLines();
        xoreBoardPlayerSidebar.setDisplayName(ChatUtil.fixColors(title));
        xoreBoardPlayerSidebar.rewriteLines(hashMap);

        if(time > 0){
            int id = RandomUtil.getRandomNumber(999999) + 1;
            user.getUserTempData().setXoreBoardKillTaskId(id);

            Bukkit.getScheduler().runTaskLaterAsynchronously(RealLife.getInstance(), () -> {
                if(user.getUserTempData().getXoreBoardKillTaskId() == id) {
                    xoreBoardPlayerSidebar.clearLines();
                    user.getUserTempData().setXoreBoardKillTaskId(0);
                }
            }, time * 20);
        } else user.getUserTempData().setXoreBoardKillTaskId(0);



    }

    public void display(User user, int time, String title, String... lines){
        int count = 15;
        ScoreboardLine[] scoreboardLines = new ScoreboardLine[lines.length];
        for(int i = 0; i < lines.length; i++) {
            scoreboardLines[i] = new ScoreboardLine(lines[i], count);
            count--;
        }

        display(user, time, title, scoreboardLines);
    }

    public void setPrefix(Player player, String prefix, String suffix){
        Team team = XoreBoardUtil.getTeam(player.getName().toLowerCase());

        if(prefix != null){
            if(prefix.length() > 15)
                prefix = prefix.substring(0, 15);
            team.setPrefix(ChatUtil.fixColors(prefix + UserManager.getUser(player).getUserConfiguration().getNicknameColor() + " "));
        }

        if(suffix != null){
            if(suffix.length() > 15)
                suffix = suffix.substring(0, 15);
            team.setSuffix(" " + ChatUtil.fixColors(suffix));
        }

        if(!team.hasEntry(player.getName()))
            team.addEntry(player.getName());
    }

    public void updatePrefix(User user){
        if(user.getPlayer().hasPermission("helper")) {
            setPrefix(user.getPlayer(), "&8" + (user.getPlayer().getGameMode() == GameMode.CREATIVE ? "[GM 1]" : "[GM 0]"), PrefixManager.getPrefixColor(user.getPlayer()) + AuthorizeManager.getStaffMember(user.getPlayer()).getRank());
            return;
        } else if(user.getCharacter().getJail() != null){
            setPrefix(user.getPlayer(), null, "&cWIEZIEN");
        } else if(user.getCharacter().getWantedlevel() > 0){
            setPrefix(user.getPlayer(), null, "&c" + user.getCharacter().getWantedlevel() + " WL");
        } else if(user.getPlayer().hasPotionEffect(PotionEffectType.CONFUSION) || user.getPlayer().hasPotionEffect(PotionEffectType.SLOW) || user.getPlayer().hasPotionEffect(PotionEffectType.WEAKNESS)) {
            setPrefix(user.getPlayer(), null, "&cCHORY");
        } else if(user.getPlayer().getHealth() != user.getPlayer().getMaxHealth()) {
            setPrefix(user.getPlayer(), null, "&cRANNY");
        } else if(user.getPlayer().getFoodLevel() < 5) {
            setPrefix(user.getPlayer(), null, "&cGLODNY");
        } else if(user.getUserTempData().getSpeedometer() != null) {
            setPrefix(user.getPlayer(), null, "&9" + DoubleUtil.format(user.getUserTempData().getSpeedometer().getSpeed()) + " km/h");
        } else if(user.getUserTempData().getThiefProtect() > System.currentTimeMillis()) {
            setPrefix(user.getPlayer(), null, "&b[CHRONIONY]");
        } else if(user.getUserConfiguration().isPvpState()) {
            setPrefix(user.getPlayer(), null, "&e[WŁ. PVP]");
        } else if(user.getCharacter().getLevel() < 3) {
            setPrefix(user.getPlayer(), null, "&a[NOWY]");
        } else  setPrefix(user.getPlayer(), null, "");

        setPrefix(user.getPlayer(), (user.isVip() ? PrefixManager.getPrefixColor(user.getPlayer()) : "&8") +(user.getUserConfiguration().isDisplayJobOnPrefix() ? user.getCharacter().getJob().getType().getShortName().toUpperCase() : (user.isVip() ? "VIP" : "(UKRYTA)")), null);
    }

    public void updateBelowName(User user){
        XoreBoardUtil.updateBelowName(user.getPlayer(), user.getCharacter().getLevel());
    }

    @Override
    public void run() {
        for(User user : UserManager.getUsersOnline()) {
            updatePrefix(user);
        }
    }

    @AllArgsConstructor
    @Getter
    public static class ScoreboardLine {
        private String text;
        private int value;
    }

}
