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
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

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

    private List<Author> foundAuthors;
    private List<Reader> foundReaders;

    private String newBookAuthorFirstName;
    private String newBookAuthorSecondName;
    private String newBookTitle;
    private String newBookReaderFirstName;
    private String newBookReaderSecondName;
    private LocalDate newBookStartDate;
    private LocalDate newBookEndDate;

    private Boolean Max=Boolean.FALSE;

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

    public void setMax(){
        this.Max=!this.Max;
    }

    public void onFindAuthor(){
        String query_select = "SELECT a ";
        String query_from = "FROM Author a";
        String query_where = " WHERE ";
        if(!this.newBookAuthorFirstName.isEmpty() && !this.newBookAuthorFirstName.equals("")){
            query_where = query_where+ " authorFirstName = '" + this.newBookAuthorFirstName+"'";
        }else if(!this.newBookAuthorSecondName.isEmpty()){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" authorSecondName = '" + this.newBookAuthorSecondName+"'";
        }else if(!this.newBookTitle.isEmpty()){
            if(query_where.length()>8)query_where=query_where+" AND";
            query_where = query_where+" b.title = '" + this.newBookTitle+"'";
            query_from = query_from+", Book b";
        }else if(!this.newBookReaderFirstName.isEmpty()){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" readerFirstName = '" + this.newBookReaderFirstName+"'";
            query_from = query_from.join(", Reader");
        }else if(!this.newBookReaderSecondName.isEmpty()){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" readerSecondName = '" + this.newBookReaderSecondName+"'";
            if(!query_from.contains("Reader")) query_from = query_from.join(", Reader");
        }else if(this.newBookStartDate!=null){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where.join(" startDate >= " + this.newBookStartDate);
            if(!query_from.contains("Rental")) query_from = query_from+", Rental";
        }else if(this.newBookEndDate!=null){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" endDate <= " + this.newBookEndDate;
            if(!query_from.contains("Rental")) query_from = query_from+", Rental";
        }else if(this.Max.equals(true)){
            if(!query_from.contains("Book")) query_from = query_from+", Book";
            query_where = query_where+" GROUP BY Author HAVING MAX(rentals.length() );";
        }
        String query = query_select+query_from+ query_where;
        askAuthorQuery(query);
        //askAuthorQuery("SELECT a FROM Author a WHERE a.authorFirstName = '"+this.newBookAuthorFirstName+"'");

    }

    public void askAuthorQuery(String query){
        try {
            this.foundAuthors = authorsDAO.getFromQuery(query);
            FacesMessage msg = new FacesMessage(foundAuthors.get(0).getAuthorFirstName(), foundAuthors.get(0).getAuthorSecondName());
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onFindReader(){
        String query_select = "SELECT a ";
        String query_from = "FROM Reader a";
        String query_where = " WHERE ";
        if(!this.newBookReaderFirstName.isEmpty()){
            query_where = query_where+" readerFirstName = '" + this.newBookReaderFirstName+"'";
        }else if(!this.newBookReaderSecondName.isEmpty()){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" readerSecondName = '" + this.newBookReaderSecondName+"'";
        }else if(!this.newBookTitle.isEmpty()){
            if(query_where.length()>8)query_where=query_where+" AND";
            query_where = query_where+" title = '" + this.newBookTitle+"'";
            query_from = query_from+", Book";
        }else if(!this.newBookAuthorFirstName.isEmpty()){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" authorFirstName = '" + this.newBookAuthorFirstName+"'";
            query_from = query_from+", Author";
        }else if(!this.newBookAuthorSecondName.isEmpty()){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" authorSecondName = '" + this.newBookAuthorSecondName+"'";
            if(!query_from.contains("Author")) query_from = query_from+", Author";
        }else if(this.newBookStartDate!=null){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" startDate >= " + this.newBookStartDate;
            if(!query_from.contains("Rental")) query_from = query_from+", Rental";
        }else if(this.newBookEndDate!=null){
            if(query_where.length()>8)query_where = query_where+" AND";
            query_where = query_where+" endDate <= " + this.newBookEndDate;
            if(!query_from.contains("Rental")) query_from = query_from+", Rental";
        }else if(this.Max.equals(true)){
            if(!query_from.contains("Book")) query_from = query_from+", Book";
            query_where = query_where+" GROUP BY Author HAVING MAX(rentals.length() );";
        }
        String query = query_select+query_from+query_where;
        askReaderQuery(query);

    }

    public void askReaderQuery(String query){
        try {

            this.foundReaders = readersDAO.getFromQuery(query);
            FacesMessage msg = new FacesMessage("Query made", "reader");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }catch(Exception e){
            e.printStackTrace();
        }
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

    public String getNewBookReaderFirstName(){
        return this.newBookReaderFirstName;
    }

    public void setNewBookReaderFirstName(String newBookReaderFirstName){
        this.newBookReaderFirstName= newBookReaderFirstName;
    }

    public String getNewBookReaderSecondName(){
        return this.newBookReaderSecondName;
    }

    public void setNewBookReaderSecondName(String newBookReaderSecondName){
        this.newBookReaderSecondName = newBookReaderSecondName;
    }

    public LocalDate getNewBookStartDate(){
        return this.newBookStartDate;
    }

    public void setNewBookStartDate( LocalDate newBookStartDate){
        this.newBookStartDate = newBookStartDate;
    }

    public LocalDate getNewBookEndDate(){
        return this.newBookEndDate;
    }

    public void setNewBookEndDate(LocalDate newBookEndDate){
        this.newBookEndDate = newBookEndDate;
    }

    public List<Author> getFoundAuthors(){
        return this.foundAuthors;
    }

    public void setFoundAuthors(List<Author> foundAuthors){
        this.foundAuthors = foundAuthors;
    }

    public List<Reader> getFoundReaders(){
        return this.foundReaders;
    }

    public void setFoundReaders(List<Reader> foundReaders){
        this.foundReaders = foundReaders;
    }
}