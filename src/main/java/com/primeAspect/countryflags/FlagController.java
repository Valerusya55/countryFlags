package com.primeAspect.countryflags;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class FlagController {

    @Autowired
    private FlagService flagService;

    @GetMapping("/countries")
    public void getURLFlags(@RequestBody List<String> countryCode,
                            String pathDirectory, String expansion) throws JsonProcessingException {
        flagService.getURLFlags(countryCode, pathDirectory, expansion);
    }
}
