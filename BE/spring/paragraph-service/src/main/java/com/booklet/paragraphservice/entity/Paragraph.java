package com.booklet.paragraphservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name="paragraph")
public class Paragraph extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="paragraph_id")
    private Long paragraphId;

    @Column(length = 301, nullable = false)
    private String paragraphContent;
    @Column(length = 30, nullable = false)
    private String paragraphColor;

    @Column
    private int paragraphPage;

    @Column
    private Long userId;

    @Column
    private String bookIsbn;

    @Builder
    public Paragraph(Long userId, String bookIsbn,String paragraphColor,int paragraphPage, String paragraphContent){
        this.userId = userId;
        this.paragraphContent = paragraphContent;
        this.paragraphColor = paragraphColor;
        this.bookIsbn = bookIsbn;
        this.paragraphPage = paragraphPage;
    }

}