import java.util.Random;
import java.util.Scanner;
public class CreateLoot{
    private InventoryRender item = Main.inventory;
    private Random generate = new Random();
    private Scanner input = new Scanner(System.in);
    private String[] wepNamCom = {"Wooden Sword","Bronze Sword","Thick Stick","Shovel","Pickaxe","Dull Axe","Bat"};
    private String[] wepNameUC = {"Steel Sword", "Sharp Axe", "Whip","Dagger","Bow & arrows","Quarter Staff","Glaive"};
    private String[] wepNameRR = {"Kenitizer","Joolian","Ethifier","Ryavie","Nikobo","Aleckend","Marhkar"};
    private String[] wepNameLG = {"Dragonbone Sword","Diamond Axe","Gauntlets of Pure Orichalcum","Demonblood Spear","Tigris Prime","Primed Martial Arts","Mk-1 Bo"};
    private String[] potionNames = {"Weak ","Strong ","Superior ","Godly "};
    public CreateLoot(){
        
    }
    public void generateLoot(int tier, String lootType){
        if (lootType.toLowerCase().equals("weapon")) {
            switch (tier) {
                //Common
                case 1:
                    String wepName = wepNamCom[generate.nextInt(7)];
                    item.addItem(new Weapon(wepName,generate.nextInt(7),generate.nextInt(100)+1));
                    break;
                //Uncommon
                case 2:
                    String wepNamUC = wepNameUC[generate.nextInt(7)];
                    item.addItem(new Weapon(wepNamUC,generate.nextInt(14)+7,generate.nextInt(350)+1));
                    break;
                //Rare
                case 3:
                    String wepNamRR = wepNameRR[generate.nextInt(7)];
                    item.addItem(new Weapon(wepNamRR,generate.nextInt(21)+14,generate.nextInt(600)+1));
                    break;
                //Legendary
                case 4:
                    String wepNamLG = wepNameLG[generate.nextInt(7)];
                    item.addItem(new Weapon(wepNamLG,generate.nextInt(40)+30,generate.nextInt(600)+1));
                    break;
                default:
                    System.err.println("Invalid loot tier");
                    break;
            }
        } else if (lootType.toLowerCase().equals("throwableitem")) {
            switch (tier) {
                //Common
                case 1:
                    String wepName = wepNamCom[generate.nextInt(7)];
                    item.addItem(new Weapon("Mini-" + wepName,generate.nextInt(7),generate.nextInt(100)+1));
                    break;
                //Uncommon
                case 2:
                    String wepNamUC = wepNameUC[generate.nextInt(7)];
                    item.addItem(new Weapon("Mini-" + wepNamUC,generate.nextInt(14)+7,generate.nextInt(350)+1));
                    break;
                //Rare
                case 3:
                    String wepNamRR = wepNameRR[generate.nextInt(7)];
                    item.addItem(new Weapon("Mini-" + wepNamRR,generate.nextInt(21)+14,generate.nextInt(600)+1));
                    break;
                //Legendary
                case 4:
                    String wepNamLG = wepNameLG[generate.nextInt(7)];
                    item.addItem(new Weapon("Mini-" + wepNamLG,generate.nextInt(40)+30,generate.nextInt(600)+1));
                    break;
                default:
                    System.err.println("Invalid loot tier");
                    break;
            }
        } else if (lootType.toLowerCase().equals("consumable")) {
            //1 is health, 2 is strength, 3 is mana
            int potionType = generate.nextInt(3)+1;
            switch (potionType) {
                case 1:
                    switch (tier) {
                        case 1:
                            item.addItem(new HealthPotion(potionNames[tier-1] + "health potion",10,5));
                            break;
                        case 2:
                            item.addItem(new HealthPotion(potionNames[tier-1] + "health potion",20,5));
                            break;
                        case 3:
                            item.addItem(new HealthPotion(potionNames[tier-1] + "health potion",40,5));
                            break;
                        case 4:
                            item.addItem(new HealthPotion(potionNames[tier-1] + "health potion",80,5));
                            break;
                        default:
                            System.err.print("Tier not found");
                            break;
                    }
                    break;
                case 2:
                    switch (tier) {
                        case 1:
                            item.addItem(new StrengthPotion(potionNames[tier-1] + "strength potion",10,5));
                            break;
                        case 2:
                            item.addItem(new StrengthPotion(potionNames[tier-1] + "strength potion",20,5));
                            break;
                        case 3:
                            item.addItem(new StrengthPotion(potionNames[tier-1] + "strength potion",40,5));
                            break;
                        case 4:
                            item.addItem(new StrengthPotion(potionNames[tier-1] + "strength potion",80,5));
                            break;
                        default:
                            System.err.print("Tier not found");
                            break;
                    }
                    break;
                case 3:
                    switch (tier) {
                        case 1:
                            item.addItem(new ManaPotion(potionNames[tier-1] + "mana potion",10,5));
                            break;
                        case 2:
                            item.addItem(new ManaPotion(potionNames[tier-1] + "mana potion",20,5));
                            break;
                        case 3:
                            item.addItem(new ManaPotion(potionNames[tier-1] + "mana potion",40,5));
                            break;
                        case 4:
                            item.addItem(new ManaPotion(potionNames[tier-1] + "mana potion",80,5));
                            break;
                        default:
                            System.err.print("Tier not found");
                            break;
                    }
                    break;
                default:
                    System.err.print("Potion type not found");
            }
        } else {
            System.err.print("Incorrect loot type");
        }
        item.checkDupe();
        Main.instance.write("You got new loot! Do you want to check your inventory?");
        Main.instance.writeGI("Yes / No : ");
        String action = Main.instance.getInput().toLowerCase();
        if (action.equals("yes")) {
            item.renderInventory();
            this.invInspect();
        }else if (action.equals("no")) {
            Main.instance.write("Oh well, let's move on!");
        }
        
    }
    public void invInspect() {
        Main.instance.writeGI("Type an item to inspect. Otherwise, type \"0\": ");
        String action2 = Main.instance.getInput();
        for (Item i : item.getItems()) {
                //Main.instance.write(i);
                if (i.getName().toLowerCase().equals(action2)) {
                    Main.instance.write("\n" + i + "\n");
                } else if (action2.equals("0")) {
                }
        }
    }
}

