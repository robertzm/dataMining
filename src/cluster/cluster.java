package cluster;

import reader.writer;
import util.instance;
import util.instances;

import java.io.IOException;
import java.util.Arrays;

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

    public cluster(instance c){
        this.center = new instance(c.inst.length);
        for(int i = 0; i < c.inst.length; i ++){
            this.center.inst[i] = c.inst[i];
        }
        this.nNum = 0;
        this.groupTotal = new double[c.inst.length];
        this.isUpdated = true;
    }

    public void clear(){
        this.nNum = 0;
        Arrays.fill(groupTotal, 0);
        this.isUpdated = false;
    }

    public void addPoint(instance inst){
        this.nNum++;
        for(int i = 0; i < groupTotal.length; i++){
            groupTotal[i] += inst.inst[i];
        }
    }

    public void update(){
        instance cTmp = new instance(this.groupTotal.length);
        for (int i = 0; i < groupTotal.length; i++){
            cTmp.inst[i] = this.groupTotal[i] / this.nNum;
            this.isUpdated = (cTmp.inst[i] != this.center.inst[i])? true: this.isUpdated;
        }
        this.center = cTmp;
    }

}
