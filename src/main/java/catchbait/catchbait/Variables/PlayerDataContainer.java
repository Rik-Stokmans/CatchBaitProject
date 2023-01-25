package catchbait.catchbait.Variables;


import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerDataContainer {

    double coins;
    double junkCatchMultiplier;
    double standardCatchMultiplier;
    double characterCatchMultiplier;
    double treasureCatchMultiplier;

    public PlayerDataContainer() {
        this(5, 1,  1, 1, 1);
    }

    public PlayerDataContainer(double Coins, double junkCatchMultiplier, double standardCatchMultiplier, double characterCatchMultiplier, double treasureCatchMultiplier) {
        this.coins = Coins;
        this.junkCatchMultiplier = junkCatchMultiplier;
        this.standardCatchMultiplier = standardCatchMultiplier;
        this.characterCatchMultiplier = characterCatchMultiplier;
        this.treasureCatchMultiplier = treasureCatchMultiplier;
    }
    /*
        Getters And Setters
    */
    public double getCoins() {
        return coins;
    }
    public void setCoins(double coins) {
        this.coins = coins;
    }
    public double getJunkCatchMultiplier() {
        return junkCatchMultiplier;
    }
    public void setJunkCatchMultiplier(double junkCatchMultiplier) {
        this.junkCatchMultiplier = junkCatchMultiplier;
    }
    public double getStandardCatchMultiplier() {
        return standardCatchMultiplier;
    }
    public void setStandardCatchMultiplier(double standardCatchMultiplier) {
        this.standardCatchMultiplier = standardCatchMultiplier;
    }
    public double getCharacterCatchMultiplier() {
        return characterCatchMultiplier;
    }
    public void setCharacterCatchMultiplier(double characterCatchMultiplier) {
        this.characterCatchMultiplier = characterCatchMultiplier;
    }
    public double getTreasureCatchMultiplier() {
        return treasureCatchMultiplier;
    }
    public void setTreasureCatchMultiplier(double treasureCatchMultiplier) {
        this.treasureCatchMultiplier = treasureCatchMultiplier;
    }
}
