package com.nullpinter.logUnSpam.filter;


import java.io.OutputStream;
import java.io.PrintStream;

import static com.nullpinter.logUnSpam.util.Utils.shouldLog;

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
