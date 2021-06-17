package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginServiceController {
	
	//private static final String VALID_CUSTOMER = "http://192.168.1.66:4200";
	private static final String VALID_CUSTOMER = "*";
	
	@Autowired
	private LoginServiceProxy proxy;
	
	@Autowired
	private KafkaServiceProxy kafkaproxy;
	
	@CrossOrigin(origins = VALID_CUSTOMER)
	@GetMapping("/ValidatePassword/{user}/{password}")
	public LoginBean isPasswordValid(@PathVariable String user,@PathVariable String password) {
		
		LoginBean bean = proxy.validatePassword(user, password);
		
		boolean result = bean.isValid();
		
		String loginResult = "LOGIN_UNSUCCESSFUL";
		if(result)
			loginResult = "LOGIN_SUCCESSFUL";
		
		kafkaproxy.setEvent(user, 0.0, "N-A", 
				"N-A", "N-A", user, 
				0, loginResult,false,false);
		
		
		return bean;
	}

}
