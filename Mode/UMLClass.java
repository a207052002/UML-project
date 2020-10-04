package Mode;
import UMLManager.*;
import Shapes.*;

public class UMLClass extends Mode {
    public UMLClass(Manager m) {
        super(m);
    }

    public UMLClass(Manager m, Coor ep) {
        super(m, ep);
    }
    
    @Override
    public void press(Coor pos) {
        int depth = _m.maxDepth() + 1;
        _m.addObj(new ClassObj("text", depth, pos, Manager._defaultH, Manager._defaultW));
    }

    @Override
    public void drag(Coor pos) {
        
    }

    @Override
    public void release(Coor pos) {
        
    }
}