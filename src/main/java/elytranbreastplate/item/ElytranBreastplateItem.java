package elytranbreastplate.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class ElytranBreastplateItem extends ArmorItem {
	private static final UUID BONUS_HEALTH_ID = UUID.fromString("c0ffee11-bea5-4000-8000-00000bca5710");

	public ElytranBreastplateItem() {
		super(
				ModArmorMaterials.ELYTRAN_BREASTPLATE,
				ArmorItem.Type.CHESTPLATE,
				new Item.Properties().rarity(Rarity.EPIC).fireResistant().stacksTo(64));
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
		}
		return builder.build();
	}
}
