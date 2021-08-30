package ru.borisof.pasteme.dao;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "pm_friendship")
public class Friendship implements Serializable {

    @Id
    @Column(name = "requester_id", nullable = false)
    private long requesterId;

    @Id
    @Column(name = "adressee_id", nullable = false)
    private long adresseeId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    Date createdAt;

}
