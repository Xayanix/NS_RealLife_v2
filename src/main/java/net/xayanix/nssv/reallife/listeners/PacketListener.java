package net.xayanix.nssv.reallife.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.PacketPlayOutScoreboardDisplayObjective;
import net.xayanix.nssv.core.basic.Main;

public class PacketListener {

    @Getter
    private static PacketListener instance;

    public PacketListener() {
        instance = this;
       // this.register();
    }

    public void register() {
        (ProtocolLibrary.getProtocolManager()).addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.LOWEST, new PacketType[]{PacketType.Play.Server.SCOREBOARD_OBJECTIVE, PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE}) {
            public void onPacketSending(final PacketEvent e) {

                if(e.getPacket().getType() == PacketType.Play.Server.SCOREBOARD_OBJECTIVE){
                    String name = e.getPacket().getStrings().readSafely(0);
                    System.out.println("Reading: " + name);
                    if (name.length() > 16){
                        System.out.println("Cancelling packet: " + name);
                        e.setReadOnly(false);
                        e.getPacket().getStrings().write(0, name.substring(0, 16));
                    }
                } else if(e.getPacket().getType() == PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE){
                    String name = e.getPacket().getStrings().readSafely(0);
                    System.out.println("Reading: " + name);
                    if (name.length() > 16){
                        System.out.println("Cancelling packet " + e.getPacket().getType().toString() + ": " + name);
                        e.setReadOnly(false);
                        e.getPacket().getStrings().write(0, name.substring(0, 16));
                    }
                }




            }
        });
    }

}
