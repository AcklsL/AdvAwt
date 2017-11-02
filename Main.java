import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable{
    public static CharacterInfo character = new CharacterInfo();
    public static InventoryRender inventory = new InventoryRender();
    private static Shop shop = new Shop();
    public static Item itemz = new Item();
    private static CreateLoot lootgenerate = new CreateLoot();
    public static Utility util =  new Utility();
    private JTextArea text;
    private JFrame frame = new JFrame("Adventures Await");
    private JTextField textField = new JTextField();
    private JLayeredPane layer = new JLayeredPane();
    //private JScrollPane sp = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private String userInput;
    private boolean hasInputted;
    private boolean needInput;
    private boolean d = false;
    private Thread thread;
    private EnemyEncounter enemy;
    private boolean hasPEnter;
    private static boolean running;
    public static Main instance;
    public Main(){
        JPanel panel = new JPanel();
        text = new JTextArea();
        JScrollPane sp = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        init();
        Container c = frame.getContentPane();
        
        frame.setSize(700,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(null);
        frame.setResizable(false);
        
        
        textField.setFocusable(true);
        textField.setBackground(new Color(242,242,242));
        textField.setSize(700, 30);
        textField.setLocation(0, 550);
        
        text.setSize(0, 0);
        text.setEditable(false);
        
        sp.createVerticalScrollBar();
        sp.add(textField);
        sp.getVerticalScrollBar().setUnitIncrement(15);
        System.out.println(sp.getVerticalScrollBar());
        
        thread = new Thread(this);
        instance = this;
        System.out.println("started");
        
        textField.addFocusListener(new FocusListener() {public void focusLost(FocusEvent e) {textField.requestFocus();}public void focusGained(FocusEvent e) {}});
        textField.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {}});
        textField.addKeyListener(new KeyListener() {
            public void keyReleased(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e){
                System.out.println((e.getKeyCode()==10 )+ ", " + Integer.toString(e.getKeyCode()) + ", " + sp.getVerticalScrollBar().getValue());
                sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
                //Enter Key
                if (e.getKeyCode() == 10){
                    layer.setLayer(textField, 5);
                    needInput = false;
                    hasInputted = true;
                    userInput = textField.getText();
                }
            }
            public void keyTyped(KeyEvent e){
            }
        });
        
        frame.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){textField.requestFocus();}}); 
        
        
        c.add(textField);
        c.add(sp);
        

        Scanner input = new Scanner(System.in);
        Random generate = new Random();
        enemy = new EnemyEncounter();
        
        inventory.addItem(new Weapon("Excalibro", (generate.nextInt(10)+7),1000));
        inventory.addItem(new ThrowableItem("Throwing Axe", (generate.nextInt(10)+1),10));
        inventory.addItem(new HealthPotion("Weak health potion",10,5));
        inventory.checkDupe();
        
        thread.start();
        writeGI("Enter name: ");
        text.setText("");
        character.setName(getInput());
        write("You, " + character.getName() + ", are here to venture into this new world.");
        write("You travel with your grand sword, Excalibro, dealing " + inventory.getItemStat("damage",0) + " damage.");
        write("You travel to destroy the evil enemy, Jimmy bean. Your travels have been in vain until your home town");
        write("of Gramblestag has been attacked. You rush back home to find monsters everywhere. What will you do?");
        choice1();
        }
        
    public void choice1(){
        writeGI("Charge/Sneak: ");
        String choice1 = getInput();
        if (choice1.equalsIgnoreCase("charge")){
            Main.instance.write("You rush in to the fight. Your excalibro senses this and increases it's attack damage.");
            inventory.getItem(0).setDamage(inventory.getItem(0).getDamage() + 5);
            enemy.generateEncounter(2,"It's not over yet!");
        } else if (choice1.equalsIgnoreCase("sneak")){
            Main.instance.write("You try and sneak behind some trees and approach them slowly. You're able to");
            Main.instance.write("take one out before you are seen. They turn around and begin charging at you");
            Main.instance.write("before you can run away.");
            enemy.generateEncounter(1,"");
        } else {
            write("What?");
            choice1();
        }
        Main.instance.write("\nYou pick up some items from the enemies.");
        lootgenerate.generateLoot(1,"consumable");
        lootgenerate.generateLoot(1,"weapon");
        Main.instance.write("The enemies still run rampant in your home town. Though you have little");
        Main.instance.write("recollection of your past, you must be just and try to save the town.");
        Main.instance.write("Running in would have you killed for sure, so you must think of another way of");
        Main.instance.write("saving the town. You see some things nearby that might help. What do you do?");
        choice2();
    }
    public void choice2() {
        Main.instance.write("(1) Scream, attracting the attention of all the enemies. \n(2) Take them out on by one, quietly");
        Main.instance.writeGI("(3) Charge in. Strategy is for cowards.\n(4) Burn the village. There isn't anything here for me anyways.");
        String choice2 = getInput();
        if (choice2.equalsIgnoreCase("1")) {
            Main.instance.write("Enemies charge at you seeing you not as an enemy, but as food.");
            Main.instance.write("Your valiant action has lead to allies that were once being attacked to rush to your side.");
            Main.instance.write("Though they may be weak, their will to fight is strong. They won't last long though,\nand they're determined to help you.");
            character.addAlly(new Allies(5,9,"Weak farmer",1000));
            character.addAlly(new Allies(5,9,"Weak miner",1000));
            character.addAlly(new Allies(5,9,"Heavily injured guard",10));
            enemy.generateEncounter(3, "They're still coming!");
        } else if (choice2.equalsIgnoreCase("2")) {
            Main.instance.write("You rush forward hiding behind crates and take them out one by one. The first one");
            Main.instance.write("goes down easily, but the second one notices you as you stab him. He died, but you know");
            Main.instance.write("that another failure will surely cause for an uproar...");
            Main.instance.writeGI("Do you think you can continue on your assassination attempts? Y/N");
            String choice21 = Main.instance.getInput();
            if (choice21.equalsIgnoreCase("y")) {
                Random gen = new Random();
                int chance = gen.nextInt(100)+1;
                if (chance > 50) {
                    
                } else {
                    
                }
            } else if (choice21.equalsIgnoreCase("n")) {
                Main.instance.write("You feel that continuing will be a fool's deadly mistake, and choose to think");
                Main.instance.write("of another way to try and destroy the enemy... But how?");
            }
        } else if (choice2.equalsIgnoreCase("3")) {
            Main.instance.write("\nYou may be a fool, but you are no coward.\n");
            enemy.generateEncounter(5,"Theres another one coming!");
            Main.instance.write("...");
            Main.instance.write("You survived?");
            Main.instance.write("I guess you deserve a reward... fit for a god.");
            lootgenerate.generateLoot(4,"weapon");
            for (int i = 0; i < 15; i++) {
                lootgenerate.generateLoot(2,"consumable");
            }
            character.addToPath("c");
        } else if (choice2.equalsIgnoreCase("4")) {
            
        } else {
            Main.instance.write("\nWhat?\n");
        }
        
        shop.generateShop("wandering merchant");
        enemy.generateEncounter(1,"");
    }
    public void write(String text1) {
        text.append(text1 + "\n");
        //sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
    }
    public void writenl(String text1) {
        text.append(text1);
        //sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
    }
    public final void init(){
        frame.setVisible(true);
    }
    public void writeGI(String text) {
       this.text.append(text + "\n");
       needInput = true;
       while(needInput == true){
           try{thread.sleep(1);}catch(Exception e){}
       }
       textField.setText("");
       //sp.getVerticalScrollBar().setValue(sp.getVerticalScrollBar().getMaximum());
    }
    public void clear(){
        text.setText("");
    }
    public String getInput(){
        return userInput;
    }
    public void kill(){
        thread.interrupt();
    }

    @Override
    public void run() {
    }
        
    public static Graphics g;
    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        util.update(g);
        //g.fillRect(0  , 0, 600, 600);
        g.setColor(Color.white);
        // this.util.write("abc");
        bs.show();  
        g.dispose();
    }
    int x =0;
    public void update() {
        if(x ==0) {
        }
        x++;
    }
    public static void main(String [] args) {
        new Main();
    }
    
}

