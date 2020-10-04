package Mode;
import UMLManager.*;
import Shapes.*;

public class UserCase extends Mode {
    public UserCase(Manager m) {
        super(m);
    }
    public UserCase(Manager m, Coor ep) {
        super(m, ep);
    }
    
    @Override
    public void press(Coor pos) {
        int depth = _m.maxDepth() + 1;
        _m.addObj(new UserCaseObj("text", depth, pos, Manager._defaultH, Manager._defaultW));
    }

    @Override
    public void drag(Coor pos) {
        
    }

    @Override
    public void release(Coor pos) {
        
    }
}