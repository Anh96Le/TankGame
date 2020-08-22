package TankGame.MainDisplay;

import TankGame.Collision.CollisionMonitor;
import TankGame.GameObj.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class TankGame extends JPanel  {
    private Signal gameSignal;
    private World theMap;
    public static final int Screen_Width = 1200;
    public static final int Screen_Height = 900;
    private BufferedImage world, interF, Welcome;
    private BufferedImage t1img = null, t2img = null, Background = null, UnBrWall=null, PowerUp = null,BrWall = null, Bullets = null, BWall2=null, PowerUp2 = null,PowerUp1=null,ScoreBoard=null, PIW =null, PIIW=null;
    private Graphics2D buffer;
    private JFrame jf;
    private Tank t1;
    private Tank t2;
    private static CollisionMonitor CollCheck;
    private ArrayList<Bullet> bulletList;

    public static void main(String[] args) {
        Thread x;
        TankGame tankGame = new TankGame();
        tankGame.init();
        try {

            while (true) {
                if (tankGame.gameSignal.returnStage()==1){
                    tankGame.repaint();
                }
                else if(tankGame.gameSignal.returnStage()==2){
                    tankGame.t1.update();
                    tankGame.t2.update();
                    tankGame.repaint();
                    tankGame.gameSignal.CheckGameOver();
                }
                else if (tankGame.gameSignal.returnStage()==3){
                    tankGame.repaint();
                }
                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {
        }
    }

    private void init() {
        this.jf = new JFrame("Tank Game");
        this.gameSignal = new Signal();
        this.bulletList =new ArrayList<>();
        this.interF = new BufferedImage(250, 650,BufferedImage.TYPE_INT_RGB );
        try {
            //System.out.println(System.getProperty("user.dir"));
            Background = ImageIO.read(getClass().getClassLoader().getResource("Background.bmp"));
            t1img = ImageIO.read(getClass().getClassLoader().getResource("tank1.png"));
            t2img = ImageIO.read(getClass().getClassLoader().getResource("tank1.png"));
            UnBrWall = ImageIO.read(getClass().getClassLoader().getResource("Wall2.png"));
            PowerUp = ImageIO.read(getClass().getClassLoader().getResource("Health.png"));
            BrWall = ImageIO.read(getClass().getClassLoader().getResource("Wall.png"));
            Bullets = ImageIO.read(getClass().getClassLoader().getResource("bullet.png"));
            BWall2 = ImageIO.read(getClass().getClassLoader().getResource("Wall3.png"));
            PowerUp1 = ImageIO.read(getClass().getClassLoader().getResource("3Shot.png"));
            PowerUp2 = ImageIO.read(getClass().getClassLoader().getResource("DamagePl.png"));
            ScoreBoard = ImageIO.read(getClass().getClassLoader().getResource("Navi.png"));
            Welcome = ImageIO.read(getClass().getClassLoader().getResource("Welcome.png"));
            PIW= ImageIO.read(getClass().getClassLoader().getResource("P1W.png"));
            PIIW= ImageIO.read(getClass().getClassLoader().getResource("P2W.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.theMap = new World(Background, UnBrWall, PowerUp, BrWall, BWall2, PowerUp1, PowerUp2);
        this.world = new BufferedImage(theMap.getWidth(), theMap.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.t1 = new Tank(90, 60, 0, 0, 0, t1img, Bullets, 1);
        this.t1.setBulletList(this.bulletList);
        this.t2 = new Tank(1400, 1400, 0, 0, 180, t2img, Bullets, 2);
        this.t2.setBulletList(this.bulletList);
        this.gameSignal.setTank(t1, t2);
        this.CollCheck= new CollisionMonitor(t1, t2, theMap.getnotMovableList());
        this.t1.setColCheck(CollCheck);
        this.t2.setColCheck(CollCheck);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);
        this.jf.addKeyListener(gameSignal);
        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);
        this.jf.setSize(Screen_Width+250, 930);
        this.jf.setResizable(false);
        this.jf.setLocationRelativeTo(null);
        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();
        super.paintComponent(g2);
        if(this.gameSignal.returnStage()==1){
        g2.drawImage(world, 0, 0, null);
        g2.drawImage(Welcome, ((Screen_Width/2)-(Welcome.getWidth()/2))+125,((Screen_Height/2)-(Welcome.getHeight()/2)), null);
        }
        if(this.gameSignal.returnStage()==2) {
            this.theMap.drawImage(buffer);
            this.t1.drawImage(buffer);
            this.t2.drawImage(buffer);
            BufferedImage LeftScreen = (BufferedImage) world.getSubimage(t1.returnxScreen(), t1.returnyScreen(), Screen_Width / 2, Screen_Height);
            BufferedImage RightScreen = (BufferedImage) world.getSubimage(t2.returnxScreen(), t2.returnyScreen(), Screen_Width / 2, Screen_Height);
            BufferedImage miniMap = world.getSubimage(0, 0, 1500, 1500);
            g2.drawImage(miniMap, (Screen_Width / 2), 0, 250, 250, null);
            g2.drawImage(LeftScreen, 0, 0, null);
            g2.drawImage(RightScreen, (Screen_Width / 2) + 250, 0, null);
            buffer = interF.createGraphics();
            this.t1.drawInterF(buffer, ScoreBoard);
            this.t2.drawInterF(buffer, ScoreBoard);
            g2.drawImage(interF, (Screen_Width / 2), 250, 250, 650, null);
        }
        if(this.gameSignal.returnStage()==3){
            g2.drawImage(new BufferedImage(Screen_Width+250, Screen_Height, BufferedImage.TYPE_INT_RGB), 0, 0, null);
            switch (gameSignal.Winneris()){
                case 1:
                    BufferedImage LeftScreen = (BufferedImage) world.getSubimage(t1.returnxScreen(), t1.returnyScreen(), Screen_Width / 2, Screen_Height);
                    g2.drawImage(LeftScreen, 0, 0, null);
                    g2.drawImage(PIW, ((Screen_Width+250)*3/4)-PIW.getWidth()/2, (Screen_Height/2)-(PIW.getWidth()/2), null);
                    break;
                case 2:
                    BufferedImage RightScreen = (BufferedImage) world.getSubimage(t2.returnxScreen(), t2.returnyScreen(), Screen_Width / 2, Screen_Height);
                    g2.drawImage(RightScreen, (Screen_Width / 2) + 250, 0, null);
                    g2.drawImage(PIIW, ((Screen_Width+250)*1/4)-PIIW.getWidth()/2, (Screen_Height/2)-(PIIW.getHeight()/2), null);
                    break;
            }
        }
    }
}
