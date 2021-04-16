import com.github.terefang.template_cli.TemplateCliMain;

public class TestGSimple {
    static String[] ARGS = {
            "-T", "GSIMPLE", "-M", "STD",
            "-D", "examples/test.gst.out",
            "-S", "examples/test.gst",
            "--additional-variables", "_out_dir=examples" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
