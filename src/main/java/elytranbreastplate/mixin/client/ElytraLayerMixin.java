package elytranbreastplate.mixin.client;

import elytranbreastplate.ElytranBreastplateMod;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ElytraLayer.class)
public class ElytraLayerMixin {
	@Redirect(
			method =
					"render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
			at =
					@At(
							value = "INVOKE",
							target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"),
			require = 0)
	private boolean elytran$renderWings(ItemStack stack, Item item) {
		return ElytranBreastplateMod.chestActsAsElytra(stack, item);
	}
}
