package com.lysyi.n26.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lysyi.n26.domain.Statistic;
import com.lysyi.n26.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

	@Autowired
	StatisticsService statisticsService;
	
	@GetMapping
	public ResponseEntity<Statistic> getCurrent() {
		Statistic statistic = this.statisticsService.getCurrent();
		ResponseEntity<Statistic> response = new ResponseEntity<Statistic>(statistic, HttpStatus.OK);
		return response;
	}
	
	@GetMapping(value="debug")
	public ResponseEntity<Map<Long,Statistic>> getAll() {
		ResponseEntity<Map<Long,Statistic>> response = new ResponseEntity<Map<Long,Statistic>>
		(this.statisticsService.getAll(), HttpStatus.OK);
		return response;
	}
	
}
