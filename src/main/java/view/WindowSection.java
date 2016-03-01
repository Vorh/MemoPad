package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Group;
import model.parseXML;

import java.io.File;


public class WindowSection {

    // Окно добавления раздела
    public static void newSection(){
        Stage root = new Stage();
        root.initModality(Modality.APPLICATION_MODAL); // Модальность окна
        root.setResizable(false);
        root.setWidth(180);
        root.setHeight(140);

        AnchorPane paneRoot = new AnchorPane();
        paneRoot.setPrefSize(180, 140);

        // Создаем основные компоненты
        components(paneRoot, root);

        root.setScene(new Scene(paneRoot));
        root.setTitle("Добавить раздел");
        root.showAndWait();

    }

    // Компоненты для окна добавления
    private static void components(AnchorPane pane, Stage root) {

        Text textName = new Text("Название раздела");
        textName.setTranslateX(35);
        textName.setTranslateY(20);

        TextField nameSection = new TextField();
        nameSection.setTranslateX(10);
        nameSection.setTranslateY(35);

        Button createSection = new Button("Создать раздел");
        createSection.setTranslateX(35);
        createSection.setTranslateY(70);

        //
        createSection.setOnMouseClicked(event -> {
            Group group = parseXML.createGroup(nameSection.getText());
            Main.rootTab.getTabs().add(Main.getTab(group,group.getPath()));
            root.close();
        });

        pane.getChildren().addAll(textName, createSection, nameSection);
    }

    // Окно удаления раздела
    public static void newDeleteSection(Tab tab, File file){
        Stage root = new Stage();


        root.initStyle(StageStyle.UTILITY);  // Убираем часть системных кнопок
        root.setTitle("Потверждение");
        root.setResizable(false);


        root.initModality(Modality.APPLICATION_MODAL); // Модальность окна
        root.setWidth(200);
        root.setHeight(130);

        AnchorPane rootPane = new AnchorPane();
        rootPane.setPrefSize(200,130);

        Button yes = new Button("Да");
        yes.setPrefSize(55,25);
        yes.setTranslateX(30);
        yes.setTranslateY(55);

        Button no = new Button("Нет");
        no.setPrefSize(55,25);
        no.setTranslateX(110);
        no.setTranslateY(55);

        Font font = new Font("Arial", 16);
        Text text = new Text("Вы уверены ?");
        text.setFont(font);
        text.setTranslateX(50);
        text.setTranslateY(30);

        // Потверждение удаления : да
        yes.setOnMouseClicked(event -> {
            Main.rootTab.getTabs().remove(tab);
            file.delete();
            root.close();
        });

        // Потверждение удаления : нет
        no.setOnMouseClicked(event -> {
            root.close();
        });

        // Добавляем копоненты на панель и сцену
        rootPane.getChildren().addAll(yes,no, text);
        root.setScene(new Scene(rootPane));
        root.showAndWait();
    }

}
