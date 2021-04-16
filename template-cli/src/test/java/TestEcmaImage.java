import com.github.terefang.template_cli.TemplateCliMain;

public class TestEcmaImage {
    static String[] ARGS = {
            "-T", "ECMA", "-M", "STD",
            "-D", "examples/gfx.ecma.svg",
            "-S", "examples/gfx.ecma",
            "--output-type", "SVG" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
