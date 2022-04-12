package org.reggsoft.srfcore;

import org.reggsoft.srfcore.persistance.entity.DataSourceVirtual;

import java.util.Random;

public class DataSourceVirtualRuntime extends Thread {
    boolean stopped = false;
    DataSourceVirtual dsModel;
    double value = 0.0;

    DataSourceVirtualRuntime(DataSourceVirtual ds) {
        this.dsModel = ds;
    }

    public synchronized void stopDataSource() {
        this.stopped = true;
    }

    private synchronized boolean keepRunning() {
        return !this.stopped;
    }

    private double getRandomValue() {
        Random r = new Random();
        return r.nextDouble();
    }

    public void run() {
        while (keepRunning() && dsModel.isEnabled()) {
            value = getRandomValue();
            System.out.println(value);
            try {
                Thread.sleep(dsModel.getUpdatePeriod() * 1000L);
            } catch (InterruptedException e) {
                System.out.println("Stopped");
            }
        }
    }
}
