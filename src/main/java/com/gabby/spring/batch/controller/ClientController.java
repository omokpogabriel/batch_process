package com.gabby.spring.batch.controller;

import com.gabby.spring.batch.client.NodeInteractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    private NodeInteractor nodeInteractor;

    @GetMapping
    public String callGoogle(){
        return nodeInteractor.callMe();
    }
}
