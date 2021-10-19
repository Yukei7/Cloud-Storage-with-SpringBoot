package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.HashService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CloudStorageApplication {

	private UserMapper userMapper;
	private HashService hashService;

	public CloudStorageApplication(UserMapper userMapper, HashService hashService) {
		this.userMapper = userMapper;
		this.hashService = hashService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageApplication.class, args);
	}

	@Bean
	public void createTestUser() {
		String hashedPass = hashService.getHashedValue("testpw", "1");
		Integer id = userMapper.insert(new User(null,
				"testuser",
				"1",
				hashedPass,
				"Test01",
				"Last Name"));

	}

}
