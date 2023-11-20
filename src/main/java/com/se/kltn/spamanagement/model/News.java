package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.constants.enums.Status;
import com.se.kltn.spamanagement.constants.enums.TypeNews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news_events")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String description;

    private TypeNews type;

    private Status status;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;
}
