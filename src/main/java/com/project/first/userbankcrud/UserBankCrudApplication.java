package com.project.first.userbankcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserBankCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserBankCrudApplication.class, args);
	}


	//	@Bean
//	CommandLineRunner runner(UserService userService){
//		return args -> {
//			// read JSON and load json
//			ObjectMapper mapper = new ObjectMapper();
//			TypeReference<List<UserDomain>> typeReference = new TypeReference<List<UserDomain>>(){};
//			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
//			try {
//				List<UserDomain> users = mapper.readValue(inputStream,typeReference);
//				userService.save(users);
//				System.out.println("Users Saved!");
//			} catch (IOException e){
//				System.out.println("Unable to save users: " + e.getMessage());
//			}
//		};
//	}



}
