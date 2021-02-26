<%
import com.github.terefang.template_maven_plugin.util.ContextUtil;

def _json=[:];

_json.all=[:];
_json.all.hosts=[:];
_json.all.children=[:];

out.println('hello world dao='+(_dao.toString()));

def _res = _dao.queryForMapList("SELECT name,model_number from test.HARDWARE limit 0,100");

_res.each{ _row ->
    _json.all.hosts[_row.name]=[:];
    _json.all.hosts[_row.name].modelNumber=_row.model_number;
};

ContextUtil.writeAsHson(true, out, _json);
%>

{
  "all": {
    "hosts": {
      "vm1.nodekite.com": {
            "var1": "value1",
            "var2": "value2"
      },
      "vm2.nodekite.com": null
    },
    "vars": {
      "webroot": "/var/www/html",
      "index": "index.php"
    },
    "children": {
      "web": {
        "hosts": {
          "vm3.nodekite.com": null,
          "vm4.nodekite.com": null
        },
        "vars": {
                  "webroot": "/web/public_html",
                  "index": "index.html"
                }
      },
      "db": {
        "hosts": {
          "vm5.nodekite.com": null,
          "vm6.nodekite.com": null
        }
      }
    }
  }
}