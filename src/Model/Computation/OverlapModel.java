package Model.Computation;

import Model.Elements.*;
import Model.IElements.RigidBody;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class OverlapModel {
    //if There is a circle, demand circle at the second place;
    RigidBody A;
    RigidBody B;
    double penetration=999999;
    Vector2 normal;
    boolean isCollided;

    public double getPenetration() {
        return penetration;
    }

    public Vector2 getNormal() {
        return normal;
    }

    public boolean isCollided() {
        return isCollided;
    }


    public OverlapModel(RigidBody a, RigidBody b)
    {
        if(a instanceof Circle && !(b instanceof  Circle)) // Error detection
        {
            isCollided=false;
            return;
        }

        isCollided=false;
        A=a;
        B=b;
        if(A instanceof Circle && B instanceof Circle)
        {
            isCollided=CirclevsCircle((Circle) A, (Circle)B);
        }
        else if(A instanceof Polygon&& B instanceof Polygon)
        {
            isCollided=PolygonvsPolygon((Polygon)A,(Polygon)B);
        }
        else if(A instanceof Polygon&& B instanceof Circle)
        {
            isCollided=PolygonvsCircle((Polygon)A,(Circle)B);
        }
    }


    boolean CirclevsCircle(Circle a, Circle b )
    {
        // Vector from A to B
        Vector2 n = Vector2.minus(b.position,a.position );
        double r = a.radius + b.radius;
        r *= r;
        if(n.lengthSquared( ) > r)
            return false;
        // Circles have collided, now compute manifold
        double d = n.length(); // perform actual sqrt

        // If distance between circles is not zero
        if(d != 0)
        {
            // Distance is difference between radius and distance
            penetration = a.radius + b.radius - d;
            // Utilize our d since we performed sqrt on it already within Length( )
            // Points from A to B, and is a unit vector
            normal = Vector2.multiply(n, 1.0/d);
            return true;
        }
        // Circles are on same position
        else
        {
            // Choose random (but consistent) values
            penetration = a.radius;
            normal = new Vector2( 1, 0 );
            return true;
        }
    }


    boolean PolygonvsPolygon(Polygon a, Polygon b)
    {
        int i;
        Vector2[] abuf=a.getBuffer();
        Vector2[] bbuf=b.getBuffer();
        Vector2 axis;
        //examine projection on a's axis
        for (i=0;i<a.num;i++){ //traverse all axis of a
            if(i<a.num-1)
            {
                axis=Vector2.minus(abuf[i+1],abuf[i] );
            }
            else
            {
                axis=Vector2.minus(abuf[0],abuf[i]);
            }
            axis = Vector2.perpendicular(axis); // Get the normal of the vector (90 degrees)
            double[] a_ = project(a,axis);
            double[] b_ = project(b,axis); // Find the projection of a and b onto axis
            double overlap=overlap(a_,b_);
            if(overlap==0)
                return false; // If they do not overlap, then no collision
            else{
                if(overlap<penetration)
                {
                    penetration=overlap;
                    normal=new Vector2(axis.x,axis.y);
                }
            }
        }
        //examine projection on b's axis
        for (i=0;i<b.num;i++){ //traverse all axis of a

            if(i<b.num-1)
            {
                axis=Vector2.minus(bbuf[i+1],bbuf[i] );
            }
            else
            {
                axis=Vector2.minus(bbuf[0],bbuf[i]);
            }
            axis = Vector2.perpendicular(axis); // Get the normal of the vector (90 degrees)
            double[] a_ = project(a,axis);
            double[] b_ = project(b,axis); // Find the projection of a and b onto axis
            double overlap=overlap(b_,a_);
            if(overlap==0)
                return false; // If they do not overlap, then no collision
            else{
                if(overlap<penetration)
                {
                    penetration=overlap;
                    normal=new Vector2(-axis.x,-axis.y);
                }
            }
        }
        return true;
    }

    //to get the maximun projection and minimum projection on axis
    double[] project(Polygon a,Vector2 axis)
    {
        axis.normalize();
        int i;
        Vector2[] abuf=a.getBuffer();
        double min=abuf[0].dot(axis);double max=min;
        for(i=1;i<a.getBuffer().length;i++)
        {
            double proj = abuf[i].dot(axis); // find the projection of every point on the polygon onto the line.
            if (proj < min) min = proj; if (proj > max) max = proj;
        }
        double arr[]=new double[]{min,max};
        return arr;
    }

    double overlap(double[] a, double[] b)
    {
        if(a[0]<=b[0])
        {
            if(a[1]<=b[0]) return 0.0;
            else if(a[1]<=b[1]) return a[1]-b[0];
            else return b[1]-b[0];
        }
        else if(a[0]<=b[1])
        {
            if(a[1]<=b[1]) return 999999;
            else return 999999;
        }
        return 0.0;
    }

    boolean PolygonvsCircle(Polygon a, Circle b)
    {
        int i;
        Vector2[] abuf=a.getBuffer();
        Vector2 axis;
        //examine projection on a's axis
        for (i=0;i<a.num;i++){ //traverse all axis of a
            if(i<a.num-1)
            {
                axis=Vector2.minus(abuf[i+1],abuf[i] );
            }
            else
            {
                axis=Vector2.minus(abuf[0],abuf[i]);
            }
            axis = Vector2.perpendicular(axis); // Get the normal of the vector (90 degrees)
            double[] a_ = project(a,axis);
            double[] b_ = projectCircle(b,axis); // Find the projection of a and b onto axis
            double overlap=overlap(a_,b_);
            if(overlap==0)
                return false; // If they do not overlap, then no collision
            else{
                if(overlap<penetration)
                {
                    penetration=overlap;
                    normal=new Vector2(axis.x,axis.y);
                }
            }
        }

        return true;
    }

    double[] projectCircle(Circle a,Vector2 axis)
    {
        axis.normalize();
        double proj = a.position.dot(axis); // find the projection of every point on the polygon onto the line.
        double arr[]=new double[]{proj-a.radius,proj+a.radius};
        return arr;
    }

}
