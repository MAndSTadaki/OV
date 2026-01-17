package abstractModel;

/**
 *
 * @author tadaki
 */
public class Car {
    
    protected double x;
    protected double xx; //位置
    protected double v;
    protected double vv; //速度
    protected double dx;

    public Car(){}

    public void setvar(double xi, double vi) {
        x = xi;
        v = vi;
        xx = x;
        vv = v;
    }

    /**
     * 現在の値を保存する
     */
    public void savevalue() {
        xx = x;
        vv = v;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setV(double v) {
        this.v = v;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double readposition() {
        return x;
    }

    public double readspeed() {
        return v;
    }

    public double readprevposition() {
        return xx;
    }

    public double readprevspeed() {
        return vv;
    }

    public double readheadway() {
        return dx;
    }

}
