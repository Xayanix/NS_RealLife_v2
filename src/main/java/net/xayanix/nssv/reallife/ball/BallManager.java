package net.xayanix.nssv.reallife.ball;

import lombok.Getter;
import net.xayanix.nssv.reallife.RealLife;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class BallManager implements Runnable {

    @Getter
    private static BallManager instance;

    @Getter
    private List<Ball> ballList;

    public BallManager(){
        instance = this;
        this.ballList = new CopyOnWriteArrayList<>();
        Bukkit.getScheduler().runTaskTimer(RealLife.getInstance(), this, 20 * 10, 20 * 10);
    }

    public void spawn(Location location){
        new Ball(location);
    }

    public Ball getBall(Entity entity){
        for(Ball ball : ballList){
            if(ball.getArmorStand() == entity)
                return ball;
        }

        return null;
    }

    @Override
    public void run() {
        for(Ball ball : this.getBallList()){
            if(ball.getLastUse() + TimeUnit.MINUTES.toMillis(3) < System.currentTimeMillis())
                ball.destroy();
        }
    }
}
