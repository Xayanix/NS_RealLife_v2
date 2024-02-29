package net.xayanix.nssv.reallife.payday;

import lombok.Getter;
import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.TimeUtil;
import net.xayanix.nssv.reallife.RealLife;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.lotto.LottoManager;
import net.xayanix.nssv.reallife.misc.ChatFields;
import net.xayanix.nssv.reallife.scoreboard.ScoreboardManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PaydayManager implements Runnable {

    @Getter
    private static PaydayManager instance;
    private Calendar calendar = Calendar.getInstance();
    private int lastPayday = calendar.get(Calendar.HOUR_OF_DAY);

    @Getter
    private long lastPaydayTimestamp = System.currentTimeMillis();
    private int paydayCount = 0;

    @Getter
    private int paydayMinutes = 15;

    public PaydayManager(){
        instance = this;
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), this::realize, 20 * TimeUnit.MINUTES.toSeconds(this.paydayMinutes), 20 * TimeUnit.MINUTES.toSeconds(this.paydayMinutes));
        Bukkit.getScheduler().runTaskTimerAsynchronously(RealLife.getInstance(), () -> {
            for(User user : UserManager.getUsersOnline()){
                if(user.getCharacter().getJob().getEarned() > 0)
                ScoreboardManager.getInstance().display(user, 5, "&b&c",
                        "&7Podsumowanie Twojej pracy:",
                        "&a+" + user.getCharacter().getJob().getEarned() + " zlotych&7",
                        "&a",
                        "&7Pracodawca wyplaci srodki za:",
                        "&2" + this.getNextPayday() + " minut");
            }
        }, 20 * TimeUnit.MINUTES.toSeconds(5), 20 * TimeUnit.MINUTES.toSeconds(5));
    }

    public void realize(){
        this.lastPaydayTimestamp = System.currentTimeMillis();
        ChatUtil.broadcast(ChatFields.SEPARATOR);
        ChatUtil.broadcast("   &7 Na zegarze godzina:&f " + TimeUtil.formatDate(System.currentTimeMillis(), "HH:mm"));
        ChatUtil.broadcast("   &7 Trwa realizowanie wyplat na konta pracownikow...");

        for(Player player : Bukkit.getOnlinePlayers()){
            User user = UserManager.getUser(player);

            if(user.getCharacter().getJob().getType() == JobType.BEZROBOTNY) {
                user.sendMessage("   &b Otrzymales zasilek dla bezrobotnych w wysokosci 5 zlotych.");
                user.getCharacter().getMoney().addToBank(5);
            } else if(user.getCharacter().getJob().getEarned() > 0){
                int earned = user.getCharacter().getJob().getEarned();
                int bonus = (int) (earned * (user.getCharacter().getJob().getLevel() / 100.0));
                if(user.getCharacter().isChangedJob()) {
                    user.sendMessage("   &c Nie otrzymales bonusu, gdyz niedawno zmieniles prace.");
                    bonus = 0;
                }

                user.sendMessage("   &b Otrzymales &7" + earned + " zlotych&b (+" + bonus + " zlotych za poziom pracy) za wykonana prace.");
                user.getCharacter().getMoney().addToBank(earned + bonus);
                user.getCharacter().getJob().setEarned(0);
                user.getCharacter().setChangedJob(false);
            } else if(user.getCharacter().getJob().getType().getReward() == 0) {
                user.sendMessage("   &c Twoja praca nie otrzymuje wyplat.");
            } else user.sendMessage("   &c Nic nie zrobiles od ostatniej wyplaty.");
        }

        for(Company company : CompanyManager.getCompany()){
            List<User> users = company.getMembersOnline();
            if(users.size() == 0)
                continue;
            //company.setEarned(company.getEarned() + RandomUtil.getRandomNumber(20000));
            int reward = (int) (company.getEarned() * company.getPaydayPercent());

            for(User user : users)
                user.getCharacter().getMoney().addToBank(reward/users.size());

            company.broadcast(" ");
            company.broadcast("   &7 Pracownicy w Twojej firmie otrzymuja &f" + company.getPaydayPercent()*100 + "% przychodu&7.");
            company.broadcast("   &7 Kazdy z pracownikow otrzymal &f" + reward/users.size() + " zlotych&7.");
            company.broadcast("   &7 Laczny zysk firmy od ostatniej wyplaty: &f" + company.getEarned() + " zlotych&7.");
            reward = (int) (company.getEarned() - reward);
            company.setBank(company.getBank() + reward);
            company.broadcast("   &7 Do banku firmy wplynelo:&f " + reward + " zlotych");
            company.setEarned(0);
        }

        ChatUtil.broadcast(" ");
        ChatUtil.broadcast("    &7Lotto:");
        ChatUtil.broadcast("       &7Wylosowany numerek:&f " + LottoManager.getInstance().getWinNumber());

        List<User> winners = LottoManager.getInstance().getWinners();
        if(winners.size() > 0){
            int reward = LottoManager.getInstance().getActualReward() / winners.size();
            for(User user : winners)
                ChatUtil.broadcast("       &a" + user.getName() + " wygrywa&e " + reward + " zlotych&a.");

            LottoManager.getInstance().giveRewards();
        } else ChatUtil.broadcast("       &cNikt nie wylosowal poprawnej liczby lotto.");

        LottoManager.getInstance().increaseReward();
        LottoManager.getInstance().setWinNumber();
        LottoManager.getInstance().resetWinners();

        ChatUtil.broadcast("       &7Aktualna stawka Lotto:&2 " + LottoManager.getInstance().getActualReward() + " zlotych");


        ChatUtil.broadcast(ChatFields.SEPARATOR);

        for(User user : UserManager.getUsersOnline()) {
            user.getCharacter().increaseRespect();
            ScoreboardManager.getInstance().display(user, 10, "&aWyplata",
                    "&7&e",
                    "&7Stan gotówki w portfelu:",
                    "&a   " + user.getCharacter().getMoney().getWallet() + " zlotych",
                    "&7&c",
                    "&7Nowy stan gotówki w banku:",
                    "&a   " + user.getCharacter().getMoney().getBank() + " zlotych",
                    "&7&a ",
                    "&7Poziom postaci",
                    "&f" + user.getCharacter().getLevel() + " Lv",
                    "&7&b ",
                    "&7Respekt",
                    "&f" + user.getCharacter().getRespect() + "/" + user.getCharacter().getRespectToNextLevel(),
                    "&7&d");
            ScoreboardManager.getInstance().updateBelowName(user);
        }

    }

    public int getNextPayday(){
        return (int) (this.getPaydayMinutes() - (System.currentTimeMillis() - PaydayManager.getInstance().getLastPaydayTimestamp()) / 1000 / 60);
    }

    @Override
    public void run() {
        calendar = Calendar.getInstance();
        if(lastPayday != calendar.get(Calendar.HOUR_OF_DAY)) {
            lastPayday = calendar.get(Calendar.HOUR_OF_DAY);
            this.lastPaydayTimestamp = System.currentTimeMillis();
            this.realize();
        }

    }
}
