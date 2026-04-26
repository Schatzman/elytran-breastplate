package elytranbreastplate.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ElytranBreastplateItem extends ArmorItem {
	private static final UUID BONUS_HEALTH_ID = UUID.fromString("c0ffee11-bea5-4000-8000-00000bca5710");
	private static final UUID MOVEMENT_SPEED_BOOST_ID =
			UUID.fromString("c0ffee11-bea5-4000-8000-00000bca5711");

	public ElytranBreastplateItem() {
		super(
				ModArmorMaterials.ELYTRAN_BREASTPLATE,
				ArmorItem.Type.CHESTPLATE,
				new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(64));
	}

	@Override
	public void appendHoverText(
			ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);
		tooltip.add(
				Component.translatable("item.elytran_breastplate.elytran_breastplate.lore.1")
						.withStyle(ChatFormatting.GRAY));
		tooltip.add(
				Component.translatable("item.elytran_breastplate.elytran_breastplate.lore.2")
						.withStyle(ChatFormatting.GRAY));
	}

	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canBeDepleted() {
		return false;
	}

	/**
	 * 1.20.1: Unbreakable NBT (same as {@code /give ... {Unbreakable:1}}) so durability
	 * is blocked, tooltip shows "Unbreakable", and other mods that read the tag behave.
	 */
	@Override
	public ItemStack getDefaultInstance() {
		ItemStack stack = super.getDefaultInstance();
		stack.getOrCreateTag().putBoolean("Unbreakable", true);
		return stack;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
		super.inventoryTick(stack, level, entity, slot, selected);
		if (stack.getItem() != this) {
			return;
		}
		if (!stack.getOrCreateTag().getBoolean("Unbreakable")) {
			stack.getOrCreateTag().putBoolean("Unbreakable", true);
		}
		if (stack.getDamageValue() > 0) {
			stack.setDamageValue(0);
		}
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.putAll(super.getDefaultAttributeModifiers(slot));
		if (slot == EquipmentSlot.CHEST) {
			builder.put(
					Attributes.MAX_HEALTH,
					new AttributeModifier(
							BONUS_HEALTH_ID,
							"Elytran breastplate",
							100.0,
							AttributeModifier.Operation.ADDITION));
			builder.put(
					Attributes.MOVEMENT_SPEED,
					new AttributeModifier(
							MOVEMENT_SPEED_BOOST_ID,
							"Elytran breastplate speed",
							0.1,
							AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		return builder.build();
	}
}
