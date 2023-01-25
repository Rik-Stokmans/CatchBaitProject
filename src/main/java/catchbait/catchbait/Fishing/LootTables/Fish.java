package catchbait.catchbait.Fishing.LootTables;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Fish {
    Material material;
    String name;
    double baseValue, rarity;


    public Fish(Material material, String name, double baseValue, double rarity) {
        this.material = material;
        this.name = name;
        this.baseValue = baseValue;
        this.rarity = rarity;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public double getBaseValue() {
        return baseValue;
    }

    public double getRarity() {
        return rarity;
    }
}
