package com.se.kltn.spamanagement.model;

import com.se.kltn.spamanagement.enums.Role;
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
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Column(name = "birth_day")
    private Date birthDay;

    private String password;

    @Column(name = "salary_gross")
    private Double salaryGross;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
