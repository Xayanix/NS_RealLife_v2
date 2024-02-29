package net.xayanix.nssv.reallife.stamina;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;

@AllArgsConstructor
@Setter
@Getter
public class Stamina {

    private int currentStamina;
    private double progress;
    private transient ArmorStand staminaWorker;

    public void setHealth(User user){
        if(this.currentStamina == 1){
            user.getPlayer().setMaxHealth(20);
            user.getPlayer().setWalkSpeed(0.2f);
            return;
        }

        double health = 20 + this.currentStamina*2;
        Bukkit.getScheduler().runTaskLater(RealLife.getInstance(), () -> {
            if(user.getPlayer() != null)
                user.getPlayer().setMaxHealth(health > 40 ? 40 : health);
            float walkSpeed = (float) (0.2f + (this.currentStamina*2 / 150.0));
            if(walkSpeed > 0.4f)
                walkSpeed = 0.4f;
            user.getPlayer().setWalkSpeed(walkSpeed);
        }, 15);
    }

    public void setCurrentStamina(int stamina){
        if(stamina > 10)
            stamina = 10;
        this.currentStamina = stamina;
    }

    public void reduce(double by){
        if(by >= 100){
            if(this.currentStamina == 1)
                return;
            this.currentStamina--;
            return;
        }

        this.progress-= by;
        if(this.progress < 0) {
            if(this.currentStamina == 1) {
                this.progress = 0;
                return;
            }

            this.currentStamina-= 1;
            this.progress = 100 + this.progress;
        }

    }

}
