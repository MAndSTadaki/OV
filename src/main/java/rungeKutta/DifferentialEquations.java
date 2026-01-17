package rungeKutta;

/**
 * 連立微分方程式
 * @author tadaki
 */
@FunctionalInterface
public interface DifferentialEquations {

    /**
     *
     * @param t 独立変数
     * @param y 従属変数
     * @return 微分方程式の右辺の値
     */
    public double[] apply(double t, double y[]);
}
