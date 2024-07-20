package org.seeding;


import org.seeding.entity.Pincode;

/**
 * pool of thread each will have connection and batch(size/data) to insert
 * using one queue to insert to push data from file,
 * each thread will take a value and add it to the batch(sql batch execution)
 */

class BatchInsert implements AutoCloseable {

    private int batchSize = 0;
    private final int batchLimit;

    public BatchInsert(int batchLimit) {
        this.batchLimit = batchLimit;
    }

    public void insert(Pincode pincode) {
        if (++batchSize >= batchLimit) {
            sendBatch();
        }
    }

    public void sendBatch() {
        System.out.format("Send batch with %d records%n", batchSize);
        batchSize = 0;
    }

    @Override
    public void close() {
        if (batchSize != 0) {
            sendBatch();
        }
    }
}