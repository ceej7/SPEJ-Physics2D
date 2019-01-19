package Controller;

import Model.IElements.RigidBody;

import java.util.Vector;

public interface PhysicsRender {
    public void updateGeometries(Vector<RigidBody> g);

    public void repaint() ;
}
