package util;

import java.util.ArrayList;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class feature {
    private int index;
    private featureType featureType;
    private ArrayList<Double> nominalFeatureSet;
    private String des;
    private double minValue;
    private double maxValue;

    public feature(int ind, featureType ft, String des){
        this.index = ind;
        this.featureType = ft;
        this.nominalFeatureSet = new ArrayList<>();
        this.des = des;
        this.minValue = Double.MAX_VALUE;
        this.maxValue = Double.MIN_VALUE;
    }

    public feature(int ind, featureType ft){
        this.index = ind;
        this.featureType = ft;
        this.nominalFeatureSet = new ArrayList<>();
        this.des = "";
        this.minValue = Double.MAX_VALUE;
        this.maxValue = Double.MIN_VALUE;
    }

    public ArrayList<Double> getNominalFeatureSet(){
        return this.nominalFeatureSet;
    }

    public void addNominalFeature(double f){
        this.nominalFeatureSet.add(f);
    }

    public boolean isNominal(){
        return this.featureType == featureType.NOMINAL;
    }

    public boolean isNumeric(){
        return this.featureType == featureType.NUMERICAL;
    }

    public void setFeatureType(util.featureType featureType) {
        this.featureType = featureType;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
