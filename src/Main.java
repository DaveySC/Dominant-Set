import java.io.Console;

public class Main {

    private void showInfo() {

    }
    public static void main(String[] args) throws Exception {
        Application application = new Application("input.txt", "output.txt");
        application.start();
    }

}