package com.crm.rest.controller;

import com.crm.rest.entity.Customer;
import com.crm.rest.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    @Qualifier("customerService")
    private Service service;

    @GetMapping("/")
    public List<Customer> getCustomerList(){
        return service.findAll();
    }
}
