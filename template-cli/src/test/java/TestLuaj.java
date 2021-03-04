import com.github.terefang.template_cli.TemplateCliMain;

public class TestLuaj {
    static String[] ARGS = {
            "-T", "LUAJ", "-M", "STD",
            "-D", "examples/lua.out",
            "-S", "examples/test.lua",
            "--additional-variables", "_out_dir=examples" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
