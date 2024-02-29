package net.xayanix.nssv.reallife.economy;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.character.money.CharacterMoney;
import net.xayanix.nssv.reallife.scoreboard.ScoreboardManager;
import net.xayanix.nssv.reallife.user.User;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyManager {

    @Getter
    private Economy economy;
    @Getter
    private static EconomyManager instance;

    public EconomyManager(){
        instance = this;

        RegisteredServiceProvider<Economy> economyProvider = RealLife.getInstance().getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

    }

    public boolean takeWallet(User user, int money){
        if(money == 0)
            return true;
        if(user.getCharacter().getMoney().getWallet() < money) {
            user.sendMessage("&8#&c Nie masz w portfelu &6" + money + " zlotych&c, aby to zrobic.");
            return false;
        }

        user.getCharacter().getMoney().removeFromWallet(money);

        user.sendMessage("&8#&a Pobrano z Twojego portfela&e " + money + " zlotych&a.");
        user.sendMessage("&8#&a Pozostaly stan gotowki w portfelu:&e " + user.getCharacter().getMoney().getWallet() + " zlotych");

        ScoreboardManager.getInstance().display(user, 10, "&aTransakcja",
                "&7Nowy stan gotówki w portfelu:",
                "&a   " + user.getCharacter().getMoney().getWallet() + " zlotych",
                "&7Stan gotówki w banku:",
                "&a   " + user.getCharacter().getMoney().getBank() + " zlotych");

        return true;
    }

    public boolean withdrawToWallet(User user, int money){
        if(money == 0)
            return true;

        CharacterMoney characterMoney = user.getCharacter().getMoney();
        if(characterMoney.getBank() < money) {
            user.sendMessage("&8#&c Nie posiadasz &6" + money + " zlotych&c w banku.");
            return false;
        }

        characterMoney.removeFromBank(money);
        characterMoney.addToWallet(money);

        user.sendMessage("&8#&a Transakcja sfinalizowana.");
        user.sendMessage("&8#&a Pozostaly stan portfela:&e " + characterMoney.getWallet() + " zlotych");
        user.sendMessage("&8#&a Pozostaly stan konta w banku:&e " + characterMoney.getBank() + " zlotych");

        ScoreboardManager.getInstance().display(user, 10, "&aTransakcja",
                "&7Nowy stan gotówki w portfelu:",
                "&a   " + user.getCharacter().getMoney().getWallet() + " zlotych",
                "&7Nowy stan gotówki w banku:",
                "&a   " + user.getCharacter().getMoney().getBank() + " zlotych");

        return true;
    }

    public boolean depositToBank(User user, int money){
        if(money == 0)
            return true;

        CharacterMoney characterMoney = user.getCharacter().getMoney();
        if(characterMoney.getWallet() < money) {
            user.sendMessage("&8#&c Nie posiadasz &6" + money + " zlotych&c w portfelu.");
            return false;
        }

        characterMoney.addToBank(money);
        characterMoney.removeFromWallet(money);

        user.sendMessage("&8#&a Transakcja sfinalizowana.");
        user.sendMessage("&8#&a Pozostaly stan portfela:&e " + characterMoney.getWallet() + " zlotych");
        user.sendMessage("&8#&a Pozostaly stan konta w banku:&e " + characterMoney.getBank() + " zlotych");

        ScoreboardManager.getInstance().display(user, 10, "&aTransakcja",
                "&7Nowy stan gotówki w portfelu:",
                "&a   " + user.getCharacter().getMoney().getWallet() + " zlotych",
                "&7Nowy stan gotówki w banku:",
                "&a   " + user.getCharacter().getMoney().getBank() + " zlotych");

        return true;
    }

}
