package catchbait.catchbait.Commands;

import catchbait.catchbait.Variables.PlayerDataContainer;
import catchbait.catchbait.Variables.VariablesCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static catchbait.catchbait.Utils.ChatUtils.format;

public class ResetVariables implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (VariablesCore.PlayerDataMap.containsKey(((Player) sender).getUniqueId())) {
                VariablesCore.PlayerDataMap.remove(((Player) sender).getUniqueId());
            }
            VariablesCore.PlayerDataMap.put(((Player) sender).getUniqueId(), new PlayerDataContainer(7, 1, 1, 1, 1));
            sender.sendMessage(format("&aReset your variables"));
            return true;
        }
        return false;
    }
}
