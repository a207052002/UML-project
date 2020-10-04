package Mode;
import UMLManager.*;
import Shapes.*;
import java.awt.Graphics2D;

abstract public class Mode {
    Manager _m;
    public Mode(Manager m) {
        _m = m;
        _ep = new Coor();
    }

    public Mode(Manager m, Coor ep) {
        _m = m;
        _ep = new Coor();
        _ep.x = ep.x;
        _ep.y = ep.y;
    }
    Coor _ep;

    public abstract void press(Coor pos);
    public abstract void drag(Coor pos);
    public abstract void release(Coor pos);
    public void rendering(Graphics2D g2) {
    }
}