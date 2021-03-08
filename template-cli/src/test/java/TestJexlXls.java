import com.github.terefang.template_cli.TemplateCliMain;

public class TestJexlXls {
    static String[] ARGS = {
            "-T", "JEXL", "-M", "STD",
            "-D", "examples/jexl-dao2.txt",
            "-S", "examples/dao2.jexl",
            "--output-type", "TEXT"

    };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
