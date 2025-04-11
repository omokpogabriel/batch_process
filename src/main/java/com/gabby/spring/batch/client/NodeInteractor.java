package com.gabby.spring.batch.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.net.Proxy;
import java.net.URI;

// to be used to resent response to the nodejs application when the file upload is completed

@Service
public class NodeInteractor {

    private final RestClient restClient;


    public NodeInteractor(RestClient.Builder builder){
        this.restClient = builder.clone()
                .baseUrl("https://www.google.com")
//                .defaultHeader("Autorization", "Bearer dfdjhfjdfjdf")
                .requestFactory(requestFactory())
                .requestInterceptor(new ClientHttpRequestInterceptor() {
                    @Override
                    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                        System.out.println("THIS IS THE REQUEST "+ request.getURI().toASCIIString());
                        return execution.execute(request, body);

                    }
                })
                .build();
    }

    private SimpleClientHttpRequestFactory requestFactory(){
         SimpleClientHttpRequestFactory simple =  new SimpleClientHttpRequestFactory();
                 simple.setConnectTimeout(300);
                 simple.setProxy(Proxy.NO_PROXY);
                 return simple;
    }


    public String callMe(){
        return restClient.get().retrieve().body(String.class);
    }
}
