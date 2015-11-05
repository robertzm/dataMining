package util;

import filter.Normalization;

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

    public double sqDist(instance center, instances insts, boolean norm){
        double rst = 0;
        if(this.inst.length != center.inst.length){
            System.out.println("ERROR: instance length not match");
            System.exit(0);
        }
        for(int i = 0; i < this.inst.length; i ++){
            if (insts.getFeatures().get(i).isNumeric())
                if (!norm)
                    rst += Math.pow((this.inst[i] - center.inst[i]), 2);
                else
                    rst += Math.pow((Normalization.norm(this.inst[i], insts.getFeatures().get(i).getMinValue(), insts.getFeatures().get(i).getMaxValue(), 0, 1)
                            - Normalization.norm(center.inst[i], insts.getFeatures().get(i).getMinValue(), insts.getFeatures().get(i).getMaxValue(), 0, 1)), 2);
            else rst += this.inst[i] == center.inst[i]? 0: 1;
        }
        return rst;
    }

}
