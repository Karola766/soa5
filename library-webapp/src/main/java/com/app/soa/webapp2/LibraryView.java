package com.app.soa.webapp2;

import com.app.soa.api.*;
import com.app.soa.api.model.*;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class LibraryView implements Serializable {
    @EJB(lookup = "java:global/library-impl/NewBookDAO!com.app.soa.api.IRemoteNewBookDAO")
    private INewBookDAO booksDAO;

    @EJB(lookup = "java:global/library-impl/ReaderDAO!com.app.soa.api.IRemoteReaderDAO")
    private IReaderDAO readersDAO;

    @EJB(lookup = "java:global/library-impl/AuthorDAO!com.app.soa.api.IRemoteAuthorDAO")
    private IAuthorDAO authorsDAO;

    @EJB(lookup="java:global/library-impl/RentalDAO!com.app.soa.api.IRemoteRentalDAO")
    private IRentalDAO rentalsDAO;

    private List<NewBook> books;
    private List<Author> authors;
    private List<Reader> readers;
    private List<Rental> rentals;

    private String newBookAuthorFirstName;
    private String newBookAuthorSecondName;
    private String newBookTitle;

    @PostConstruct
    public void init() {
        books = booksDAO.findAll();
        authors = authorsDAO.findAll();
        readers = readersDAO.findAll();
        rentals = rentalsDAO.findAll();
    }

    public void onRowEdit(RowEditEvent event) {
        NewBook editedBook = (NewBook) event.getObject();
        booksDAO.edit(editedBook);
        FacesMessage msg = new FacesMessage("Book Edited", (editedBook.getId().toString()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((NewBook) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void onAddNew() {
        Author authorToAdd = new Author(newBookAuthorFirstName, newBookAuthorSecondName);

        for(Author pom:authors){
            if(pom.getAuthorFirstName().equals(newBookAuthorFirstName)&& pom.getAuthorSecondName().equals(newBookAuthorSecondName)){
                authorToAdd = pom;
                break;
            }
        }

        NewBook bookToAdd = new NewBook(newBookTitle, authorToAdd);
        bookToAdd.setAuthor(authorToAdd);

        try {
            authorToAdd = authorsDAO.persist(authorToAdd);
            bookToAdd = booksDAO.persist(bookToAdd);
            books.add(bookToAdd);
            authors.add(authorToAdd);
            FacesMessage msg = new FacesMessage("New Book added", bookToAdd.getId().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage("Invalid input!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<NewBook> getBooks() {
        return books;
    }

    public void setBooks(List<NewBook> books) {
        this.books = books;
    }

    public List<Author> getAuthors(){
        return authors;
    }

    public void setAuthors(List<Author> authors){
        this.authors = authors;
    }

    public List<Reader>getReaders(){
        return this.readers;
    }

    public void setReader(List<Reader> readers){
        this.readers = readers;
    }

    public List<Rental>getRentals(){
        return this.rentals;
    }

    public void setRentals(List<Rental> rentals){
        this.rentals = rentals;
    }

    public String getNewBookAuthorFirstName() {
        return newBookAuthorFirstName;
    }

    public void setNewBookAuthorFirstName(String newBookAuthorFirstName) {
        this.newBookAuthorFirstName = newBookAuthorFirstName;
    }

    public String getNewBookAuthorSecondName() {
        return newBookAuthorSecondName;
    }

    public void setNewBookAuthorSecondName(String newBookAuthorSecondName) {
        this.newBookAuthorSecondName = newBookAuthorSecondName;
    }

    public String getNewBookTitle() {
        return newBookTitle;
    }

    public void setNewBookTitle(String newBookTitle) {
        this.newBookTitle = newBookTitle;
    }
}