package com.lysyi.n26.domain;


public class Statistic {
	
	private Double sum;
	private Double avg;
	private Double max;
	private Double min;
	private Long count;
	
	public Statistic(){
		this.sum = 0D;
		this.avg = null;
		this.max = Double.MIN_VALUE;
		this.min = Double.MAX_VALUE;
		this.count = 0L;
	}
	public Double getSum() {
		return sum;
	}
	public Double getAvg() {
		return avg;
	}
	/**
	 * 
	 * @return max value or null if there is no data yet
	 */
	public Double getMax() {
		return count==0?null:max;
	}
	/**
	 * 
	 * @return min value or null if there is no data yet
	 */
	public Double getMin() {
		return count==0?null:min;
	}
	public Long getCount() {
		return count;
	}
	
	@Override
	public String toString() {
		return "Statistic [sum=" + sum + ", avg=" + avg + ", max=" + max + ", min=" + min + ", count=" + count + "]";
	}
	/**
	 * The method updates this statistic with a new arriving value.
	 * Is it synchronized to prevent simultaneous updates, which can mess up calculations
	 * 
	 * @param amount The amount of a transaction that should affect this statistics report.
	 */
	public synchronized void update(double amount) {
		this.sum += amount;
		this.count++;
		this.max = Math.max(this.max, amount);
		this.min = Math.min(this.min, amount);
		this.avg = this.sum/this.count;
	}
	
}
