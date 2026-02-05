package FindYourThingApplication.services;

import FindYourThingApplication.entities.Payment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService
{
    public Integer createPayment(){return 0;}
    public void cancelPayment(){}
    //transaction
    public void pay(){}
    //additionally - export user's payment history
    public List<Payment> getUserPayments(){return new ArrayList<>();
    }
}
