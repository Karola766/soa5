package com.app.soa.webapp2;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@FacesConverter("DateConverter")
public class DateConverter implements Converter<LocalDate> {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if(s.equals("")||s.equals(" ")){
            return null;
        }else{
        return LocalDate.parse(s, formatter);}
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, LocalDate localDate) {
        return localDate.format(formatter);
    }
}