import com.github.terefang.template_cli.TemplateCliMain;

public class TestGSimple
{
    static String[] ARGS = {
            "-T", "GSIMPLE", "-M", "TEMPLATE",
            "-D", "examples/test-data.gst.out",
            "-S", "examples/data.pdx",
            "-F", "examples/test-data.gst"
            };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }

    static String[] ARGS_2 = {
            "-T", "GSIMPLE", "-M", "STD",
            "-D", "examples/test.gst.out",
            "-S", "examples/test.gst",
            "--additional-variables", "_out_dir=examples" };
    public static void main_2(String[] args) {
        TemplateCliMain.main(ARGS_2);
    }
}
