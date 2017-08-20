package logic;

import objects.File;
import ui.UserInterface;

import java.util.ArrayList;

public class Changer {

    public static void deleteFiles(ArrayList<File> files, UserInterface userInterface) {
        for (File f : files) {
            if (f.isFile())
                if (!f.delete())
                    userInterface.display(userInterface.getText().getDeletionErrorMsg(f.getAbsolutePath()));

        }
    }
}
