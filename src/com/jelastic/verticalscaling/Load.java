package com.jelastic.verticalscaling;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ruslan
 */
public class Load {

    private int sleep = 0;
    private int longSleep = 60000;
    private int numCycles = 10;
    private int mode = 1;

    public Load(int sleep) {
        this.sleep = sleep;
    }

    public Load(int sleep, int mode) {
        this(sleep);
        this.mode = mode;
    }

    public void run() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        try {
            switch (mode) {
                case 1:
                    simple();
                    break;
                case 2:
                    gil();
                    break;
                default:
                    System.out.println("Unknown load mode: " + mode);
            }

        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        System.out.println("total time = " + (System.currentTimeMillis() - start) + ", ms");
        System.out.println("done");

    }

    private void simple() throws InterruptedException, OutOfMemoryError {
        int n = 1 * 1024 * 512 / 2;
        List list = new LinkedList();
        for (int i = 0; i < n; i++) {
            String[] str = new String[1024];
            list.add(str);
            if (i % 100 == 0) {
                //System.out.println("i=" + i);
                Thread.sleep(sleep);
            }
        }
    }

    private static volatile Object foo[] = new Object[20];

    private void gil() throws InterruptedException, OutOfMemoryError {
        int ratio = 20;
        for (int k = 0; k < numCycles; k++) {
            int n = 1 * 1024 * 512 / 3 ;
            List list = new LinkedList();
            for (int i = 0; i < n; i++) {
                String[] str = new String[1024];
                list.add(str);
                for (int r = 0; r < ratio; r++) {
                    foo[r] = new String[1024];
                }
                if (i % 100 == 0) {
                    Thread.sleep(sleep);
                }
            }
            System.out.println("Load is finished. Sleeping...");
            Thread.sleep(longSleep);
            list.clear();
            System.out.println("Calling list.clear(). Sleeping...");
            Thread.sleep(longSleep);
            System.gc();
            System.out.println("Calling System.gc(). Sleeping...");
            Thread.sleep(longSleep);
        }
    }

}
