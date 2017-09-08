/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jelastic.verticalscaling;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ruslan
 */
public class MemoryUsage implements Runnable {

    @Override
    public void run() {

        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(MemoryUsage.class.getName()).log(Level.SEVERE, null, ex);
            }
            java.lang.management.MemoryUsage mu = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();

            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss");
            
            print(ft.format(dNow) + " -> Init", mu.getInit());
            print("Used", mu.getUsed());
            print("Committed", mu.getCommitted());
            print("Max", mu.getMax());

            System.out.println();
        }

    }

    private void print(String name, double value) {
        System.out.print(name + ": " + Math.round(value / 1024 / 1024) + "M ");
    }
}
