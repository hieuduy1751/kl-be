package com.se.kltn.spamanagement.service.impl;

import com.se.kltn.spamanagement.constants.ErrorMessage;
import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.constants.enums.TypeNews;
import com.se.kltn.spamanagement.dto.request.NewsRequest;
import com.se.kltn.spamanagement.dto.response.NewsResponse;
import com.se.kltn.spamanagement.exception.BadRequestException;
import com.se.kltn.spamanagement.exception.ResourceNotFoundException;
import com.se.kltn.spamanagement.model.News;
import com.se.kltn.spamanagement.repository.NewsRepository;
import com.se.kltn.spamanagement.service.NewsService;
import com.se.kltn.spamanagement.utils.MappingData;
import com.se.kltn.spamanagement.utils.NullUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.se.kltn.spamanagement.constants.ErrorMessage.NEWS_NOT_FOUND;

@Service
@Log4j2
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public NewsResponse createNews(NewsRequest newsRequest) {
        log.debug("creat new news or event");
        News news = MappingData.mapObject(newsRequest, News.class);
        news.setStatus(Status.ACTIVE);
        checkEndDate(news.getEndDate());
        return MappingData.mapObject(this.newsRepository.save(news), NewsResponse.class);
    }


    @Override
    public List<NewsResponse> getListNews(int page, int size) {
        log.debug("get list news and events");
        Pageable pageable = PageRequest.of(page, size);
        return MappingData.mapListObject(this.newsRepository.findAll(pageable).getContent(), NewsResponse.class);
    }

    @Override
    public NewsResponse findNewsById(Long id) {
        log.debug("find news or event by id: " + id);
        News news = this.newsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(NEWS_NOT_FOUND));
        return MappingData.mapObject(news, NewsResponse.class);
    }

    @Override
    public NewsResponse updateNews(Long id, NewsRequest newsRequest) {
        log.debug("update news or event have id: " + id);
        News news = this.newsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(NEWS_NOT_FOUND));
        NullUtils.updateIfPresent(news::setTitle, newsRequest.getTitle());
        NullUtils.updateIfPresent(news::setContent, newsRequest.getContent());
        NullUtils.updateIfPresent(news::setEndDate, newsRequest.getEndDate());
        NullUtils.updateIfPresent(news::setStartDate, newsRequest.getStartDate());
        NullUtils.updateIfPresent(news::setImageUrl, newsRequest.getImageUrl());
        NullUtils.updateIfPresent(news::setDescription, newsRequest.getDescription());
        NullUtils.updateIfPresent(news::setType, TypeNews.valueOf(newsRequest.getType().toUpperCase()));
        news.setStatus(checkStatus(news.getEndDate()));
        checkEndDate(news.getEndDate());
        return MappingData.mapObject(this.newsRepository.save(news), NewsResponse.class);
    }

    @Override
    public void deleteNews(Long id) {
        log.debug("delete news or event have id: " + id);
        News news = this.newsRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(NEWS_NOT_FOUND));
        this.newsRepository.delete(news);
    }

    private Status checkStatus(Date endDate) {
        if (endDate.after(new Date())) {
            return Status.INACTIVE;
        }
        return Status.ACTIVE;
    }

    private void checkEndDate(Date endDate) {
        if (endDate.before(new Date())) {
            throw new BadRequestException(ErrorMessage.DATE_BEFORE_EXCEPTION);
        }
    }
}
