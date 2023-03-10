package com.fujitsu.feeCalculator.DomainTests;

import com.fujitsu.feeCalculator.BLL.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResponseFactoryTests {

    private final ResponseFactory responseFactory;


    @Autowired
    public ResponseFactoryTests(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }


}
