package cluster;

import util.instance;
import util.instances;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class cluster {
    public instance center;
    public int nNum;
    public double[] groupTotal;
    public boolean isUpdated;
    private HashMap<Double, Integer> hm;

    public cluster(instance c){
        this.center = new instance(c.inst.length);
        for(int i = 0; i < c.inst.length; i ++){
            this.center.inst[i] = c.inst[i];
        }
        this.nNum = 0;
        this.groupTotal = new double[c.inst.length];
        Arrays.fill(this.groupTotal, 0);
        this.isUpdated = true;
        hm = new HashMap<>();
    }

    public void clear(){
        this.nNum = 0;
        Arrays.fill(groupTotal, 0);
        this.isUpdated = false;
        this.hm.clear();
    }

    public void addPoint(instance inst, instances insts){
        this.nNum++;
        for(int i = 0; i < groupTotal.length; i++){
            if(insts.getFeatures().get(i).isNumeric())
                groupTotal[i] += inst.inst[i];
            else {
                this.hm.merge(inst.inst[i], 1, Integer::sum);
            }
        }
    }

    public void update(instances insts){
        instance cTmp = new instance(this.groupTotal.length);
        for (int i = 0; i < groupTotal.length; i++){
            if (insts.getFeatures().get(i).isNumeric()) {
                cTmp.inst[i] = this.groupTotal[i] / this.nNum;
                this.isUpdated = (cTmp.inst[i] != this.center.inst[i]) ? true : this.isUpdated;
            }else {
                int tmpCtr = Integer.MIN_VALUE;
                Double tmpFeature = 0.0;
                for (Double d: insts.getFeatures().get(i).getNominalFeatureSet()){
                    if (this.hm.containsKey(d) && this.hm.get(new Double(d)) > tmpCtr){
                        tmpCtr = this.hm.get(new Double(d));
                        tmpFeature = d;
                    }
                }
                cTmp.inst[i] = tmpFeature;
            }
        }
        this.center = cTmp;
    }

}
