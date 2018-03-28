package com.lysyi.n26.n26.Challenge.service;

import java.time.Instant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.lysyi.n26.domain.Transaction;
import com.lysyi.n26.service.TransactionsService;
import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionsServiceTest {

	@Value("${time.frame.length}")
	private long timeFrameLength;
	
	@Autowired
	private TransactionsService transactionService;
	
	@Test
	public void postTransaction() throws Exception {		
		Transaction transaction = new Transaction(99.99, Instant.now().toEpochMilli());
		assertTrue(transactionService.post(transaction));			
	}
	
	@Test
	public void postOldTransaction() throws Exception {				
		Transaction transaction = new Transaction(99.99, Instant.now().toEpochMilli() - 2*1000*timeFrameLength);
		assertFalse(transactionService.post(transaction));
	}
	
	@Test
	public void postFutureTransaction() throws Exception {		
		Transaction transaction = new Transaction(99.99, Instant.now().toEpochMilli()+ 1000);
		assertFalse(transactionService.post(transaction));
	}
	
}
