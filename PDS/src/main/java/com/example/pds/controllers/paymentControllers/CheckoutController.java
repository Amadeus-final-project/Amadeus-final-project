package com.example.pds.controllers.paymentControllers;

import com.example.pds.model.payments.ChargeRequest;
import lombok.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
public class CheckoutController {
    //@Value("${STRIPE_PUBLIC_KEY}") -> have go register
    private String stripePublicKey;

    @RequestMapping("/checkout")
    public void checkout(Model model) {
        model.addAttribute("amount", 50 * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
    }
}
