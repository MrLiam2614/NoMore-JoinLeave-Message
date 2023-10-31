package net.mrliam2614.nmjlm.nomorejoinleavemessage;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.mrliam2614.nmjlm.nomorejoinleavemessage.config.ConfigManager;
import net.mrliam2614.nmjlm.nomorejoinleavemessage.config.impl.NotificationConfig;

@Getter
public class NoMoreJoinLeaveMessage implements ModInitializer {
    @Getter
    private static NoMoreJoinLeaveMessage instance;
    private NotificationConfig notificationConfig;

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        instance = this;
        notificationConfig = ConfigManager.loadConfig(NotificationConfig.class);

        System.out.println("NoMoreJoinLeaveMessage has been initialized!");
        System.out.println("Join Notification: " + notificationConfig.isJoinNotification());
        System.out.println("Leave Notification: " + notificationConfig.isLeaveNotification());
    }
}
