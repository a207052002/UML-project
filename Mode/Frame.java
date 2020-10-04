package Mode;
import UMLManager.*;
import Shapes.*;
import java.awt.Graphics2D;

public class Frame extends Mode {
    public Frame(Manager m) {
        super(m);
    }

    public Frame(Manager m, Coor ep) {
        super(m, ep);
    }
    
    @Override
    public void press(Coor pos) {
        
    }

    @Override
    public void drag(Coor pos) {
        _ep.x = pos.x;
        _ep.y = pos.y;
    }

    @Override
    public void release(Coor pos) {
        _m.endFraming(pos);
        _m.setMode(new Select(_m, _ep));
    }

    @Override
    public void rendering(Graphics2D g2) {
        _m.renderFraming(_ep, g2);
    }
}