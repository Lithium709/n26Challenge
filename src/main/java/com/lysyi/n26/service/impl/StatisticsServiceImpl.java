package com.lysyi.n26.service.impl;

import java.time.Instant;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.LongStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.lysyi.n26.domain.Statistic;
import com.lysyi.n26.domain.Transaction;
import com.lysyi.n26.service.StatisticsService;

/** 
 * Statistic service implementantion focused on O(1) complexity Statistics obtaining vs O(n*log(n)) complexity transaction processing trade-off 
 * 
 * @author OleksandrLysyi
 *
 */

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Value("${time.frame.length}")
	private long timeFrameLength;

	private Map<Long, Statistic>  statisticsCache = new ConcurrentHashMap<>();
	
	private Queue<Long> transactionTimeJournal = new PriorityBlockingQueue<>();
	
	/**
	 *  The method simply extracts precalculated Statistic object from cache.
	 *  The time complexity is O(1).
	 *  
	 * @return Statistic object with sum, avg, min, max and count values for current moment of time
	 *
	 */
	@Override
	public Statistic getCurrent() {
		long nowSeconds = Instant.now().getEpochSecond();
		return statisticsCache.computeIfAbsent(nowSeconds, (k)->new Statistic());	
	}
	
	/**
	 *  Posts a new transaction for processing.
	 *  The method simply updates all relevant future Statistics reports.   
	 *  To track expired epoch second we use PriorityBlockingQueue instance.
	 *  As per javadoc BlockingQueue implementations and ConcurrentHashMap are thread-safe.
	 *  The only modifying statistics method {@link com.lysyi.n26.domain.Statistic#update(double)} is synchronized
	 *  The time complexity is O(seconds*log n).Where s in range 0..60. In best cases, where is little or no time discrepancy (transaction timestamps come succinctly)
	 *  it can be close to O(1)
	 *   
	 * @param transaction Transaction instance for processing
	 * @return true if transaction is accepted for calculations
	 *         false if transaction expired or invalid        
	 *         
	 */
	@Override
	public boolean push(Transaction transaction) {
		long transationTimestamp = transaction.getTimestamp();
		long currentTimestamp = Instant.now().toEpochMilli();
		if(currentTimestamp-transationTimestamp > 1000 * timeFrameLength || transationTimestamp > currentTimestamp){
			return false;
		}
		LongStream.range(currentTimestamp/1000, transationTimestamp/1000 + timeFrameLength).parallel()
		.forEach(t->{
			Statistic statistic = statisticsCache.computeIfAbsent(t, (k)->new Statistic());
			statistic.update(transaction.getAmount());
			transactionTimeJournal.offer(t);}				
		);
		return true;
	}
	/*
	   As the programs runs, expired statistics should be periodically evicted for cache to prevent memory leak.
	*/
    @Scheduled(fixedDelayString = "${journal.eviction.period}")
	private void evictTimesQueue(){
		long expiredTreshold = Instant.now().getEpochSecond();
		while(!transactionTimeJournal.isEmpty() && transactionTimeJournal.peek() < expiredTreshold){
			statisticsCache.remove(transactionTimeJournal.poll());
		}
	}

	@Override
	public Map<Long, Statistic> getAll() {
		return statisticsCache;	
	}

}
