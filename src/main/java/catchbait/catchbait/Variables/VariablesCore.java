package catchbait.catchbait.Variables;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VariablesCore {

    public static YamlConfiguration File;

    public static HashMap<UUID, PlayerDataContainer> PlayerDataMap = new HashMap<>();

    public static void LoadPlayerVariables(String Path) {
        File = YamlConfiguration.loadConfiguration(new File(Path, "Variables/PlayerData.yml"));

        if (File.contains("data")) {
            File.getConfigurationSection("data.coins").getKeys(false).forEach(key -> {
                double coins = File.getDouble("data.coins." + key);
                double junkCatchMultiplier = File.getDouble("data.junkCatchMultiplier." + key);
                double standardCatchMultiplier = File.getDouble("data.standardCatchMultiplier." + key);
                double characterCatchMultiplier = File.getDouble("data.characterCatchMultiplier." + key);
                double treasureCatchMultiplier = File.getDouble("data.treasureCatchMultiplier." + key);
                PlayerDataMap.put(UUID.fromString(key), new PlayerDataContainer(coins, junkCatchMultiplier, standardCatchMultiplier, characterCatchMultiplier, treasureCatchMultiplier));
            });
        }
    }

    public static void SavePlayerVariables(String Path) throws IOException {

        for (Map.Entry<UUID, PlayerDataContainer> value : PlayerDataMap.entrySet()) {
            File.set("data.coins." + value.getKey(), value.getValue().getCoins());
            File.set("data.junkCatchMultiplier." + value.getKey(), value.getValue().getJunkCatchMultiplier());
            File.set("data.standardCatchMultiplier." + value.getKey(), value.getValue().getStandardCatchMultiplier());
            File.set("data.characterCatchMultiplier." + value.getKey(), value.getValue().getCharacterCatchMultiplier());
            File.set("data.treasureCatchMultiplier." + value.getKey(), value.getValue().getTreasureCatchMultiplier());
        }
        saveFile(File,"Variables/PlayerData.yml",Path);
    }

    public static void saveFile(YamlConfiguration file, String s, String Path) throws IOException {
        file.save(new File(Path, s));
    }
}
