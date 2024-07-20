package org.seeding;


import org.seeding.entity.Pincode;

import java.util.Queue;

class RunnableBatch implements Runnable {

    private boolean started = true;
    private Queue<Pincode> queue;
    private int batchLimit;

    public RunnableBatch(int batchLimit, Queue<Pincode> queue) {
        this.batchLimit = batchLimit;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (BatchInsert batch = new BatchInsert(batchLimit)) {
            while (!queue.isEmpty() || started) {
                Pincode pincode = queue.poll();
                if (pincode == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {

                    }
                } else {
                    // this is the insertion method
                    batch.insert(pincode);
                }
            }
        }
    }

    public void stop() {
        started = false;
    }
}