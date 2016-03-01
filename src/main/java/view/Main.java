package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Group;
import model.ListFiles;
import model.Record;
import model.parseXML;

import java.io.File;
import java.util.List;

public class Main extends Application {

    public static TabPane rootTab;
    private static TableColumn<Record, String> name;
    private static TableColumn<Record, String> login;
    private static TableColumn<Record, String> password;
    private static TableColumn<Record, String> note;
    private static TableColumn<Record, String> date;


    @Override
    public void start(Stage primaryStage) throws Exception {

        // Создаем главную сцену
        primaryStage.setWidth(660);
        primaryStage.setHeight(550);
        primaryStage.setResizable(true);
        primaryStage.setTitle("MemoPad");

        // Панель на которой будет распологаться панель с таблицами
        AnchorPane root = new AnchorPane();
        root.setPrefSize(655, 500);


        // Создаем панель для отображения таблиц
        rootTab = new TabPane();
        rootTab.setTranslateY(0);
        rootTab.setPrefSize(655, 500);
        rootTab.setMinWidth(655);
        rootTab.getStyleClass().addAll("tab-header-background");

        // Создаем таблицы
        showTab(rootTab);


        root.getChildren().addAll(rootTab);
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll("style.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Создаем и добавляем панель-таблицы на панель
    public static void showTab(TabPane rootTab) {
        // Очищаем все таблицы на всякий случай
        rootTab.getTabs().clear();

        // Получаем путь к папке с ХМЛ
        File[] file =  ListFiles.listFiles();

        for (int i = 0; i <file.length ; i++) {

            // Получаем данные из xml файла
            Group group = parseXML.unmarshaller(file[i]);

            // Создаем таблицу и заполняем ее данными
            Tab tab = getTab(group, file[i]);
            rootTab.getTabs().add(tab);
        }
    }

    // Создаем таблицу
    public static Tab getTab(Group group, File file) {
        File path = file;

        // Создаем панель-таблицу
        Tab tab = new Tab();
        tab.setClosable(false);
        tab.setText(group.getName());

        // Панель на которую поместим таблицу с записями
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(650,470);

        // Создаем таблицу
        TableView tableView = new TableView();
        tableView.setEditable(true);

        // Настраиваем ратягивание колонок на всю ширину
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setPrefSize(650, 450);
        tableView.setMaxHeight(450);

        // Отступы по краям панели
        AnchorPane.setTopAnchor(tableView,8.0);
        AnchorPane.setLeftAnchor(tableView,13.0);
        AnchorPane.setBottomAnchor(tableView,37.0);
        AnchorPane.setRightAnchor(tableView,25.0);

        // Создаем колонки таблицы
        name = new TableColumn();
        login = new TableColumn();
        password = new TableColumn();
        note = new TableColumn();
        date = new TableColumn();

        name.setPrefWidth(100);
        name.setMaxWidth(200);
        name.setMinWidth(75);

        login.setPrefWidth(100);
        login.setMaxWidth(200);
        login.setMinWidth(75);

        password.setPrefWidth(100);
        password.setMaxWidth(200);
        password.setMinWidth(75);

        note.setPrefWidth(100);
        note.setMaxWidth(200);
        note.setMinWidth(75);

        date.setPrefWidth(100);
        date.setMaxWidth(200);
        date.setMinWidth(75);

        name.setText("Имя");
        login.setText("Логин");
        password.setText("Пароль");
        note.setText("Заметка");
        date.setText("Дата");

        // Создаем кнопки для создания, удаления записей и разделов
        Font font = new Font("Arial", 10);

        Button addRecord = new Button("Добавить запись");
        addRecord.getStyleClass().addAll("scene");
        addRecord.setFont(font);
        addRecord.setPrefSize(120, 30);
        addRecord.setTranslateX(510);
        addRecord.setTranslateY(390);

        Button addSection = new Button("Добавить раздел");
        addSection.getStyleClass().addAll("scene");
        addSection.setFont(font);
        addSection.setPrefSize(120, 30);
        addSection.setTranslateX(380);
        addSection.setTranslateY(390);

        Button deleteRecord = new Button("Удалить запись");
        deleteRecord.getStyleClass().addAll("scene");
        deleteRecord.setFont(font);
        deleteRecord.setPrefSize(120, 30);
        deleteRecord.setTranslateX(12);
        deleteRecord.setTranslateY(390);

        Button deleteSection = new Button("Удалить раздел");
        deleteSection.getStyleClass().addAll("scene");
        deleteSection.setFont(font);
        deleteSection.setPrefSize(120, 30);
        deleteSection.setTranslateX(140);
        deleteSection.setTranslateY(390);

        // Список в который помешается записи
        ObservableList records = FXCollections.observableArrayList();

        // Список записей из xml
        List<Record> list = group.getList();

        // Пробегаемся по списку и добавляем записи в таблицу
        for (Record record : list) {
            showRecord(record,tableView,records);
        }


        // Привязка событий к кнопкам
        addRecord.setOnMouseClicked(event -> {
            WindowRecord.newRecord(tableView, records, file);
        });

        addSection.setOnMouseClicked(event -> {
            WindowSection.newSection();
        });

        deleteSection.setOnMouseClicked(event -> {
              WindowSection.newDeleteSection(tab, file);
        });

        deleteRecord.setOnMouseClicked(event -> {
            int s = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().remove(s);
            parseXML.marshalSave(records, path, group);
        });


        // Изменение и сохранение xml при изменеия ячейки в таблицы
        name.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
            public void handle(TableColumn.CellEditEvent<Record, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
                parseXML.marshalSave(records, file, group);
            }
        });

        login.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        login.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
            public void handle(TableColumn.CellEditEvent<Record, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setLogin(event.getNewValue());
                parseXML.marshalSave(records, file, group);
            }
        });

        password.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        password.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
            public void handle(TableColumn.CellEditEvent<Record, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
                parseXML.marshalSave(records, file, group);
            }
        });

        note.setCellFactory(TextFieldTableCell.<Record>forTableColumn());
        note.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Record, String>>() {
            public void handle(TableColumn.CellEditEvent<Record, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setNote(event.getNewValue());
                parseXML.marshalSave(records, file, group);
            }
        });


        // Добавляем все компоненты на панели
        tableView.getColumns().addAll(name, login, password, note, date);
        pane.getChildren().addAll(tableView, addRecord, addSection, deleteRecord, deleteSection);
        tab.setContent(pane);
        return  tab;
    }



    // Отображает запись в таблице
    public static void showRecord(Record record, TableView tableView , ObservableList records) {
        records.addAll(record);
        name.setCellValueFactory(new PropertyValueFactory<Record, String>("name"));
        login.setCellValueFactory(new PropertyValueFactory<Record, String>("login"));
        password.setCellValueFactory(new PropertyValueFactory<Record, String>("password"));
        note.setCellValueFactory(new PropertyValueFactory<Record, String>("note"));
        date.setCellValueFactory(new PropertyValueFactory<Record, String>("date"));
        tableView.setItems(records);
    }

    public static void main(String[] args) {
        launch();
    }


}
