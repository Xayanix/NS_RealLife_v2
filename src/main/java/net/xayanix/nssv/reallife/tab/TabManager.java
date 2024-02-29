package net.xayanix.nssv.reallife.tab;

import lombok.Getter;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.tab.variables.*;
import net.xayanix.nssv.reallife.tab.variables.company.CMoneyVariable;
import net.xayanix.nssv.reallife.tab.variables.company.ChefVariable;
import net.xayanix.nssv.reallife.tab.variables.company.MembersVariable;
import net.xayanix.nssv.reallife.tab.variables.company.NameVariable;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import pl.xayanix.nssv.tab.LaglessTab;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class TabManager implements Runnable {

    @Getter
    private static List<TabMoney> moneyList = new CopyOnWriteArrayList<>();

    public void calc() {
        moneyList.clear();
        for (User user : UserManager.getUsers().values())
            moneyList.add(new TabMoney(user.getName(), user.getUserConfiguration().getNicknameColor(), user.getCharacter().getMoney().getWallet() + user.getCharacter().getMoney().getBank()));

        moneyList.sort(new TabMoneySort());

    }

    public void init() {
        LaglessTab.getInstance().getTabManager().registerVariable(new ChefVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new CMoneyVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new MembersVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new NameVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new BankVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new JobVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new MoneyVariable());
        LaglessTab.getInstance().getTabManager().registerVariable(new PaymentVariable());

        for (int i = 1; i < 60; i++)
            LaglessTab.getInstance().getTabManager().registerVariable(new TopMoneyVariable(i));

    }

    @Override
    public void run() {
        this.calc();
    }

    public TabManager() {
        this.init();
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this, 60, 20 * TimeUnit.MINUTES.toSeconds(3));
    }

}
