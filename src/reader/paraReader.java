package reader;

import com.beust.jcommander.DynamicParameter;
import com.beust.jcommander.IStringConverter;
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

    @Parameter(names = {"-k"}, description = "The clusters number")
    public int clusterNum = 3;

    @Parameter(names = {"-sr"}, description = "The clusters number range start point")
    public int startRange = 3;

    @Parameter(names = {"-er"}, description = "The clusters number range end point")
    public int endRange = 3;

    @Parameter(names = {"-iter"}, description = "The iteration for each clusters number")
    public int iter = 100;

    @Parameter(names = {"-m"}, description = "The maximum iteration to find centroid")
    public int maxIter = 100;

    @Parameter(names = {"-n"}, description = "Normalization or not, 0: non-normalization, 1: normalization")
    public int norm = 0;
}


