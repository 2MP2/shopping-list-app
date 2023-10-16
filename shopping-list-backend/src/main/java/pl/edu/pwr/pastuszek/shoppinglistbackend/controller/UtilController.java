package pl.edu.pwr.pastuszek.shoppinglistbackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service.UtilService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UtilController {
    private final UtilService utilService;

    // FOR DEVELOPMENT ONLY !!!
    // Make sure to remove after deploying to production
    @PostMapping("reset")
    public Map<String, String> resetDatabase() {
        return utilService.resetDatabase();
    }

}
