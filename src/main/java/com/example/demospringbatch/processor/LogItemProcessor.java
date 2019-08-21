package com.example.demospringbatch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.example.demospringbatch.model.Log;

public class LogItemProcessor implements ItemProcessor<Log, Log> {

	@Override
	public Log process(final Log log) throws Exception {
		return log;
	}

}
