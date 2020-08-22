package TankGame.GameObj;

import TankGame.Collision.CollisionMonitor;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends movable {
    private int bulletLife = 150;
    private int xCoor, yCoor, Velx, Vely, Width, Height, angle, tankID;
    private BufferedImage bullet;
    private Rectangle Bound = new Rectangle();
    private boolean isGone;
    private int damage;
    private CollisionMonitor CollCheck;

    public int getDamage() {
        return damage;
    }

    public Bullet(int damage, int xCoor, int yCoor,int angle, BufferedImage img, int tankID, CollisionMonitor collCheck){
        isGone=false;
        Velx = (int) Math.round(3 * Math.cos(Math.toRadians(angle)));
        Vely = (int) Math.round(3 * Math.sin(Math.toRadians(angle)));
        this.CollCheck = collCheck;
        this.damage=damage;
        this.tankID = tankID;
        this.angle=angle;
        this.bullet=img;
        this.Width = img.getWidth();
        this.Height = img.getHeight();
        this.xCoor= xCoor-(Width/2);
        this.yCoor = yCoor-(Height/2);
        //System.out.println(this.xCoor+" "+this.yCoor);
        Bound.setSize(this.Width, this.Height);
    }

    private void countDownLife(){
        this.bulletLife--;
        if (bulletLife==0){
            System.out.println("bullet Died");
            isGone=true;
            xCoor=0;
            yCoor=0;
        }
    }

    @Override
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
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void HandleCollision(Gameobj e) {
        if(!(e instanceof PoUp)){
            isGone=true;
            xCoor=0;
            yCoor=0;
            e.HandleCollision(this);
        }

    }

    public boolean CollisionCheck(Gameobj compare){

        boolean Collied = false;
        Rectangle toCompare = compare.getBound();
        if (this.Bound.intersects(toCompare)){
            //System.out.println(compare);
            Collied = true;
            //System.out.println("Collied");
        }
        return  Collied;
    }

    @Override
    public void update() throws InterruptedException{
        this.moveForwards();
        CollCheck.bulletCollisionCheck(this);
    }

    public int camefromTank(){
        return tankID;
    }
    public void moveForwards() {

        xCoor += (Velx);
        yCoor += (Vely);
        Bound.setLocation(xCoor,yCoor);
        countDownLife();
    }
    @Override
    public void drawImage(Graphics g) {
            if(isGone!=true) {
                AffineTransform rotation = AffineTransform.getTranslateInstance(xCoor, yCoor);
                rotation.rotate(Math.toRadians(angle), this.bullet.getWidth() / 2.0, this.bullet.getHeight() / 2.0);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(this.bullet, rotation, null);
                //g2d.draw(Bound);
            }
    }

    @Override
    public boolean returnIsGone() {
        return this.isGone;
    }

    @Override
    public String toString(){
        return "this is a Bullet";
    }
}
