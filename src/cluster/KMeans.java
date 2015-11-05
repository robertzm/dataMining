package cluster;

import reader.writer;
import util.instance;
import util.instances;

import java.io.IOException;
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
    public double sqDistSum;


    public KMeans (int k, instances insts) {
        super();
        this.setCluster("KMeans");
        this.K = k;
        this.centers = new ArrayList<>();
        this.itera = 0;
        this.insts = insts;
        this.sqDistSum = 0;

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
            this.sqDistSum = 0;
            for(cluster c: this.centers){
                c.clear();
            }
            updated = false;
            for (instance inst: this.insts.insts){
                double min = Double.MAX_VALUE;
                int minIndex = 0;
                for (int i = 0; i < this.K; i++){
                    double tmp = inst.sqDist(this.centers.get(i).center);
                    if(tmp < min){
                        min = tmp;
                        minIndex = i;
                    }
                }
                this.centers.get(minIndex).addPoint(inst);
                this.insts.trnRst.add(new Double(minIndex));
                this.sqDistSum += min;
            }
            for (cluster c: this.centers){
                c.update();
                updated = c.isUpdated || updated;
            }
        }
    }


    public void output (writer wtr) throws IOException {
        for (int i = 0; i < this.centers.size(); i++){
            wtr.writer.write("The " + i + "th centroid:" + "\n");
            for (int j = 0; j < this.centers.get(i).center.inst.length; j++){
                if (this.insts.getFeatures().get(i).isNumeric())
                    wtr.writer.write("10.3%f");
            }
        }


    }

}

