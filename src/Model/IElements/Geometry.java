package Model.IElements;

import Model.Computation.Vector2;

public interface Geometry {
    public static enum Shape{Circle, Rectangle,Triangle,Polygon};
    public Shape getShape();
    public Vector2[] getBuffer();
}
