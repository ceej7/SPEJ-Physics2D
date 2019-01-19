import Controller.PhysicsEngineController;
import Controller.PhysicsRender;
import Model.Computation.Vector2;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        GameRender gr=new GameRender();
        PhysicsEngineController pc=PhysicsEngineController.getPhysicsEngineController();
        pc.setRender(gr);
        pc.initialGravity(200);
        pc.resetPhysicEngine();
        pc.initialResistance(0.1,0.1);
//        Eg. setWall
        pc.initialWall(-1, 0, 2, 80);//Left
        pc.initialWall(0, 80, 110, 2);//Bottom
        pc.initialWall(110, 0, 2, 80);//Right
        pc.initialWall(0, -1, 110, 2);//Up

        //Eg. setBall(x,y,r,color)
        double eBall=0.8;
        pc.initialBall(1,0,0,eBall,80, 3, 2, new Color(255, 0, 0));
        pc.initialBall(1,0,0,eBall,70, 3, 2, new Color(0, 255, 0));
        pc.initialBall(1,0,0,eBall,50.3, 10,2, new Color(0, 0, 255));
        pc.initialBall(1,0,0,eBall,10, 8, 2, new Color(255, 0, 0));
        pc.initialBall(1,0,0,eBall,20, 3, 3, new Color(0, 255, 0));

        //Eg. setBox()
        double eBox=0.8;
        pc.initialBox(1,20, 30, eBox,70, 20,2,3, new Color(255, 255, 0));
        pc.initialBox(1,-20, -30, eBox,60, 20,2,3, new Color(0, 255, 0));
        pc.initialBox(1,0, 0, eBox,100, 55,4,2, new Color(0, 0, 255));

        //Eg. SetTriangle
        pc.initialTriangle(1,0,0,0.8,50,40,10,10,2,new Color(87,145,4));

        //Eg. SetPolygon
        Vector2[] buf=new Vector2[6];
        buf[0]=new Vector2(70,20);
        buf[1]=new Vector2(75,20);
        buf[2]=new Vector2(78,24);
        buf[3]=new Vector2(75,28);
        buf[4]=new Vector2(70,28);
        buf[5]=new Vector2(67,24);
        pc.initialPolygon(5,0,0,0.8,buf,new Color(87,125,125));

        pc.readyPhysicsRunning();
        pc.startPhysicsRunning();

        JFrame frame = new JFrame("Simple Demo");
        Container contentPane = frame.getContentPane();
        contentPane.add(gr);
        frame.setSize(1400, 1000);
        frame.setVisible(true);


    }
}
