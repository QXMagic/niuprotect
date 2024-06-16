package com.niuniu.babyprotect.tools;
public class KalmanFilter {
    private static final double Q = 1.0E-5d;
    private static final double R = 0.01d;
    private double Gauss;
    private double current;
    private double estimate;
    private double kalmanGain;
    private double mdelt;
    private double pdelt;
    private double predict;

    public void initial() {
        this.pdelt = 4.0d;
        this.mdelt = 3.0d;
    }

    public double KalmanFilter(double oldValue, double value) {
        this.predict = oldValue;
        this.current = value;
        double d = this.pdelt;
        double d2 = this.mdelt;
        double sqrt = Math.sqrt((d * d) + (d2 * d2)) + 1.0E-5d;
        this.Gauss = sqrt;
        double d3 = this.pdelt;
        double sqrt2 = Math.sqrt((sqrt * sqrt) / ((sqrt * sqrt) + (d3 * d3))) + R;
        this.kalmanGain = sqrt2;
        double d4 = this.current;
        double d5 = this.predict;
        this.estimate = ((d4 - d5) * sqrt2) + d5;
        double d6 = 1.0d - sqrt2;
        double d7 = this.Gauss;
        this.mdelt = Math.sqrt(d6 * d7 * d7);
        return this.estimate;
    }
}
