import com.github.terefang.template_cli.TemplateCliMain;

public class TestJexlXls {
    static String[] ARGS = {
            "-T", "JEXL", "-M", "STD",
            "-D", "examples/jexl.xlsx",
            "-S", "examples/dao.jexl",
            "--output-type", "BIN"

    };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
