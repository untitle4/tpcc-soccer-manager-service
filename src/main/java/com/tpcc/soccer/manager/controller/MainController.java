package com.tpcc.soccer.manager.controller;

import com.tpcc.soccer.manager.User;
import com.tpcc.soccer.manager.UserRepository;
import com.tpcc.soccer.manager.model.UserRequest;
import com.tpcc.soccer.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewUser (@RequestParam String name
            , @RequestParam String email) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        User n = new User();

        userRepository.save(n);
        return "Saved";
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "/getUser")
    public ResponseEntity<User> getUser() {

        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
