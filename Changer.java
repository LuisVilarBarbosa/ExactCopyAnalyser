import java.io.File;
import java.util.ArrayList;

public class Changer {

    public static void deleteFirstFileInList(ArrayList<ArrayList<File>> files, Menu menu) {
        for (ArrayList<File> list : files) {
            if (list.size() >= 1) {
                File f = list.get(0);
                if (f.isFile())
                    if (!f.delete())
                        menu.display(menu.getText().getDeletionErrorMsg(f.getAbsolutePath()));
            }
        }
    }
}
