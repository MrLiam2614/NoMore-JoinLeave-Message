package net.mrliam2614.nmjlm.nomorejoinleavemessage.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.mrliam2614.nmjlm.nomorejoinleavemessage.NoMoreJoinLeaveMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

@Mixin(targets = "net.minecraft.server.players.PlayerList")
public class PlayerConnectionMixin {
    @Inject(method = "broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Ljava/util/function/Function;Z)V", at = @At("HEAD"), cancellable = true)
    private void onBroadcastSystem(net.minecraft.network.chat.Component component, Function<ServerPlayer, net.minecraft.network.chat.Component> function, boolean bl, CallbackInfo ci) {
        String messageText = component.getString();
        if (messageText.contains("joined the game") && !NoMoreJoinLeaveMessage.getInstance().getNotificationConfig().isJoinNotification()) {
            ci.cancel();
        } else if (messageText.contains("left the game") && !NoMoreJoinLeaveMessage.getInstance().getNotificationConfig().isLeaveNotification()) {
            ci.cancel();
        }
    }
}
