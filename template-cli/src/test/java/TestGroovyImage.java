import com.github.terefang.template_cli.TemplateCliMain;

public class TestGroovyImage {
    static String[] ARGS = {
            "-T", "GROOVY", "-M", "STD",
            "-D", "examples/groovy.svg",
            "-S", "examples/gfx.groovy",
            "--output-type", "SVG" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
