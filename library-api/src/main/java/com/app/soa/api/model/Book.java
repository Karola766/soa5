package com.app.soa.api.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "library")
public class Book implements Serializable {
    private Integer id;
    private String authorFirstName;
    private String authorSecondName;
    private String title;
    private String isbn;
    private Integer year;
    private Double price;

    public Book() {
    }

    public Book(String authorFirstName, String authorSecondName, String title, String isbn, Integer year, Double price) {
        this.authorFirstName = authorFirstName;
        this.authorSecondName = authorSecondName;
        this.title = title;
        this.isbn = isbn;
        this.year = year;
        this.price = price;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "author_first_name", nullable = false)
    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    @Column(name = "author_second_name", nullable = false)
    public String getAuthorSecondName() {
        return authorSecondName;
    }

    public void setAuthorSecondName(String authorSecondName) {
        this.authorSecondName = authorSecondName;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "isbn", nullable = false, unique = true)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Column(name = "year", nullable = false)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column(name = "price", nullable = false)
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}