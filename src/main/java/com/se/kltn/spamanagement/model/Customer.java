package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.enums.CustomerClass;
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
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private Date birthDay;

    private CustomerClass customerClass;

}
