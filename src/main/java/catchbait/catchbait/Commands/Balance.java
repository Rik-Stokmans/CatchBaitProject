package catchbait.catchbait.Commands;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.data.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

import static catchbait.catchbait.Utils.ChatUtils.format;
import static catchbait.catchbait.Variables.VariablesCore.PlayerDataMap;

public class Balance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            Player player = (Player) sender;
            double coins = PlayerDataMap.get(player.getUniqueId()).getCoins();
            player.sendMessage(format("Balance: &6" + df.format(coins) + "&6⛁"));
            return true;
        }
        return false;
    }
}
