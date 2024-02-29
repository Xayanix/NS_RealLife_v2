package net.xayanix.nssv.reallife.commands.loader;

import net.xayanix.nssv.reallife.commands.*;
import net.xayanix.nssv.reallife.commands.registerer.CommandRegisterer;
import net.xayanix.nssv.reallife.commands.DeliverCommand;

public class CommandLoader {

    public CommandLoader(){
        CommandRegisterer.register("opengui", new GuiCommand("opengui"));
        CommandRegisterer.register("konto", new CreateCharCommand("konto", "mojekonto", "stats"));
        CommandRegisterer.register("acontrol", new AdminCommand("acontrol"));
        CommandRegisterer.register("praca", new JobCommand("praca"));
        CommandRegisterer.register("plac", new PayCommand("plac", "pay", "zaplac"));
        CommandRegisterer.register("ulecz", new HealCommand("ulecz"));
        CommandRegisterer.register("uwolnij", new FreedomCommand("uwolnij"));
        CommandRegisterer.register("zbijwl", new ReduceWantedLevelCommand("zbijwl"));
        CommandRegisterer.register("akceptuj", new AcceptCommand("akceptuj"));
        CommandRegisterer.register("kasyno", new CasinoCommand("kasyno"));
        CommandRegisterer.register("tankowanie", new RefuelCommand("tankowanie", "tank"));
        CommandRegisterer.register("wanted", new WantedCommand("wanted", "poszukiwani"));
        CommandRegisterer.register("zlecenie", new KillOrderCommand("zlecenie"));
        CommandRegisterer.register("namierz", new LocalizeCommand("namierz"));
        CommandRegisterer.register("lotto", new LottoCommand("lotto"));
        CommandRegisterer.register("wzywaj", new CallJobCommand("wzywaj", "wezwanie", "wezwij"));
        CommandRegisterer.register("slub", new MarryCommand("slub", "marry"));
        CommandRegisterer.register("firma", new CompanyCommand("firma", "caution"));
        CommandRegisterer.register("kaucja", new CautionCommand("kaucja"));
        CommandRegisterer.register("plecak", new BackPackCommand("plecak"));
        CommandRegisterer.register("money", new MoneyCommand("money", "stankonta"));
        CommandRegisterer.register("konfiguracja", new ChatConfigurationCommand("konfiguracja", "ustawienia"));
        CommandRegisterer.register("dostarcz", new DeliverCommand("dostarcz", "dostarczpaczke"));
        CommandRegisterer.register("wyrzucpaczke", new CancelDeliverCommand("wyrzucpaczke"));
        CommandRegisterer.register("celpaczki", new DeliverTargetCommand("celpaczki"));
        CommandRegisterer.register("silownia", new TrainCommand("silownia", "cwicz"));
        CommandRegisterer.register("ochrona", new BodyGuardCommand("ochrona", "chron"));
        CommandRegisterer.register("sprzedajdzialke", new SellPlotCommand("sprzedajdzialke"));
        CommandRegisterer.register("spawn", new SpawnCommand("spawn"));
        CommandRegisterer.register("kopalnia", new KopalniaCommand("kopalnia"));
        CommandRegisterer.register("rodzina", new FamilyCommand("rodzina", "family"));
        CommandRegisterer.register("ogl", new BroadcastCommand("ogl", "ogloszenie"));
        CommandRegisterer.register("repair", new RepairCommand("repair", "napraw"));
        CommandRegisterer.register("pozar", new FireCommand("pozar", "palisie"));
        CommandRegisterer.register("itp", new ITeleportCommand("itp"));
        CommandRegisterer.register("wsiadz", new GetInCarCommand("wsiadz", "wsiadaj"));
        CommandRegisterer.register("naprawauto", new RepairCarCommand("naprawauto", "naprawiaj"));
    }

}
