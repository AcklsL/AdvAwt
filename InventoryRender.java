import java.util.ArrayList;
import java.util.List;
public class InventoryRender{
    private String name;
    private String stat;
    private String stat1;
    private int totalSlots;
    private Item[] inSlot;
    private ArrayList<Item> items = new ArrayList<Item>();
    private String[] statTypes = {"name","damage","itemType","durability","all"};
    private Item itema;
    private List<String> itemNames;
    private int[] itemDamages;
    
    public ArrayList<Item> getItems(){
        return items;
    }
    public Item getItem(int index){
        return items.get(index);
    }
    
    public void remove(int index){
        items.remove(index);
    }
    
    public int indexOf(Item in) {
        return items.indexOf(in);
    }
    
    public boolean contains(String in) {
        if (items.contains(in)) {
            return true;
        } else {
            return false;
        }
    }
    
    public InventoryRender(){
        //Used to pull and change inventory slots. Don't change this object!! Use setters & getters.
        itema = Main.itemz;
    }
    
    //adds items to the inventory.
    public void addItem(Item i) {
            items.add(i);
    }
    
    public int getItemsSize(){
        return items.size();
    }
    
    public String getItemStat(String getStat, String itemName){
        for (Item i: items){
            if (itemName.equals(items.indexOf(i))) {
                name = itemName;
            }
        }
        return items.get(items.indexOf(name)).getValue(getStat);
    }
    public Item getItem(String name) {
        for (Item i: items){   
            if(i.getName() == name) {
                return i;
            }
            }
        System.err.println("Item not found");
        return null;
    }
    
    public String getItemStat(String getStat, int itemID){
        try {
           return items.get(itemID).getValue(getStat);
        } catch(Exception e) {}
        return null;
    }
   
   
    //renders inventory and is limited to it.
    public void renderInventory(){
        //Combines all duplicates
        this.checkDupe();
        
        //stores the current stepper of the item it is on.
        int itemInSlot = 0;
        //renders inventory by generating row, then the items in the row
        //creates rows by counting how many rows it will need
        for (int i = 0; i < ((items.size()/4)+1); i++) { 
            Main.instance.writenl("[");
            //prints out items in each row. it con only print out 4 items per row
                for (int a = 0; a < 4; a++) {
                    //Had problems trying to make the for loop not reach into an unreachable index. Fixed by using if loop to check before printing
                    if (itemInSlot < items.size()) { 
                        if (a == 3) {
                            Main.instance.writenl(items.get(itemInSlot).getName());
                        }else{
                            Main.instance.writenl(items.get(itemInSlot).getName() + ", ");
                        }
                        itemInSlot++;
                    } else {
                        Main.instance.writenl("__, ");
                    }
            }
            Main.instance.writenl("]\n");
            
        }
    }
    private Item ii;
    private Item aa;
    public void checkDupe() {
        for (int i = 0; i < items.size(); i++) {
            for (int a  = 0; a < items.size(); a++) {
                if (i < items.size() && a < items.size()) {
                    ii = items.get(i);
                    aa = items.get(a);
                }
                if (items.indexOf(ii) != items.indexOf(aa)){
                    if (ii.getName().equals(aa.getName()) && ii.getDamage() == aa.getDamage()) {
                        int higherDamage = Math.max(ii.getDamage(),aa.getDamage());
                        if (ii.getDamage() == higherDamage) {
                            ii.setDurability(ii.getDurability() + aa.getDurability());
                            items.remove(items.indexOf(aa));
                        } else if (aa.getDamage() == higherDamage) {
                            aa.setDurability(aa.getDurability() + ii.getDurability());
                            items.remove(items.indexOf(ii));
                        }
                    }
                }
            }
        }
    }
    
    public void checkSpecialDupe() {
        for (int i = 0; i < (items.size()-1); i++) {
            for (int a  = 0; a < (items.size()-1); a++) {
                if (i != items.size()) {
                    Item ii = items.get(i);
                    if (a != items.size()) {
                        Item aa = items.get(a);
                        if (items.indexOf(ii) != items.indexOf(aa)){
                            if (ii.getName().equals(aa.getName())) {
                                int higherDamage = Math.max(ii.getDamage(),aa.getDamage());
                                if (ii.getDamage() == higherDamage) {
                                    ii.setDurability(ii.getDurability() + aa.getDurability());
                                    items.remove(items.indexOf(aa));
                                } else if (aa.getDamage() == higherDamage) {
                                    aa.setDurability(aa.getDurability() + ii.getDurability());
                                    items.remove(items.indexOf(ii));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
