package com.app.soa.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "author")
public class Author implements Serializable {
    private Integer id;
    private String authorFirstName;
    private String authorSecondName;
    private List<NewBook> books;

    public Author() {
    }

    public Author(String authorFirstName, String authorSecondName) {
        this.authorFirstName = authorFirstName;
        this.authorSecondName = authorSecondName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false)
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

    @OneToMany(targetEntity=NewBook.class, mappedBy="author", fetch = FetchType.EAGER)
    private List<NewBook> getBooks(){
        return this.books;
    }

    private void setBooks(List<NewBook> books){
        this.books = books;
    }

}