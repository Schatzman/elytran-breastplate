package elytranbreastplate.mixin;

import elytranbreastplate.ElytranBreastplateMod;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Redirect(
			method = "updateFallFlying",
			at =
					@At(
							value = "INVOKE",
							target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
	private boolean elytran$fallFlyingChest(ItemStack stack, Item item) {
		return ElytranBreastplateMod.chestActsAsElytra(stack, item);
	}

	@Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
	private void elytran$noFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (source.is(DamageTypeTags.IS_FALL)
				&& self.getItemBySlot(EquipmentSlot.CHEST).is(ElytranBreastplateMod.ELYTRAN_BREASTPLATE)) {
			cir.setReturnValue(false);
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	private void elytran$regeneration(CallbackInfo ci) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (self.level().isClientSide()) {
			return;
		}
		if (!self.getItemBySlot(EquipmentSlot.CHEST).is(ElytranBreastplateMod.ELYTRAN_BREASTPLATE)) {
			return;
		}
		self.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1, false, true, true));
	}
}
