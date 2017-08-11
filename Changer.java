import java.io.File;
import java.util.ArrayList;

public class Changer {

    public void deleteFirstFileInList(ArrayList<ArrayList<File>> files) {
        for (ArrayList<File> list : files) {
            if (list.size() >= 1) {
                File f = list.get(0);
                if (f.isFile())
                    if (!f.delete())
                        System.out.println("It was not possible to remove '" + f.getAbsolutePath() + "'.");
            }
        }
    }
}
