package TankGame.GameObj;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PoUp extends Stationary {
    private int POVal;
    private boolean isGone = false;
    private boolean drawable = true;
    private int xCoor;
    private int yCoor;
    private int Width = 0, Height = 0;
    private BufferedImage PU;
    private Rectangle Bound;

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
        if (e instanceof Tank){
        drawable=false;
        isGone=true;
        xCoor=0;
        yCoor=0;
        ((Tank) e).HandlePowerUp(POVal);}
    }

    public boolean returnIsGone(){
        return this.isGone;
    }

    public Rectangle getBound() {
        return Bound;
    }

    public PoUp(int x, int y, BufferedImage img, BufferedImage img1, BufferedImage img2){
       this.POVal = (int)(Math.random() * 3) + 1;
        switch (POVal){
            case 1:
                PU=img;
                break;
            case 2:
                PU=img1;
                break;
            case 3:
                PU=img2;
                break;
        }
        this.Width= 30;
        this.xCoor=x*Width;
        this.Height= 30;
        this.yCoor=y*Height;
        Bound = new Rectangle(xCoor,yCoor,Width,Height);
    }

    @Override
    public void drawImage(Graphics g){
        if (drawable==true){
        Graphics2D g2d= (Graphics2D) g;
        g2d.drawImage(PU, xCoor, yCoor,null);
        g2d.draw(Bound);}
    }
}
