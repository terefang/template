import com.github.terefang.template_cli.TemplateCliMain;

public class TestDao {
    static String[] ARGS = {
            "-T", "GROOVY", "-M", "STD",
            "-D", "examples/simple.out",
            "-S", "examples/simple.gst",
            "--jdbc-user", "root",
            "--jdbc-pass", "sa",
            "--jdbc-url", "jdbc:mysql://127.1.1.1:6612/",
            "--jdbc-driver", "com.mysql.jdbc.Driver" };
    public static void main(String[] args) {
        TemplateCliMain.main(ARGS);
    }
}
