package com.app.soa.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "reader")
public class Reader implements Serializable {
    private Integer id;
    private String readerFirstName;
    private String readerSecondName;
    private List<Rental> rentals;

    public Reader() {
    }

    public Reader(String readerFirstName, String readerSecondName) {
        this.readerFirstName = readerFirstName;
        this.readerSecondName = readerSecondName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "reader_first_name", nullable = false)
    public String getReaderFirstName() {
        return readerFirstName;
    }

    public void setReaderFirstName(String readerFirstName) {
        this.readerFirstName = readerFirstName;
    }

    @Column(name = "reader_second_name", nullable = false)
    public String getReaderSecondName() {
        return readerSecondName;
    }

    public void setReaderSecondName(String readerSecondName) {
        this.readerSecondName = readerSecondName;
    }

    @OneToMany(targetEntity=Rental.class, mappedBy="reader", fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    public List<Rental> getRentals(){
        return this.rentals;
    }

    public void setRentals(List<Rental> rentals){
        this.rentals = rentals;
    }

}