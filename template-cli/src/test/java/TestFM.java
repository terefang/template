import com.github.terefang.template_cli.TemplateCliMain;

public class TestFM {
    static String[] ARGS = {
            "-T", "FREEMARKER", "-M", "STD",
            "-D", "examples/check.fm.out",
            "-S", "examples/check.fm",
            "--additional-variables", "_out_dir=examples" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
