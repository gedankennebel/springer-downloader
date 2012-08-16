package edu.hm.util.springer.util;


public abstract class AbstractRunnableWithRetry implements Runnable {

    private int maxRetries;

    protected AbstractRunnableWithRetry(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    @Override
    public final void run() {

        int retries;
        for (retries = 0; retry(retries); retries++) {
            if (retries > 0) {
                onRetry(retries);
            }
            runInternal();
        }

        if (shouldRetry()) {
            onFailure(retries);
        }
    }

    private boolean retry(int retries) {
        return shouldRetry() && retries <= maxRetries;
    }

    protected abstract void runInternal();

    protected abstract boolean shouldRetry();

    protected void onFailure(int retries) {
    }

    protected void onRetry(int retries) {
    }
}
