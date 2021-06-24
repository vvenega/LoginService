package com.example.demo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginServiceController {
	

	private static final String VALID_CUSTOMER = "*";

	private LoginServiceProxy proxy;

	private KafkaServiceProxy kafkaproxy;
	
	@Autowired
	RestTemplate restTemplate;

	
	@CrossOrigin(origins = VALID_CUSTOMER)
	@GetMapping("/ValidatePassword/{user}/{password}")
	public LoginBean isPasswordValid(@PathVariable String user,@PathVariable String password) {
		
		proxy = new LoginServiceProxy();
		kafkaproxy = new KafkaServiceProxy();
		
		LoginBean bean = proxy.setRestTemplate(restTemplate)
				.validatePassword(user, password);

		
		boolean result = bean.isValid();
		
		String loginResult = "LOGIN_UNSUCCESSFUL";
		if(result)
			loginResult = "LOGIN_SUCCESSFUL";
		
		kafkaproxy.setRestTemplate(restTemplate).setEvent(user, 0.0, "N-A", 
				"N-A", "N-A", user, 
				0, loginResult,false,false);
		
		
		return bean;
	}
	
	@Bean
	  @LoadBalanced
	  public RestTemplate restTemplate() {
	    return new RestTemplate();
	  }
	
	
}
