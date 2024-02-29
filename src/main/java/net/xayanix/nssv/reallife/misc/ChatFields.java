package net.xayanix.nssv.reallife.misc;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import net.xayanix.nssv.core.utils.ChatUtil;

public class ChatFields {

    public static String SEPARATOR = "&8&m-------------------------------------";
    public static String ME_COLOR = ChatUtil.fixColors("&d");
    public static TextComponent TELEPORT_MESSAGE;

    static {
        TELEPORT_MESSAGE = new TextComponent(ChatUtil.fixColors("&8#&b Kliknij, aby teleportowac sie do gracza."));
        TELEPORT_MESSAGE.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( ChatUtil.fixColors("&bTeleportuj sie natychmiast do gracza.") ).create() ) );
    }

    public static TextComponent getTeleportMessage(String nickname){
        TELEPORT_MESSAGE.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/itp " + nickname ) );
        return TELEPORT_MESSAGE;
    }

}
