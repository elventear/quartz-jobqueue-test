package test

import org.quartz.DateBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SchedulerFactory
import org.quartz.Trigger
import org.quartz.impl.StdSchedulerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.quartz.DateBuilder.futureDate
import static org.quartz.JobBuilder.newJob
import static org.quartz.TriggerBuilder.newTrigger

class ThreadQueueTest {
    private final static Properties schedulerProperties = [
            'org.quartz.threadPool.threadCount': '5',
            'org.quartz.threadPool.class': 'org.quartz.simpl.SimpleThreadPool',
            'org.quartz.jobStore.class': 'org.quartz.simpl.RAMJobStore',
            'org.quartz.triggerListener.NAME.class': 'test.TestTriggerListener',
            'org.quartz.jobListener.NAME.class': 'test.TestJobListener'
    ]

    void run() {
        Logger log = LoggerFactory.getLogger(ThreadQueueTest)

        log.info("------- Initializing ----------------------")

        SchedulerFactory sf = new StdSchedulerFactory(schedulerProperties)
        Scheduler sched = sf.getScheduler()

        log.info("------- Initialization Complete -----------")

        log.info("------- Scheduling Jobs -------------------")

        JobDetail job = newJob(TriggerEchoJob).withIdentity("TriggerEchoJob").build()

        Date startTime = futureDate(5, DateBuilder.IntervalUnit.SECOND)

        log.info("Future date trigger ${startTime}")

        Trigger trigger1 = newTrigger().withIdentity("Trigger1").startAt(startTime).forJob(job).build()
        sched.scheduleJob(job, trigger1)


        (2..10).each {
            Trigger trigger = newTrigger().withIdentity("Trigger${it}").startAt(startTime).forJob(job).build()
            sched.scheduleJob(trigger)
        }

        // Start up the scheduler (nothing can actually run until the
        // scheduler has been started)
        sched.start();
        log.info("------- Started Scheduler -----------------");

        // wait long enough so that the scheduler as an opportunity to
        // fire the triggers
        log.info("------- Waiting 30 seconds... -------------");
        try {
            Thread.sleep(60L * 1000L);
            // executing...
        } catch (Exception e) {
            //
        }

        // shut down the scheduler
        log.info("------- Shutting Down ---------------------");
        sched.shutdown(true);
        log.info("------- Shutdown Complete -----------------");

    }
    public static void main(String[] args) throws Exception {
        new ThreadQueueTest().run()
    }
}
