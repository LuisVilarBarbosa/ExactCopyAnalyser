public class ExactCopyAnalyser {

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: java ExactCopyAnalyser");
            return;
        }

        UserInterface userInterface = new UserInterface();
        userInterface.start();
    }
}
