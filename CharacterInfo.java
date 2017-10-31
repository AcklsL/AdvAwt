import java.util.ArrayList;
public class CharacterInfo{
    // instance variables - replace the example below with your own
    private String name;
    private int health;
    private boolean knowsMagic;
    private int mana;
    private int maxMana;
    private int money;
    private String path;
    private ArrayList<Allies> allies;

    public CharacterInfo(){
        // initialise instance variables
        name = null;
        health = 100;
        maxMana = 0;
        mana = 0;
        knowsMagic = false;
        money = 0;
    }
    
    public void addAlly(Allies i) {
        allies.add(i);
    }
    public Allies getAlly(int index) {
        return allies.get(index);
    }
    public ArrayList<Allies> getArrayAlly(){
        return allies;
    }
    
    public void addToPath(String in){
        path = (path + in);
    }
    public char getPathAtIndex(int index){
        char[] tpath = (path.toLowerCase()).toCharArray();
        try{
            return tpath[index];
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.err.print("Make sure the path selected has been added to path!"); 
            return 'z';
        }
    }
    
    public void setName(String newName){
        name = newName;
    }
    public String getName(){
        return name;
    }
    public void setHealth(int newHealth){
        health = newHealth;
    }
    public int getHealth(){
        return health;
    }
    public boolean hasLearnedMagic(){
        return knowsMagic;
    }
    public void learnMagic(boolean learn){
        knowsMagic = learn;
    }
    public void setMana(int in){
        mana = in;
    }
    public void setMaxMana(int in){
        maxMana = in;
    }
    public int getMana(){
        return mana;
    }
    public int getMaxMana(){
        return maxMana;
    }
    public int getMoney(){
        return money;
    }
    public void setMoney(int in){
        money = in;
    }
}
