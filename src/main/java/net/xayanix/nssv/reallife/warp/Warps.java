package net.xayanix.nssv.reallife.warp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.xayanix.nssv.reallife.misc.LocationList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum Warps {

    BANK("Bank", Material.EMERALD, new Location(Bukkit.getWorld("PlotWorld"), -231, 65, -5)),
    KASYNO("Kasyno", Material.DIAMOND, new Location(Bukkit.getWorld("PlotWorld"), -151, 65, -5)),
    STREFA_BIZNESU("Strefa Biznesu", Material.GOLD_INGOT, new Location(Bukkit.getWorld("PlotWorld"), -275, 65, 6)),
    STREFA_BIZNESU_2("Strefa Biznesu 2", Material.GOLD_INGOT, new Location(Bukkit.getWorld("PlotWorld"), -510, 65, -85)),
    PARK("Park", Material.SAPLING, new Location(Bukkit.getWorld("PlotWorld"), -257, 65, 102)),
    CUKIERNIA("Cukiernia", Material.COOKIE, new Location(Bukkit.getWorld("PlotWorld"), -171, 65, 132)),
    TARTAK_KOPALNIA("Tartak/Kopalnia", Material.STONE_PICKAXE, new Location(Bukkit.getWorld("PlotWorld"), -131, 65, -197)),
    ZAJEZDNIA("Zajezdnia Autobusowa", Material.BARRIER, new Location(Bukkit.getWorld("PlotWorld"), 29, 65, -226)),
    TESCO("Tesco", Material.STORAGE_MINECART, new Location(Bukkit.getWorld("PlotWorld"), -471, 65, -56)),
    KFC("KFC", Material.COOKED_CHICKEN, new Location(Bukkit.getWorld("PlotWorld"), -207, 65, -119)),
    STADION_PILKARSKI("Stadion Pilkarski", Material.SLIME_BALL, new Location(Bukkit.getWorld("PlotWorld"), -195, 65, -217)),
    KOSCIOL("Kosciol", Material.STICK, new Location(Bukkit.getWorld("PlotWorld"), -245, 65, 164)),
    SZPITAL_APTEKA("Szpital/Apteka", Material.POTION, new Location(Bukkit.getWorld("PlotWorld"), -140, 65, 183)),
    GROVE_STREET("Grove Street", Material.RECORD_3, new Location(Bukkit.getWorld("PlotWorld"), -65, 65, 182)),
    SKLEP_ZOOLOGICZNY("Sklep Zoologiczny", Material.MOB_SPAWNER, new Location(Bukkit.getWorld("PlotWorld"), -131, 65, 48)),
    WESOLE_MIASTECZKO("Wesole Miasteczko", Material.EXPLOSIVE_MINECART, new Location(Bukkit.getWorld("PlotWorld"), -194, 65, -368)),
    POLICJA_WIEZIENIE("Policja/Wiezienie", Material.IRON_BARDING, new Location(Bukkit.getWorld("PlotWorld"), 7, 65, -135)),
    SCHRONISKO("Schronisko", Material.NAME_TAG, new Location(Bukkit.getWorld("PlotWorld"), -415, 65, 134)),
    BIURO_HA("Biuro Lowcow Glow", Material.DIAMOND_SWORD, new Location(Bukkit.getWorld("PlotWorld"), -340, 148, -175)),
    HURTOWNIA("Hurtownia Kurierow", Material.CHEST, new Location(Bukkit.getWorld("PlotWorld"), -35, 65, -245)),
    SILOWNIA("Silownia", Material.LEVER, new Location(Bukkit.getWorld("PlotWorld"), -421, 65, -68)),
    BLOKI_MIESZKALNE("Bloki Mieszkalne", Material.TRAP_DOOR, new Location(Bukkit.getWorld("PlotWorld"), 183, 66, -207)),
    WYCIECZKA("Wycieczka za miasto", Material.LEAVES, LocationList.SUROWCOWA),
    SALON_AUT("Salon aut", Material.MINECART, new Location(Bukkit.getWorld("PlotWorld"), -281, 65, -68));

    private String name;
    private Material icon;
    private Location location;


}
