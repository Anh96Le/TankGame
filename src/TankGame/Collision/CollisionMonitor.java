package TankGame.Collision;

import TankGame.GameObj.Bullet;
import TankGame.GameObj.Gameobj;
import TankGame.GameObj.Tank;


import java.util.ArrayList;

public class CollisionMonitor {
    private ArrayList<Tank> TankList= new ArrayList<>();
    private Tank t1;
    private Tank t2;
    private ArrayList<Gameobj> UnMovableList;

    public CollisionMonitor(Tank t1, Tank t2, ArrayList<Gameobj> UnMovableList){
        this.TankList.add(t1) ;
        this.TankList.add(t2) ;
        this.t1=t1;
        this.t2=t2;
        this.UnMovableList = UnMovableList;
    }

    public void bulletCollisionCheck(Bullet e){
            Bullet tempBullet = e;
            for(int t= 0; t<TankList.size();t++){
                //System.out.println("Checking Collision with Tank " +t);
                if(tempBullet.CollisionCheck(TankList.get(t))==true){
                    //System.out.println("Bullet from tank:" + tempBullet.camefromTank() + "Collied with "+ TankList.get(t).getTankID());
                    if(tempBullet.camefromTank()!=TankList.get(t).getTankID()) {
                        //System.out.println("Bullet from tank:" + tempBullet.camefromTank() + "Handling Collision with "+ TankList.get(t).getTankID());
                        tempBullet.HandleCollision(TankList.get(t));
                        break;
                    }
                }
            }
            for(int j=0; j<UnMovableList.size();j++){
                Gameobj temp = UnMovableList.get(j);
                    if(tempBullet.CollisionCheck(temp)){
                        tempBullet.HandleCollision(temp);
                    }
                if(UnMovableList.get(j).returnIsGone()){
                    UnMovableList.remove(j);
                    j--;
                }
            }
    }

    public void CheckCollision(){
        if(t1.CollisionCheck(t2)){
            t1.HandleCollision(t2);
        }
        if(t2.CollisionCheck(t1)){
            t2.HandleCollision(t1);
        }
        for(int i=0; i<UnMovableList.size();i++){
            Gameobj temp = UnMovableList.get(i);
            for(int t= 0; t<TankList.size();t++){
                if(TankList.get(t).CollisionCheck(temp)){
                    TankList.get(t).HandleCollision(temp);
                }
            }
            if(UnMovableList.get(i).returnIsGone()){
                UnMovableList.remove(i);
                i--;
            }
        }
    }
}
