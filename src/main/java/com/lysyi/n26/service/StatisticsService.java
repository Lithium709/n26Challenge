package com.lysyi.n26.service;

import java.util.Map;

import com.lysyi.n26.domain.Statistic;
import com.lysyi.n26.domain.Transaction;
/**
 * 
 * @author OleksandrLysyi
 * 
 *  Common interface for statistics 
 *
 */
public interface StatisticsService {
	/**
	 * Main interface method
	 *  
	 * @return Statistic object with sum avg min max count values for current moment of time
	 * 
	 *
	 */
	Statistic getCurrent();

	/**
	 *  Posts a new transaction for processing
	 * 
	 * @param transaction Transaction instance for processing
	 * @return true if transaction is accepted for calculations
	 *         false if transaction expired or invalid
	 */
	boolean push(Transaction transaction);

	/**
	 * Helped method for debugging
	 * 
	 * @return all precalculated stats being stored
	 */
	Map<Long, Statistic> getAll();
	
}
