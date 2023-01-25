package catchbait.catchbait.ItemHandling;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static catchbait.catchbait.Utils.ChatUtils.format;

public class ItemTools {

    public static ItemStack refreshItem(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        if (nbti.hasKey("Type")) {

            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);


            if (nbti.getString("Type").equals("JUNK")) {
                //Lore
                ItemMeta iMeta = item.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                double value = nbti.getDouble("Value");

                lores.add(" ");
                lores.add(format("&7Value: &e" + df.format(value) + "&6⛁"));
                lores.add(format("&8[Estimated Value]"));
                iMeta.setLore(lores);

                item.setItemMeta(iMeta);
            }

            else if (nbti.getString("Type").equals("FISH")) {
                //Lore
                ItemMeta iMeta = item.getItemMeta();
                ArrayList<String> lores = new ArrayList<>();
                int tier = nbti.getInteger("Tier");
                double value = nbti.getDouble("Value");

                lores.add(" ");
                lores.add(format("&7Value: &e" + df.format(value) + "&6⛁"));
                lores.add(format("&8[Estimated Value]"));
                lores.add(" ");

                if (tier == 0) {
                    lores.add(format("&8[✫✫✫]"));
                }
                else if (tier == 1) {
                    lores.add(format("&6[&e✫&8✫✫&6]"));
                }
                else if (tier == 2) {
                    lores.add(format("&5[&d✫✫&8✫&5]"));
                }
                else {
                    lores.add(format("&3[&b✫✫✫&3]"));
                }

                iMeta.setLore(lores);

                item.setItemMeta(iMeta);
            }

            //returning the item
            return item;
        }
        return null;
    }
}
