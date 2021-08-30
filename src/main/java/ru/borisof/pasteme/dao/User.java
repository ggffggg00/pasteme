package ru.borisof.pasteme.dao;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data()
@Entity()
@Builder()
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pm_user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String name;


    @Column(name = "email", nullable = false, length = 100)
    public String email;

    @Column(name = "password_hash", nullable = false, length = 40)
    private String passwordHash;


}
