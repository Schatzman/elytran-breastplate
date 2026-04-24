package elytranbreastplate.mixin;

import elytranbreastplate.ElytranBreastplateMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {
	@Redirect(
			method = "tryToStartFallFlying",
			at =
					@At(
							value = "INVOKE",
							target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private boolean elytran$tryFlyChest(ItemStack stack, Item item) {
		return ElytranBreastplateMod.chestActsAsElytra(stack, item);
	}
}
