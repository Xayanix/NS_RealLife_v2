package net.xayanix.nssv.reallife.user;

import lombok.Getter;
import lombok.Setter;
import net.xayanix.nssv.core.utils.ChatUtil;
import org.inventivetalent.glow.GlowAPI;

@Getter
@Setter
public class UserConfiguration {

    private boolean displayJobOnPrefix;
    private boolean displayLevelOnChat;
    private boolean displayCompanyOnChat;
    private boolean receiveAdvertisments;
    private boolean nicknameColorsOnTab;
    private boolean pvpState;
    private String nicknameColor;
    private long nicknameExpire;

    public UserConfiguration(){
        this.displayJobOnPrefix = true;
        this.displayLevelOnChat = true;
        this.displayCompanyOnChat = true;
        this.receiveAdvertisments = true;
        this.nicknameColorsOnTab = true;
        this.pvpState = false;
        this.nicknameColor = ChatUtil.fixColors("&7");
        this.nicknameExpire = 0;
    }

    public void init(){
        if(this.nicknameExpire > 0 && this.nicknameExpire < System.currentTimeMillis()){
            this.nicknameExpire = 0;
            this.nicknameColor = ChatUtil.fixColors("&7");
        }

    }

}
