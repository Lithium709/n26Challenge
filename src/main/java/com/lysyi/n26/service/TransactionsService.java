package com.lysyi.n26.service;

import com.lysyi.n26.domain.Transaction;

public interface TransactionsService {

	boolean post(Transaction transaction);
	
}
