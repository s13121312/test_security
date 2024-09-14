package com.example.test_security.controller;


import com.example.test_security.dto.JoinDto;
import com.example.test_security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JoinController {

    private final JoinService joinService;

    public JoinController(JoinService joinService){
        this.joinService = joinService;
    }

    @PostMapping("/join")
    public String joinP(JoinDto joinDto){

        joinService.JoinProcess(joinDto);

        return "ok";
    }

}
