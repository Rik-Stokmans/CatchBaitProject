package catchbait.catchbait.Fishing.Rods;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static catchbait.catchbait.Utils.ChatUtils.format;

public class Rod {

    boolean isValidRod;

    ItemStack rodItem;
    String name;
    int minimumLevel;
    int tier;
    double xp;
    int catchTime;
    int catchTimeRange;

    public Rod(ItemStack rod) {
        NBTItem nbti = new NBTItem(rod);
        if (nbti.hasKey("Type") && nbti.getString("Type").equals("Rod")) {
            isValidRod = true;

            rodItem = rod;
            name = nbti.getString("RodName");
            minimumLevel = nbti.getInteger("MinimumLevel");
            tier = nbti.getInteger("Tier");
            xp = nbti.getDouble("Xp");
            catchTime = nbti.getInteger("CatchTime");
            catchTimeRange = getCatchTimeRange();

        } else isValidRod = false;
    }

    public ItemStack getRod() {
        if (!isValidRod) return null;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);

        ItemStack item = rodItem;
        //Name
        ItemMeta iMeta = item.getItemMeta();
        String numeral;
        if (tier == 0 || tier == 1 || tier == 2) {
            if (tier == 0) numeral = "0";
            else if (tier == 1) numeral = "I";
            else numeral = "II";
            iMeta.setDisplayName(format(name + " &8[&7" + numeral + "&8]"));
        }
        else if (tier == 3 || tier == 4 || tier == 5){
            if (tier == 3) numeral = "III";
            else if (tier == 4) numeral = "IV";
            else numeral = "V";
            iMeta.setDisplayName(format(name + " &6[&e" + numeral + "&6]"));
        }
        else if (tier == 6 || tier == 7 || tier == 8){
            if (tier == 6) numeral = "VI";
            else if (tier == 7) numeral = "VII";
            else numeral = "VIII";
            iMeta.setDisplayName(format(name + " &5[&d" + numeral + "&5]"));
        }
        else {
            if (tier == 9) numeral = "IX";
            else if (tier == 10) numeral = "X";
            else numeral = "XI";
            iMeta.setDisplayName(format(name + " &3[&b" + numeral + "&3]"));
        }

        //Lore todo
        ArrayList<String> lores = (ArrayList<String>) iMeta.getLore();
        lores.set(0, format(getSpeedName() + " &8(&7" + catchTime + " &8- &7" + (catchTime + getCatchTimeRange()) + "&8)"));
        lores.set(2, format("&7Lv. Min &f" + minimumLevel));
        lores.set(4, format("&7[&a" + xp + "&f → &a" + getTierUpXp() + "&7]"));

        iMeta.setLore(lores);
        item.setItemMeta(iMeta);
        //NBT
        NBTItem nbti = new NBTItem(rodItem);
        nbti.setInteger("Tier", tier);
        nbti.setDouble("Xp", xp);
        nbti.setInteger("CatchTime", catchTime);
        nbti.applyNBT(item);

        return item;
    }

    public void addXp(double amount) {
        if (getTierUpXp() != 0) {
            xp += amount;
            double tierUpXp = getTierUpXp();
            while (xp >= tierUpXp) {
                xp -= tierUpXp;
                tier++;
                tierUpXp = getTierUpXp();
                catchTimeRange = getCatchTimeRange();
            }
        }
    }

    private int getTierUpXp() {
        if (tier == 0) return 10;
        else if (tier == 1) return 25;
        else if (tier == 2) return 50;
        else if (tier == 3) return 100;
        else if (tier == 4) return 250;
        else if (tier == 5) return 500;
        else if (tier == 6) return 800;
        else if (tier == 7) return 1250;
        else if (tier == 8) return 2000;
        else if (tier == 9) return 3000;
        else if (tier == 10) return 4500;
        else return 0;
    }

    public int getCatchTimeRange() {
        if (tier == 0) return 20;
        else if (tier == 1) return 17;
        else if (tier == 2) return 15;
        else if (tier == 3) return 13;
        else if (tier == 4) return 11;
        else if (tier == 5) return 8;
        else if (tier == 6) return 6;
        else if (tier == 7) return 4;
        else if (tier == 8) return 2;
        else if (tier == 9) return 1;
        else if (tier == 10) return 0;
        else return 0;
    }

    public String getSpeedName() {
        if (catchTime == 12) return "&9Super Slow";
        else if (catchTime == 10) return "&9Very Slow";
        else if (catchTime == 8) return "&9Slow";
        else if (catchTime == 7) return "&9Normal";
        else if (catchTime == 5) return "&9Fast";
        else if (catchTime == 3) return "&9Very Fast";
        else return "&9Super Fast";
    }


    //generates a new rod
    public static ItemStack getNewRod(String name, int minLevel, int tier) {
        ItemStack rod = new ItemStack(Material.FISHING_ROD);

        ItemMeta iMeta = rod.getItemMeta();
        ArrayList<String> lores = new ArrayList<>();
        lores.add(format("&9Slow &8(&712 &8- &732&8)"));
        lores.add(format(" "));
        lores.add(format("&7Lv. Min. &f" + minLevel));
        lores.add(format(" "));
        lores.add(format("&8[&20&f → &210&8]"));
        iMeta.setLore(lores);
        rod.setItemMeta(iMeta);

        NBTItem nbti = new NBTItem(rod);
        nbti.setString("Type", "Rod");
        nbti.setString("RodName", name);
        nbti.setInteger("MinimumLevel", minLevel);
        nbti.setInteger("Tier", tier);
        nbti.setDouble("Xp", 0.0);
        nbti.setInteger("CatchTime", 7);
        nbti.applyNBT(rod);

        rod = new Rod(rod).getRod();

        return rod;
    }

    public boolean isValidRod() {
        return isValidRod;
    }
    public int getTier() {
        return tier;
    }
    public double getXp() {
        return xp;
    }
}
