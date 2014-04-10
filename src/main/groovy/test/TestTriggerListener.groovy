package test

import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class TestTriggerListener implements TriggerListener {
    private static final Logger LOG = LoggerFactory.getLogger(TestTriggerListener)


    String getName() {
        this.class.name
    }

    void triggerFired(Trigger trigger, JobExecutionContext context) {
        LOG.info("triggerFired ${trigger.key} ${new Date()}")

    }

    boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        LOG.info("vetoJobExecution  ${trigger.key} ${new Date()}")

    }

    void triggerMisfired(Trigger trigger) {
        LOG.info("triggerMisfired  ${trigger.key} ${new Date()}")

    }

    void triggerComplete(Trigger trigger, JobExecutionContext context,
                         Trigger.CompletedExecutionInstruction triggerInstructionCode)  {
        LOG.info("triggerComplete ${trigger.key} ${new Date()}")
    }
}
