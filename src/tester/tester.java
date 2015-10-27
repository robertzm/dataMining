package tester;

import cluster.KMeans;
import com.beust.jcommander.JCommander;
import reader.arffReader;
import reader.paraReader;
import util.instances;

import java.io.IOException;

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
        String[] argv1 = {"-i", "dat/baskball.arff", "-k", "2"};
        new JCommander(jct, argv1);

        instances insts = new instances();

        arffReader rdr = new arffReader(jct.inDir, insts);
        rdr.readTrnInst();
        rdr.close();

        if(insts.isAllNumeric()){
            KMeans km = new KMeans(jct.clusterNum, insts);
            km.train();
        }else{
            System.out.println("ERROR: not all features numerical");
            System.exit(0);
        }
    }
}
