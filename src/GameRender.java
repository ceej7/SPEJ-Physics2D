


import Controller.PhysicsRender;
import Model.Computation.Vector2;
import Model.Elements.Circle;
import Model.IElements.Geometry;
import Model.IElements.RigidBody;
import Model.IElements.Texture;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

//The view of the game must implement Interface PhysicsRender to use the Physicworld
public class GameRender extends JPanel implements PhysicsRender
{

    private int frameCount;
    private double scale; // The scale of Screen size v.s. RealWorld
    private Vector<RigidBody> rigids;

    public GameRender()
    {
        //Initial member
        scale=10.0;
        //Count the frames
        frameCount=0;
        this.setSize(1400, 1000);

    }

    @Override
    public void updateGeometries(Vector<RigidBody> g)
    {
        rigids=g;
    }

    @Override
    public void paintComponent(Graphics g)//Render Methods: to render all acceptable Geometries by getMin and get Max
    {
        super.paintComponent(g);
        if(rigids==null) return;
        for(RigidBody rigid:rigids)
        {
            Geometry geo=(Geometry)rigid;
            g.setColor(((Texture)geo).getColor());
            Geometry.Shape shape=geo.getShape();
            switch (shape)
            {
                //if the Geometry of a Obj is Circle
                case Circle:
                {
                    Circle cir=(Circle)geo;
                    int x1=(int)((cir.getMin().x)*scale);
                    int y1=(int)((cir.getMin().y)*scale);
                    int x2=(int)((cir.getMax().x)*scale);
                    int y2=(int)((cir.getMax().y)*scale);
                    g.fillOval(x1, y1, x2-x1, y2-y1);
                    break;
                }
                //if the Geometry of a Obj is Polygon
                case Polygon:
                {
                    Vector2[] buf=geo.getBuffer();
                    int xBuffer[]=new int[buf.length];
                    int yBuffer[]=new int[buf.length];
                    for(int i=0;i<buf.length;i++)
                    {
                        xBuffer[i]=(int)(buf[i].x*scale);
                        yBuffer[i]=(int)(buf[i].y*scale);
                    }
                    g.fillPolygon(xBuffer,yBuffer,buf.length);
                    break;
                }

            }
        }
    }



}
