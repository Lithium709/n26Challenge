package com.lysyi.n26.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lysyi.n26.domain.Transaction;
import com.lysyi.n26.service.StatisticsService;
import com.lysyi.n26.service.TransactionsService;

@Service
public class TransactionServiceImpl implements TransactionsService{

	@Autowired
	private StatisticsService statisticsService;

	@Override
	public boolean post(Transaction transaction) {
		return statisticsService.push(transaction);
	}

}
