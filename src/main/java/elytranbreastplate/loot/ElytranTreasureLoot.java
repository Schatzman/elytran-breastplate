package elytranbreastplate.loot;

import elytranbreastplate.ElytranBreastplateMod;
import java.util.Set;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetNbtFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public final class ElytranTreasureLoot {
	private static final Set<ResourceLocation> TREASURE_TABLES =
			Set.of(
					new ResourceLocation("minecraft", "chests/buried_treasure"),
					new ResourceLocation("minecraft", "chests/shipwreck_treasure"),
					new ResourceLocation("minecraft", "chests/end_city_treasure"),
					new ResourceLocation("minecraft", "chests/bastion_treasure"),
					new ResourceLocation("minecraft", "chests/ancient_city"),
					new ResourceLocation("minecraft", "chests/ruined_portal"),
					new ResourceLocation("minecraft", "chests/woodland_mansion"),
					new ResourceLocation("minecraft", "chests/stronghold_library"));

	private ElytranTreasureLoot() {
	}

	public static void register() {
		LootTableEvents.MODIFY.register(
				(resourceManager, lootManager, id, tableBuilder, source) -> {
					if (!source.isBuiltin() || !TREASURE_TABLES.contains(id)) {
						return;
					}
					tableBuilder.withPool(treasurePool());
				});
	}

	private static LootPool.Builder treasurePool() {
		return LootPool.lootPool()
				.setRolls(ConstantValue.exactly(3))
				.add(
						AlternativesEntry.alternatives(
								branch(0), branch(1), branch(2), branch(3)));
	}

	private static LootPoolSingletonContainer.Builder<?> branch(int customModelData) {
		CompoundTag tag = new CompoundTag();
		tag.putInt("CustomModelData", customModelData);
		CompoundTag trim = new CompoundTag();
		trim.putString("pattern", "minecraft:eye");
		trim.putString("material", "minecraft:redstone");
		tag.put(ArmorTrim.TAG_TRIM_ID, trim);
		SetEnchantmentsFunction.Builder ench = new SetEnchantmentsFunction.Builder();
		ench.withEnchantment(Enchantments.ALL_DAMAGE_PROTECTION, ConstantValue.exactly(5));
		ench.withEnchantment(Enchantments.BLAST_PROTECTION, ConstantValue.exactly(5));
		ench.withEnchantment(Enchantments.PROJECTILE_PROTECTION, ConstantValue.exactly(5));
		ench.withEnchantment(Enchantments.FIRE_PROTECTION, ConstantValue.exactly(5));
		Enchantment flightAffinity =
				BuiltInRegistries.ENCHANTMENT.get(new ResourceLocation("flight_affinity", "flight_affinity"));
		if (flightAffinity != null) {
			ench.withEnchantment(flightAffinity, ConstantValue.exactly(1));
		}
		return LootItem.lootTableItem(ElytranBreastplateMod.ELYTRAN_BREASTPLATE)
				.apply(SetItemCountFunction.setCount(ConstantValue.exactly(1)))
				.apply(ench)
				.apply(SetNbtFunction.setTag(tag));
	}
}
