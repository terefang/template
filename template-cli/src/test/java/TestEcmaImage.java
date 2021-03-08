import com.github.terefang.template_cli.TemplateCliMain;

public class TestEcmaImage {
    static String[] ARGS = {
            "-T", "ECMA", "-M", "STD",
            "-D", "examples/ecma.pdf",
            "-S", "examples/gfx.ecma",
            "--output-type", "PDF" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
