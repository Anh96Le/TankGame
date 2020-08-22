package TankGame.MainDisplay;

import TankGame.GameObj.Tank;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.PrivateKey;

public class Signal implements KeyListener {
    private Tank t1, t2;
    private Signal gameSignal;
    private final int Enter;
    int Stage;

    public void setTank(Tank t1, Tank t2){
        this.t1=t1;
        this.t2=t2;
    }

    public void CheckGameOver(){
        if(t1.getStocks()<=0 || t2.getStocks()<=0){
            this.Stage= 3;
        }
    }

    public int Winneris(){
        int Winner =1 ;
        if(t2.getStocks()>t1.getStocks()){
            Winner=2;
        }
        return Winner;
    }

    public int returnStage(){
        return this.Stage;
    }
    public Signal(){
        this.Stage=1;
        this.Enter=KeyEvent.VK_ENTER;
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        if (keyPressed == Enter){
            this.Stage = 2;
            System.out.println(" GameStarting");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
