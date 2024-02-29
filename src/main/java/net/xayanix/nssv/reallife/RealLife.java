package net.xayanix.nssv.reallife;

import lombok.Getter;
import net.xayanix.nssv.reallife.ball.Ball;
import net.xayanix.nssv.reallife.ball.BallManager;
import net.xayanix.nssv.reallife.commands.loader.CommandLoader;
import net.xayanix.nssv.reallife.configuration.Configuration;
import net.xayanix.nssv.reallife.end.EndManager;
import net.xayanix.nssv.reallife.gunshop.GunShopInventory;
import net.xayanix.nssv.reallife.job.firefighter.FireManager;
import net.xayanix.nssv.reallife.ilness.IlnessManager;
import net.xayanix.nssv.reallife.job.headhunter.HeadHunterManager;
import net.xayanix.nssv.reallife.job.medic.MedicManager;
import net.xayanix.nssv.reallife.lotto.LottoManager;
import net.xayanix.nssv.reallife.mine.MineManager;
import net.xayanix.nssv.reallife.mongodb.MongoConnection;
import net.xayanix.nssv.reallife.economy.EconomyManager;
import net.xayanix.nssv.reallife.jail.JailTask;
import net.xayanix.nssv.reallife.job.JobListener;
import net.xayanix.nssv.reallife.listeners.*;
import net.xayanix.nssv.reallife.payday.PaydayManager;
import net.xayanix.nssv.reallife.petshop.PetManager;
import net.xayanix.nssv.reallife.region.WorldGuardManager;
import net.xayanix.nssv.reallife.resourcepack.ResourceManager;
import net.xayanix.nssv.reallife.scoreboard.ScoreboardManager;
import net.xayanix.nssv.reallife.speedometer.SpeedometerTask;
import net.xayanix.nssv.reallife.stamina.StaminaManager;
import net.xayanix.nssv.reallife.tab.TabManager;
import net.xayanix.nssv.reallife.trash.TrashManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.world.WeatherManager;
import net.xayanix.nssv.reallife.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class RealLife extends JavaPlugin {

    @Getter
    public static RealLife instance;

    public void onEnable(){
        instance = this;

        new Configuration();
        new MongoConnection().load();

        new PaydayManager();
        new EndManager();
        new ResourceManager();
        new EconomyManager();
        new WorldGuardManager();
        new TrashManager();
        new FireManager();
        new LottoManager();
        new MineManager();
        new TabManager();
        new IlnessManager();
        new WeatherManager();

        new PacketListener();
        new BlockPlaceListener();
        new BlockBreakListener();
        new PlotCancelActionListener();
        new PlayerChairSitListener();
        new PlayerDamageListener();
        new PlayerSignEditListener();
        new PlayerCommandListener();
        new PlayerTeleportListener();
        new PlayerRespawnListener();
        new PlayerDamageEntityListener();
        new PlayerDropItemListener();
        new EntityCombustListener();
        new PlayerJoinListener();
        new PlayerLoginListener();
        new PlayerSneakListener();
        new PlayerDeathListener();
        new PlayerChatListener();
        new JobListener();
        new PrepareAnvilListener();
        new CitizensListener(this);
        new PlayerQuitListener();
        new PlayerInteractListener();
        new VehicleEnterListener();
        new VehicleExitListener();
        new PlayerInteractEntityListener();
        new PreTransactionListener();
        new PlayerMoveListener();
        new EntityDeathListener();
        new WeatherChangeListener();
        new PlayerArmorStandManipulateListener();
        new PlayerDismountListener();
        new PlayerFishListener();
        new WeaponPreShootListener();
        new PlayerConsumeListener();
        new ChunkLoadListener();
        new HeadHunterManager();
        new EntityVelocityListener();
        new VehiclePlaceListener();
        new PacketListener();

        new CommandLoader();

        new JailTask().start();
        new SpeedometerTask().start();

        new BallManager();
        new PetManager();
        new MedicManager();
        new StaminaManager().destroyAll();
        new ScoreboardManager();
        new WorldManager().setGameRules();

        GunShopInventory.setPrices();

        Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "citizens reload"), 20 * TimeUnit.MINUTES.toMillis(2));

    }

    public void onDisable(){
        for(User user : UserManager.getUsersOnline())
            StaminaManager.getInstance().destroy(user);
        for(Ball ball : BallManager.getInstance().getBallList())
            ball.destroy();
    }

}
