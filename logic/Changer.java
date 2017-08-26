package logic;

import objects.File;
import ui.UserInterface;

import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

public class Changer {

    public static void deleteFiles(ArrayList<File> files, UserInterface userInterface) throws NoSuchFileException {
        for (File f : files)
            if (!f.isFile())
                throw new NoSuchFileException(f.getAbsolutePath());

        for (File f : files)
            if (!f.delete())
                userInterface.display(userInterface.getText().getDeletionErrorMsg(f.getAbsolutePath()));
    }

    public static void deleteFolders(ArrayList<File> files, UserInterface userInterface) throws NotDirectoryException {
        for (File f : files)
            if (!f.isDirectory())
                throw new NotDirectoryException(f.getAbsolutePath());

        for (File f : files)
            if (!f.delete())
                userInterface.display(userInterface.getText().getDeletionErrorMsg(f.getAbsolutePath()));
    }
}
