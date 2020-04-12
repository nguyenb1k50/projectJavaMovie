package com.myclass.controller;

import com.myclass.service.StripeClient;
import com.myclass.validate.Authorized;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

import net.bytebuddy.asm.Advice.This;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	private StripeClient stripeClient;

	@Autowired
	PaymentController(StripeClient stripeClient) {
		this.stripeClient = stripeClient;
	}

	@PostMapping("/charge")
	@Authorized(role ="user")
	public Object chargeCard(HttpServletRequest request) throws Exception {
		String token = request.getHeader("token");
		Double amount = Double.parseDouble(request.getHeader("amount"));
		String cusId = request.getHeader("cusId");
		Boolean isSaveCard = Integer.parseInt(request.getHeader("save")) == 1 ? true : false;
		//case 1: already save card
		if(cusId != null && !cusId.isEmpty()) {
			return this.stripeClient.chargeCustomerCard(cusId, amount);
		}else if (isSaveCard) { //case 2: first time or new card
			// create new stripe customer then save to database so next time we just send cusId
			Customer newCus = this.stripeClient.createCustomer(token, "zachpro1935@gmail.com");
			return this.stripeClient.chargeCustomerCard(newCus.getId(), amount);
		} else //case 3: charge 1 time
			return this.stripeClient.chargeNewCard(token, amount);
	}
}
