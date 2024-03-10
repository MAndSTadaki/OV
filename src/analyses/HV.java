package analyses;

import abstractModel.Car;
import abstractModel.OV;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * headway-velocity
 *
 * @author tadaki
 */
public class HV {

    private final OV ov;

    public HV(OV ov) {
        this.ov = ov;
    }

    /**
     * 一台の車両の軌跡を追う
     *
     * @param dt
     * @param tstep
     * @return
     */
    public List<Point2D.Double> doExec(double dt,int tstep) {
        List<Point2D.Double> plist = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10000; i++) {
            ov.updatestate(dt,tstep);
            Car car = ov.getCar(0);
            plist.add(new Point2D.Double(car.readheadway(), car.readspeed()));
        }
        return plist;
    }

}
