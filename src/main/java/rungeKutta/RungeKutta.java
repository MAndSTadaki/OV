package rungeKutta;

/**
 * 4次のRunge Kutta法
 *
 * @author tadaki
 */
public class RungeKutta {

    /**
     * t から t + h へ、一ステップ進める
     *
     * @param t 独立変数の初期値
     * @param y 従属変数の初期値
     * @param h 独立変数の変化
     * @param eq 微分方程式のクラス
     * @return t+hにおける従属変数の値
     */
    public static double[] rk4(
            double t, double y[], double h, DifferentialEquations eq) {
        int n = y.length;
        double hh = h / 2.;
        double h6 = h / 6.;
        double k1[] = eq.apply(t, y);
        double xh = t + hh;
        double yt[] = new double[n];
        for (int i = 0; i < n; i++) {  yt[i] = y[i] + hh * k1[i];  }
        double k2[] = eq.apply(xh, yt);
        for (int i = 0; i < n; i++) {  yt[i] = y[i] + hh * k2[i];  }
        double k3[] = eq.apply(xh, yt);
        for (int i = 0; i < n; i++) {  yt[i] = y[i] + h * k3[i]; }
        double k4[] = eq.apply(t + h, yt);
        double yy[] = new double[n];
        for (int i = 0; i < n; i++) {
            yy[i] = y[i] + h6 * (k1[i] + 2. * k2[i] + 2. * k3[i] + k4[i]);
        }
        return yy;
    }

    /**
     * t1からt2までnstepで進める
     *
     * @param vstart 従属変数の初期値
     * @param t1 独立変数の初期値
     * @param t2 独立変数の終端値
     * @param nstep t1からt2への刻み数
     * @param eq 微分方程式のクラス
     * @return t2における従属変数の値
     */
    public static double[][] rkdumb(
            double vstart[], double t1, double t2, int nstep,
            DifferentialEquations eq) {
        int n = vstart.length;
        double y[][] = new double[n][nstep];
        double v[] = new double[n];
        for (int i = 0; i < n; i++) {
            v[i] = vstart[i];  y[i][0] = v[i];
        }

        double t = t1;
        double h = (t2 - t1) / nstep;
        if ((t + h) == t) {
            System.err.println("too small step");  return y;
        }
        for (int k = 1; k < nstep; k++) {
            double vout[] = rk4(t, v, h, eq);
            t += h;
            for (int i = 0; i < n; i++) {
                v[i] = vout[i];  y[i][k] = v[i];
            }
        }
        return y;
    }
    
    private RungeKutta(){}
}
