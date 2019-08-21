package com.example.demospringbatch.configuuration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.demospringbatch.model.Log;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("!!! JOB FINISHED! Time to verify the results");

			List<Log> results = jdbcTemplate.query("SELECT access_date, access_ip, request_type, status, user_agent FROM log", new RowMapper<Log>() {
				@Override
				public Log mapRow(ResultSet rs, int row) throws SQLException {
					return new Log(rs.getDate(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				}
			});

			for (Log log : results) {
				LOG.info("Found <" + log + "> in the database.");
			}

		}
	}
}