/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jelastic.verticalscaling;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author ruslan
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        int sleep = 10;
        if (args.length > 0){
            sleep = Integer.parseInt(args[0]);
        }
        new Thread(new MemoryUsage()).start();
        while (true) {
            System.in.read();
            Load test = new Load(sleep);
            test.run();
            test = null;

            Thread.sleep(10000);
            //System.gc();
        }
    }

}
