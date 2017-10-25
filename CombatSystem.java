import java.util.*;
public class CombatSystem{
    private EnemyEncounter enemy = new EnemyEncounter();
    private Random generate = new Random();
    private Scanner input = new Scanner(System.in);

    private String[] combatTypes = {"normal", "hard"};
    private boolean skipEnemyAttack;
    private boolean endFightAbrupt;
    private CharacterInfo character; 
    private InventoryRender render = Main.inventory;
    private int damageBoost;

    
    public CombatSystem(){
        skipEnemyAttack = false;
        character = Main.character;
        endFightAbrupt = false;
        damageBoost = 0;
    }
    
    //Notates a boss! DO NOT MIX UP!
    public void beginCombatBoss(int fightBoss, int bossNumber){
    }
    
    public void beginCombatNormal(String difficulty) {
        Main.instance.write("\nYou encounter a " + enemy.getMonsterName() + "!");
        Main.instance.write("Health: " + enemy.getHealth());
        Main.instance.write("Damage: " + enemy.getDamage());
        String action;
                     //Hard top, Normal bottom
        if (difficulty.equals(combatTypes[0])) {
            while (enemy.getHealth() > 0 && endFightAbrupt == false) {
                Main.instance.write("What would you like to do?");
                Main.instance.writeGI("Attack, Use Magic, Check Inventory, Run, Stats");
                action = Main.instance.getInput();
                action = action.toLowerCase();
                Main.instance.write("");
                if (action.equals("attack")){
                    this.attack();
                }else if (action.equals("use magic")){
                    this.useMagic();
                }else if (action.equals("check inventory")){
                    this.checkInventory();
                }else if (action.equals("stats")) {
                    Main.instance.write(this.displayStats());
                }else if (action.equals("run")){
                    this.run();
                }else{
                    Main.instance.write("What was that?");
                    skipEnemyAttack = true;
                }
                if (skipEnemyAttack == false) {
                    this.enemyAttack();
                } else {
                    skipEnemyAttack = false;
                }
                Main.instance.write("\n");
                if (endFightAbrupt){
                 Main.instance.write("The fight abruptly ended!");
                 break;
                } else if (enemy.getHealth() < 1){
                 Main.instance.write(enemy.getMonsterName() + " has died!");
                 int money = generate.nextInt(10)+1;
                 character.setMoney(character.getMoney() + money);
                 Main.instance.write("You gained " + money + " coins!");
                 Main.instance.write("Amount of coins: " + character.getMoney());
                
                }
                if (damageBoost != 0) {
                    damageBoost--;
                }
            }
        }else{
        }
    }
    
    public void attack(){
        String attackInput;
        Main.instance.write("What item would you like to attack with?: ");
           // foreach vaule in a array or arraylist
        for (Item i: render.getItems() ){
           if (i instanceof Weapon || i instanceof ThrowableItem) {
               Main.instance.write(i.getName() + "("+ i.getDurability() +")" + " - " + i.getDamage() + " damage");
               //Main.instance.write(render.getItemStat("name",i) + " - " + render.getItemStat("damage",i) + " damage");
            }
        }
        Main.instance.writeGI("");
        attackInput = Main.instance.getInput().toLowerCase();
        for (int i = 0; i < render.getItemsSize(); i++){
            if (attackInput.equals(render.getItemStat("name",i).toLowerCase()) && (render.getItem(render.getItems().get(i).getName()) instanceof Weapon || render.getItem(render.getItems().get(i).getName()) instanceof ThrowableItem)) {
                Main.instance.write("You struck " + enemy.getMonsterName() + " for " + render.getItemStat("damage",i) + " damage with " + render.getItemStat("name",i));
                enemy.setHealth(enemy.getHealth() - ((Integer.parseInt(render.getItemStat("damage",i))) + damageBoost));
                render.getItem(render.getItems().get(i).getName()).setDurability(render.getItem(render.getItems().get(i).getName()).getDurability() - 1);
                /*
                for (Item ite: render.getItems()){
                    if (ite.getName().equals(render.getItemStat("name",i).toLowerCase())){
                        ite.setDurability(ite.getDurability() - 1);
                    }
                }
                */
            }
        }
        
    }
    public void useMagic(){
        if (character.hasLearnedMagic()){
            //add magic
        } else {
            skipEnemyAttack = true;
            Main.instance.write("You haven't learned magic yet!");
        }
    }
    public void checkInventory(){
        String invAction;
        String invAction2;
        render.renderInventory();
        Main.instance.writeGI("\nUse/Return/Inspect: ");
        invAction = Main.instance.getInput().toLowerCase();
        if (invAction.equals("use")) {
            Main.instance.writeGI("What item would you like to use?");
            invAction2 = Main.instance.getInput().toLowerCase();
            for (Item i: render.getItems()) {
                if (i.getName().toLowerCase().equals(invAction2)) {
                    if (i instanceof Weapon || i instanceof ThrowableItem) {
                        Main.instance.write("You struck " + enemy.getMonsterName() + " for " + i.getDamage() + " damage with " + i.getName());
                        enemy.setHealth(enemy.getHealth() - (i.getDamage() + damageBoost));
                        i.setDurability(i.getDurability() - 1);
                        skipEnemyAttack = false;
                    }else if (i instanceof Consumable) {
                        for (Item potions : render.getItems()){
                            if (potions.getName().toLowerCase().equals(invAction2)) {
                                if (potions instanceof HealthPotion) {
                                    character.setHealth(character.getHealth() + potions.getDamage());
                                    Main.instance.write("You gained " + potions.getDamage() + " health!");
                                    potions.setDurability(potions.getDurability() - 1);
                                } else if (potions instanceof StrengthPotion) {
                                    damageBoost = potions.getDamage();
                                    Main.instance.write("You gained " + potions.getDamage() + " temporary attack damage!");
                                    potions.setDurability(potions.getDurability() - 1);
                                } else if (potions instanceof ManaPotion) {
                                    character.setMana(character.getMana() + potions.getDamage());
                                    Main.instance.write("You gained " + potions.getDamage() + " mana!");
                                    potions.setDurability(potions.getDurability() - 1);
                                }
                            }
                        }
                        skipEnemyAttack = true;
                    }else {
                        System.err.println("Cannot find suitable item type for item defined.");
                    }
                }
            }
        }else if (invAction.equals("return")){
            skipEnemyAttack = true;
        }else if (invAction.equals("inspect")){
            Main.instance.writeGI("What item would you like to inspect: ");
            invAction2 = Main.instance.getInput();
            for (Item i : render.getItems()) {
                if (i.getName().toLowerCase().equals(invAction2)) {
                    Main.instance.write(i.toString());
                    skipEnemyAttack = true;
                    break;
                }
            }
            skipEnemyAttack = true;
        }else {
            Main.instance.write("Invalid option!");
            this.checkInventory();
        }
    }
    public void run(){
        int runChance = generate.nextInt(3)+1;
        if (runChance == 2) {
            endFightAbrupt = true;
            skipEnemyAttack = true;
        } else {
            int surviveChance = generate.nextInt(3)+1;
            if (surviveChance == 2) {
                Main.instance.write("You failed to escape, but managed to get behind cover!");
                skipEnemyAttack = true;
            } else {
                Main.instance.write("You failed to escape, and you are left wide-open for an attack!");
            }
        }
    }
    public void enemyAttack(){
        character.setHealth(character.getHealth() - enemy.getDamage());
        Main.instance.write("The " + enemy.getMonsterName() + " struck you for " + enemy.getDamage() + " damage!");
    }
    public String displayStats(){
        //Main.instance.write("Enemy Health: " + enemyStats.getHealth() + "\nEnemy Damage: " + enemyStats.getDamage());
        //("\nPlayer Health: " + character.getHealth() + "\nPlayer Damage: " + character.getAttackDamage());
        skipEnemyAttack = true;
        return "Enemy Health: " + enemy.getHealth() + "\nEnemy Damage: " + enemy.getDamage() + "\n" + "\nPlayer Health: " + character.getHealth();
    }
}