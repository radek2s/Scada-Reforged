package org.reggsoft.srfcore;

public class CycleLogger extends Thread {

    int iteration = 0;
    boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }

    private synchronized boolean keepRunning() {
        return this.doStop == false;
    }

    public void run() {
        while (keepRunning()) {
            System.out.println(iteration + "#: Running");
            iteration++;
            if(iteration == 25) {
                this.doStop();
            }
            try {
                Thread.sleep(10L * 1000L);
            } catch (InterruptedException e) {
                System.out.println();
            }
        }
    }




}
