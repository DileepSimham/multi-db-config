package com.example.demo.job;

import java.util.List;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.db1.entities.User1;
import com.example.demo.db1.repositories.User1Repository;
import com.example.demo.db2.entities.User2;
import com.example.demo.db2.entities.User2;
import com.example.demo.db2.repositories.User2Repository;
import com.example.demo.db3.entities.User3;
import com.example.demo.db3.repositories.User3Repository;

@Component
public class DataTransferJob implements Job {

	@Autowired
	private User1Repository user1Repository;

	@Autowired
	private User2Repository user2Repository;

	@Autowired
	private User3Repository user3Repository;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// Transfer data from db1 to db3
		List<User1> user1List = user1Repository.findAll();
		List<User3> user3List = user1List.stream().map(user1 -> {
			User3 user3 = new User3();
			user3.setUsername(user1.getUsername());
			user3.setCountry(user1.getCountry());
			return user3;
		}).collect(Collectors.toList());
		user3Repository.saveAll(user3List);

		// Transfer data from db3 to db2
		List<User3> user3ListForDb2 = user3Repository.findAll();
		List<User2> user2List = user3ListForDb2.stream().map(user3 -> {
			User2 user2 = new User2();
			user2.setUsername(user3.getUsername());
			user2.setCountry(user3.getCountry());
			return user2;
		}).collect(Collectors.toList());
		user2Repository.saveAll(user2List);

	}

}
