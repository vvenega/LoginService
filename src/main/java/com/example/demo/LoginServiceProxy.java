package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

//http://localhost:8103/

//@FeignClient(name="LoginDAO"/*,url="http://LOGIN-SERVICE-DAO/"*/)
public class LoginServiceProxy {
	
	//@Autowired
	RestTemplate restTemplate;
	
	private final String DAO="DAO";
	
	
	
	//@GetMapping("/ValidatePasswordDao/{user}/{password}")
	public LoginBean validatePassword(@PathVariable String user,@PathVariable String password) {
		
		System.err.println(Config.getProperty(this.DAO)+":"+restTemplate);
		
		LoginBean bean = restTemplate.exchange(Config.getProperty(this.DAO)+"/ValidatePasswordDao/{user}/{password}",
		          HttpMethod.GET, null, new ParameterizedTypeReference<LoginBean>() {}, user,password).getBody();
		
		return bean;
	}
	
	/*@Bean
	  @LoadBalanced
	  public RestTemplate restTemplate() {
	    return new RestTemplate();
	  }*/
	
	public LoginServiceProxy setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
		return this;
	}

	

}
