package edu.hm.util.springer.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;


public class ExecutorServiceChain {

    private List<ExecutorServiceChainElement> executorServiceList = null;

    public ExecutorServiceChain() {
    }

    public ExecutorServiceChain(ExecutorService... executorServices) {
        for (ExecutorService executorService : executorServices) {
            addExecutorService(executorService);
        }
    }

    public void addExecutorService(ExecutorService executorService) {
        if (executorServiceList == null) {
            executorServiceList = new LinkedList<ExecutorServiceChainElement>();
        }
        executorServiceList.add(new ExecutorServiceChainElement(executorService));
    }


    public void initiateShutdown() {
        executorServiceList = Collections.unmodifiableList(executorServiceList);

        if (executorServiceList != null && !executorServiceList.isEmpty()) {
            executorServiceList.get(0).initShutdown();
        }
    }

    private class ExecutorServiceChainElement {
        private ExecutorService executorService;

        private ExecutorServiceChainElement getNext() {
            int index = executorServiceList.indexOf(this);
            if (index < executorServiceList.size() - 1) {
                return executorServiceList.get(index + 1);
            }
            return null;
        }

        private ExecutorServiceChainElement(ExecutorService executorService) {
            this.executorService = executorService;
        }

        private void initShutdown() {
            executorService.execute(new Runnable() {
                public void run() {
                    executorService.shutdown();
                    ExecutorServiceChainElement next = getNext();
                    if (next != null) {
                        initShutdown();
                    }
                }
            });
        }
    }


}
