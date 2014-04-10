package test

import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.JobListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TestJobListener implements JobListener {
    private static final Logger LOG = LoggerFactory.getLogger(TestJobListener)

    String getName() {
        this.class.name
    }

    void jobToBeExecuted(JobExecutionContext context) {
        LOG.info("jobToBeExecuted ${context.getTrigger().getKey()} ${new Date()}")
    }

    void jobExecutionVetoed(JobExecutionContext context) {
        LOG.info("jobExecutionVetoed ${context.getTrigger().getKey()} ${new Date()}")
    }

    void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        LOG.info("jobWasExecuted ${context.getTrigger().getKey()} ${new Date()}")
    }

}
