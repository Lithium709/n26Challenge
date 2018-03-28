package com.lysyi.n26.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lysyi.n26.domain.Transaction;
import com.lysyi.n26.service.TransactionsService;

import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	TransactionsService  transactionsService;

	@PostMapping
	@ApiResponses(value = {
			        @ApiResponse (code = 201, message = "New transaction processed"),
			        @ApiResponse(code = 204, message = "Transaction expired")
			       })
	public ResponseEntity<Transaction> post(@RequestBody @Valid Transaction transaction) {

		if(this.transactionsService.post(transaction)){
			return new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);
		}
		return  new ResponseEntity<Transaction>(HttpStatus.NO_CONTENT);
	}
}
