package com.app.soa.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "rental")
public class Rental implements Serializable {

    private NewBook book;

    private Reader reader;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer id;

    public Rental() {
    }

    public Rental(Reader reader, NewBook book, LocalDate start_date, LocalDate end_date) {
        this.book = book;
        this.reader = reader;
        this.startDate = start_date;
        this.endDate = end_date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="book_id")
    public NewBook getBook() {
        return this.book;
    }

    public void setBook(NewBook book) {
        this.book = book;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="reader_id")
    public Reader getReader() {
        return this.reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date", nullable = false)
    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}