package com.sahabuddin.contactmanager.controllers;

import ch.qos.logback.core.model.Model;
import com.sahabuddin.contactmanager.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Slf4j
@Controller
@RequestMapping
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final EmailService emailService;

    @GetMapping(value = "/forget-password")
    public String openEmailForm(Model model) {
        return "forget_password";
    }

    @PostMapping(value = "/send-otp")
    public String sendOTP(@RequestParam("email") String email, Model model) {
        log.info("Sending OTP for {}", email);
        Random random = new Random();
        int otp = random.nextInt(999999);
        log.info("OTP generated {}", otp);
        String message = String.format("OTP generated for %s and OTP is: %s", email, otp);
        log.info(message);
        emailService.sendSimpleMessage(email, "Forget password OTP", message);
        return "verify_otp";
    }

    @PostMapping(value = "/verify-otp")
    public String verifyOTP(@RequestParam("otp") String otp, Model model) {
        log.info("OTP is {}", otp);

        return "change_password";
    }

}
