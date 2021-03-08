import com.github.terefang.template_cli.TemplateCliMain;

public class TestLuajImage {
    static String[] ARGS = {
            "-T", "LUAJ",
            "-D", "examples/lua.svg",
            "--output-type", "SVG",
            "-S", "examples/gfx.lua"
    };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
