package net.xayanix.nssv.reallife.actionbar;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBar {

	private String message;

	public ActionBar(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void sendActionBar(Player player) {
		player.sendActionBar(ChatUtil.fixColors(this.message));
	}

	public void sendActionBar(final Player p, final int seconds) {
		new BukkitRunnable() {
			int sec = seconds;

			@Override
			public void run() {
				if (sec > 0) {
					if (p != null && p.isOnline()) {
						sendActionBar(p);
						sec--;
						return;
					}
				}
				cancel();
			}
		}.runTaskTimer(RealLife.getInstance(), 1, 20);
	}

	@SuppressWarnings("deprecation")
	public void broadcastActionBar() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			sendActionBar(p);
		}
	}
}
