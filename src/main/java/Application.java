import cmd.Cmd;
import picocli.CommandLine;

public class Application {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new Cmd()).execute(args);
        System.exit(exitCode);
    }
}
