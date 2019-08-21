package com.example.demospringbatch.configuuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.BindException;

import com.example.demospringbatch.model.Log;
import com.example.demospringbatch.processor.LogItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	private static final int NUMBER_OF_LINES_PER_TIME = 10;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Bean
	public FlatFileItemReader<Log> reader() {
		FlatFileItemReader<Log> reader = new FlatFileItemReader<Log>();
		reader.setResource(new ClassPathResource("log_access.log"));
		reader.setLineMapper(new DefaultLineMapper<Log>() {
			{
				setLineTokenizer(new DelimitedLineTokenizer("|") {
					{
						setNames(new String[] { "accessDate", "accessIp", "requestType", "status", "userAgent" });
					}
				});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Log>() {
					@Override
					public Log mapFieldSet(FieldSet fs) throws BindException {
						String dateString = fs.readString(0);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
						Date date = null;
						try {
							date = sdf.parse(dateString);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						Log log = new Log(date, fs.readString(1), fs.readString(2), fs.readString(3), fs.readString(4));
						return log;
					}

					{
						setTargetType(Log.class);
					}
				});
			}
		});
		return reader;
	}

	@Bean
	public LogItemProcessor processor() {
		return new LogItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Log> writer() {
		JdbcBatchItemWriter<Log> writer = new JdbcBatchItemWriter<Log>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Log>());
		writer.setSql(
				"INSERT INTO log (access_date, access_ip, request_type, status, user_agent) VALUES (:accessDate, :accessIp, :requestType, :status, :userAgent)");
		writer.setDataSource(dataSource);
		return writer;
	}

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener) {
		return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1()).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Log, Log>chunk(NUMBER_OF_LINES_PER_TIME).reader(reader()).processor(processor()).writer(writer()).build();
	}

}
