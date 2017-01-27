package com.fsfn.springbatch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringBatchApplication.class, locations = { "classpath:test-context.xml" })
@SpringBootTest
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, StepScopeTestExecutionListener.class })
public class SpringBatchApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(SpringBatchApplicationTests.class);

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void contextLoads() {

		try {
			jobLauncherTestUtils.launchJob();
		} catch (Exception e) {
			log.error("Exception in test cases");
			e.printStackTrace();
		}

	}

}
