package net.xayanix.nssv.reallife.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public enum JobType {

    BEZROBOTNY("Bezrobotny", "Bezrobotny", "Bezrobotny, otrzymuje zasilek dla bezrobotnych.", 0, Material.WEB, 0, "", Collections.emptyList()),
    GORNIK("Gornik", "Gornik", "Pracuje glownie w kopalni, zarabia za kazda wykopana rude.", 140, Material.STONE_PICKAXE, 2, "kazda wykopana rude &7i&f 1 zlotych za kamien", Collections.singletonList("Musisz kopac w kopalnii, aby zarabiac.")),
    DRWAL("Drwal", "Drwal", "Jego zyciem sa drzewa, scinaj je, aby zarobic", 60, Material.WOOD_AXE, 10, "kazde sciete drzewo", Collections.singletonList("Scinaj drzewa, aby zarabiac.")),
    BUDOWNICZY("Budowniczy", "Budowniczy", "Uwielbia budowac, zarabia za stawianie klocków.", 30, Material.BRICK_STAIRS, 3, "kazdy postawiony solidny blok", Collections.emptyList()),
    RATOWNIK_MEDYCZNY("Ratownik Medyczny", "Medyk", "Leczy ludzi z ich dolegliwosci.", 2500, Material.POTION, 70, "kazde uleczenie czlowieka", Collections.singletonList("/ulecz <gracz> - leczy rannego lub chorego gracza")),
    POLICJANT("Policjant", "Policja", "Pilnuje porzadku w miescie lapiac zlodziei i wsadzajac ich kratki.", 600, Material.LAPIS_BLOCK, 80, "kazdy Wanted Level schwytanego przestepcy", Arrays.asList("/namierz <gracz> - namierza gracza", "/wanted - poszukiwani gracze", "Ukucnij i kliknij LPM na gracza, aby go aresztowac.")),
    STRAZAK("Straz Pozarna", "Strazak", "Gasi pozary w miescie.", 10000, Material.LAVA_BUCKET, 120, "kazde ugaszenie pozaru", Arrays.asList("Klikaj LPM na pozar, aby go gasic.", "/pozar - teleportuj do losowego pozaru")),
    KLUSOWNIK("Klusownik", "Klusownik", "Poluje na dzika zwierzyne.", 110, Material.BOW, 20, "kazde zabicie zwierzyny", Collections.emptyList()),
    ZLODZIEJ("Zlodziej", "Zlodziej", "Zarabia poprzez okradanie ludzi.", 0, Material.EMERALD, 80, "", Collections.singletonList("Ukucnij i kliknij LPM na gracza, aby go okrasc.")),
    PRACOWNIK_STACJI("Mechanik", "Mechanik", "Zarabia poprzez tankowanie aut.", 2000, Material.HOPPER, 80, "kazde zatankowanie i naprawe pojazdu", Arrays.asList("/tankowanie <gracz> <kwota>", "/naprawauto <gracz> <kwota>")),
    SPRZATACZ("Sprzatacz", "Sprzatacz", "Oczyszcza miasto z zanieczyszczen.", 500, Material.MELON_SEEDS, 30, "posprzatanie kazdego smiecia", Collections.singletonList("Kliknij LPM na zanieszczyszczenie, aby je posprzatac.")),
    HYCEL("Hycel", "Hycel", "Lapie pozostawione bezpanskie psy.", 1500, Material.LEASH, 30, "zlapanie kazdego psa", Collections.singletonList("Kliknij PPM na pozostawionego psa.")),
    LOWCA_GLOW("Lowca Glow", "Lowca Glow", "Eliminuje przestepcow z powierzchni ziemi.", 5000, Material.IRON_SWORD, 60, "zabicie kazdego przestepcy", Arrays.asList("/wanted - poszukiwani gracze", "/zlecenie <gracz> - wez zlecenie na gracza")),
    KURIER("Kurier", "Kurier", "Dostarcza paczki do sklepow w miescie.", 3500, Material.CHEST, 80, "dostarczenie kazdej paczki", Arrays.asList("/celpaczki - aktualny cel paczki", "/dostarcz - dostarczasz paczke", "/wyrzucpaczke - wyrzucasz paczke")),
    OCHRONA("Ochroniarz", "Ochrona", "Chroni ludzi w miescie.", 2550, Material.GOLD_CHESTPLATE, 80, "sprzedanie ochrony graczowi", Collections.singletonList("/ochrona <gracz> <kwota> - sprzedaje ochrone graczowi")),
    RYBAK("Rybak", "Rybak", "Lowi ryby.", 70, Material.FISHING_ROD, 40, "zlowiona rybe", Collections.emptyList()),
    KOWAL("Kowal", "Kowal", "Naprawia przedmioty.", 1500, Material.ANVIL, 60, "naprawiony przedmiot", Collections.singletonList("/napraw <gracz> <cena> - zaoferuj naprawe przedmiotu")),
    PRAWNIK("Prawnik", "Prawnik", "Wyciagnie Cie z kazdej sytuacji prawnej", 1500, Material.APPLE, 100, "uwolnienie z wiezienia i zbicie WL", Arrays.asList("/wanted - poszukiwani gracze", "/uwolnij <gracz> <kwota> - uwalnia gracza z wiezienia", "/zbijwl <gracz> <kwota> - redukuje poziom poszukiwania gracza"));

    private String fullName;
    private String shortName;
    private String description;
    private int reward;
    private Material icon;
    private double pointChance;
    private String rewardFor;
    private List<String> jobCommands;

}
