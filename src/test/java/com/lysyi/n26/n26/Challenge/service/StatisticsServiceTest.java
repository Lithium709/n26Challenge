package com.lysyi.n26.n26.Challenge.service;

import java.time.Instant;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lysyi.n26.domain.Statistic;
import com.lysyi.n26.domain.Transaction;
import com.lysyi.n26.service.StatisticsService;
import com.lysyi.n26.service.TransactionsService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StatisticsServiceTest {
	
	@Value("${time.frame.length}")
	private long timeFrameLength;

	@Autowired
	private StatisticsService statisticsService;
	
	@Autowired
	private TransactionsService transactionService;

	@Test
	public void showStatistic(){
		
		long now = Instant.now().toEpochMilli();
			
		transactionService.post(new Transaction(100, now-1000));
		transactionService.post(new Transaction(200, now-1000*timeFrameLength/2));
		transactionService.post(new Transaction(300, now-1000*timeFrameLength/4));
		transactionService.post(new Transaction(800, now+1000*timeFrameLength/4));
		
		Statistic statistics = statisticsService.getCurrent();
		
		assertEquals(Double.valueOf(200.0D), statistics.getAvg());
		assertEquals(Double.valueOf(300.0D), statistics.getMax());
		assertEquals(Double.valueOf(100.0D), statistics.getMin());
		assertEquals(Double.valueOf(600.0D), statistics.getSum());
	}
	
}
