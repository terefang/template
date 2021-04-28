import com.github.terefang.template_cli.TemplateCliMain;

public class TestScripting {
    static String[] ARGS = {
            "-T", "SCRIPT", "-M", "SCRIPT",
            "-D", "examples/",
            "-S", "examples/script.py",
            "--additional-context-file", "examples/data.pdx",
            "--additional-variables", "_out_dir=examples" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
    static String[] ARGS2 = {
            "-T", "SCRIPT", "-M", "SCRIPT",
            "-D", "examples/",
            "-S", "examples/script.groovy",
            "--additional-context-file", "examples/data.pdx",
            "--additional-variables", "_out_dir=examples" };
    public static void main2(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
