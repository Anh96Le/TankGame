package TankGame.GameObj;


import java.awt.*;
import java.awt.image.BufferedImage;


public class UBWall extends Stationary {
    private Rectangle Bound;
    private BufferedImage UnBrWall;
    private int xCoor;
    private int yCoor;
    private int Width;

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

    @Override
    public void HandleCollision(Gameobj e) {
    }

    private int Height;
    public UBWall(int x, int y, BufferedImage img){
        UnBrWall=img;
        this.Width= 30;
        this.xCoor=x*Width;
        //System.out.println(Width);
        this.Height= 30;
        this.yCoor=y*Height;
        //System.out.println(Height);
        Bound= new Rectangle(xCoor, yCoor, Width, Height);
    }

    @Override
    public void drawImage(Graphics g){
        Graphics2D g2d= (Graphics2D) g;
        g2d.drawImage(UnBrWall, xCoor, yCoor,Width, Height, null);
        g2d.draw(Bound);
    }

    @Override
    public boolean returnIsGone() {
        return false;
    }
}
