package com.boustead.SeleniumAutoScaler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRet {

    @GetMapping("/testCheckQueue")
    public String testCheckQueue(){
        return "null";
    }

    @GetMapping("/testChangeRep")
    public String testChangeRep(){
        return "null";
    }
}
