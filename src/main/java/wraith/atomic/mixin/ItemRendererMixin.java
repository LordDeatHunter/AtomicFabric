package wraith.atomic.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wraith.atomic.item.AtomicCombinerItem;
import wraith.atomic.registry.ItemRegistry;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow @Final private ItemModels models;
    int rotation = 0;
    float itemRotation = 0;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"))
    public void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (stack.getItem() != ItemRegistry.get("atomic_combiner") || renderMode == ModelTransformation.Mode.GROUND) {
            return;
        }
        if (renderMode != ModelTransformation.Mode.GUI) {
            DefaultedList<ItemStack> inventory = AtomicCombinerItem.getInventoryFromStack(stack);
            int i = 0;
            for (int ox = -1; ox <= 1; ++ox) {
                for (int oy = -1; oy <= 1; ++oy) {
                    if (ox == 0 && oy == 0) {
                        continue;
                    }
                    matrices.push();
                    matrices.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(itemRotation));
                    matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(itemRotation));
                    matrices.translate(ox / 2f, 0, oy / 2f);
                    matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(itemRotation + i * 6));
                    matrices.scale(0.5f, 0.5f, 0.5f);
                    MinecraftClient.getInstance().getItemRenderer().renderItem(inventory.get(i), renderMode, false, matrices, vertexConsumers, light, overlay, models.getModel(inventory.get(i)));
                    matrices.pop();
                    ++i;
                }
            }
            itemRotation = (itemRotation + 0.5f) % 360;
        }

        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(rotation));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(-45));
        rotation = (rotation + 1) % 360;
    }

}
