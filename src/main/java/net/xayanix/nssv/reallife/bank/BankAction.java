package net.xayanix.nssv.reallife.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BankAction {

    DEPOSIT("WPLATA"),
    WITHDRAW("WYPLATA");

    private String desc;

}
