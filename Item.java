
public class Item{
   private String name;
   private int damage;
   private boolean isWeapon;
   private boolean isThrowable;
   private boolean isConsumable;
   private String[] use = {"Weapon","Throwing","Consumable"};
   private String itemUse;
   private int durability;
   private int id;
   private String[] types = {"name","damage","itemType","durability"};
   private static int numberOfItems;
   public Item(String itemName, int newDamage, boolean isWeaponIn, boolean isThrowableIn, boolean isConsumableIn,int durabilityIn){
       name = itemName;
       damage = newDamage;
       isWeapon = isWeaponIn;
       isThrowable = isThrowableIn;
       isConsumable = isConsumableIn;
       durability = durabilityIn;
       id = numberOfItems;
       numberOfItems++;
       if (isWeapon) {
           itemUse = use[0];
       } else if (isThrowable) {
           itemUse = use[1];
       } else if (isConsumable) {
           itemUse = use[2];
       }
   }
   public int getID() {
       return id;
    }
   public Item(){
       name = "Undefined";
       damage = 0;
       isWeapon = true;
       isThrowable = true;
       isConsumable = true;
       durability = 0;
   }
   
   public String getName(){
       return name;
   }
   public int getDamage(){
       return damage;
   }
   public void setDamage(int in){
       damage = in;
   }
   public String getItemUse(){
       return itemUse;
   }
   public int getDurability(){
       return durability;
   }
   public void setDurability(int durabilityIn){
       durability = durabilityIn;
   }
   public String getValue(String typeIn){
       String dmg = Integer.toString(damage);
       String dur = Integer.toString(durability);
       String nme = this.getName();
       String typ = this.getItemUse();
       if (typeIn.equals("name")){
           return nme;
       }else if (typeIn.equals("damage")) {
           return dmg;
       }else if (typeIn.equals("itemType")) {
           return typ;
       }else if (typeIn.equals("durability")) {
           return dur;
       }
       return "";
   }
   
   public String toString(){
       return "Name: " + name + "\nDamage: " + damage + "\nUse: " + itemUse + "\nDurability: " + durability;
    }
}
