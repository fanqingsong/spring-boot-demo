package com.roncoo.education.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roncoo.education.bean.RoncooUser;
import com.roncoo.education.mapper.RoncooUserMapper;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private RoncooUserMapper roncooUserMappper;

	@Operation(summary = "查找", description = "根据用户ID查找用户")
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public RoncooUser get(@RequestParam(defaultValue = "1") Integer id) {
		return roncooUserMappper.selectByPrimaryKey(id);
	}
	
	@Hidden
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public int delete(@RequestParam(defaultValue = "1") Integer id) {
		return roncooUserMappper.deleteByPrimaryKey(id);
	}
}
