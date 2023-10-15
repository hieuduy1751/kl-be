package com.se.kltn.spamanagement.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(MultipartFile multipartFile) throws Exception;
}
