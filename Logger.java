import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Logger {
    private File file;
    private FileOutputStream fileOutputStream;

    public Logger(Text text) throws IOException {
        String filename;
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(Calendar.getInstance().getTime());

        do {
            filename = "FilesStructureAnalyser " + timeStamp + " " + Long.toHexString(Double.doubleToLongBits(Math.random())) + ".txt";
            file = new File(filename);
        } while (file.exists());

        this.fileOutputStream = new FileOutputStream(file);
        String loggerStartMsg = text.getLoggerStartMsg().replaceAll("\n", "\r\n");
        this.fileOutputStream.write(loggerStartMsg.getBytes());
    }

    public String getPath() {
        return file.getPath();
    }

    public void list(String message, ArrayList<ArrayList<File>> list) throws IOException {
        String myMessage = message.replaceAll("\n", "\r\n");
        StringBuilder sb = new StringBuilder();
        sb.append(myMessage).append("\r\n");
        for (ArrayList<File> l : list) {
            for (File f : l)
                sb.append(f.getAbsolutePath()).append("\r\n");
            sb.append("\r\n");
        }
        fileOutputStream.write(sb.toString().getBytes());
    }

    public void list(String message, HashMap<File, File> map) throws IOException {
        String myMessage = message.replaceAll("\n", "\r\n");
        StringBuilder sb = new StringBuilder();
        sb.append(myMessage).append("\r\n");
        for (File f1 : map.keySet()) {
            File f2 = map.get(f1);
            sb.append(f1.getAbsolutePath()).append("\r\n")
                    .append(f2.getAbsolutePath()).append("\r\n\r\n");
        }
        fileOutputStream.write(sb.toString().getBytes());
    }

    public void close() throws IOException {
        fileOutputStream.close();
    }
}
