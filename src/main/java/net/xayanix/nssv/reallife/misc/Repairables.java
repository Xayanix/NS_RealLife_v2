package net.xayanix.nssv.reallife.misc;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;

public class Repairables {

    private static Material[] tools = new Material[]{
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_SWORD,
            Material.DIAMOND_SPADE,
            Material.DIAMOND_AXE,
            Material.DIAMOND_HOE,
            Material.DIAMOND_HELMET,
            Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS,
            Material.DIAMOND_BOOTS,
            Material.IRON_PICKAXE,
            Material.IRON_SWORD,
            Material.IRON_SPADE,
            Material.IRON_AXE,
            Material.IRON_HOE,
            Material.IRON_HELMET,
            Material.IRON_CHESTPLATE,
            Material.IRON_LEGGINGS,
            Material.IRON_BOOTS,
            Material.GOLD_PICKAXE,
            Material.GOLD_SWORD,
            Material.GOLD_SPADE,
            Material.GOLD_AXE,
            Material.GOLD_HOE,
            Material.GOLD_HELMET,
            Material.GOLD_CHESTPLATE,
            Material.GOLD_LEGGINGS,
            Material.GOLD_BOOTS,
            Material.STONE_PICKAXE,
            Material.STONE_SWORD,
            Material.STONE_SPADE,
            Material.STONE_AXE,
            Material.STONE_HOE,
            Material.CHAINMAIL_HELMET,
            Material.CHAINMAIL_CHESTPLATE,
            Material.CHAINMAIL_LEGGINGS,
            Material.CHAINMAIL_BOOTS,
            Material.WOOD_PICKAXE,
            Material.WOOD_SWORD,
            Material.WOOD_SPADE,
            Material.WOOD_AXE,
            Material.WOOD_HOE,
            Material.LEATHER_HELMET,
            Material.LEATHER_CHESTPLATE,
            Material.LEATHER_LEGGINGS,
            Material.LEATHER_BOOTS,
            Material.FLINT_AND_STEEL,
            Material.SHEARS,
            Material.BOW,
            Material.FISHING_ROD,
            Material.ELYTRA,
            Material.ANVIL};

    public static boolean isRepairable(Material m){
        return ArrayUtils.contains(tools, m);
    }

}
