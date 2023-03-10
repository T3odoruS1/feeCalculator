package com.fujitsu.feeCalculator.REST;

import com.fujitsu.feeCalculator.BLL.MessageBuilder;
import com.fujitsu.feeCalculator.Domain.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;

public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("/error")
    public Message handleError(HttpServletRequest request) {

        return MessageBuilder.getMessage("Looks like something went wrong. Just in case- Acceptable parameters: city={tallinn,tartu,parnu}&vehicle={car,bike,scooter}");
    }
}
