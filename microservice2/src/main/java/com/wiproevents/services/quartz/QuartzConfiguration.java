package com.wiproevents.services.quartz;

import org.quartz.SimpleTrigger;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

import java.util.Properties;

/**
 * Created by wangjinggang on 2018/2/18.
 */
@Configuration
public class QuartzConfiguration {
    @Value("${email.check.interval}")
    private int emailCheckInterval;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {

        QuartzJobFactory sampleJobFactory = new QuartzJobFactory();
        sampleJobFactory.setApplicationContext(applicationContext);
        return sampleJobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory(applicationContext));
        Properties quartzProperties = new Properties();
        factory.setQuartzProperties(quartzProperties);
        factory.setTriggers(emailJobTrigger().getObject());
        return factory;
    }

    @Bean(name = "emailJobTrigger")
    public SimpleTriggerFactoryBean emailJobTrigger() {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(emailJobDetails().getObject());
        factoryBean.setStartDelay(0);
        factoryBean.setRepeatInterval(emailCheckInterval);
        factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
        return factoryBean;
    }

    @Bean(name = "emailJobDetails")
    public JobDetailFactoryBean emailJobDetails() {

        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(EmailJob.class);
        jobDetailFactoryBean.setDescription("I am testing");
        jobDetailFactoryBean.setDurability(true);
        jobDetailFactoryBean.setName("the name");

        return jobDetailFactoryBean;
    }

}
