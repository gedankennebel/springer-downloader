package edu.hm.util.springer;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProcessorImpl.class)
public interface Processor {

    void processBatchDownload(String... isbns);

}
