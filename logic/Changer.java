package logic;

import objects.File;
import ui.UserInterface;

import java.util.ArrayList;

public class Changer {

    public static void deleteFirstFileInList(ArrayList<ArrayList<File>> files, UserInterface userInterface) {
        for (ArrayList<File> list : files) {
            if (list.size() >= 1) {
                File f = list.get(0);
                if (f.isFile())
                    if (!f.delete())
                        userInterface.display(userInterface.getText().getDeletionErrorMsg(f.getAbsolutePath()));
            }
        }
    }
}
