package elytranbreastplate.item;

import java.util.EnumMap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public enum ModArmorMaterials implements ArmorMaterial {
	ELYTRAN_BREASTPLATE(
			"elytran_breastplate",
			37,
			createDefenseMap(16),
			15,
			SoundEvents.ARMOR_EQUIP_NETHERITE,
			6.0F,
			0.1F,
			Ingredient.of(Items.NETHERITE_INGOT));

	private static EnumMap<ArmorItem.Type, Integer> createDefenseMap(int chestDefense) {
		EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
		map.put(ArmorItem.Type.BOOTS, 0);
		map.put(ArmorItem.Type.LEGGINGS, 0);
		map.put(ArmorItem.Type.CHESTPLATE, chestDefense);
		map.put(ArmorItem.Type.HELMET, 0);
		return map;
	}

	private final String name;
	private final int durabilityMultiplier;
	private final EnumMap<ArmorItem.Type, Integer> defenseByType;
	private final int enchantmentValue;
	private final SoundEvent equipSound;
	private final float toughness;
	private final float knockbackResistance;
	private final Ingredient repairIngredient;

	ModArmorMaterials(
			String name,
			int durabilityMultiplier,
			EnumMap<ArmorItem.Type, Integer> defenseByType,
			int enchantmentValue,
			SoundEvent equipSound,
			float toughness,
			float knockbackResistance,
			Ingredient repairIngredient) {
		this.name = name;
		this.durabilityMultiplier = durabilityMultiplier;
		this.defenseByType = defenseByType;
		this.enchantmentValue = enchantmentValue;
		this.equipSound = equipSound;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
		this.repairIngredient = repairIngredient;
	}

	@Override
	public int getDurabilityForType(ArmorItem.Type type) {
		if (type != ArmorItem.Type.CHESTPLATE) {
			return 0;
		}
		return 15 * this.durabilityMultiplier;
	}

	@Override
	public int getDefenseForType(ArmorItem.Type type) {
		return this.defenseByType.getOrDefault(type, 0);
	}

	@Override
	public int getEnchantmentValue() {
		return this.enchantmentValue;
	}

	@Override
	public SoundEvent getEquipSound() {
		return this.equipSound;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return this.repairIngredient;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public float getToughness() {
		return this.toughness;
	}

	@Override
	public float getKnockbackResistance() {
		return this.knockbackResistance;
	}
}
