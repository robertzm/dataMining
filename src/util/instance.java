package util;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class instance {
    public double[] inst;

    public instance (int n){
        inst = new double[n];
    }

    public double sqDist(instance center){
        double rst = 0;
        if(this.inst.length != center.inst.length){
            System.out.println("ERROR: instance length not match");
            System.exit(0);
        }
        for(int i = 0; i < this.inst.length; i ++){
            rst += Math.pow((this.inst[i] - center.inst[i]), 2);
        }
        return rst;
    }

}
