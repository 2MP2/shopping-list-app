package pl.edu.pwr.pastuszek.shoppinglistbackend.logic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pwr.pastuszek.shoppinglistbackend.logic.repositorie.UtilityRepository;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UtilService {
    private final UtilityRepository utilityRepository;

    public Map<String, String> resetDatabase() {
        utilityRepository.resetDatabaseContents();
        return Map.of("message", "Database reset successful!");
    }
}
