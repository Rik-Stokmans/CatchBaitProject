package catchbait.catchbait;

import catchbait.catchbait.Commands.*;
import catchbait.catchbait.Fishing.Fishing;
import catchbait.catchbait.Fishing.LootTables.CharacterLootTable;
import catchbait.catchbait.Fishing.LootTables.JunkLootTable;
import catchbait.catchbait.Fishing.LootTables.StandardLootTable;
import catchbait.catchbait.Fishing.LootTables.TreasureLootTable;
import catchbait.catchbait.Variables.VariablesCore;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.ArrayList;

public final class CatchBait extends JavaPlugin {

    @Override
    public void onEnable() {
        //loading in all variables
        String Path = getDataFolder().getAbsoluteFile().toString();
        VariablesCore.LoadPlayerVariables(Path);

        //loading all the loottables
        JunkLootTable.initTier1Junk();
        StandardLootTable.initTier1Fish();
        CharacterLootTable.initCharacterTables();
        TreasureLootTable.initTreasureTables();

        this.getLogger().info("Plugin Enabled");

        ArrayList<Listener> events = new ArrayList<>();
        //list of events
        events.add(new Fishing());

        for (Listener l : events){
            getServer().getPluginManager().registerEvents(l, this);
        }

        this.getCommand("resetVariables").setExecutor(new ResetVariables());
        this.getCommand("balance").setExecutor(new Balance());
        this.getCommand("quickSellAll").setExecutor(new QuickSellAll());
        this.getCommand("quickSell").setExecutor(new QuickSell());
        this.getCommand("getFish").setExecutor(new GetFish());
    }

    @Override
    public void onDisable() {
        //saving all variables
        String Path = getDataFolder().getAbsoluteFile().toString();
        try {
            catchbait.catchbait.Variables.VariablesCore.SavePlayerVariables(Path);
        } catch (IOException e) { throw new RuntimeException(e);}

        this.getLogger().info("Plugin Disabled");
    }
}
