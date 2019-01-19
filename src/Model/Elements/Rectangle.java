package Model.Elements;

import Model.Computation.Vector2;

import java.awt.*;

import static java.lang.Math.abs;

public class Rectangle extends Polygon {
    public Vector2 min;
    public Vector2 max;

    public Rectangle(double m, Vector2 f, Vector2 v, double e, double x_min, double y_min, double width, double height) {
        super(m, f, v,e,null);
        min=new Vector2(x_min,y_min);
        max=new Vector2(x_min+width,y_min+height);


        num=4;
        buffer=new Vector2[4];
        buffer[0]=new Vector2(min);
        buffer[1]=new Vector2(max.x,min.y);
        buffer[2]=new Vector2(max);
        buffer[3]=new Vector2(min.x,max.y);
        color=new Color(0,0,0);
        volume=(max.x-min.x)*(max.y-min.y);


    }

    //Position update by ticks
    @Override
    public void update(double ticks) {
        double deltaX=velocity.x*ticks+0.5*force.x*massInv*ticks*ticks;
        double deltaY=velocity.y*ticks+0.5*force.y*massInv*ticks*ticks;
        min.x=min.x+deltaX;
        min.y=min.y+deltaY;
        max.x=max.x+deltaX;
        max.y=max.y+deltaY;

        for(int i=0;i<4;i++)
        {
            buffer[i].x+=deltaX;
            buffer[i].y+=deltaY;
        }

        //Vel changes
        Vector2 a=new Vector2(force.x*massInv,force.y*massInv);
        Vector2 deltaAcc=new Vector2(-c*volume*velocity.x*abs(velocity.x)*massInv,-c*volume*velocity.y*abs(velocity.y)*massInv);
        velocity.x=velocity.x+a.x*ticks;
        velocity.y=velocity.y+a.y*ticks;
    }

    @Override
    public void update(Vector2 vec) {
        min.x=min.x+vec.x;
        min.y=min.y+vec.y;
        max.x=max.x+vec.x;
        max.y=max.y+vec.y;
        for(int i=0;i<4;i++)
        {
            buffer[i].x+=vec.x;
            buffer[i].y+=vec.y;
        }
    }

    //Implements Interfaces of ITexture and IGeometry
    public Vector2 getMin() {
        return new Vector2(min);
    }

    public Vector2 getMax() {
        return new Vector2(max);
    }

    public Vector2 getExtra() {
        return null;
    }

//    @Override
//    public Shape getShape() {
//        return Shape.Rectangle;
//    }
}
