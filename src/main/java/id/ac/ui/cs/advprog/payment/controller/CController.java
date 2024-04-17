package id.ac.ui.cs.advprog.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class CController {
    String createHTML = "userCreate";
    @GetMapping("/")
    @ResponseBody
    public String createUserPage(Model model) {
        return "<h1>AFK-3's Payment</h1>";
    }
}