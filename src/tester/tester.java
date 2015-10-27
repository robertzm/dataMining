package tester;

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
        String[] argv1 = {"-i", "dat/baskball.arff"};
        new JCommander(jct, argv1);

        instances insts = new instances();

        arffReader rdr = new arffReader(jct.inDir, insts);
        rdr.readTrnInst();
        rdr.close();

    }
}
