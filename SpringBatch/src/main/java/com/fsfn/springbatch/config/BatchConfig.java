package com.fsfn.springbatch.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.fsfn.springbatch.listener.JobCompletionNotificationListener;
import com.fsfn.springbatch.step.Processor;
import com.fsfn.springbatch.step.Reader;
import com.fsfn.springbatch.step.Writer;

@Configuration
public class BatchConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).listener(listener).flow(step1()).end()
				.build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<String, String> chunk(1).reader(new Reader()).processor(new Processor())
				.writer(new Writer()).build();
	}

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	// TODO ResourcelessTransactionManager vs DataSourceTransactionManager use case 

	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return  new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public SimpleJobLauncher jobLauncher(@Qualifier("jobRepository") JobRepository jobRepository) {
		SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
		simpleJobLauncher.setJobRepository(jobRepository);
		return simpleJobLauncher;
	}

	@Bean(name = "jobRepository")
	public JobRepository jobRepository(@Qualifier("transactionManager") PlatformTransactionManager transactionManager)
			throws Exception {
		MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
		mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
		return mapJobRepositoryFactoryBean.getObject();
	}

}
