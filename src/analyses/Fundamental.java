package analyses;

import java.awt.geom.Point2D;

import java.util.List;
import abstractModel.OV;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author tadaki
 */
public class Fundamental {

    final private double length;
    private final int tLength;
    private final OV ov;
    private final int tstep;
    private final double dt;

    public Fundamental(int tLength, OV ov, double dt, int tstep) {
        System.out.println(tLength);
        this.tLength = tLength;
        length = ov.getLength();
        this.ov = ov;
        this.dt = dt;
        this.tstep = tstep;
    }

    public List<Point2D.Double> doExec(int numCarFrom, int numCarTo,
            int numCarStep, int numRepeat) {
        int num = numCarFrom;
        List<Point2D.Double> data = Collections.synchronizedList(new ArrayList<>());
        while (num < numCarTo) {
            ov.changeNumCar(num);
            ov.ovinit();
            for (int i = 0; i < tLength; i++) {
                ov.updatestate(dt, tstep);
            }
            List<Double> speedList = ov.getSpeedList();
            double v = 0;
            for (int i = 0; i < numRepeat; i++) {
                v = speedList.stream().reduce(
                        v, (a, _item) -> a + _item);
                ov.updatestate(dt, tstep);
            }
            v /= num * numRepeat;
            double density = (double) num / length;
            data.add(new Point2D.Double(density, density * v));
            num += numCarStep;
        }
        return data;
    }

}
