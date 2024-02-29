package net.xayanix.nssv.reallife.commands;

import net.xayanix.nssv.core.utils.ChatUtil;
import net.xayanix.nssv.core.utils.StringUtil;
import net.xayanix.nssv.reallife.company.Company;
import net.xayanix.nssv.reallife.company.CompanyManager;
import net.xayanix.nssv.reallife.job.JobManager;
import net.xayanix.nssv.reallife.job.JobType;
import net.xayanix.nssv.reallife.offer.Offer;
import net.xayanix.nssv.reallife.offer.OfferType;
import net.xayanix.nssv.reallife.payday.PaydayManager;
import net.xayanix.nssv.reallife.user.User;
import net.xayanix.nssv.reallife.user.UserManager;
import net.xayanix.nssv.reallife.warp.Travel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class CompanyCommand extends BukkitCommand {

    public CompanyCommand(String name, String... aliases) {
        super(name);
        this.description = "Default command.";
        this.setAliases(Arrays.asList(aliases));
    }

    private void sendInfo(CommandSender arg0){
        arg0.sendMessage(ChatUtil.fixColors("&8&m------------------&r&8[&6 FIRMA &8]&m------------------"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zaloz <nazwa> &7- zaklada wlasna firme"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zamknij &7- zamyka firme"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zatrudnij <nick> &7- zatrudnia do firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zwolnij <nick> &7- zwalnia z firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zatrudnienie &7- akceptuje zatrudnienie do firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma wypowiedzenie &7- odchodzisz dobrowolnie z firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma info <nazwa> &7- informacje o firmie"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma wplac <kwota> &7- wplaca pieniadze dla firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma wyplac <kwota> &7- wyplaca pieniadze z firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma szef <nick> &7- przekazuje szefa firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zadanie <opis zadania> &7- przekaz pracownikow zadanie na dzis"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zadania &7- wyswietla dzisiejsze zadanie firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma zarobek <procent> &7- ustal procent wyplaty dla pracownikow"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma ustawsiedzibe &7- ustawia siedzibe firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 /firma siedziba [firma] &7- teleportuje do siedziby firmy"));
        arg0.sendMessage(ChatUtil.fixColors("&8#&6 Czat firmy:&7 !!wiadomosc"));
        arg0.sendMessage(ChatUtil.fixColors("&8&m------------------&r&8[&6 FIRMA &8]&m------------------"));
    }

    @Override
    public boolean execute(CommandSender arg0, String arg2, String[] arg3) {
        if(!(arg0 instanceof Player)) return false;
        User user = UserManager.getUser(arg0.getName());
        if(arg3.length == 0){
            sendInfo(arg0);
            return false;
        }
        else if(arg3.length == 1){
            String ar1 = arg3[0];

            if(ar1.equalsIgnoreCase("siedziba")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz wlasnej firmy.");
                    return false;
                }

                if(user.getCharacter().getCompany().getHome() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Twoja firma ma nieprawidlowa siedzibe.");
                    return false;
                }

                new Travel(user, user.getCharacter().getCompany().getHome(), 5);
            }

            else if(ar1.equalsIgnoreCase("info")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }
                Company c = user.getCharacter().getCompany();

                String members = "";
                for(User mem : c.getMembers()){
                    Player player = mem.getPlayer();
                    if(player != null){
                        members = members + "&7, &a" + mem;
                    }
                    else members = members + "&7, &7" + mem;
                }
                members = ChatUtil.fixColors(members.replaceFirst("&7, ", ""));

                arg0.sendMessage(ChatUtil.fixColors("&8&m------------------&r&8[&6 FIRMA &8]&m------------------"));
                ChatUtil.sendMessage(arg0, "&8 »&7 Nazwa firmy:&6 " + c.getName());
                ChatUtil.sendMessage(arg0, "&8 »&7 Szef firmy:&6 " + c.getBoss());
                ChatUtil.sendMessage(arg0, "&8 »&7 Procent wyplaty dla pracownikow:&6 " + c.getPaydayPercent()*100 + "%");
                ChatUtil.sendMessage(arg0, "&8 »&7 Stan konta firmy:&6 " + c.getBank() + " zlotych");
                if(user.getPlayer().hasPermission("admin"))
                    ChatUtil.sendMessage(arg0, "&8 »&7 Do nastepnej wyplaty:&6 " + c.getEarned() + " zlotych");
                ChatUtil.sendMessage(arg0, "&8 »&7 Czlonkowie firmy [" + CompanyManager.hasAnyMemberOnline(c) + "/" + c.getMembers().size() + "]:&6 " + members);
                arg0.sendMessage(ChatUtil.fixColors("&8&m------------------&r&8[&6 FIRMA &8]&m------------------"));
            }

            else if(ar1.equalsIgnoreCase("zamknij")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz wlasnej firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                ChatUtil.broadcast("&8#&c " + arg0.getName() + " &7oglosil upadlosc firmy &c" + user.getCharacter().getCompany().getName() + "&7.");
                CompanyManager.deleteCompany(user.getCharacter().getCompany());
                user.getCharacter().setCompany(null);

            }
            else if(ar1.equalsIgnoreCase("wypowiedzenie")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Szef firmy nie moze jej opuscic.");
                    return false;
                }

                user.getCharacter().getCompany().broadcast("&8#&c " + arg0.getName() + " &7zwolnil sie z Twojej firmy.");
                user.getCharacter().setCompany(null);

            }

            else if(ar1.equalsIgnoreCase("zadania")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                ChatUtil.sendMessage(arg0, "&8#&c Zadanie Twojej firmy na dzis:&7 " + user.getCharacter().getCompany().getDescription());

            }

            else if(ar1.equalsIgnoreCase("ustawsiedzibe")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                user.getCharacter().getCompany().setHome((((Player) arg0).getLocation()));
                user.getCharacter().getCompany().broadcast("&8#&c " + arg0.getName() + " &7zaktualizowal siedzibe firmy&7.");

            }
            else ChatUtil.sendMessage(arg0, "&8#&c Nieprawidlowe uzycie komendy.");


        }
        else if(arg3.length >= 2){
            String ar1 = arg3[0];
            String ar2 = arg3[1];
            if(ar1.equalsIgnoreCase("zaloz")){
                if(user.getCharacter().getCompany() != null){
                    ChatUtil.sendMessage(arg0, "&8#&c Posiadasz wlasna firme lub jestes czlonkiem innej.");
                    return false;
                }

                if(!CompanyManager.isVaildCompanyName(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Nazwa firmy jest nieprawidlowa.");
                    ChatUtil.sendMessage(arg0, "&8#&7 Nazwa firmy musi posiadac minimalnie &c3 znaki&7, maksymalnie &c16 znakow&7 oraz moze skladac sie z liter od A do Z.");
                    return false;
                }

                if(CompanyManager.isCompany(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Nazwa firmy jest zajeta.");
                    return false;
                }

                if(!user.isCooldownFinished("companycreate", TimeUnit.MINUTES.toMillis(30))){
                    ChatUtil.sendMessage(arg0, "&8#&c Firmy mozna zakladac raz na 30 minut.");
                    return false;
                }

                if(!arg0.hasPermission("vip")){
                    if(user.getCharacter().getMoney().getBank() < 75000){
                        ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz 75000 zlotych na koncie bankowym.");
                        return false;
                    }
                    user.getCharacter().getMoney().removeFromBank(75000);
                }

                Company c = new Company(ar2, arg0.getName(), ((Player) arg0).getLocation());
                user.getCharacter().setCompany(c);
                c.insert();

                if(!arg0.hasPermission("vip"))
                    c.setBank(5000);

                ChatUtil.broadcast("&8#&a " + arg0.getName() + " &7zarejestrowal dzialalnosc firmy &a" + user.getCharacter().getCompany().getName() + "&7.");
            }
            else if(ar1.equalsIgnoreCase("siedziba")){
                Company c = CompanyManager.getCompany(ar2);
                if(c == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Firma nie istnieje.");
                    return false;
                }

                if(c.getHome() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Ta firma ma nieprawidlowa siedzibe.");
                    return false;
                }

                new Travel(user, c.getHome(), 5);
            }
            else if(ar1.equalsIgnoreCase("zatrudnij")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                if(user.getCharacter().getCompany().getMembers().size() >= ((arg0.hasPermission("vip")) ? 20 : 10)){
                    ChatUtil.sendMessage(arg0, "&8#&c Firma posiada maksymalna liczbe pracownikow.");
                    ChatUtil.sendMessage(arg0, "&8#&c Zwolnij innego pracownika, aby zatrudnic nowego.");
                    return false;
                }

                Player player = Bukkit.getPlayer(ar2);
                if(player == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest w grze.");
                    return false;
                }

                User useri = UserManager.getUser(player);
                if(useri.getCharacter().getCompany() != null){
                    ChatUtil.sendMessage(arg0, "&8#&c Gracz jest zatrudniony w innej firmie.");
                    return false;
                }
                useri.getUserTempData().addOffer(new Offer(arg0.getName(), 0, OfferType.COMPANY));

                ChatUtil.sendMessage(player, "&8#&7 Otrzymales kontrakt do firmy &a" + user.getCharacter().getCompany().getName() + "&7.");
                ChatUtil.sendMessage(player, "&8#&7 Jesli chcesz zaakceptowac propozycje pracy wpisz &a/akceptuj " + OfferType.COMPANY + " " + arg0.getName() + "&7.");
                ChatUtil.sendMessage(arg0, "&8#&7 Wyslano propozycje dolaczenia do firmy.");
            }
            else if(ar1.equalsIgnoreCase("zwolnij")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                User useri = UserManager.getUser(ar2);

                if(useri == null || user.getCharacter().getCompany() != useri.getCharacter().getCompany()){
                    ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest pracownikiem Twojej firmy.");
                    return false;
                }

                if(user.getCharacter().getCompany().isOwner(useri)){
                    ChatUtil.sendMessage(arg0, "&8#&c Szef nie moze zostac zwolniony.");
                    return false;
                }

                user.getCharacter().getCompany().broadcast("&8#&c " + useri.getName() + "&7 zostal zwolniony z firmy przez szefa.");
                useri.getCharacter().setCompany(null);
                useri.synchronize();

            }
            else if(ar1.equalsIgnoreCase("wplac")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!StringUtil.isInteger(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Kwota wplaty jest nieprawidlowa.");
                    return false;
                }

                int i = Integer.valueOf(ar2);

                if(i < 0){
                    ChatUtil.sendMessage(arg0, "&8#&c Kwota wplaty jest nieprawidlowa.");
                    return false;
                }

                if(i > user.getCharacter().getMoney().getBank()){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz kwoty &7" + i + " zlotych&c w banku.");
                    return false;
                }
                user.getCharacter().getMoney().removeFromBank(i);
                user.getCharacter().getCompany().setBank(user.getCharacter().getCompany().getBank() + i);
                user.getCharacter().getCompany().broadcast("&8#&c " + arg0.getName() + " &7dokonal wplaty na konto firmowe w wysokosci &c" + i + " zlotych&7.");
            }
            else if(ar1.equalsIgnoreCase("wyplac")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                if(!StringUtil.isInteger(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Kwota wyplaty jest nieprawidlowa.");
                    return false;
                }

                int i = Integer.valueOf(ar2);
                if(i < 0){
                    ChatUtil.sendMessage(arg0, "&8#&c Kwota wyplaty jest nieprawidlowa.");
                    return false;
                }

                if(i > user.getCharacter().getCompany().getBank()){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz kwoty &7" + i + " zlotych&c w banku firmowym.");
                    return false;
                }
                user.getCharacter().getMoney().addToWallet(i);
                user.getCharacter().getCompany().setBank(user.getCharacter().getCompany().getBank() - i);
                user.getCharacter().getCompany().broadcast("&8#&c " + arg0.getName() + " &7dokonal wyplaty z konta firmowego w wysokosci &c" + i + " zlotych&7.");
            }
            else if(ar1.equalsIgnoreCase("zarobek")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(PaydayManager.getInstance().getNextPayday() < 12){
                    ChatUtil.sendMessage(arg0, "&8#&c Zarobek pracownikow firmy mozesz modyfikowac tylko przez 3 minuty od ostatniej wyplaty.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                if(!StringUtil.isInteger(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Procent wyplaty musi zawierac sie od 20% do 100%.");
                    return false;
                }

                double i = Integer.valueOf(ar2);
                if(i > 100 || i < 20){
                    ChatUtil.sendMessage(arg0, "&8#&c Procent wyplaty musi zawierac sie od 20% do 100%.");
                    return false;
                }

                Company company = user.getCharacter().getCompany();
                company.broadcast("&8#&c Szef Twojej firmy zmienil wysokosc wyplat na &6" + i + "% dla pracownikow&c.");
                company.setPaydayPercent(i / 100.0);

            }
            else if(ar1.equalsIgnoreCase("szef")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                User u = UserManager.getUser(ar2);
                if(u == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie odnaleziono gracza.");
                    return false;
                }

                if(u.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest pracownikiem Twojej firmy.");
                    return false;
                }

                if(u.getCharacter().getCompany() != user.getCharacter().getCompany()){
                    ChatUtil.sendMessage(arg0, "&8#&c Gracz nie jest pracownikiem Twojej firmy.");
                    return false;
                }

                if(user.getCharacter().getCompany().isOwner(u)){
                    if(user.getCharacter().getCompany().getOwners().size() == 1){
                        ChatUtil.sendMessage(arg0, "&8#&c Jestes jedynym szefem w firmie, nie mozesz zabrac sobie praw&c.");
                        return false;
                    }

                    user.getCharacter().getCompany().removeOwner(u);
                    ChatUtil.sendMessage(arg0, "&8#&c Zabrano wlasciciela firmy &7" + u.getName() + "&c.");
                    return true;
                }

                user.getCharacter().getCompany().addOwner(u);
                user.getCharacter().getCompany().broadcast("&8#&c " + ar2 + " &7zostal nowym szefem firmy&7.");

            }
            else if(ar1.equalsIgnoreCase("zadanie")){
                if(user.getCharacter().getCompany() == null){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie posiadasz firmy.");
                    return false;
                }

                if(!user.getCharacter().getCompany().isOwner(arg0.getName())){
                    ChatUtil.sendMessage(arg0, "&8#&c Nie jestes szefem tej firmy.");
                    return false;
                }

                String zadanie = "";
                for(int i = 1; i < arg3.length; i++){
                    zadanie = zadanie + " " + arg3[i];
                }

                zadanie = zadanie.replace("'", "");

                user.getCharacter().getCompany().setDescription(zadanie);
                user.getCharacter().getCompany().broadcast("&8#&c " + arg0.getName() + " &7zaktualizowal zadania firmy&7.");

            }
            else if(ar1.equalsIgnoreCase("info")){
                if(!CompanyManager.isCompany(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Firma nie istnieje.");
                    return false;
                }
                Company c = CompanyManager.getCompany(ar2);

                String members = "";
                for(User mem : c.getMembers()){
                    Player player = mem.getPlayer();
                    if(player != null){
                        members = members + "&7, &a" + mem;
                    }
                    else members = members + "&7, &7" + mem;
                }
                members = ChatUtil.fixColors(members.replaceFirst("&7, ", ""));

                arg0.sendMessage(ChatUtil.fixColors("&8&m------------------&r&8[&6 FIRMA &8]&m------------------"));
                ChatUtil.sendMessage(arg0, "&8 »&7 Nazwa firmy:&6 " + c.getName());
                ChatUtil.sendMessage(arg0, "&8 »&7 Szef firmy:&6 " + c.getBoss());
                ChatUtil.sendMessage(arg0, "&8 »&7 Procent wyplaty dla pracownikow:&6 " + c.getPaydayPercent()*100 + "%");
                ChatUtil.sendMessage(arg0, "&8 »&7 Stan konta firmy:&6 " + c.getBank() + " zlotych");
                ChatUtil.sendMessage(arg0, "&8 »&7 Czlonkowie firmy [" + CompanyManager.hasAnyMemberOnline(c) + "/" + c.getMembers().size() + "]:&6 " + members);
                arg0.sendMessage(ChatUtil.fixColors("&8&m------------------&r&8[&6 FIRMA &8]&m------------------"));
            }

            else if(ar1.equalsIgnoreCase("zamknij") && arg0.hasPermission("admin")){
                if(!CompanyManager.isCompany(ar2)){
                    ChatUtil.sendMessage(arg0, "&8#&c Firma nie istnieje.");
                    return false;
                }
                Company c = CompanyManager.getCompany(ar2);

                ChatUtil.broadcast("&8#&c (admin) " + arg0.getName() + " &7oglosil upadlosc firmy &c" + c + "&7.");
                CompanyManager.deleteCompany(c);

            }

            else ChatUtil.sendMessage(arg0, "&8#&c Nieprawidlowe uzycie komendy.");

        }

        return true;
    }
}
