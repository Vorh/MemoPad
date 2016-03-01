package model;

import java.io.File;

public class ListFiles {

    public static File[] listFiles(){
        File path = new File("src\\main\\resources\\data");

//        File path = new File("date");
//        path = new File(path.getAbsolutePath());

        File[] pathList = path.listFiles();
        return pathList;
    }

}
