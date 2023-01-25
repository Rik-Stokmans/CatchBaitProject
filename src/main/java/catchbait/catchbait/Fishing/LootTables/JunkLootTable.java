package catchbait.catchbait.Fishing.LootTables;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static catchbait.catchbait.ItemHandling.ItemTools.refreshItem;
import static catchbait.catchbait.Utils.ChatUtils.format;

public class JunkLootTable {
    public static ArrayList<Junk> Tier1Junk;

    public static void initTier1Junk() {
        Tier1Junk = new ArrayList<>();

        Tier1Junk.add(new Junk(Material.GLASS_PANE, "&7Glass Shards", 0.3, 3));
        Tier1Junk.add(new Junk(Material.STONE_BUTTON, "&7Rock", 0.4, 3));
        Tier1Junk.add(new Junk(Material.FEATHER, "&7Feather", 0.5, 3));
        Tier1Junk.add(new Junk(Material.BOWL, "&7Broken Bowl", 0.7, 3));

        Tier1Junk.add(new Junk(Material.POISONOUS_POTATO, "&7Rotten Potato", 1.1, 2));
        Tier1Junk.add(new Junk(Material.STRING, "&7Old Fishing Line", 1.2, 2));
        Tier1Junk.add(new Junk(Material.FLOWER_POT, "&7Flower Pot", 1.3, 2));

        Tier1Junk.add(new Junk(Material.STICK, "&7Old Walking Stick", 1.7, 1.5));
        Tier1Junk.add(new Junk(Material.TRIPWIRE_HOOK, "&7Rusted Key", 1.9, 1.5));

        Tier1Junk.add(new Junk(Material.SKELETON_SKULL, "&7Skull", 2.7, 1));
    }

    public static ItemStack getJunk(Junk junk) {
        Material material = junk.getMaterial();
        String name = junk.getName();
        double value = junk.getBaseValue();
        ItemStack item = new ItemStack(material);

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        //Item Meta
        ItemMeta iMeta = item.getItemMeta();
        iMeta.setDisplayName(format(name));
        item.setItemMeta(iMeta);

        //NBT
        NBTItem nbti = new NBTItem(item);
        nbti.setString("Type", "JUNK");
        nbti.setDouble("Value", value);
        nbti.applyNBT(item);

        item = refreshItem(item);
        //returning the item
        return item;
    }

    public static Junk getTier1Junk() {
        double totalRarity = 0;
        for (Junk junk : Tier1Junk) totalRarity += junk.getRarity();

        double randomNum = ThreadLocalRandom.current().nextDouble(0, totalRarity);

        for (Junk junk : Tier1Junk) {
            if (randomNum <= junk.getRarity()) {
                return junk;
            }
            else randomNum -= junk.getRarity();
        }
        return null;
    }
}
