package reader;

import util.featureType;
import util.instance;
import util.instances;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class arffReader extends BufferedReader {
    public String dir;
    public String delimiter;
    public instances insts;

    public arffReader(String dir,
                      String deli,
                      instances insts) throws FileNotFoundException {
        super(new FileReader(dir));
        this.dir = dir;
        this.delimiter = deli;
        this.insts = insts;
    }

    public arffReader(String dir,
                      instances insts) throws FileNotFoundException {
        super(new FileReader(dir));
        this.dir = dir;
        this.delimiter = ",";
        this.insts = insts;
    }

    public void readTrnInst() throws IOException {
        readArffHeader();
        this.insts.insts = readInst();

    }

    private String skip(String line) throws IOException {
        while(line.isEmpty() || line.charAt(0) == '%') {
            line = readLine();
        }
        return line;
    }

    private void readArffHeader() throws IOException {
        String line;
        int featureIndex = 0;

        line = skip(readLine());
        if(line.startsWith("@relation ")) {
            this.insts.setDes(line.substring(10));
        }else {
            System.out.println("ERROR, @relation expected");
            System.exit(0);
        }
        while ((line = skip(readLine())) != null && line.startsWith("@attribute ")) {
            line = line.substring(11);
            String[] stmp = line.split(" ",2);
            if(stmp[0].equals("class")) {
                this.insts.setClassPosi(featureIndex);
            }
            if (line.contains("{")) {
                this.insts.addFeatures(featureType.NOMINAL, stmp[0], stmp[1]);
            }else {
                this.insts.addFeatures(featureType.NUMERICAL, stmp[0]);
            }
            featureIndex++;
        }
        if(!line.startsWith("@data")){
            System.out.println("ERROR, @data expected");
            System.exit(0);
        }
    }

    private ArrayList<instance> readInst() throws IOException {
        String line;
        ArrayList<instance> rst = new ArrayList<>();
        double indicator = (double) this.insts.feature2Double.size();

        while((line = readLine()) != null){
            if(line.isEmpty()) continue;
            String[] items = line.split(delimiter);

            int i = 0;
            instance instTemp = new instance(this.insts.getNumFeatures());
            for (String item : items) {
                if(this.insts.isNumeric(i)){
                    instTemp.inst[i] = Double.parseDouble(item);
                    if (instTemp.inst[i] < this.insts.getFeatures().get(i).getMinValue())
                        this.insts.getFeatures().get(i).setMinValue(instTemp.inst[i]);
                    if (instTemp.inst[i] > this.insts.getFeatures().get(i).getMaxValue())
                        this.insts.getFeatures().get(i).setMaxValue(instTemp.inst[i]);
                }else if(this.insts.isNominal(i)){
                    String sTmp = i + ":" + item;
                    if(this.insts.feature2Double.containsKey(sTmp)){
                        instTemp.inst[i] = this.insts.feature2Double.get(sTmp);
                    }else{
                        this.insts.feature2Double.put(sTmp, indicator);
                        this.insts.double2Feature.put(indicator, item);
                        this.insts.getFeatures().get(i).addNominalFeature(indicator);
                        instTemp.inst[i] = indicator;
                        indicator++;
                    }
                }
                i++;
            }
            rst.add(instTemp);
            this.insts.numInsts++;
        }
        return rst;
    }
}
