
public class Allies{
    private int allyHealth;
    private int allyDamage;
    private String allyName;
    private int damageBuff;
    private int allyAttach;
    private boolean canReduceAttach;
    
    public Allies(int health, int damage, String name, int allyAttach){
        allyHealth = health;
        allyDamage = damage;
        allyName = name;
        if (allyAttach > 999){
            canReduceAttach = false;
        } else {
            canReduceAttach = true;
        }
    }
    
    public String getName(){
        return allyName;
    }
    public void setName(String newName){
        allyName = newName;
    }
    public int getDamage(){
        return (allyDamage + damageBuff);
    }
    public void setDamage(int in){
        allyDamage = in;
    }
    public void setHealth(int in){
        allyHealth = in;
    }
    public int getHealth(){
        return allyHealth;
    }
    public void setDamageBuff(int in){
        damageBuff = in;
    }
    public void reduceAttach(){
        if (canReduceAttach){
            allyAttach--;
            if (allyAttach < 1) {
                Main.instance.write(allyName + " loses trust in you. They've left your party!");
            }
        }
    }
}
