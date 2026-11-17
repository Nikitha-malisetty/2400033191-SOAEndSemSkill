package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DataController {
    private final JdbcTemplate jdbcTemplate;

    public DataController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/data")
    public ResponseEntity<List<Map<String, Object>>> getData() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT id, name, description FROM demo_data ORDER BY id"
        );
        return ResponseEntity.ok(rows);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "app-service");
    }
}
