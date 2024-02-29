package net.xayanix.nssv.reallife.misc;

import net.xayanix.nssv.core.utils.ChatUtil;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import xyz.upperlevel.spigot.book.BookUtil;

public class ItemContainer {

    public static ItemStack TUTORIAL_BOOK = BookUtil.writtenBook()
            .author("NETHERSTORM")
            .title("Poradnik")
            .pages(
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("&lSPIS TRESCI:"))
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("1. O co tutaj chodzi?")
                                            .onClick(BookUtil.ClickAction.changePage(2))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby przejsc do tej sekcji."))
                                            .build()
                            ).newLine()
                            .add(
                                    BookUtil.TextBuilder.of("2. Jak zarabiac?")
                                            .onClick(BookUtil.ClickAction.changePage(3))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby przejsc do tej sekcji."))
                                            .build()
                            ).newLine()
                            .add(
                                    BookUtil.TextBuilder.of("3. Podstawowe komendy")
                                            .onClick(BookUtil.ClickAction.changePage(4))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby przejsc do tej sekcji."))
                                            .build()
                            ).newLine()
                            .add(
                                    BookUtil.TextBuilder.of("4. Wyplaty")
                                            .onClick(BookUtil.ClickAction.changePage(5))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby przejsc do tej sekcji."))
                                            .build()
                            ).newLine()
                            .add(
                                    BookUtil.TextBuilder.of("5. Darmowe jedzenie")
                                            .onClick(BookUtil.ClickAction.changePage(6))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby przejsc do tej sekcji."))
                                            .build()
                            ).newLine()
                            .add(
                                    BookUtil.TextBuilder.of("6. Gdzie jest ...?")
                                            .onClick(BookUtil.ClickAction.changePage(7))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby przejsc do tej sekcji."))
                                            .build()
                            ).newLine()
                            .build(),
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("1. O co tutaj chodzi?"))
                            .newLine().newLine()
                            .add("Znajdujesz sie na serwerze RP (RolePlay), gdzie staramy sie odwzorowac najlepiej jak to mozliwe realne zycie.")
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("Powrót")
                                            .color(ChatColor.RED)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby powrocic."))
                                            .build()
                            ).newLine()
                            .build(),
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("2. Jak zarabiac?"))
                            .newLine().newLine()
                            .add("Aby zarabiac musisz posiadac prace. Udaj sie do urzedu i wez jedna z dostepnych prac.")
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("Powrót")
                                            .color(ChatColor.RED)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby powrocic."))
                                            .build()
                            ).newLine()
                            .build(),
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("3. Podstawowe komendy"))
                            .newLine().newLine()
                            .add("Wszystkie komendy znajdziesz w /pomoc.")
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("Powrót")
                                            .color(ChatColor.RED)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby powrocic."))
                                            .build()
                            ).newLine()
                            .build(),
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("4. Wyplaty"))
                            .newLine().newLine()
                            .add("Wyplaty sa realizowane co 15 minut.")
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("Powrót")
                                            .color(ChatColor.RED)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby powrocic."))
                                            .build()
                            ).newLine()
                            .build(),
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("5. Darmowe jedzenie"))
                            .newLine().newLine()
                            .add("Aby za darmo podniesc poziom glodu udaj sie do budki z Hot-Dogami kolo urzedu.")
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("Powrót")
                                            .color(ChatColor.RED)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby powrocic."))
                                            .build()
                            ).newLine()
                            .build(),
                    new BookUtil.PageBuilder()
                            .add(ChatUtil.fixColors("6. Gdzie jest ...?"))
                            .newLine().newLine()
                            .add("Aby sprawnie i latwo poruszac sie po miescie korzystaj z przystanku autobusowego.")
                            .newLine().newLine()
                            .add(
                                    BookUtil.TextBuilder.of("Powrót")
                                            .color(ChatColor.RED)
                                            .onClick(BookUtil.ClickAction.changePage(1))
                                            .onHover(BookUtil.HoverAction.showText("Kliknij, aby powrocic."))
                                            .build()
                            ).newLine()
                            .build()
            )
            .build();

}
