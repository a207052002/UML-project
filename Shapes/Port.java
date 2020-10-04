package Shapes;

public class Port {

    public Port(int type, PortObj portObj) {
        _portObj = portObj;
        _portType = type;
    }

    PortObj _portObj;
    int _portType;

    public int portType() {
        return _portType;
    }
    
    public Coor pos() {
        double width = _portObj.width();
        double height = _portObj.height();
        Coor oPos = _portObj.pos();
        Coor tmpPos = new Coor();
        if(_portType == PortObj.NPORT) {
            tmpPos.x = oPos.x + width/2;
            tmpPos.y = oPos.y;
        } else if(_portType == PortObj.EPORT) {
            tmpPos.x = oPos.x + width;
            tmpPos.y = oPos.y + height/2;
        } else if(_portType == PortObj.WPORT) {
            tmpPos.x = oPos.x;
            tmpPos.y = oPos.y + height/2;
        } else if(_portType == PortObj.SPORT) {
            tmpPos.x = oPos.x + width/2;
            tmpPos.y = oPos.y + height;
        }
        return tmpPos;
    }
    
}