package abstractModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleFunction;
import rungeKutta.DifferentialEquations;
import rungeKutta.RungeKutta;

/**
 * 最適速度交通流モデル
 *
 * @author tadaki
 */
public class OV {

    final private double length;//コース長
    private int numCar;//車両数
    private List<Car> cars;//車両リスト
    private double t;//時刻
    private DifferentialEquations equation;//微分方程式
    protected double alpha;//感受率
    protected DoubleFunction<Double> ovfunction;//最適速度関数

    /*
     高速道路対応
     double vmax = 33.6;
     final double d = 25.;
     final double w = 23.3;
     final double c = 0.913;
     final double a = 2.;
     */
//    final private CarParameters p = new CarParameters(33.6, 25., 23.3, 0.913, 2.);
    /*
     実験2009対応
     double vmax = 12;
     final double d = 9.;
     final double w = 5.5;
     final double c = 0.9;
     final double a = 1.8;
     */
//    final private CarParameters p = new CarParameters(12., 9., 5.5, 0.98, 2.);
    /**
     * circuitの長さを指定して初期化
     *
     * @param leng
     * @param alpha
     * @param ovfunction
     */
    public OV(double leng, double alpha, DoubleFunction<Double> ovfunction) {
        this.length = leng;
        this.alpha = alpha;
        this.ovfunction = ovfunction;
    }

    /**
     * 初期状態生成
     */
    public void ovinit() {
        cars = Collections.synchronizedList(new ArrayList<>());
        double dr = length / numCar;
        for (int i = 0; i < numCar; i++) {
            Car car = new Car();
            car.setvar(i * dr, 0.);
            cars.add(car);
        }
        int ii = (int) (0.4 * numCar);
        if (ii >= 0 && ii < cars.size()) {
            cars.get(ii).setvar(ii * dr - 0.2 * dr, 0.);//摂動
        }

        t = 0.;
        //偶数番要素は位置、奇数番要素は速度
        equation = (double td, double y[]) -> {
            double dy[] = new double[2 * numCar];
            for (int i = 0; i < numCar; i++) {
                int j = (i + 1) % numCar;
                double headway = y[2 * j] - y[2 * i];
                headway = (headway + length) % length;
                dy[2 * i + 1] = alpha * (ovfunction.apply(headway) - y[2 * i + 1]);
                dy[2 * i] = y[2 * i + 1];
            }
            return dy;
        };
    }

    /**
     * 状態更新 : dtをtstepに分割して積分
     *
     * @param dt
     * @param tstep
     */
    public void updatestate(double dt, int tstep) {//
        double y[] = new double[2 * numCar];
        for (int i = 0; i < numCar; i++) {
            y[2 * i] = cars.get(i).readposition() % length;
            y[2 * i + 1] = cars.get(i).readspeed();
        }
        double yy[][] = RungeKutta.rkdumb(y, 0, dt, tstep, equation);

        //位置及び速度の保存
        cars.stream().forEach(c -> c.savevalue());

        for (int i = 0; i < numCar; i++) {
            cars.get(i).setX(yy[2 * i][tstep - 1] % length);
            cars.get(i).setV(yy[2 * i + 1][tstep - 1]);
            int j = (i + 1) % numCar;
            double headway = yy[2 * j][tstep - 1] - yy[2 * i][tstep - 1];
            headway = (headway + length) % length;
            cars.get(i).setDx(headway);
        }

        t += dt;
    }

    public List<Double> getSpeedList() {
        List<Double> list = Collections.synchronizedList(new ArrayList<>());
        cars.stream().forEach(c -> list.add(c.readspeed()));
        return list;
    }

    /**
     * 車両数を変更
     *
     * @param numCar
     */
    public void changeNumCar(int numCar) {
        this.numCar = numCar;
        cars = null;
        ovinit();
    }

    public int getNumCar() {
        return numCar;
    }

    public double getLength() {
        return length;
    }

    public Car getCar(int i) {
        return cars.get(i);
    }

    public DoubleFunction<Double> getOvfunction() {
        return ovfunction;
    }

    public double getT() {
        return t;
    }

}
