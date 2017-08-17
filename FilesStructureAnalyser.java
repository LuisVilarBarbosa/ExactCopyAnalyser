public class FilesStructureAnalyser {

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: java FilesStructureAnalyser");
            return;
        }

        UserInterface userInterface = new UserInterface();
        userInterface.start();
    }
}
