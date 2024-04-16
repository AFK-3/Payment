package id.ac.ui.cs.advprog.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class cController {
    String createHTML = "userCreate";
    @GetMapping("/")
    @ResponseBody
    public String createUserPage(Model model) {
        return "<h1>haloo berhasil ya</h1>";
    }
}