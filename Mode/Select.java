package Mode;
import UMLManager.*;
import Shapes.*;

public class Select extends Mode {
    public Select(Manager m) {
        super(m);
    }

    public Select(Manager m, Coor ep) {
        super(m, ep);
    }

    @Override
    public void press(Coor pos) {
        if(_m.selectObj(pos)) {
            _m.repaint();
            _m.setMode(new Drag(_m, _ep));
        } else {
            _ep.x = pos.x;
            _ep.y = pos.y;
            _m.startFraming(pos);
            _m.setMode(new Frame(_m, _ep));
        }
    }

    @Override
    public void drag(Coor pos) {
        
    }

    @Override
    public void release(Coor pos) {
        
    }
}