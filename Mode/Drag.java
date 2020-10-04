package Mode;
import UMLManager.*;
import Shapes.*;

public class Drag extends Mode {
    public Drag(Manager m) {
        super(m);
    }
    public Drag(Manager m, Coor ep) {
        super(m, ep);
    }
    
    @Override
    public void press(Coor pos) {
        
    }

    @Override
    public void drag(Coor pos) {
        _m.moveSelected(pos);
        _m.reFresh();
    }

    @Override
    public void release(Coor pos) {
        _m.moveSelected(pos);
        _m.setMode(new Select(_m));
    }
}