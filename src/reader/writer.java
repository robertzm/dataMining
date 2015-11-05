package reader;

import java.io.*;

/**
 * Created by robertmeng on 11/4/15.
 *
 * @Author robert Z. Meng
 * @mailto zibo.meng@emory.edu
 * @Description
 */
public class writer {
    public BufferedWriter writer;
    private String format = "utf-8";

    public writer(String dir) throws IOException {
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir), this.format));
    }

    public writer(String dir, String format) throws IOException {
        setFormat(format);
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir), this.format));
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
