import java.util.Scanner;
import java.util.Random;
public class Shop{
    // instance variables - replace the example below with your own
    private Scanner input = new Scanner(System.in);
    private Random generate = new Random();
    private InventoryRender inv = Main.inventory;
    private CharacterInfo chr = Main.character;
    private Utility utl = new Utility();
    private InventoryRender shopInv = new InventoryRender();
    private String[] wepNamCom = {"Wooden Sword","Bronze Sword","Thick Stick","Shovel","Pickaxe","Dull Axe","Bat"};
    private String[] wepNameUC = {"Steel Sword", "Sharp Axe", "Whip","Dagger","Bow & arrows","Quarter Staff","Glaive"};
    private String[] wepNameRR = {"Kenitizer","Joolian","Ethifier","Ryavie","Nikobo","Aleckend","Marhkar"};
    private String[] wepNameLG = {"Dragonbone Sword","Diamond Axe","Gauntlets of Pure Orichalcum","Demonblood Spear","Tigris Prime","Primed Martial Arts","Mk-1 Bo"};
    private String[] potionNames = {"Weak ","Strong ","Superior ","Godly "};
    
    public Shop(){
    }

    public void generateShop(String shopName){
        String saleItemName = utl.getRandomItemName();
        int saleItemDMG = generate.nextInt(21)+8;
        int saleCost = generate.nextInt(10)+1;
        Main.instance.write("\nOh Hello! Welcome to the " + shopName + " shop!");
        Main.instance.write("Today's sale is on " + saleItemName + "! It deals " + saleItemDMG + " damage. Its for an amazing sale for only " + saleCost + " coins!");
        Main.instance.write("Would you like to take this once in a lifetime opportunity?");
        Main.instance.writeGI("Yes/No: ");
        String action = Main.instance.getInput();
        if (action.equalsIgnoreCase("yes")) {
            if (chr.getMoney() >= saleCost) {
                inv.addItem(new Weapon(saleItemName,saleItemDMG,generate.nextInt(250)+100));
                Main.instance.write("\nThanks for your purchase!");
            } else {
                Main.instance.write("\nYou don't have the funds!");
            }
        } else if (action.equalsIgnoreCase("no")){
            Main.instance.write("\nYour loss!");
        }
        
        this.generateShopRepeatable();
    }
    public void generateShopRepeatable(){
        Main.instance.write("\nWhat would you like to do?");
        Main.instance.writeGI("Check shop/Sell items/Check money/Exit shop");
        String action2 = Main.instance.getInput();
        if (action2.equalsIgnoreCase("check shop")) {
            for (int i = 0; i < 21 ; i++) {
                shopInv.addItem(new Weapon(utl.getRandomItemName(),generate.nextInt(21)+8,generate.nextInt(300)+50));
                shopInv.addItem(new ThrowableItem(utl.getRandomItemName(),generate.nextInt(21)+8,generate.nextInt(300)+50));
            }
            for (int i = 0; i < 4; i++) {
                shopInv.addItem(new ManaPotion(utl.getRandomPotionName() + "mana potion",generate.nextInt(30)+10,10));
                shopInv.addItem(new HealthPotion(utl.getRandomPotionName() + "health potion",generate.nextInt(30)+10,10));
                shopInv.addItem(new StrengthPotion(utl.getRandomPotionName() + "strength potion",generate.nextInt(30)+10,10));
            }
            shopInv.checkSpecialDupe();
            this.checkShop();
            this.generateShopRepeatable();
        } else if (action2.equalsIgnoreCase("sell items")) {
            inv.renderInventory();
            Main.instance.writeGI("Type an item to sell, or type \"0\" to return to the menu.");
            String action3 = Main.instance.getInput();
            for (Item i : inv.getItems()) {
                if (i.getName().equalsIgnoreCase(action3)) {
                    chr.setMoney(chr.getMoney() + (i.getDurability() / 2));
                    Main.instance.write("You gained a profit of " + (i.getDurability() / 2));
                    inv.remove(inv.indexOf(i));
                    break;
                } else {
                }
            }
            this.generateShopRepeatable();
        } else if (action2.equalsIgnoreCase("exit shop")) {
            Main.instance.write("Goodbye!");
        } else if (action2.equalsIgnoreCase("check money")) {
            Main.instance.write("You have " + chr.getMoney() + " coins.");
            this.generateShopRepeatable();
        }else {
            Main.instance.write("I didn't get that.");
            this.generateShopRepeatable();
        }
    }
    
    public void checkShop() {
        Main.instance.write("\nThese are my wares. Feel free to check them out!\n");
        shopInv.renderInventory();
        Main.instance.write("\nWhat would you like to do?");
        Main.instance.writeGI("Purchase/Inspect/Return");
        String action4 = Main.instance.getInput();
        if (action4.equalsIgnoreCase("purchase")) {
            Main.instance.writeGI("\nType in an item you would like to purchase.");
            String action6 = Main.instance.getInput();
            for (Item i : shopInv.getItems()) {
                if (action6.equalsIgnoreCase(i.getName())) {
                    Main.instance.write("It'll cost ya " + (i.getDurability()/70) +" coins. You sure?");
                    Main.instance.writeGI("Yes/No: ");
                    String action5 = Main.instance.getInput();
                    if (action5.equalsIgnoreCase("yes")) {
                        if (chr.getMoney() >= (i.getDurability()/70)) {
                            chr.setMoney(chr.getMoney() - (i.getDurability()/70));
                            inv.addItem(i);
                            shopInv.remove(shopInv.indexOf(i));
                            Main.instance.write("Thank you for your purchase!");
                            break;
                        }
                    } else if (action5.equalsIgnoreCase("no")) {
                        Main.instance.write("Alright, no problem.");
                        this.checkShop();
                    }
                }
            }
        } else if (action4.equalsIgnoreCase("inspect")) {
            Main.instance.writeGI("\nType an item you would like to inspect.");
            String action7 = Main.instance.getInput();
            for (Item i : shopInv.getItems()) {
                if (action7.equalsIgnoreCase(i.getName())){
                    Main.instance.write("\n" + i + "\n");
                }
            }
            this.checkShop();
        } else if (action4.equalsIgnoreCase("return")) {
            
        } else {
            Main.instance.write("I didn't get that.");
            this.checkShop();
        }
    }
    
}
