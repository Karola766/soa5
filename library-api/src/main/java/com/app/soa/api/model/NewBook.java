package com.app.soa.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "book")
public class NewBook implements Serializable {
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id", nullable = false)
    private Author author;

    private String title;

    @OneToMany(targetEntity=Rental.class, mappedBy="book", fetch = FetchType.EAGER)
    private List<Rental> rentals;

    public NewBook() {
    }

    public NewBook(String title, Author author) {
        this.author = author;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Rental> getRentals(){
        return this.rentals;
    }

    public void setRentals(List<Rental>rentals){
        this.rentals=rentals;
    }

}