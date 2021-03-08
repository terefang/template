import com.github.terefang.template_cli.TemplateCliMain;

public class TestEcma {
    static String[] ARGS = {
            "-T", "ECMA", "-M", "STD",
            "-D", "examples/esp.out",
            "-S", "examples/test.esp"
            };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
