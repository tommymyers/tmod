package cc.tommymyers.tmod.mixin;

import cc.tommymyers.tmod.input.InputHandler;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    @Inject(
        method = "onKey",
        cancellable = true,
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/Keyboard;debugCrashStartTime:J"
        )
    )
    private void onKeyboardInput(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
        if (InputHandler.processKey(key, action)) {
            ci.cancel();
        }
    }

}
