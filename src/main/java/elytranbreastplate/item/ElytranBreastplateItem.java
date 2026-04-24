package elytranbreastplate.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.UUID;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

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
