package com.webtechproject.backend.controller;

import com.webtechproject.backend.model.User;
import com.webtechproject.backend.model.Goal;
import com.webtechproject.backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<?>>> globalSearch(@RequestParam String query) {
        Map<String, List<?>> results = searchService.searchAll(query);
        return ResponseEntity.ok(results);
    }
}

