package com.se.kltn.spamanagement.dto.request;

import com.se.kltn.spamanagement.constants.enums.TypeNews;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequest {

    @NotBlank(message = "title is require")
    private String title;

    @NotBlank(message = "content is require")
    private String content;

    private String description;

    @NotBlank(message = "type is require")
    private String type;

    private String imageUrl;

    private Date startDate;

    private Date endDate;
}
