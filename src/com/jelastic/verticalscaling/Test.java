package com.jelastic.verticalscaling;

import java.io.IOException;

/**
 *
 * @author ruslan
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Press <Enter> to start the load test");
        int sleep = 10;
        if (args.length > 0){
            sleep = Integer.parseInt(args[0]);
        }
        boolean gc = false; 
        if (args.length > 1){
            gc = Boolean.parseBoolean(args[1]);
        }
        int nGc = 1; 
        if (args.length > 2){
            nGc = Integer.parseInt(args[2]);
        }
        new Thread(new MemoryUsage()).start();
        while (true) {
            int n = System.in.read();
            if (n > -1){
                Load test = new Load(sleep);
                test.run();
                test = null;
            }

            Thread.sleep(10000);
            if (gc) 
                for (int i = 0; i < nGc; i++) {
                    System.out.println("Calling GC...");
                    System.gc();
                }
        }
    }

}
