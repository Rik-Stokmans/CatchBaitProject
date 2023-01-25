package catchbait.catchbait.Fishing.LootTables;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static catchbait.catchbait.Utils.ChatUtils.format;

public class CharacterLootTable {

    public static ArrayList<ItemStack> Tier1CharacterItemsLootTable;

    public static void initCharacterTables() {
        Tier1CharacterItemsLootTable = new ArrayList<>();

        ItemStack item = getItem(Material.BARREL, "&6Old Barrel o' Whiskey", " ,&6Nice", 100);
        Tier1CharacterItemsLootTable.add(item);
    }

    public static ItemStack getItem(Material item, String name, String lore, double baseWorth) {
        ItemStack i = new ItemStack(item);
        ItemMeta meta = i.getItemMeta();

        meta.setDisplayName(format(name));

        String l = format(lore);
        List<String> lores = Arrays.asList(l.split(","));
        meta.setLore(lores);

        NBTItem nbti = new NBTItem(i);
        nbti.setDouble("BaseWorth", baseWorth);
        nbti.applyNBT(i);

        i.setItemMeta(meta);

        return i;
    }

    public static ItemStack getTier1CharacterLoot() {
        int size = Tier1CharacterItemsLootTable.size();
        int randomNum = ThreadLocalRandom.current().nextInt(0, size);
        return Tier1CharacterItemsLootTable.get(randomNum);
    }
}
