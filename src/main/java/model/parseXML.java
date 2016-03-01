package model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class parseXML {

    // Добавляем запись в xml
    public static void marshal (Record record, File file){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Group.class);
            Marshaller marshaller =   jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);

            Group group  =  unmarshaller(file);

            group.getList().add(record);
            marshaller.marshal(group, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    // Извлекаем данные из xml
    public static Group unmarshaller(File file){
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Group.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();
            Group group = (Group) un.unmarshal(file);
            return group;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Создаем новый раздел (файл) XML
    public static Group createGroup(String name){
        File path = new File("src\\main\\resources\\data\\" + name + ".xml");

//        File path = new File("date");
//        path = new File(path.getAbsolutePath());
//        path = new File(path + "\\" + name + ".xml");

        Group group = new Group();
        group.setName(name);
        group.setPath(path);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Group.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(group, path);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return  group;
    }


    // Пересохраняем xml после изменения таблицы
    public static void marshalSave (List<Record> list, File file, Group group){
        group.getList().clear();
        for (Record record : list) {
            group.getList().add(record);
        }
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Group.class);
            Marshaller marshaller =   jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(group, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
