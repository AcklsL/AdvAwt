import java.util.Random;
import java.awt.*;
/**
 * Filled with utility commands that is for QOL.
 */


public class Utility{
    private Random generate = new Random();
    private String[] wepNamCom = {"Wooden Sword","Bronze Sword","Thick Stick","Shovel","Pickaxe","Dull Axe","Bat"};
    private String[] wepNameUC = {"Steel Sword", "Sharp Axe", "Whip","Dagger","Bow & arrows","Quarter Staff","Glaive"};
    private String[] wepNameRR = {"Kenitizer","Joolian","Ethifier","Ryavie","Nikobo","Aleckend","Marhkar"};
    private String[] wepNameLG = {"Dragonbone Sword","Diamond Axe","Gauntlets of Pure Orichalcum","Demonblood Spear","Tigris Prime","Primed Martial Arts","Mk-1 Bo"};
    private String[] potionNames = {"Weak ","Strong ","Superior ","Godly "};
    private Graphics g;
    public Utility(){

    }
    public void update(Graphics g){
        this.g = g;
    }
    private int ydectext = 0;
    public String getRandomItemName() {
        int tier = generate.nextInt(4)+1;
        String randItemName;
        switch (tier) {
            case 1:
                randItemName = wepNamCom[generate.nextInt(7)];
                return randItemName;
            case 2:
                randItemName = wepNameUC[generate.nextInt(7)];
                return randItemName;
            case 3:
                randItemName = wepNameRR[generate.nextInt(7)];
                return randItemName;
            case 4:
                randItemName = wepNameLG[generate.nextInt(7)];
                return randItemName;
            default:
                System.err.println("Incorrect tier for shop sale");
                break;
        }
        return "";
    }
    
    
    public String getRandomPotionName() {
        String potName = potionNames[generate.nextInt(4)];
        return potName;
    }
    
}
