package com.se.kltn.spamanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponse {

    private Long id;

    private String title;

    private String content;

    private String description;

    private String type;

    private String imageUrl;

    private String status;

    private Date startDate;

    private Date endDate;
}
