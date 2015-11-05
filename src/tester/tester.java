package tester;

import cluster.KMeans;
import com.beust.jcommander.JCommander;
import reader.arffReader;
import reader.paraReader;
import reader.writer;
import util.instances;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by robertmeng on 10/27/15.
 *
 * @Author robert Z. Meng
 * @Mailto zibo.meng@emory.edu
 * @Description
 */
public class tester {
    public static void main(String[] args) throws IOException {
        paraReader jct = new paraReader();
        String[] argv1 = {"-i", "dat/baskball.arff", "-o", "output.txt", "-sr", "2", "-er", "30", "-iter", "100"};
        new JCommander(jct, argv1);

        instances insts = new instances();

        arffReader rdr = new arffReader(jct.inDir, insts);
        rdr.readTrnInst();
        rdr.close();

        writer output = new writer(jct.outDir, "utf-8");

        int bestK = 0;
        double bestSSE = Double.MAX_VALUE;
        ArrayList<double[]> bestCenters;
        int start = jct.startRange;

        if(insts.isAllNumeric()){
            while( start < jct.endRange) {
                int iteration = jct.iter;
                double tmpBestSSE = Double.MAX_VALUE;
                ArrayList<double[]> tmpBestCenters = new ArrayList<>();

                while(iteration-- > 0) {
                    KMeans km = new KMeans(start, insts);
                    km.train();
                    if(km.sqDistSum < tmpBestSSE){
                        tmpBestSSE = km.sqDistSum;
                        tmpBestCenters = new ArrayList<>();
                        for(int i = 0; i < km.centers.size(); i++) {
                            tmpBestCenters.add(km.centers.get(i).center.inst);
                        }
                    }
                }
                System.out.println(start);
                System.out.println(tmpBestSSE);

                if(tmpBestSSE < bestSSE){
                    bestSSE = tmpBestSSE;
                    bestCenters = (ArrayList<double[]>) tmpBestCenters.clone();
                    bestK = start;
                }
                start ++;
            }
        }else{
            System.out.println("ERROR: not all features numerical");
            System.exit(0);
        }
    }
}
