package net.xayanix.nssv.reallife.company;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CompanyType {

    UNKNOWN("Nie sprecyzowano"),
    HANDLOWA("Handlowa"),
    USLUGOWA("Uslugowa"),
    REKLAMOWA("Reklamowa");

    private String name;

    @Override
    public String toString() {
        return this.name;
    }
}
