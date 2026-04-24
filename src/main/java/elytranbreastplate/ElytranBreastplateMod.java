package elytranbreastplate;

import elytranbreastplate.item.ElytranBreastplateItem;
import elytranbreastplate.loot.ElytranTreasureLoot;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ModInitializer;

public final class ElytranBreastplateMod implements ModInitializer {
	public static final String MOD_ID = "elytran_breastplate";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Item ELYTRAN_BREASTPLATE = new ElytranBreastplateItem();

	@Override
	public void onInitialize() {
		Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, "elytran_breastplate"), ELYTRAN_BREASTPLATE);
		ElytranTreasureLoot.register();
		LOGGER.info("Elytran Breastplate initialized");
	}

	/** Redirect helper: treat our chestplate like elytra for flight / wing checks when vanilla compares to {@link Items#ELYTRA}. */
	public static boolean chestActsAsElytra(ItemStack stack, net.minecraft.world.item.Item item) {
		return stack.is(item) || (item == Items.ELYTRA && stack.is(ELYTRAN_BREASTPLATE));
	}
}
