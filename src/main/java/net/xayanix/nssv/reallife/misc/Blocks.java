package net.xayanix.nssv.reallife.misc;

import org.bukkit.Material;

public class Blocks {

	public static int[] blocks;

	static {
		blocks = new int[Material.values().length];
		try {
			Blocks.blocks[Material.BEACON.ordinal()] = 2;
		}
		catch (NoSuchFieldError noSuchFieldError2) {}
		try {
			Blocks.blocks[Material.BEDROCK.ordinal()] = 3;
		}
		catch (NoSuchFieldError noSuchFieldError3) {}
		try {
			Blocks.blocks[Material.BOOKSHELF.ordinal()] = 4;
		}
		catch (NoSuchFieldError noSuchFieldError4) {}
		try {
			Blocks.blocks[Material.BRICK.ordinal()] = 5;
		}
		catch (NoSuchFieldError noSuchFieldError5) {}
		try {
			Blocks.blocks[Material.BURNING_FURNACE.ordinal()] = 6;
		}
		catch (NoSuchFieldError noSuchFieldError6) {}
		try {
			Blocks.blocks[Material.CLAY.ordinal()] = 7;
		}
		catch (NoSuchFieldError noSuchFieldError7) {}
		try {
			Blocks.blocks[Material.CLAY_BRICK.ordinal()] = 8;
		}
		catch (NoSuchFieldError noSuchFieldError8) {}
		try {
			Blocks.blocks[Material.COAL_BLOCK.ordinal()] = 9;
		}
		catch (NoSuchFieldError noSuchFieldError9) {}
		try {
			Blocks.blocks[Material.COAL_ORE.ordinal()] = 10;
		}
		catch (NoSuchFieldError noSuchFieldError10) {}
		try {
			Blocks.blocks[Material.COBBLESTONE.ordinal()] = 11;
		}
		catch (NoSuchFieldError noSuchFieldError11) {}
		try {
			Blocks.blocks[Material.COMMAND.ordinal()] = 12;
		}
		catch (NoSuchFieldError noSuchFieldError12) {}
		try {
			Blocks.blocks[Material.DIAMOND_BLOCK.ordinal()] = 13;
		}
		catch (NoSuchFieldError noSuchFieldError13) {}
		try {
			Blocks.blocks[Material.DIAMOND_ORE.ordinal()] = 14;
		}
		catch (NoSuchFieldError noSuchFieldError14) {}
		try {
			Blocks.blocks[Material.DIRT.ordinal()] = 15;
		}
		catch (NoSuchFieldError noSuchFieldError15) {}
		try {
			Blocks.blocks[Material.DISPENSER.ordinal()] = 16;
		}
		catch (NoSuchFieldError noSuchFieldError16) {}
		try {
			Blocks.blocks[Material.DOUBLE_STEP.ordinal()] = 17;
		}
		catch (NoSuchFieldError noSuchFieldError17) {}
		try {
			Blocks.blocks[Material.WOOD_DOUBLE_STEP.ordinal()] = 18;
		}
		catch (NoSuchFieldError noSuchFieldError18) {}
		try {
			Blocks.blocks[Material.DROPPER.ordinal()] = 19;
		}
		catch (NoSuchFieldError noSuchFieldError19) {}
		try {
			Blocks.blocks[Material.EMERALD_BLOCK.ordinal()] = 20;
		}
		catch (NoSuchFieldError noSuchFieldError20) {}
		try {
			Blocks.blocks[Material.EMERALD_ORE.ordinal()] = 21;
		}
		catch (NoSuchFieldError noSuchFieldError21) {}
		try {
			Blocks.blocks[Material.FURNACE.ordinal()] = 22;
		}
		catch (NoSuchFieldError noSuchFieldError22) {}
		try {
			Blocks.blocks[Material.GLASS.ordinal()] = 23;
		}
		catch (NoSuchFieldError noSuchFieldError23) {}
		try {
			Blocks.blocks[Material.GLOWING_REDSTONE_ORE.ordinal()] = 24;
		}
		catch (NoSuchFieldError noSuchFieldError24) {}
		try {
			Blocks.blocks[Material.GLOWSTONE.ordinal()] = 25;
		}
		catch (NoSuchFieldError noSuchFieldError25) {}
		try {
			Blocks.blocks[Material.GOLD_BLOCK.ordinal()] = 26;
		}
		catch (NoSuchFieldError noSuchFieldError26) {}
		try {
			Blocks.blocks[Material.GOLD_ORE.ordinal()] = 27;
		}
		catch (NoSuchFieldError noSuchFieldError27) {}
		try {
			Blocks.blocks[Material.GRASS.ordinal()] = 28;
		}
		catch (NoSuchFieldError noSuchFieldError28) {}
		try {
			Blocks.blocks[Material.GRAVEL.ordinal()] = 29;
		}
		catch (NoSuchFieldError noSuchFieldError29) {}
		try {
			Blocks.blocks[Material.HARD_CLAY.ordinal()] = 30;
		}
		catch (NoSuchFieldError noSuchFieldError30) {}
		try {
			Blocks.blocks[Material.HAY_BLOCK.ordinal()] = 31;
		}
		catch (NoSuchFieldError noSuchFieldError31) {}
		try {
			Blocks.blocks[Material.HUGE_MUSHROOM_1.ordinal()] = 32;
		}
		catch (NoSuchFieldError noSuchFieldError32) {}
		try {
			Blocks.blocks[Material.HUGE_MUSHROOM_2.ordinal()] = 33;
		}
		catch (NoSuchFieldError noSuchFieldError33) {}
		try {
			Blocks.blocks[Material.ICE.ordinal()] = 34;
		}
		catch (NoSuchFieldError noSuchFieldError34) {}
		try {
			Blocks.blocks[Material.IRON_BLOCK.ordinal()] = 35;
		}
		catch (NoSuchFieldError noSuchFieldError35) {}
		try {
			Blocks.blocks[Material.IRON_ORE.ordinal()] = 36;
		}
		catch (NoSuchFieldError noSuchFieldError36) {}
		try {
			Blocks.blocks[Material.JACK_O_LANTERN.ordinal()] = 37;
		}
		catch (NoSuchFieldError noSuchFieldError37) {}
		try {
			Blocks.blocks[Material.JUKEBOX.ordinal()] = 38;
		}
		catch (NoSuchFieldError noSuchFieldError38) {}
		try {
			Blocks.blocks[Material.LAPIS_BLOCK.ordinal()] = 39;
		}
		catch (NoSuchFieldError noSuchFieldError39) {}
		try {
			Blocks.blocks[Material.LAPIS_ORE.ordinal()] = 40;
		}
		catch (NoSuchFieldError noSuchFieldError40) {}
		try {
			Blocks.blocks[Material.LEAVES.ordinal()] = 41;
		}
		catch (NoSuchFieldError noSuchFieldError41) {}
		try {
			Blocks.blocks[Material.LEAVES_2.ordinal()] = 42;
		}
		catch (NoSuchFieldError noSuchFieldError42) {}
		try {
			Blocks.blocks[Material.LOG.ordinal()] = 43;
		}
		catch (NoSuchFieldError noSuchFieldError43) {}
		try {
			Blocks.blocks[Material.LOG_2.ordinal()] = 44;
		}
		catch (NoSuchFieldError noSuchFieldError44) {}
		try {
			Blocks.blocks[Material.MOB_SPAWNER.ordinal()] = 45;
		}
		catch (NoSuchFieldError noSuchFieldError45) {}
		try {
			Blocks.blocks[Material.MOSSY_COBBLESTONE.ordinal()] = 46;
		}
		catch (NoSuchFieldError noSuchFieldError46) {}
		try {
			Blocks.blocks[Material.MYCEL.ordinal()] = 47;
		}
		catch (NoSuchFieldError noSuchFieldError47) {}
		try {
			Blocks.blocks[Material.NETHERRACK.ordinal()] = 48;
		}
		catch (NoSuchFieldError noSuchFieldError48) {}
		try {
			Blocks.blocks[Material.NETHER_BRICK.ordinal()] = 49;
		}
		catch (NoSuchFieldError noSuchFieldError49) {}
		try {
			Blocks.blocks[Material.NETHER_BRICK_ITEM.ordinal()] = 50;
		}
		catch (NoSuchFieldError noSuchFieldError50) {}
		try {
			Blocks.blocks[Material.NOTE_BLOCK.ordinal()] = 51;
		}
		catch (NoSuchFieldError noSuchFieldError51) {}
		try {
			Blocks.blocks[Material.OBSIDIAN.ordinal()] = 52;
		}
		catch (NoSuchFieldError noSuchFieldError52) {}
		try {
			Blocks.blocks[Material.PACKED_ICE.ordinal()] = 53;
		}
		catch (NoSuchFieldError noSuchFieldError53) {}
		try {
			Blocks.blocks[Material.PISTON_BASE.ordinal()] = 54;
		}
		catch (NoSuchFieldError noSuchFieldError54) {}
		try {
			Blocks.blocks[Material.PISTON_STICKY_BASE.ordinal()] = 55;
		}
		catch (NoSuchFieldError noSuchFieldError55) {}
		try {
			Blocks.blocks[Material.PUMPKIN.ordinal()] = 57;
		}
		catch (NoSuchFieldError noSuchFieldError57) {}
		try {
			Blocks.blocks[Material.QUARTZ_BLOCK.ordinal()] = 58;
		}
		catch (NoSuchFieldError noSuchFieldError58) {}
		try {
			Blocks.blocks[Material.QUARTZ_ORE.ordinal()] = 59;
		}
		catch (NoSuchFieldError noSuchFieldError59) {}
		try {
			Blocks.blocks[Material.REDSTONE_BLOCK.ordinal()] = 60;
		}
		catch (NoSuchFieldError noSuchFieldError60) {}
		try {
			Blocks.blocks[Material.REDSTONE_LAMP_OFF.ordinal()] = 61;
		}
		catch (NoSuchFieldError noSuchFieldError61) {}
		try {
			Blocks.blocks[Material.REDSTONE_LAMP_ON.ordinal()] = 62;
		}
		catch (NoSuchFieldError noSuchFieldError62) {}
		try {
			Blocks.blocks[Material.REDSTONE_ORE.ordinal()] = 63;
		}
		catch (NoSuchFieldError noSuchFieldError63) {}
		try {
			Blocks.blocks[Material.SAND.ordinal()] = 65;
		}
		catch (NoSuchFieldError noSuchFieldError65) {}
		try {
			Blocks.blocks[Material.SANDSTONE.ordinal()] = 66;
		}
		catch (NoSuchFieldError noSuchFieldError66) {}
		try {
			Blocks.blocks[Material.SMOOTH_BRICK.ordinal()] = 68;
		}
		catch (NoSuchFieldError noSuchFieldError68) {}
		try {
			Blocks.blocks[Material.SNOW_BLOCK.ordinal()] = 69;
		}
		catch (NoSuchFieldError noSuchFieldError69) {}
		try {
			Blocks.blocks[Material.SPONGE.ordinal()] = 70;
		}
		catch (NoSuchFieldError noSuchFieldError70) {}
		try {
			Blocks.blocks[Material.STAINED_CLAY.ordinal()] = 71;
		}
		catch (NoSuchFieldError noSuchFieldError71) {}
		try {
			Blocks.blocks[Material.STAINED_GLASS.ordinal()] = 72;
		}
		catch (NoSuchFieldError noSuchFieldError72) {}
		try {
			Blocks.blocks[Material.STONE.ordinal()] = 73;
		}
		catch (NoSuchFieldError noSuchFieldError73) {}
		try {
			Blocks.blocks[Material.WOOD.ordinal()] = 75;
		}
		catch (NoSuchFieldError noSuchFieldError75) {}
		try {
			Blocks.blocks[Material.WOOD_DOUBLE_STEP.ordinal()] = 76;
		}
		catch (NoSuchFieldError noSuchFieldError76) {}
		try {
			Blocks.blocks[Material.WOOL.ordinal()] = 77;
		}
		catch (NoSuchFieldError noSuchFieldError77) {}
		try {
			Blocks.blocks[Material.WORKBENCH.ordinal()] = 78;
		}
		catch (NoSuchFieldError noSuchFieldError78) {}
		try {
			Blocks.blocks[Material.BED_BLOCK.ordinal()] = 79;
		}
		catch (NoSuchFieldError noSuchFieldError79) {}
		try {
			Blocks.blocks[Material.PISTON_EXTENSION.ordinal()] = 80;
		}
		catch (NoSuchFieldError noSuchFieldError80) {}
		try {
			Blocks.blocks[Material.PISTON_MOVING_PIECE.ordinal()] = 81;
		}
		catch (NoSuchFieldError noSuchFieldError81) {}
		try {
			Blocks.blocks[Material.STEP.ordinal()] = 82;
		}
		catch (NoSuchFieldError noSuchFieldError82) {}
		try {
			Blocks.blocks[Material.WOOD_STAIRS.ordinal()] = 83;
		}
		catch (NoSuchFieldError noSuchFieldError83) {}
		try {
			Blocks.blocks[Material.CHEST.ordinal()] = 84;
		}
		catch (NoSuchFieldError noSuchFieldError84) {}
		try {
			Blocks.blocks[Material.SOIL.ordinal()] = 85;
		}
		catch (NoSuchFieldError noSuchFieldError85) {}
		try {
			Blocks.blocks[Material.WOODEN_DOOR.ordinal()] = 86;
		}
		catch (NoSuchFieldError noSuchFieldError86) {}
		try {
			Blocks.blocks[Material.COBBLESTONE_STAIRS.ordinal()] = 87;
		}
		catch (NoSuchFieldError noSuchFieldError87) {}
		try {
			Blocks.blocks[Material.IRON_DOOR_BLOCK.ordinal()] = 88;
		}
		catch (NoSuchFieldError noSuchFieldError88) {}
		try {
			Blocks.blocks[Material.CACTUS.ordinal()] = 89;
		}
		catch (NoSuchFieldError noSuchFieldError89) {}
		try {
			Blocks.blocks[Material.FENCE.ordinal()] = 90;
		}
		catch (NoSuchFieldError noSuchFieldError90) {}
		try {
			Blocks.blocks[Material.SOUL_SAND.ordinal()] = 91;
		}
		catch (NoSuchFieldError noSuchFieldError91) {}
		try {
			Blocks.blocks[Material.CAKE_BLOCK.ordinal()] = 92;
		}
		catch (NoSuchFieldError noSuchFieldError92) {}
		try {
			Blocks.blocks[Material.TRAP_DOOR.ordinal()] = 93;
		}
		catch (NoSuchFieldError noSuchFieldError93) {}
		try {
			Blocks.blocks[Material.MONSTER_EGGS.ordinal()] = 94;
		}
		catch (NoSuchFieldError noSuchFieldError94) {}
		try {
			Blocks.blocks[Material.IRON_FENCE.ordinal()] = 95;
		}
		catch (NoSuchFieldError noSuchFieldError95) {}
		try {
			Blocks.blocks[Material.THIN_GLASS.ordinal()] = 96;
		}
		catch (NoSuchFieldError noSuchFieldError96) {}
		try {
			Blocks.blocks[Material.MELON_BLOCK.ordinal()] = 97;
		}
		catch (NoSuchFieldError noSuchFieldError97) {}
		try {
			Blocks.blocks[Material.FENCE_GATE.ordinal()] = 98;
		}
		catch (NoSuchFieldError noSuchFieldError98) {}
		try {
			Blocks.blocks[Material.BRICK_STAIRS.ordinal()] = 99;
		}
		catch (NoSuchFieldError noSuchFieldError99) {}
		try {
			Blocks.blocks[Material.SMOOTH_STAIRS.ordinal()] = 100;
		}
		catch (NoSuchFieldError noSuchFieldError100) {}
		try {
			Blocks.blocks[Material.NETHER_FENCE.ordinal()] = 101;
		}
		catch (NoSuchFieldError noSuchFieldError101) {}
		try {
			Blocks.blocks[Material.NETHER_BRICK_STAIRS.ordinal()] = 102;
		}
		catch (NoSuchFieldError noSuchFieldError102) {}
		try {
			Blocks.blocks[Material.ENCHANTMENT_TABLE.ordinal()] = 103;
		}
		catch (NoSuchFieldError noSuchFieldError103) {}
		try {
			Blocks.blocks[Material.BREWING_STAND.ordinal()] = 104;
		}
		catch (NoSuchFieldError noSuchFieldError104) {}
		try {
			Blocks.blocks[Material.CAULDRON.ordinal()] = 105;
		}
		catch (NoSuchFieldError noSuchFieldError105) {}
		try {
			Blocks.blocks[Material.ENDER_PORTAL_FRAME.ordinal()] = 106;
		}
		catch (NoSuchFieldError noSuchFieldError106) {}
		try {
			Blocks.blocks[Material.ENDER_STONE.ordinal()] = 107;
		}
		catch (NoSuchFieldError noSuchFieldError107) {}
		try {
			Blocks.blocks[Material.DRAGON_EGG.ordinal()] = 108;
		}
		catch (NoSuchFieldError noSuchFieldError108) {}
		try {
			Blocks.blocks[Material.WOOD_STEP.ordinal()] = 109;
		}
		catch (NoSuchFieldError noSuchFieldError109) {}
		try {
			Blocks.blocks[Material.SANDSTONE_STAIRS.ordinal()] = 110;
		}
		catch (NoSuchFieldError noSuchFieldError110) {}
		try {
			Blocks.blocks[Material.ENDER_CHEST.ordinal()] = 111;
		}
		catch (NoSuchFieldError noSuchFieldError111) {}
		try {
			Blocks.blocks[Material.SPRUCE_WOOD_STAIRS.ordinal()] = 112;
		}
		catch (NoSuchFieldError noSuchFieldError112) {}
		try {
			Blocks.blocks[Material.BIRCH_WOOD_STAIRS.ordinal()] = 113;
		}
		catch (NoSuchFieldError noSuchFieldError113) {}
		try {
			Blocks.blocks[Material.JUNGLE_WOOD_STAIRS.ordinal()] = 114;
		}
		catch (NoSuchFieldError noSuchFieldError114) {}
		try {
			Blocks.blocks[Material.COBBLE_WALL.ordinal()] = 115;
		}
		catch (NoSuchFieldError noSuchFieldError115) {}
		try {
			Blocks.blocks[Material.ANVIL.ordinal()] = 116;
		}
		catch (NoSuchFieldError noSuchFieldError116) {}
		try {
			Blocks.blocks[Material.TRAPPED_CHEST.ordinal()] = 117;
		}
		catch (NoSuchFieldError noSuchFieldError117) {}
		try {
			Blocks.blocks[Material.DAYLIGHT_DETECTOR.ordinal()] = 118;
		}
		catch (NoSuchFieldError noSuchFieldError118) {}
		try {
			Blocks.blocks[Material.HOPPER.ordinal()] = 119;
		}
		catch (NoSuchFieldError noSuchFieldError119) {}
		try {
			Blocks.blocks[Material.QUARTZ_STAIRS.ordinal()] = 120;
		}
		catch (NoSuchFieldError noSuchFieldError120) {}
		try {
			Blocks.blocks[Material.STAINED_GLASS_PANE.ordinal()] = 121;
		}
		catch (NoSuchFieldError noSuchFieldError121) {}
		try {
			Blocks.blocks[Material.ACACIA_STAIRS.ordinal()] = 122;
		}
		catch (NoSuchFieldError noSuchFieldError122) {}
		try {
			Blocks.blocks[Material.DARK_OAK_STAIRS.ordinal()] = 123;
		}
		catch (NoSuchFieldError noSuchFieldError123) {}
		try {
			Blocks.blocks[Material.LADDER.ordinal()] = 146;
		}
		catch (NoSuchFieldError noSuchFieldError146) {}
		try {
			Blocks.blocks[Material.VINE.ordinal()] = 147;
		}
		catch (NoSuchFieldError noSuchFieldError147) {}
		try {
			Blocks.blocks[Material.WATER_LILY.ordinal()] = 148;
		}
		catch (NoSuchFieldError noSuchFieldError148) {}
		try {
			Blocks.blocks[Material.SKULL.ordinal()] = 149;
		}
		catch (NoSuchFieldError noSuchFieldError149) {}
		try {
			Blocks.blocks[Material.FLOWER_POT.ordinal()] = 150;
		}
		catch (NoSuchFieldError noSuchFieldError150) {}
		try {
			Blocks.blocks[Material.DIODE_BLOCK_OFF.ordinal()] = 151;
		}
		catch (NoSuchFieldError noSuchFieldError151) {}
		try {
			Blocks.blocks[Material.DIODE_BLOCK_ON.ordinal()] = 152;
		}
		catch (NoSuchFieldError noSuchFieldError152) {}
		try {
			Blocks.blocks[Material.REDSTONE_COMPARATOR_OFF.ordinal()] = 153;
		}
		catch (NoSuchFieldError noSuchFieldError153) {}
		try {
			Blocks.blocks[Material.REDSTONE_COMPARATOR_ON.ordinal()] = 154;
		}
		catch (NoSuchFieldError noSuchFieldError154) {}
	}

}
