package cluster;

import filter.Normalization;
import util.instance;
import util.instances;

import java.io.IOException;
import java.io.Writer;
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
    public double SSE;
    public double BSS;
    public boolean norm = false;

    public KMeans (int k, instances insts, boolean norm) {
        super();
        this.setCluster("KMeans");
        this.K = k;
        this.centers = new ArrayList<>();
        this.itera = 0;
        this.insts = insts;
        this.SSE = 0;
        this.BSS = 0;
        this.norm = norm;

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
            this.SSE = 0;
            for(cluster c: this.centers){
                c.clear();
            }
            updated = false;
            for (instance inst: this.insts.insts){
                double min = Double.MAX_VALUE;
                int minIndex = 0;
                for (int i = 0; i < this.K; i++){
                    double tmp = inst.sqDist(this.centers.get(i).center, this.insts, this.norm);
                    if(tmp < min){
                        min = tmp;
                        minIndex = i;
                    }
                }
                this.centers.get(minIndex).addPoint(inst, this.insts);
                this.insts.trnRst.add(new Double(minIndex));
                this.SSE += min;
            }
            for (cluster c: this.centers){
                c.update(this.insts);
                updated = c.isUpdated || updated;
            }
        }
        updateBSS();
    }

    private void updateBSS(){
        for (int i = 0; i < this.centers.size(); i++)
            for (int j = i + 1; j < this.centers.size(); j++)
                for (int k = 0; k < this.insts.getFeatures().size(); k++) {
                    if (this.insts.getFeatures().get(k).isNumeric()) {
                        if (!this.norm) {
                            double tmp = (this.centers.get(i).center.inst[k] - this.centers.get(j).center.inst[k]);
                            this.BSS += tmp * tmp;
                        }else {
                            double tmp = (Normalization.norm(this.centers.get(i).center.inst[k], this.insts.getFeatures().get(k).getMinValue(), this.insts.getFeatures().get(k).getMaxValue(), 0, 1) -
                                    Normalization.norm(this.centers.get(j).center.inst[k], this.insts.getFeatures().get(k).getMinValue(), this.insts.getFeatures().get(k).getMaxValue(), 0, 1));
                            this.BSS += tmp * tmp;
                        }
                    }else{
                        this.BSS += 1;
                    }
                }
    }

    public void printInfo () {
        System.out.println("The best " + this.centers.size() + " clusters: (SSE = " + String.format("%10.3f", this.SSE) + "\tBSS = " + String.format("%10.3f", this.BSS) + " )");
        for (int i = 0; i < this.centers.size(); i++){
            System.out.print("The " + i + "th cluster:" + "\n");
            System.out.print("Final points number in this cluster: " + this.centers.get(i).nNum + "(" + String.format("%10.2f", (double)this.centers.get(i).nNum / this.insts.numInsts * 100) + "%)\n");
            if (this.insts.getClassPosi() != -1){
                if (this.insts.getFeatures().get(this.insts.getClassPosi()).isNumeric())
                    System.out.print("The class in this cluster:" + String.format("%10.2f", this.centers.get(i).center.inst[this.insts.getClassPosi()]) + "\n");
                else {
                    System.out.println(String.format("%10s", "class") + String.format("%10s", "number"));
                    for (Double d: this.insts.getFeatures().get(this.insts.getClassPosi()).getNominalFeatureSet()){
                        System.out.println(String.format("%10s", this.insts.double2Feature.get(new Double(d))) + String.format("%10d", (this.centers.get(i).hm.containsKey(new Double(d)))? this.centers.get(i).hm.get(new Double(d)) : 0));
                    }
                }
            }
            System.out.print("Centroid:" + "\n");
            for (int j = 0; j < this.centers.get(i).center.inst.length; j++){
                if (this.insts.getFeatures().get(j).isNumeric())
                    System.out.print(String.format("%10.3f", this.centers.get(i).center.inst[j]));
                else if(this.insts.getFeatures().get(j).isNominal())
                    System.out.print(String.format("%20s", this.insts.double2Feature.get(this.centers.get(i).center.inst[j])));
                else System.out.print("Error");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }


    public void output (Writer wtr) throws IOException {
        wtr.write("The best " + this.centers.size() + " clusters: (SSE = " + String.format("%10.3f", this.SSE) + "\tBSS = " + String.format("%10.3f", this.BSS) + " ) \n");
        for (int i = 0; i < this.centers.size(); i++){
            wtr.write("The " + i + "th cluster:" + "\n");
            wtr.write("Final points number in this cluster: " + this.centers.get(i).nNum + "(" + String.format("%10.2f", (double)this.centers.get(i).nNum / this.insts.numInsts * 100) + "%)\n");
            if (this.insts.getClassPosi() != -1) {
                if (this.insts.getFeatures().get(this.insts.getClassPosi()).isNumeric()){
                    wtr.write("The class in this cluster:" + String.format("%10.2f", this.centers.get(i).center.inst[this.insts.getClassPosi()]) + "\n");
                    wtr.write("\n");
                }else {
                    wtr.write(String.format("%10s", "class") + String.format("%10s", "number"));
                    wtr.write("\n");
                    for (Double d: this.insts.getFeatures().get(this.insts.getClassPosi()).getNominalFeatureSet()){
                        wtr.write(String.format("%10s", this.insts.double2Feature.get(new Double(d))) + String.format("%10d", (this.centers.get(i).hm.containsKey(new Double(d)))? this.centers.get(i).hm.get(new Double(d)) : 0));
                        wtr.write("\n");
                    }
                }
            }

            wtr.write("Centroid:" + "\n");
            for (int j = 0; j < this.centers.get(i).center.inst.length; j++){
                if (this.insts.getFeatures().get(j).isNumeric())
                    wtr.write(String.format("%10.3f", this.centers.get(i).center.inst[j]));
                else if(this.insts.getFeatures().get(j).isNominal())
                    wtr.write(String.format("%20s", this.insts.double2Feature.get(this.centers.get(i).center.inst[j])));
                else System.out.print("Error");
            }
            wtr.write("\n");
        }
        wtr.write("\n");
    }

}

