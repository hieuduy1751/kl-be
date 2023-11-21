package com.se.kltn.spamanagement.controller;

import com.se.kltn.spamanagement.dto.request.NewsRequest;
import com.se.kltn.spamanagement.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    @Operation(summary = "get list news")
    public ResponseEntity<Object> getListNews(@RequestParam(defaultValue = "0", value = "page", required = false) int page,
                                              @RequestParam(defaultValue = "10", value = "size", required = false) int size) {
        return ResponseEntity.ok(this.newsService.getListNews(page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "get news by id")
    public ResponseEntity<Object> getNewsById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.newsService.findNewsById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete news")
    public ResponseEntity<String> deleteNews(@PathVariable("id") Long id) {
        this.newsService.deleteNews(id);
        return ResponseEntity.ok().body("News deleted");
    }

    @PutMapping("/{id}")
    @Operation(summary = "update news")
    public ResponseEntity<Object> updateNews(@PathVariable("id") Long id,
                                             @RequestBody NewsRequest newsRequest) {
        return ResponseEntity.ok().body(this.newsService.updateNews(id, newsRequest));
    }

    @PostMapping
    @Operation(summary = "create news")
    public ResponseEntity<Object> createNews(@Valid @RequestBody NewsRequest newsRequest) {
        return ResponseEntity.ok().body(this.newsService.createNews(newsRequest));
    }
}
