package util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class instances {
    public HashMap<String, Double> feature2Double;        // nominal feature mapping and back to integer
    public HashMap<Double, String> double2Feature;

    private int numFeatures;
    private int classPosi;
    private String des;

    private ArrayList<feature> features;

    public ArrayList<instance> insts;
    public ArrayList<Double> trnRst;

    public int numInsts;

    public instances(){
        this.feature2Double = new HashMap<>();
        this.double2Feature = new HashMap<>();
        this.numFeatures = 0;
        this.classPosi = -1;
        this.numInsts = 0;
        this.des = "";
        this.features = new ArrayList<>();
        this.insts = new ArrayList<>();
        this.trnRst = new ArrayList<>();
    }

    public boolean isAllNumeric(){
        for(feature f: this.features){
            if(f.isNominal()) return false;
        }
        return true;
    }

    public void addFeatures(featureType ft, String des){
        this.features.add(new feature(this.numFeatures, featureType.NUMERICAL, des));
        this.numFeatures++;
    }

    public void addFeatures(featureType ft, String des, String c){
        this.features.add(new feature(this.numFeatures, featureType.NOMINAL, des));
        c = c.replace(" ", "");
        c = c.substring(1, c.length()-1);

        String[] stmp = c.split(",");
        double indicator = (double) feature2Double.size();
        for(String s: stmp){
            String tmp = this.numFeatures+":"+s;
            if(!feature2Double.containsKey(tmp)){
                feature2Double.put(tmp, indicator);
                double2Feature.put(indicator, s);
                this.features.get(this.numFeatures).addNominalFeature(indicator);
                indicator ++;
            }
        }
        this.numFeatures++;
    }

    public boolean isNominal(int i){
        return this.features.get(i).isNominal();
    }

    public boolean isNumeric(int i){
        return this.features.get(i).isNumeric();
    }

    public int getClassPosi() {
        return classPosi;
    }

    public void setClassPosi(int classPosi) {
        this.classPosi = classPosi;
    }

    public int getNumFeatures() {
        return numFeatures;
    }

    public ArrayList<feature> getFeatures() {
        return features;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
