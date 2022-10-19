package system;

import object.Ship;
import system.input.AnotherKeyboardInput;

import java.awt.geom.Point2D;

public class CameraSystem {
    private Point2D.Float position;
    private float z;//z es que tan cerca o lejos debe estar la camara del escenario o personaje.
    private float baseZ;//baseZ es que tanto o cuantos pixeles debe abarcar la camara en la pantalla.

    private float aspecRatio;
    private float fieldOfView;

    //private Matrix vew;


    public CameraSystem(Point2D.Float position) {
        this.position = position;
    }

    public void update(int dt, Ship player) {
        position.x = -player.getX() + Constants.WIDTH/2-70;
        position.y = -player.getY() + Constants.HEIGHT/2;
    }

    public void render() {

    }

    public Point2D.Float getPosition() {
        return position;
    }

    public void setPosition(Point2D.Float position) {
        this.position = position;
    }
}
