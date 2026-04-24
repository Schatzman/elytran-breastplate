package elytranbreastplate.mixin.client;

import elytranbreastplate.item.ElytranBreastplateItem;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Vanilla resolves armor textures under the {@code minecraft} namespace; mod textures live under
 * {@code elytran_breastplate}.
 */
@Mixin(HumanoidArmorLayer.class)
public class HumanoidArmorLayerMixin {
	@Inject(method = "getArmorLocation", at = @At("HEAD"), cancellable = true)
	private void elytran$modNamespace(
			ArmorItem armorItem,
			boolean inner,
			@Nullable String trimSuffix,
			CallbackInfoReturnable<ResourceLocation> cir) {
		if (!(armorItem instanceof ElytranBreastplateItem)) {
			return;
		}
		int layer = inner ? 2 : 1;
		cir.setReturnValue(
				new ResourceLocation(
						"elytran_breastplate",
						"textures/models/armor/elytran_breastplate_layer_" + layer + ".png"));
	}
}
