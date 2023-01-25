package catchbait.catchbait.Fishing;

import catchbait.catchbait.Fishing.LootTables.JunkLootTable;
import catchbait.catchbait.Fishing.Rods.Rod;
import catchbait.catchbait.Variables.PlayerDataContainer;
import catchbait.catchbait.Variables.VariablesCore;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

import static catchbait.catchbait.Fishing.LootTables.CharacterLootTable.getTier1CharacterLoot;
import static catchbait.catchbait.Fishing.LootTables.JunkLootTable.*;
import static catchbait.catchbait.Fishing.LootTables.StandardLootTable.*;
import static catchbait.catchbait.Fishing.LootTables.TreasureLootTable.getTier1TreasureLoot;
import static catchbait.catchbait.Utils.ChatUtils.format;

public class Fishing implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        if (!VariablesCore.PlayerDataMap.containsKey(e.getPlayer().getUniqueId())) {
            Bukkit.broadcastMessage(format("&7 Welcome to the server " + e.getPlayer().getName()));
            VariablesCore.PlayerDataMap.put(e.getPlayer().getUniqueId(), new PlayerDataContainer());
        }
    }

    @EventHandler
    public void onFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        PlayerFishEvent.State state = e.getState();
        //get the stats of the rod
        ItemStack rodItem = player.getInventory().getItemInMainHand();
        if (!rodItem.hasItemMeta()) {
            e.setCancelled(true);
            player.sendMessage(format("&cYou Can Only Use Your Fishing Rod In Your Main Hand"));
            return;
        }
        Rod rod = new Rod(rodItem);

        NBTItem nbti = new NBTItem(rodItem);

        //get the stats of the player
        PlayerDataContainer data = VariablesCore.PlayerDataMap.get(e.getPlayer().getUniqueId());

        //check for boosts or multipliers

        if (state.equals(PlayerFishEvent.State.FISHING)) {

            //calculate the min and max catch time
            int catchTime = nbti.getInteger("CatchTime");
            int min = catchTime;
            int max = catchTime + rod.getCatchTimeRange();

            //set the min and maximum wait time
            setWaitTime(min, max, e.getHook());

        }
        else if (state.equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            //temp
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);

            //add xp to the rod
            rod.addXp(1.0);
            player.getInventory().setItemInMainHand(rod.getRod());

            //generate the chances
            double junkReward = 70 * data.getJunkCatchMultiplier();
            double standardReward = 100 * data.getStandardCatchMultiplier();
            double characterReward = 8 * data.getCharacterCatchMultiplier();
            double treasureReward = 2 * data.getTreasureCatchMultiplier();
            double total = (junkReward + standardReward + characterReward + treasureReward);
            double random = Math.random() * total;
            e.getPlayer().sendMessage(format("Junk: " + junkReward + " " + df.format(junkReward/total*100)) + "%");
            e.getPlayer().sendMessage(format("Standard: " + standardReward + " " + df.format(standardReward/total*100)) + "%");
            e.getPlayer().sendMessage(format("Character: " + characterReward + " " + df.format(characterReward/total*100)) + "%");
            e.getPlayer().sendMessage(format("Treasure: " + treasureReward + " " + df.format(treasureReward/total*100)) + "%");
            Bukkit.broadcastMessage(String.valueOf(random));

            //calculate the tier of the item



            ItemStack reward = new ItemStack(Material.STONE);
            if (random < junkReward) {
                e.getPlayer().sendMessage(format("&eJUNK"));
                //tier 1
                reward = getJunk(getTier1Junk());
            }
            else if (random < standardReward + junkReward) {
                e.getPlayer().sendMessage(format("&eSTANDARD"));
                //tier 1
                reward = getFish(getTier1Fish());
            }
            else if (random < characterReward + standardReward + junkReward) {
                e.getPlayer().sendMessage(format("&eCHARACTER"));
                //tier 1 todo
                reward = getTier1CharacterLoot();
            }
            else {
                e.getPlayer().sendMessage(format("&eTREASURE"));
                //tier 1 todo
                reward = getTier1TreasureLoot();
            }

            //set the catch item
            Item caught = (Item) e.getCaught();
            caught.setItemStack(reward);
        }
    }

    private void setWaitTime(int minSec, int maxSec, FishHook hook) {
        hook.setApplyLure(false);
        hook.setMinWaitTime(minSec * 20);
        hook.setMaxWaitTime(maxSec * 20);
    }




}
