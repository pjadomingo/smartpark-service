package com.hitachi.smartpark_service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

@SpringBootTest
class SmartParkServiceApplicationTests {

	@Autowired
	private ScheduledAnnotationBeanPostProcessor schedulerProcessor;

	@Test
	void schedulingIsEnabled() {
		assertThat(schedulerProcessor).isNotNull();
	}

	@Test
	void contextLoads() {
		// No code needed.
		// If SmartParkServiceApplication fails to start, this test fails.
	}
}
