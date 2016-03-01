package model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "login", "password" , "note" , "date"}, name = "record")
@XmlRootElement
public class Record {

    private String name;
    private String login;
    private String password;
    private String note;
    private String date;


    @Override
    public String toString(){
        return name + " " + login + " " + password + " " + note + " " + date;
    }

    //<editor-fold desc="GetAndSet">
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    //</editor-fold>

    public static Record createRecord(String name,String login, String password, String note , String data){
        Record record = new Record();
        record.setName(name);
        record.setLogin(login);
        record.setPassword(password);
        record.setNote(note);
        record.setDate(data);
        return record;
    }
}
