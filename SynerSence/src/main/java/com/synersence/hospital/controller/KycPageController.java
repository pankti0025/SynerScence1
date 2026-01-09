package com.synersence.hospital.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class KycPageController {

    @GetMapping("/kyc/camera")
    public String openKycCamera() {
        return "kyc-camera";
    }
}
