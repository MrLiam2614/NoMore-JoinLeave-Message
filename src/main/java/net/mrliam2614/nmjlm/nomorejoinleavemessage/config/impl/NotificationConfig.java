package net.mrliam2614.nmjlm.nomorejoinleavemessage.config.impl;

import lombok.Getter;
import lombok.Setter;
import net.mrliam2614.nmjlm.nomorejoinleavemessage.config.annotation.ConfigField;
import net.mrliam2614.nmjlm.nomorejoinleavemessage.config.annotation.ConfigFile;

@ConfigFile(name = "notification.yml", path = "./config/no-more-join-leave-message")
@Getter
@Setter
public class NotificationConfig {
    @ConfigField(path = "joinNotification")
    private boolean joinNotification = true;
    @ConfigField(path = "leaveNotification")
    private boolean leaveNotification = true;
}
