package com.lysyi.n26.n26.Challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.lysyi.n26.N26ChallengeApplication;

@WebMvcTest(N26ChallengeApplication.class)
@RunWith(SpringRunner.class)

public class TransactionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Value("${time.frame.length}")
	private long timeFrameLength;
	
	@Test
	public void postTransaction() throws Exception {
		
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 100,");
		json.append("\"timestamp\": " + Instant.now().toEpochMilli());
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json")))
		.andExpect(status().isCreated())
		.andExpect(content().contentType("application/json;charset=UTF-8"));
	}
	
	@Test
	public void postOldTransaction() throws Exception {
		
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 99,");
		json.append("\"timestamp\": " + ( Instant.now().toEpochMilli()-2*1000*timeFrameLength));
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json")))
		.andExpect(status().isNoContent());
	}
	
	@Test
	public void postFutureTransaction() throws Exception {
		
		StringBuilder json = new StringBuilder();
		json.append("{");
		json.append("\"amount\": 98,");
		json.append("\"timestamp\": " + ( Instant.now().toEpochMilli()+1000*timeFrameLength));
		json.append("}");
		
		 this.mockMvc.perform(post("/transactions")
		.contentType("application/json")
		.content(json.toString())
		.accept(MediaType.parseMediaType("application/json")))
		.andExpect(status().isNoContent());
		 
	}
	
}
