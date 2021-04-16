import com.github.terefang.template_cli.TemplateCliMain;

public class TestGroovy {
    static String[] ARGS = {
            "-T", "GROOVY", "-M", "STD",
            "-D", "examples/test.groovy.out",
            "-S", "examples/test.groovy",
            "--additional-variables", "_out_dir=examples" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
