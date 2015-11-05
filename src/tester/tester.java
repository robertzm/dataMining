package tester;

import cluster.KMeans;
import com.beust.jcommander.JCommander;
import reader.arffReader;
import reader.paraReader;
import util.instances;

import java.io.*;

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
        String[] argv1 = {"-i", "dat/baskball.arff", "-o", "output.txt", "-sr", "2", "-er", "20", "-iter", "100", "-n", "0"};
        //String[] argv1 = {"-i", "dat/cloud.arff", "-o", "output.txt", "-sr", "2", "-er", "10", "-iter", "100", "-n", "false"};
        new JCommander(jct, argv1);

        instances insts = new instances();

        arffReader rdr = new arffReader(jct.inDir, insts);
        rdr.readTrnInst();
        rdr.close();

        Writer output =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jct.outDir), "utf-8"));

        int start = jct.startRange;

        while( start < jct.endRange) {
            int iteration = jct.iter;
            KMeans bestKM = new KMeans(start, insts, jct.norm == 1);
            bestKM.SSE = Double.MAX_VALUE;

            while(iteration-- > 0) {
                KMeans km = new KMeans(start, insts, jct.norm == 1);
                km.train();
                if(km.SSE < bestKM.SSE){
                    bestKM = km;
                }
            }
            bestKM.printInfo();
            bestKM.output(output);
            start ++;
        }

        output.close();

    }
}
