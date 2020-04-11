package com.myclass.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.validate.Authorized;
@RestController
public class HelloWorldController {
@RequestMapping({ "/hello" })
@Authorized(role = "user")
public String firstPage() {
return "Hello World";
}
}
