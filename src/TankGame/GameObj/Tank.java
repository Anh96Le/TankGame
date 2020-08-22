package TankGame.GameObj;

import TankGame.Collision.CollisionMonitor;
import TankGame.MainDisplay.TankGame;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends movable{
    private int originalx, originaly;
    private int xCoor;
    private int yCoor;
    private int Velx;
    private int Vely;
    private int angle;
    private final int R = 2;
    private final int rotationalSpeed = 4;
    private Rectangle Bound = new Rectangle();
    private BufferedImage img, bulletImg;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shoot;
    private int prvX;
    private int prvY;
    private int Width;
    private int Height;
    private int TankID;
    private int health, stocks;
    private ArrayList<Bullet> bulletList;
    private boolean isGone=false;
    private boolean TrShot = false;
    private int damage = 10;
    private boolean lock=false;
    private CollisionMonitor CollCheck;

    public int getStocks(){
        return this.stocks;
    }
    public void setBulletList(ArrayList<Bullet> BL){
        this.bulletList = BL;
    }

    public void HandlePowerUp(int po){
        switch (po){
            case 1:
                health+=5;
                if (health>=100){
                    health=100;
                }
                break;
            case 2:
                TrShot=true;
                break;
            case 3:
                damage += 1;
                break;
        }
    }

    public Rectangle getBound() {
        return Bound;
    }

    @Override
    public int getxCoor() {
        return xCoor;
    }

    @Override
    public int getyCoor() {
        return yCoor;
    }

    @Override
    public int getWidth() {
        return Width;
    }

    @Override
    public int getHeight() {
        return Height;
    }

    public void setColCheck(CollisionMonitor tankColCheck){
        this.CollCheck = tankColCheck;
    }

    public Tank(int x, int y, int vx, int vy, int angle, BufferedImage img, BufferedImage bullets, int tankID) {
        this.originalx=x;
        this.originaly=y;
        this.TankID = tankID;
        this.xCoor = x;
        this.yCoor = y;
        this.Velx = vx;
        this.Vely = vy;
        this.img = img;
        this.angle = angle;
        this.Width = img.getWidth();
        this.Height = img.getHeight();
        this.bulletImg = bullets;
        Bound.setSize(Width,Height);
        this.health=100;
        this.stocks=3;
        this.prvX=xCoor;
        this.prvY=yCoor;
    }
    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void toggletankShoot(){
        this.shoot = true;
    }

    void unToggletankShoot(){
        this.shoot = false;
        lock = false;
    }

    public int returnxScreen(){
        int xScreen= xCoor-(TankGame.Screen_Width/4);
        if (xScreen < 0) {
            xScreen = 0;
        }
        if (xScreen >= World.GameW - (TankGame.Screen_Width/2)) {
            xScreen = World.GameW - (TankGame.Screen_Width/2);
        }
        return xScreen;
    }

    public int returnyScreen(){
        int yScreen =  yCoor-(TankGame.Screen_Height/2);
        if (yScreen < 0){
            yScreen = 0;
        }
        if (yScreen >= World.GameH - (TankGame.Screen_Height)) {
            yScreen = World.GameH - (TankGame.Screen_Height);
        }
        return yScreen;
    }

    private int getBulletx(){
        return xCoor+(Width/2);
    }

    private int getBullety(){
        return this.yCoor+(Height/2);
    }

    public int getTankID(){
        return this.TankID;
    }

    @Override
    public void update() throws InterruptedException {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        Bound.setLocation(xCoor,yCoor);
        if(this.shoot==true){
            if(!lock) {
                this.shooting();
                lock=true;
            }
        }
        if(!bulletList.isEmpty()){
        for (int i=bulletList.size()-1; i >=0;i--){
            bulletList.get(i).update();
            if(bulletList.get(i).returnIsGone()==true){
                System.out.println("Bullet removed");
                bulletList.remove(i);
            }
        }
        }
    }

    private void shooting(){
        Bullet aBullet = new Bullet(this.damage, getBulletx(), getBullety(), this.angle, bulletImg, this.TankID, CollCheck);
        bulletList.add(aBullet);
        if(TrShot){
        aBullet = new Bullet(this.damage, getBulletx(), getBullety(), this.angle+20, bulletImg, this.TankID, CollCheck);
        bulletList.add(aBullet);
        aBullet = new Bullet(this.damage, getBulletx(), getBullety(), this.angle-20, bulletImg, this.TankID, CollCheck);
        bulletList.add(aBullet);
        }
        this.shoot = false;
    }

    private void rotateLeft() {
        this.angle -= this.rotationalSpeed;
    }

    private void rotateRight() {
        this.angle += this.rotationalSpeed;
    }

    private void moveBackwards() {
        Velx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        Vely = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        prvX=xCoor;
        prvY=yCoor;
        xCoor -= Velx;
        yCoor -= Vely;
        CollCheck.CheckCollision();
        //checkBorder();
    }

    private void moveForwards() {
        Velx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        Vely = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        prvX=xCoor;
        prvY=yCoor;
        xCoor += Velx;
        yCoor += Vely;
        CollCheck.CheckCollision();
        //checkBorder();
    }

    public void HandleCollision(Gameobj e){

        if(!(e instanceof Bullet)){
        if(((xCoor+Velx) >=e.getxCoor()-Width)&&((xCoor+Velx) <=e.getxCoor()+e.getWidth())){
        this.xCoor=prvX-1;}
        if(((xCoor+Velx) <= e.getxCoor()+e.getWidth())&&((xCoor+Velx)+1>e.getxCoor())){
            this.xCoor=prvX+1;
        }
        if(((yCoor+Vely)<= e.getyCoor()+e.getHeight())&&(yCoor+Vely)>=e.getyCoor()){
        this.yCoor=prvY+1;}
        if(((yCoor+Vely)>= e.getyCoor()-Height)&&((yCoor+Vely+Height)<=(e.getyCoor()+e.getHeight()))){
            this.yCoor = prvY-1;
        }
        if(!( e instanceof movable)){
        e.HandleCollision(this);}}
        else if(e instanceof Bullet){
            Bullet temp = (Bullet) e;
            damaged(temp.getDamage());
        }
    }

    private void damaged(int damage){
        this.health-=damage;
        if(health<=0){
            this.TrShot=false;
            this.damage=10;
            xCoor=originalx;yCoor=originaly;
            health = 100;
            stocks=stocks-1;
        }
    }

    private void checkBorder() {
        if (xCoor < 32) {
            xCoor = 32;
        }
        if (xCoor >= World.GameW - 82){
            xCoor = World.GameW - 82;
        }
        if (yCoor < 32) {
            yCoor = 32;
        }
        if (yCoor >= World.GameH - 82) {
            yCoor = World.GameH - 82;
        }
    }

    @Override
    public String toString() {
        return "x=" + xCoor + ", y=" + yCoor + ", angle=" + angle;
    }

    public boolean CollisionCheck(Gameobj compare){
        boolean Collied = false;
        Rectangle toCompare = compare.getBound();
        if (this.getBound().intersects(toCompare)){
            //System.out.println("Tank Touching Something");
            Collied = true;
        }
        return  Collied;
    }
    public void drawInterF(Graphics g, BufferedImage e ){
        Graphics2D g2d = (Graphics2D) g;
        BufferedImage background = e;
        if(TankID ==1){
            g2d.drawImage(background, 0, 0,250,650, null);
            g2d.setColor(new Color(200,0,0));
            g2d.fillRect(20, 125, 50, 200);
            g2d.setColor(new Color(0,200,0));
            //System.out.println("health t1="+health);
            g2d.fillRect(20, 125, 50, (2*health));
            //System.out.println(stocks);
            for(int i=0; i <stocks; i++){
            g2d.drawImage(this.img, 75, 125+(30*i)+5, 30,30, null);}
        }
        else if(TankID ==2){
            g2d.setColor(new Color(200,0,0));
            g2d.fillRect(175, 125, 50, 200);
            g2d.setColor(new Color(0,200,0));
            g2d.fillRect(175, 125, 50, (2*health));
            for(int i=0; i <stocks; i++){
            g2d.drawImage(this.img, 140, 125+(30*i)+5, 30,30, null);}
        }
    }
    @Override
    public void drawImage(Graphics g) {
        if(true) {
            AffineTransform rotation = AffineTransform.getTranslateInstance(xCoor, yCoor);
            rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.img, rotation, null);
            //g2d.draw(Bound);
            try {
                if (!bulletList.isEmpty()) {
                    for (Bullet e : bulletList) {
                        e.drawImage(g2d);
                    }
                }
            } catch (Exception e) {
                e.getMessage();
                System.out.println("bullet drew on top of another");
            }
        }
    }

    @Override
    public boolean returnIsGone() {
        return isGone;
    }

}
