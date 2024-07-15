package com.example.demo.job;

import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {

	@Bean
	public JobDetailFactoryBean jobDetailFactoryBean() {
		JobDetailFactoryBean factory = new JobDetailFactoryBean();
		factory.setJobClass(DataTransferJob.class);
		factory.setDurability(true);
		return factory;
	}

	@Bean
	public SimpleTriggerFactoryBean triggerFactoryBean(JobDetail jobDetail) {
		SimpleTriggerFactoryBean factory = new SimpleTriggerFactoryBean();
		factory.setJobDetail(jobDetail);
		factory.setRepeatInterval(60000); // every 60 seconds
		factory.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
		return factory;
	}

}
