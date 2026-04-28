package elytranbreastplate.mixin.client;

import elytranbreastplate.ElytranBreastplateMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Elytra Slot skips rendering when the trinket stack is an armor item. Our breastplate is
 * armor but should still show elytra wings; for the guard only, pretend the item is vanilla
 * elytra so the layer draws (texture still comes from ElytraRenderResult).
 */
@Mixin(targets = "com.illusivesoulworks.elytraslot.client.ElytraSlotLayer")
public class ElytraSlotLayerMixin {
	@Redirect(
			method = "lambda$render$0",
			at =
					@At(
							value = "INVOKE",
							target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
	private Item elytran$unmaskForArmorCheck(ItemStack stack) {
		Item item = stack.getItem();
		if (item == ElytranBreastplateMod.ELYTRAN_BREASTPLATE) {
			return Items.ELYTRA;
		}
		return item;
	}
}
