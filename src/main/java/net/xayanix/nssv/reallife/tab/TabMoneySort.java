package net.xayanix.nssv.reallife.tab;

import java.util.Comparator;

public class TabMoneySort implements Comparator<TabMoney> {

    @Override
    public int compare(TabMoney o1, TabMoney o2) {
        if (o1.getMoney() == o2.getMoney())
            return 0;
        else if(o1.getMoney() > o2.getMoney())
            return -1;
        else if(o1.getMoney() < o2.getMoney())
            return 1;
        else return 1;

    }

}
