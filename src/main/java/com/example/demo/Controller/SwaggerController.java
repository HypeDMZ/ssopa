package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@Controller
public class SwaggerController {
    @GetMapping("/api/doc/")
    public String redirectSwagger() {
        return "redirect:/swagger-ui/index.html";
    }
}