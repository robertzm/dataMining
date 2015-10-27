package cluster;

import util.instance;
import util.instances;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class KMeans extends clustering {
    public int K;
    public ArrayList<cluster> centers;
    public instances insts;
    public int itera;


    public KMeans (int k, instances insts) {
        super();
        this.setCluster("KMeans");
        this.K = k;
        this.centers = new ArrayList<>();
        this.itera = 0;
        this.insts = insts;

        Random rand = new Random();

        ArrayList<Integer> randIndex = new ArrayList<>();
        for (int i = 0; i < this.K; i++) {
            int tmp = rand.nextInt(this.insts.numInsts);
            while (randIndex.contains(tmp)) {
                tmp = rand.nextInt(this.insts.numInsts);
            }
            randIndex.add(new Integer(tmp));
        }

        for (int i : randIndex) {
            this.centers.add(new cluster(this.insts.insts.get(i)));
        }
    }

    public void train(){
        boolean updated = true;
        while(updated){
            this.itera++;
            this.insts.trnRst.clear();
            for(cluster c: this.centers){
                c.clear();
            }
            updated = false;
            for (instance inst: this.insts.insts){
                double min = Double.MAX_VALUE;
                int minIndex = 0;
                for (int i = 0; i < this.K; i++){
                    double tmp = inst.distance(this.centers.get(i).center);
                    if(tmp < min){
                        min = tmp;
                        minIndex = i;
                    }
                }
                this.centers.get(minIndex).addPoint(inst);
                this.insts.trnRst.add(new Double(minIndex));
            }
            for (cluster c: this.centers){
                c.update();
                updated = c.isUpdated || updated;
            }
        }
    }

}

