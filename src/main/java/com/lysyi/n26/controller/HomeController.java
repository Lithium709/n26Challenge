package com.lysyi.n26.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

	@RequestMapping("/")
	ModelAndView index(){
		return new ModelAndView("redirect:/swagger-ui.html");
	}
}
