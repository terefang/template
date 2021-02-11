import com.github.terefang.template_cli.TemplateCliMain;

public class TestPreProc {
    static String[] ARGS = { "-T", "PREPROCESSOR", "-M", "STD", "-S", "examples/", "-D", "target/examples/", "-E", ".txt", "-I", "**/*.pp" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
