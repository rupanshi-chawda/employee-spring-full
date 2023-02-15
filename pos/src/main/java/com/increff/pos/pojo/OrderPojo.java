package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Setter
@Getter
public class OrderPojo extends AbstractVersionPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column
    private ZonedDateTime time;

    @Column//todo do we need
    private String invoicePath;
}
