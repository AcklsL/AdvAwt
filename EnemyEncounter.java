import java.util.Random;
public class EnemyEncounter{
    // instance variables - replace the example below with your own
    Random generate = new Random();
    private String[] monsterNames = {"Aple Scree", "Bio Boker", "Zimbibe", "Hula Hoo", "DAMACIA", "Pimpel"};
    private int enemyHealth;
    private int enemyDamage;
    private String monsterName;
    
    public EnemyEncounter(){
        // initialise instance variables
        monsterName = monsterNames[generate.nextInt(6)];
        enemyDamage = generate.nextInt(10)+1;
        enemyHealth = generate.nextInt(100)+30;
    }

    public void generateEncounter(int amount){
        CombatSystem combat = new CombatSystem();
        for (int i  = 0; i < amount; i++){
            combat.beginCombatNormal("normal");
        }
    }
    
    public int getHealth(){
        return enemyHealth;
    }
    public int getDamage(){
        return enemyDamage;
    }
    public String getMonsterName(){
        return monsterName;
    }
    public void setHealth(int newHealth){
        enemyHealth = newHealth;
    }
    public void setDamage(int newDamage){
        enemyDamage = newDamage;
    }
    public void setMonsterName(String newName){
        monsterName = newName;
    }
}
