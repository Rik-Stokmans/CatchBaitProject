package catchbait.catchbait.Commands;

import catchbait.catchbait.Variables.PlayerDataContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

import static catchbait.catchbait.Utils.ChatUtils.format;
import static catchbait.catchbait.Variables.VariablesCore.PlayerDataMap;

public class QuickSellAll implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory inventory = player.getInventory();
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            int soldItemAmount = 0;
            double soldItemsValue = 0;
            for (ItemStack item : inventory) {
                if (item != null && !(item.getData().getItemType().equals(Material.AIR))) {
                    NBTItem nbti = new NBTItem(item);
                    if (nbti.hasKey("Type")) {
                        if (nbti.getString("Type").equals("FISH")) {
                            double Value = nbti.getDouble("Value");
                            double sellAmount = Value * item.getAmount();
                            double coins = PlayerDataMap.get(player.getUniqueId()).getCoins() + sellAmount;
                            PlayerDataMap.get(player.getUniqueId()).setCoins(coins);
                            soldItemAmount += item.getAmount();
                            soldItemsValue += sellAmount;
                            inventory.remove(item);
                        }
                        if (nbti.getString("Type").equals("JUNK")) {
                            double Value = nbti.getDouble("Value");
                            double sellAmount = Value * item.getAmount();
                            double coins = PlayerDataMap.get(player.getUniqueId()).getCoins() + sellAmount;
                            PlayerDataMap.get(player.getUniqueId()).setCoins(coins);
                            soldItemAmount += item.getAmount();
                            soldItemsValue += sellAmount;
                            inventory.remove(item);
                        }
                    }
                }
            }
            if (soldItemAmount == 0) {
                player.sendMessage(format("&cYou Don't Have Any Sellable Items"));
                return true;
            }
            else {
                player.sendMessage(format(" "));
                if (soldItemAmount == 1)
                    player.sendMessage(format("   " + soldItemAmount + " &7Item &b→ &e" + df.format(soldItemsValue) + "&6⛁"));
                else
                    player.sendMessage(format("   " + soldItemAmount + " &7Items &b→ &e" + df.format(soldItemsValue) + "&6⛁"));
                player.sendMessage(format(" "));
                return true;
            }
        }
        return false;
    }
}
