//package com.booklet.paragraphservice.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Fetch;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.Date;
//
//@Entity
//@Getter
//@NoArgsConstructor
//@Table(name="comment")
//public class Comment extends BaseTimeEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "comment_id")
//    private Long commentId;
//    @Column(nullable = false)
//    private int commentGroup;
//    @Column(nullable = false)
//    private int commentDepth;
//    @Column(nullable = false)
//    private String commentContent;
//
//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "paragraph_id")
//    private Paragraph paragraph;
//
//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//
//
//}