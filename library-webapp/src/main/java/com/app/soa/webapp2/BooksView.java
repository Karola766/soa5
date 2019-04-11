package com.app.soa.webapp2;

import org.primefaces.event.RowEditEvent;
import com.app.soa.api.IBooksDAO;
import com.app.soa.api.model.Book;

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
public class BooksView implements Serializable {
    @EJB(lookup = "java:global/library-impl/BooksDAO!com.app.soa.api.IRemoteBooksDAO")
    private IBooksDAO booksDAO;

    private List<Book> books;

    private String newBookAuthorFirstName;
    private String newBookAuthorSecondName;
    private String newBookTitle;
    private String newBookIsbn;
    private Integer newBookYear;
    private Double newBookPrice;

    @PostConstruct
    public void init() {
        books = booksDAO.findAll();
    }

    public void onRowEdit(RowEditEvent event) {
        Book editedBook = (Book) event.getObject();
        booksDAO.edit(editedBook);
        FacesMessage msg = new FacesMessage("Book Edited", (editedBook.getId().toString()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Book) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public void onAddNew() {
        Book bookToAdd = new Book(newBookAuthorFirstName, newBookAuthorSecondName, newBookTitle, newBookIsbn, newBookYear, newBookPrice);

        try {
            bookToAdd = booksDAO.persist(bookToAdd);
            books.add(bookToAdd);
            FacesMessage msg = new FacesMessage("New Book added", bookToAdd.getId().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            FacesMessage msg = new FacesMessage("Invalid input!");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    public String getNewBookIsbn() {
        return newBookIsbn;
    }

    public void setNewBookIsbn(String newBookIsbn) {
        this.newBookIsbn = newBookIsbn;
    }

    public Integer getNewBookYear() {
        return newBookYear;
    }

    public void setNewBookYear(Integer newBookYear) {
        this.newBookYear = newBookYear;
    }

    public Double getNewBookPrice() {
        return newBookPrice;
    }

    public void setNewBookPrice(Double newBookPrice) {
        this.newBookPrice = newBookPrice;
    }
}