package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="LoginDAO",url="http://localhost:8103/")
public interface LoginServiceProxy {
	
	@GetMapping("/ValidatePasswordDao/{user}/{password}")
	public LoginBean validatePassword(@PathVariable String user,@PathVariable String password);
	

}
