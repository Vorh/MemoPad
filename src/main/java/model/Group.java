package model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@XmlType(propOrder = {"name", "list" , "path"} , name = "name")
@XmlRootElement
public class Group {
    private String name;

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    private File path;

    private List<Record> list = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Record> getList() {
        return list;
    }

    public void setList(List<Record> list) {
        this.list = list;
    }
}
