package TankGame.GameObj;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BWall extends Stationary {
    int xCoor;
    int yCoor;
    int Width = 0, Height = 0;
    int health = 20;
    BufferedImage BRW, BRW2;
    private Rectangle Bound;
    private boolean isGone;

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

    @Override
    public void HandleCollision(Gameobj e) {
        if(e instanceof Bullet){
            Bullet tempbullet = (Bullet) e;
            damaged(tempbullet.getDamage());
        }

    }

    public void damaged(int damage){
        health-=damage;
        if(health<=0){
            isGone=true;
        }
    }

    public Rectangle getBound() {
        return Bound;
    }
    public BWall(int x, int y, BufferedImage img, BufferedImage img2){
        BRW2=img2;
        BRW=img;
        this.Width= 30;
        this.xCoor=x*Width;
        //System.out.println(Width);
        this.Height= 30;
        this.yCoor=y*Height;
        Bound = new Rectangle(xCoor,yCoor,Width,Height);
    }
    @Override
    public void drawImage(Graphics g){
        if(!isGone){
        Graphics2D g2d= (Graphics2D) g;
        g2d.drawImage(BRW2, xCoor, yCoor,null);
        if(health==20){
        g2d.drawImage(BRW, xCoor, yCoor,null);}
        g2d.draw(Bound);}
    }

    @Override
    public boolean returnIsGone() {
        return isGone;
    }
}
