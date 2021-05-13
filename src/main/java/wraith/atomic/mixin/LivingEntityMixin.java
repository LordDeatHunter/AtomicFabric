package wraith.atomic.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.atomic.registry.ItemRegistry;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "swingHand(Lnet/minecraft/util/Hand;Z)V", at = @At("HEAD"), cancellable = true)
    public void swingHand(Hand hand, boolean bl, CallbackInfo ci) {
        if (((LivingEntity)(Object)this).getStackInHand(hand).getItem() == ItemRegistry.get("atomic_combiner")) {
            ci.cancel();
        }
    }

}
