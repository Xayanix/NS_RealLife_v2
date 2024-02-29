package net.xayanix.nssv.reallife.character.money;

import lombok.Getter;
import net.xayanix.nssv.reallife.economy.EconomyManager;

public class CharacterMoney {

    private String account;

    @Getter
    private long bank;

    public CharacterMoney(String name){
        this.account = name;
    }

    public void addToBank(long amount){
        this.bank+= amount;
    }

    public void removeFromBank(long amount){
        this.bank-= amount;
    }

    public int getWallet() {
        return (int) EconomyManager.getInstance().getEconomy().getBalance(account);
    }

    public void addToWallet(int amount){
        EconomyManager.getInstance().getEconomy().depositPlayer(account, amount);
    }

    public void removeFromWallet(int amount){
        EconomyManager.getInstance().getEconomy().withdrawPlayer(account, amount);
    }

}
