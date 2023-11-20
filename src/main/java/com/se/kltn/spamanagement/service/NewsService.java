package com.se.kltn.spamanagement.service;

import com.se.kltn.spamanagement.dto.request.NewsRequest;
import com.se.kltn.spamanagement.dto.response.NewsResponse;

import java.util.List;

public interface NewsService {

    NewsResponse createNews(NewsRequest newsRequest);

    List<NewsResponse> getListNews(int page, int size);

    NewsResponse findNewsById(Long id);

    NewsResponse updateNews(Long id, NewsRequest newsRequest);

    void deleteNews(Long id);
}
