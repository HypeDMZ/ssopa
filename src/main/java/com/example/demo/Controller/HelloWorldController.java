// src/main/java/com.demogroup.demoweb/Controller/HelloWorldController.java

package com.example.demo.Controller;
import com.example.demo.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/greeting")
    public Object test(@RequestParam(name="name", required=false, defaultValue="World") String name) {



        return "Hello World";

    }
}