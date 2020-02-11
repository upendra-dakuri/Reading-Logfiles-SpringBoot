package com.spring.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.service.LogFilesService;

@RestController
public class SerachController {

	@Autowired
	LogFilesService logFilesService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public Set<String> searchValue() {
		return logFilesService.logFiles();
	}
}
