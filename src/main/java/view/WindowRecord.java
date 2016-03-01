package view;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Record;
import model.parseXML;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WindowRecord {

    private static TextField name;
    private static TextField login;
    private static TextField password;
    private static TextField note;
    private static String date;

    // Создаем окно добавления записи
    public static void newRecord(TableView tableView , ObservableList records, File fileXML){
        Stage root = new Stage();
        root.initModality(Modality.APPLICATION_MODAL);
        root.setResizable(false);
        root.setWidth(180);
        root.setHeight(300);
        root.setTitle("Добавить запись");

        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(180, 300);

        // Определяем текущию дату
        date = currentDate();

        // Создаем коробку с поля и надписями для удобной группировки
        VBox box = BoxWithFields();

        // Создаем кнопку
        Button createRecord = createRecordButton();

        // Создание записи - запись в XML и отображение в таблице
        createRecord.setOnMouseClicked(event -> {
            Record record = Record.createRecord(
                    name.getText(),
                    login.getText(),
                    password.getText(),
                    note.getText(),
                    date);

            // Добавление в ХМЛ
            parseXML.marshal(record, fileXML);

            // Добавляем запись в таблицу
            Main.showRecord(record,tableView,records);
            root.close();
        });

        // Добавляем компоненты на панель и сцену
        pane.getChildren().addAll(box,createRecord);
        root.setScene(new Scene(pane));
        root.showAndWait();
    }

    // Записываем текущию дату
    private static String currentDate() {
        Date currentDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(currentDate);
    }

    // Создание кнопки
    private static Button createRecordButton() {
        Button buttonAdd = new Button();
        buttonAdd.setText("Создать запись");
        buttonAdd.setTranslateX(33);
        buttonAdd.setTranslateY(224);

        return buttonAdd;
    }

    // Создание коробку с полями для ввода и надписи к ним
    private static VBox BoxWithFields() {
        VBox box = new VBox();
        box.setSpacing(5);
        box.setTranslateX(10);
        box.setTranslateY(10);

        Text textRecordSection = new Text();
        textRecordSection.setText("Раздел записи");

        Text textName = new Text();
        textName.setText("Имя");
        Text nameLogin = new Text();
        nameLogin.setText("Логин");
        Text textPassword = new Text();
        textPassword.setText("Пароль");
        Text textNote = new Text();
        textNote.setText("Заметка");

        name = new TextField();
        login = new TextField();
        password = new TextField();
        note = new TextField();


        box.getChildren().addAll(textName, name, nameLogin, login, textPassword, password, textNote, note);
        return box;
    }



}
