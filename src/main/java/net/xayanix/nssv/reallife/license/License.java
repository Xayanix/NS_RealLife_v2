package net.xayanix.nssv.reallife.license;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum License {

    DOWOD_OSOBISTY("Dowod Osobisty", 0, Material.PAPER),
    PRAWO_JAZDY_KAT_A("Prawo Jazdy kat. A", 1500, Material.ARMOR_STAND),
    PRAWO_JAZDY_KAT_B("Prawo Jazdy kat. B", 1900, Material.MINECART),
    LICENCJA_RYBAKA("Licencja Rybaka", 250, Material.FISHING_ROD),
    LICENCJA_NA_BRON("Licencja na bron", 15000, Material.DIAMOND_SWORD),
    LICENCJA_PILOTA("Licencja Pilota", 250000, Material.WOOD);

    private String name;
    private int price;
    private Material icon;

    @Override
    public String toString() {
        return this.name;
    }
}
