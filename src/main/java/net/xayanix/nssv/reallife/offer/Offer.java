package net.xayanix.nssv.reallife.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class Offer {

    private String offerer;
    private int price;
    private OfferType type;
    private final long offerTime = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(30);

}
