package com.fujitsu.feeCalculator.REST;
import com.fujitsu.feeCalculator.BLL.ResponseFactory;
import com.fujitsu.feeCalculator.REST.DataClasses.IRestResponseMessage;
import com.fujitsu.feeCalculator.REST.DataClasses.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

    @RequestMapping("api/v1/delivery_fee")
@RestController
public class FeeController {
    private final ResponseFactory responseFactory;
    @Autowired
    public FeeController(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    /**
     * Main API endpoint
     * @param city city name
     * @param vehicle vehicle name
     * @return Message returned through REST api.
     */
    @GetMapping
    public IRestResponseMessage getDeliveryFee(@RequestParam String city, @RequestParam String vehicle){
        return responseFactory.getMessageFromRequest(city, vehicle);
    }
}
