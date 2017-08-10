public class ExactCopyAnalyser {

    public static void main(String[] args) {
        if (args.length != 0) {
            System.out.println("Usage: java ExactCopyAnalyser");
            return;
        }

        Menu menu = new Menu();
        menu.start();
    }
}
