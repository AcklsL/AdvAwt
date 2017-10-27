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
        
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setLayout(null);
        frame.setResizable(false);
        
        layer.setPreferredSize(new Dimension(600,500));
        layer.add(panel,1);
        layer.add(textField,2);
        
        textField.setFocusable(true);
        textField.setBackground(new Color(242,242,242));
        textField.setSize(600, 30);
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
                System.out.println((e.getKeyCode()==10 )+ ", " + Integer.toString(e.getKeyCode()));
                
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
        write("You travel to destroy the evil enemy, Jimmy bean. Your travels have been in vain until...");
        enemy.generateEncounter(1);
        lootgenerate.generateLoot(1,"consumable");
        lootgenerate.generateLoot(1,"weapon");
        shop.generateShop("wandering merchant");
        enemy.generateEncounter(1);
        }
    
    public void write(String text1) {
        text.append(text1 + "\n");
    }
    public void writenl(String text1) {
        text.append(text1);
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
    }
    public String getInput(){
        return userInput;
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

