package reader;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by robertmeng on 10/21/15.
 *
 * @Author robertzm
 * @mailto zibo.meng@emory.edu
 * @Description
 */

public class paraReader {
    @Parameter(names = {"-i", "-indir"}, description = "Input file directory")
    public String inDir;

    @Parameter(names = {"-o", "-outdir"}, description = "Output file directory")
    public String outDir;

    @Parameter(names = {"-d", "-delimiter"}, description = "Delimiter symbol in input file")
    public String deli;

    @Parameter(names = {"-p", "-posit"}, description = "Position of the class labels")
    public int classPosi = 0;

    @Parameter(names = {"-f", "-features"}, description = "The features number")
    public int features = 43;

    @Parameter(names = {"-k"}, description = "The clusters number")
    public int clusterNum = 3;

}
