package cc.nptr.logunspam.filter;

import static cc.nptr.logunspam.util.Utils.shouldLog;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Covers the plain {@link System#out} sink, which several mods still write to.
 */
public class SOutFilter extends PrintStream {

    public SOutFilter(OutputStream out) {
        super(out, true);
    }

    @Override
    public void println(String s) {
        if (shouldLog(s)) {
            super.println(s);
        }
    }

    public static void apply() {
        System.setOut(new SOutFilter(System.out));
    }
}
