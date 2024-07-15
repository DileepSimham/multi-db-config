package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db2.entities.User2;
import com.example.demo.db2.repositories.User2Repository;

@RestController
@RequestMapping("/user")
public class TestController {

	@Autowired
	private User2Repository user2Repository;

	@PostMapping("/save")
	public User2 saveUser2(@RequestBody User2 user2) {
		User2 save = user2Repository.save(user2);
		return save;
	}
}
