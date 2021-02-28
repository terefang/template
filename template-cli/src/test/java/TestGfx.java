import com.github.terefang.template_cli.TemplateCliMain;

public class TestGfx {
    static String[] ARGS = {
            "-T", "GROOVY", "-M", "STD",
            "-D", "examples/gfx.txt",
            "-S", "examples/gfx.gst",
            "--additional-variables", "_out_dir=examples" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
