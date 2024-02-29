package net.xayanix.nssv.reallife.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OfferType {

    REDUCE_WANTED_LEVEL("WL"),
    FREEDOM_FROM_JAIL("WOLNOSC"),
    CASINO("KASYNO"),
    FUEL("TANKOWANIE"),
    REPAIR("NAPRAWA"),
    COMPANY("FIRMA"),
    FAMILY("RODZINA"),
    PROTECTION("OCHRONA"),
    HEAL("ULECZENIE"),
    ANVIL("KOWAL"),
    MARRY("SLUB");

    private String commandArgument;

    @Override
    public String toString() {
        return this.getCommandArgument();
    }
}
