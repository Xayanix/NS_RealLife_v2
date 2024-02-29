package net.xayanix.nssv.reallife.petshop;

import lombok.Getter;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PetManager {

    @Getter
    private static PetManager instance;

    @Getter
    private List<Pet> petList;

    public PetManager() {
        instance = this;
        this.petList = new ArrayList<>();

        this.petList.addAll(
                Arrays.asList(
                    new Pet("Kon", 40000, EntityType.HORSE),
                    new Pet("Kot", 25000, EntityType.OCELOT),
                    new Pet("Królik", 15000, EntityType.RABBIT),
                    new Pet("Papuga", 30000, EntityType.PARROT),
                    new Pet("Lama", 45000, EntityType.LLAMA),
                    new Pet("Osiol", 30000, EntityType.DONKEY),
                    new Pet("Mul", 30000, EntityType.MULE),
                    new Pet("Mis polarny", 50000, EntityType.POLAR_BEAR),
                    new Pet("Kalamarnica", 10000, EntityType.SQUID),
                    new Pet("Krowa grzybowa", 100000, EntityType.MUSHROOM_COW),
                    new Pet("Kon szkielet", 200000, EntityType.SKELETON_HORSE)
                ));

    }

}
