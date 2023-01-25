package catchbait.catchbait.Commands;

import catchbait.catchbait.Variables.PlayerDataContainer;
import catchbait.catchbait.Variables.VariablesCore;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static catchbait.catchbait.Fishing.LootTables.CharacterLootTable.getTier1CharacterLoot;
import static catchbait.catchbait.Fishing.LootTables.JunkLootTable.getJunk;
import static catchbait.catchbait.Fishing.LootTables.JunkLootTable.getTier1Junk;
import static catchbait.catchbait.Fishing.LootTables.StandardLootTable.getFish;
import static catchbait.catchbait.Fishing.LootTables.StandardLootTable.getTier1Fish;
import static catchbait.catchbait.Fishing.LootTables.TreasureLootTable.getTier1TreasureLoot;
import static catchbait.catchbait.Fishing.Rods.Rod.getNewRod;
import static catchbait.catchbait.Utils.ChatUtils.format;

public class GetFish implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getInventory().addItem(getNewRod("&bThe Hooker", 0, 0));

            PlayerDataContainer data = VariablesCore.PlayerDataMap.get(player.getUniqueId());

            double junkReward = 70 * data.getJunkCatchMultiplier();
            double standardReward = 100 * data.getStandardCatchMultiplier();
            double characterReward = 8 * data.getCharacterCatchMultiplier();
            double treasureReward = 2 * data.getTreasureCatchMultiplier();
            double total = (junkReward + standardReward + characterReward + treasureReward);

            for (int i = 0; i < 100; i++) {
                double random = Math.random() * total;
                ItemStack reward = new ItemStack(Material.STONE);
                if (random < junkReward) {
                    reward = getJunk(getTier1Junk());
                }
                else if (random < standardReward + junkReward) {
                    reward = getFish(getTier1Fish());
                }
                else if (random < characterReward + standardReward + junkReward) {
                    reward = getTier1CharacterLoot();
                }
                else {
                    reward = getTier1TreasureLoot();
                }

                player.getWorld().dropItem(player.getLocation(), reward);
            }
            return true;
        }
        return false;
    }
}
