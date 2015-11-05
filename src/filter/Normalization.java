package filter;

/**
 * Created by robertmeng on 11/5/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class Normalization {
    public static double norm(double val, double min, double max, double newMin, double newMax){
        return (val - min) * (newMax - newMin) / (max - min) + newMin;
    }
}
