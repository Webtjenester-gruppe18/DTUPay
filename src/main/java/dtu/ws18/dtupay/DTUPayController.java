package dtu.ws18.dtupay;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DTUPayController {
    @RequestMapping("/dtupay")
    public String index() {
        return "ToDo: Implement DTUPay";
    }
}
