import com.github.terefang.template_cli.TemplateCliMain;

public class TestScriptingWithArgs {
    static String[] ARGS = {
            "-T", "SCRIPT", "-M", "SCRIPT",
            "-D", "examples/",
            "-S", "examples/with_args.rb",
            "--additional-context-file", "examples/data.pdx",
            "--additional-variables", "_out_dir=examples",
            "--",
            "this",
            "are",
            "additional",
            "args",
    };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
