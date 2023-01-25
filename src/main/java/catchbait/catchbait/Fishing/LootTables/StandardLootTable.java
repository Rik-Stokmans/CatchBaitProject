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

public class StandardLootTable {

    public static ArrayList<Fish> Tier1Fish;

    public static void initTier1Fish() {
        Tier1Fish = new ArrayList<>();

        Tier1Fish.add(new Fish(Material.COD, "&bThe Arapaima", 0.8, 3));
        Tier1Fish.add(new Fish(Material.COD, "&bTambaqui", 1.0, 3));
        Tier1Fish.add(new Fish(Material.COD, "&bCandiru", 1.2, 3));
        Tier1Fish.add(new Fish(Material.COD, "&bArmored Catfish", 1.4, 3));

        Tier1Fish.add(new Fish(Material.SALMON, "&bRed-bellied Piranha", 3.2, 2));
        Tier1Fish.add(new Fish(Material.SALMON, "&bElectric Eel", 3.8, 2));
        Tier1Fish.add(new Fish(Material.SALMON, "&bTucunaré Peacock Bass", 4.5, 2));

        Tier1Fish.add(new Fish(Material.TROPICAL_FISH, "&bPirarucu", 7.1, 1.5));
        Tier1Fish.add(new Fish(Material.TROPICAL_FISH, "&bPayara Vampire Fish", 8.4, 1.5));

        Tier1Fish.add(new Fish(Material.PUFFERFISH, "&bThe Amazon Puffer", 16.3, 1));

    }

    public static ItemStack getFish(Fish fish) {
        Material material = fish.getMaterial();
        String name = fish.getName();
        ItemStack item = new ItemStack(material);
        //code that generates a random tier
        int tier = 0;
        //initial chances
        double tier0 = 15;
        double tier1 = 10;
        double tier2 = 3;
        double tier3 = 1;
        //total amount later used to calculate a random number
        double tierTotal = tier0 + tier1 + tier2 + tier3;
        //generates a random number to determine what tier the item should be
        double tierRandom = ThreadLocalRandom.current().nextDouble(0, tierTotal);
        //sets the tier to the
        double value;
        if (tierRandom < tier0)  {
            tier = 0;
            value = fish.getBaseValue();
        }
        else if (tierRandom < tier0 + tier1) {
            tier = 1;
            value = fish.getBaseValue() * 2.0;
        }
        else if (tierRandom < tier0 + tier1 + tier2) {
            tier = 2;
            value = fish.getBaseValue() * 4.0;
        }
        else {
            tier = 3;
            value = fish.getBaseValue() * 7.0;
        }



        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        //Item Meta
        ItemMeta iMeta = item.getItemMeta();
        iMeta.setDisplayName(format(name));
        item.setItemMeta(iMeta);

        //NBT
        NBTItem nbti = new NBTItem(item);
        nbti.setString("Type", "FISH");
        nbti.setDouble("Value", value);
        nbti.setInteger("Tier", tier);
        nbti.applyNBT(item);

        item = refreshItem(item);
        //returning the item
        return item;
    }

    public static Fish getTier1Fish() {
        double totalRarity = 0;
        for (Fish fish : Tier1Fish) totalRarity += fish.getRarity();

        double randomNum = ThreadLocalRandom.current().nextDouble(0, totalRarity);

        for (Fish fish : Tier1Fish) {
            if (randomNum <= fish.getRarity()) {
                return fish;
            }
            else randomNum -= fish.getRarity();
        }
        return null;
    }
}
