package TankGame.GameObj;

import java.awt.*;

public abstract class Gameobj {

    public abstract Rectangle getBound();
    public abstract int getxCoor();
    public abstract int getyCoor();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract void HandleCollision(Gameobj e);
    public abstract void drawImage(Graphics g);
    public abstract boolean returnIsGone();

}
