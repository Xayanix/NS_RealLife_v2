package net.xayanix.nssv.reallife.character;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.reallife.backpack.BackPack;
import net.xayanix.nssv.reallife.character.money.CharacterMoney;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.family.Family;
import net.xayanix.nssv.reallife.family.FamilyRank;
import net.xayanix.nssv.reallife.jail.Jail;
import net.xayanix.nssv.reallife.job.Job;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.license.License;
import net.xayanix.nssv.reallife.stamina.Stamina;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Character {

    private String username;
    private CharacterMoney money;

    @Setter
    private BackPack backPack;
    private Stamina stamina;

    private String name;
    private String surname;
    private int age;
    private List<License> licenses;

    @Setter
    private Job job;
    @Setter
    private boolean changedJob;

    @Setter
    private Jail jail;

    @Setter
    private int wantedlevel;

    private int phoneNumber;
    private int respect;
    private int level;

    @Setter
    private String marry;

    private transient Company company;
    private String companyName;

    private transient Family family;
    @Setter
    private FamilyRank familyRank;
    private String familyName;

    @Setter
    private int lotto;
    @Setter
    private long vipMine;

    private ConcurrentHashMap<JobType, Integer> jobLevelMap;




    public Character(User user, String name, String surname, int age, Job job){
        this.username = user.getName();
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.job = job;
        this.wantedlevel = 0;
        this.phoneNumber = 0; // TO DO
        this.respect = 0;
        this.level = 1;
        this.lotto = -1;
        this.vipMine = 0;
        this.company = null;
        this.familyRank = FamilyRank.UNKNOWN;
        this.marry = "";
        this.stamina = new Stamina(1, 0, null);
        this.money = new CharacterMoney(this.username);
        this.licenses = new CopyOnWriteArrayList<>();
        this.jobLevelMap = new ConcurrentHashMap<>();
    }

    public void loaded(){
        if(this.jobLevelMap == null){
            this.jobLevelMap = new ConcurrentHashMap<>();
            this.jobLevelMap.put(this.getJob().getType(), this.getJob().getLevel());
        }

        if(this.stamina.getCurrentStamina() > 10)
            this.stamina.setCurrentStamina(10);

    }


    public User getUser(){
        return UserManager.getUser(username);
    }

    public String getFullCharacterName(){
        if(this.getUser().isNew())
            return "Gracz nie utworzyl postaci";
        return this.name + " " + this.surname;
    }

    public int getRespectToNextLevel(){
        return this.level * 15;
    }

    public void increaseRespect(){
        respect++;
        if(respect >= this.getRespectToNextLevel()){
            this.level++;
            this.respect = 0;
            int reward = this.level * 2500;
            this.getUser().getCharacter().getMoney().addToWallet(reward);

            this.getUser().sendMessage("&8#&a Nowy poziom postaci!");
            this.getUser().sendMessage("&8#&a Twoja postac ma teraz &e" + this.level + " poziom&a!");
            this.getUser().sendMessage("&8#&a Otrzymujesz &e" + reward + " zlotych&a bonusu!");
        }
    }

    public void setCompany(Company company){
        this.company = company;
        this.companyName = company == null ? null : company.getName();
    }

    public void setFamily(Family family){
        this.family = family;
        this.familyRank = FamilyRank.UNKNOWN;
        this.familyName = family == null ? null : family.getName();

    }

}
