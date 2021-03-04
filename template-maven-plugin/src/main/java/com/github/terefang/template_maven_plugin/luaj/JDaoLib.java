package com.github.terefang.template_maven_plugin.luaj;

import com.github.terefang.jdao.JDAO;
import com.github.terefang.jdao.JdaoUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class JDaoLib extends TwoArgFunction
{
    public JDaoLib() {
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaTable dao = new LuaTable();
        dao.set("connect", new _connect());
        dao.set("sqlite", new _sqlite());
        dao.set("mysql", new _mysql());
        dao.set("mariadb", new _mariadb());
        dao.set("pgsql", new _pgsql());
        env.set("jdao", dao);
        if (!env.get("package").isnil()) env.get("package").get("loaded").set("jdao", dao);
        return dao;
    }

    static final class _connect extends VarArgFunction
    {
        @Override
        @SneakyThrows
        public Varargs invoke(Varargs _args)
        {
            JDAO _dao = null;
            Connection _conn = null;
            if(_args.narg()==1)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("", _args.checkjstring(1), "", "");
            }
            else
            if(_args.narg()==2)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec(_args.checkjstring(1), _args.checkjstring(2), "", "");
            }
            else
            if(_args.narg()==3)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec(_args.checkjstring(1), _args.checkjstring(2), _args.checkjstring(3), "");
            }
            else
            if(_args.narg()==4)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec(_args.checkjstring(1), _args.checkjstring(2), _args.checkjstring(3), _args.checkjstring(4));
            }
            else
            {
                throw new IllegalArgumentException(""+_args.narg());
            }

            _dao = JDAO.createDaoFromConnection(_conn, false);
            return CoerceJavaToLua.coerce(new jdaoObject(_dao));
        }
    }

    static final class _sqlite extends VarArgFunction
    {
        @Override
        @SneakyThrows
        public Varargs invoke(Varargs _args)
        {
            JDAO _dao = null;
            Connection _conn = null;
            if(_args.narg()==1)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("", "jdbc:sqlite:"+_args.checkjstring(1), "", "");
            }
            else
            if(_args.narg()==2)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("", "jdbc:sqlite:"+_args.checkjstring(1), _args.checkjstring(2), "");
            }
            else
            if(_args.narg()==3)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("", "jdbc:sqlite:"+_args.checkjstring(1), _args.checkjstring(2), _args.checkjstring(3));
            }
            else
            {
                throw new IllegalArgumentException(""+_args.narg());
            }

            _dao = JDAO.createDaoFromConnection(_conn, false);
            _dao.setDbType(JDAO.DB_TYPE_SQLITE);
            return CoerceJavaToLua.coerce(new jdaoObject(_dao));
        }
    }

    static final class _mysql extends VarArgFunction
    {
        @Override
        @SneakyThrows
        public Varargs invoke(Varargs _args)
        {
            JDAO _dao = null;
            Connection _conn = null;
            if(_args.narg()==1)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("com.mysql.jdbc.Driver", "jdbc:mysql://"+_args.checkjstring(1), "", "");
            }
            else
            if(_args.narg()==2)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("com.mysql.jdbc.Driver", "jdbc:mysql://"+_args.checkjstring(1), _args.checkjstring(2), "");
            }
            else
            if(_args.narg()==3)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("com.mysql.jdbc.Driver", "jdbc:mysql://"+_args.checkjstring(1), _args.checkjstring(2), _args.checkjstring(3));
            }
            else
            {
                throw new IllegalArgumentException(""+_args.narg());
            }

            _dao = JDAO.createDaoFromConnection(_conn, false);
            _dao.setDbType(JDAO.DB_TYPE_MYSQL);
            return CoerceJavaToLua.coerce(new jdaoObject(_dao));
        }
    }

    static final class _mariadb extends VarArgFunction
    {
        @Override
        @SneakyThrows
        public Varargs invoke(Varargs _args)
        {
            JDAO _dao = null;
            Connection _conn = null;
            if(_args.narg()==1)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("org.mariadb.jdbc.Driver", "jdbc:mariadb://"+_args.checkjstring(1), "", "");
            }
            else
            if(_args.narg()==2)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("org.mariadb.jdbc.Driver", "jdbc:mariadb://"+_args.checkjstring(1), _args.checkjstring(2), "");
            }
            else
            if(_args.narg()==3)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("org.mariadb.jdbc.Driver", "jdbc:mariadb://"+_args.checkjstring(1), _args.checkjstring(2), _args.checkjstring(3));
            }
            else
            {
                throw new IllegalArgumentException(""+_args.narg());
            }

            _dao = JDAO.createDaoFromConnection(_conn, false);
            _dao.setDbType(JDAO.DB_TYPE_MYSQL);
            return CoerceJavaToLua.coerce(new jdaoObject(_dao));
        }
    }

    static final class _pgsql extends VarArgFunction
    {
        @Override
        @SneakyThrows
        public Varargs invoke(Varargs _args)
        {
            JDAO _dao = null;
            Connection _conn = null;
            if(_args.narg()==1)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("org.postgresql.Driver", "jdbc:postgresql://"+_args.checkjstring(1), "", "");
            }
            else
            if(_args.narg()==2)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("org.postgresql.Driver", "jdbc:postgresql://"+_args.checkjstring(1), _args.checkjstring(2), "");
            }
            else
            if(_args.narg()==3)
            {
                _conn = JdaoUtils.createConnectionByDataSourceSpec("org.postgresql.Driver", "jdbc:postgresql://"+_args.checkjstring(1), _args.checkjstring(2), _args.checkjstring(3));
            }
            else
            {
                throw new IllegalArgumentException(""+_args.narg());
            }

            _dao = JDAO.createDaoFromConnection(_conn, false);
            _dao.setDbType(JDAO.DB_TYPE_POSTGRES);
            return CoerceJavaToLua.coerce(new jdaoObject(_dao));
        }
    }

    @Slf4j
    static final class jdaoObject
    {
        JDAO _dao;

        public jdaoObject(JDAO dao)
        {
            this();
            this._dao = dao;
        }

        public jdaoObject()
        {
            super();
        }

        @SneakyThrows
        public LuaTable queryForMapList(String _query, List _param)
        {
            List<Map<String, Object>> _res = _dao.queryForMapList(_query, _param);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMapList(String _query, Map _param)
        {
            List<Map<String, Object>> _res = _dao.queryForMapList(_query, _param);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMapList(String _query, String _param)
        {
            List<Map<String, Object>> _res = _dao.queryForMapList(_query, _param);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMapList(String _query)
        {
            List<Map<String, Object>> _res = _dao.queryForMapList(_query);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMap(String _query, List _param)
        {
            Map<String, Object> _res = _dao.queryForMap(_query, _param);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMap(String _query, Map _param)
        {
            Map<String, Object> _res = _dao.queryForMap(_query, _param);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMap(String _query, String _param)
        {
            Map<String, Object> _res = _dao.queryForMap(_query, _param);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForMap(String _query)
        {
            Map<String, Object> _res = _dao.queryForMap(_query);
            return resToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForColumnList(String _query, List _param)
        {
            List<Object> _res = _dao.queryForColumnList(_query, _param);
            return listToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForColumnList(String _query, Map _param)
        {
            List<Object> _res = _dao.queryForColumnList(_query, _param);
            return listToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForColumnList(String _query, String _param)
        {
            List<Object> _res = _dao.queryForColumnList(_query, _param);
            return listToLuaTable(_res);
        }

        @SneakyThrows
        public LuaTable queryForColumnList(String _query)
        {
            List<Object> _res = _dao.queryForColumnList(_query);
            return listToLuaTable(_res);
        }

        @SneakyThrows
        public LuaValue queryForScalar(String _query, List _param)
        {
            Object _res = _dao.queryForScalar(_query, _param);
            return CoerceJavaToLua.coerce(_res);
        }

        @SneakyThrows
        public LuaValue queryForScalar(String _query, Map _param)
        {
            Object _res = _dao.queryForScalar(_query, _param);
            return CoerceJavaToLua.coerce(_res);
        }

        @SneakyThrows
        public LuaValue queryForScalar(String _query, String _param)
        {
            Object _res = _dao.queryForScalar(_query, _param);
            return CoerceJavaToLua.coerce(_res);
        }

        @SneakyThrows
        public LuaValue queryForScalar(String _query)
        {
            Object _res = _dao.queryForScalar(_query);
            return CoerceJavaToLua.coerce(_res);
        }

        public void close()
        {
            this._dao.close();
            this._dao = null;
        }

        static LuaTable listToLuaTable(List<Object> _res)
        {
            LuaTable _ret = new LuaTable();
            int _i = 1;
            for(Object _row : _res)
            {
                _ret.set(_i, CoerceJavaToLua.coerce(_row));
                _i++;
            }
            return _ret;
        }

        static LuaTable resToLuaTable(List<Map<String, Object>> _res)
        {
            LuaTable _ret = new LuaTable();
            int _i = 1;
            for(Map<String, Object> _row : _res)
            {
                _ret.set(_i, resToLuaTable(_row));
                _i++;
            }
            return _ret;
        }

        static LuaTable resToLuaTable(Map<String, Object> _row)
        {
            LuaTable _lrow = new LuaTable();
            for(Map.Entry<String, Object> _entry : _row.entrySet())
            {
                _lrow.set(_entry.getKey(), CoerceJavaToLua.coerce(_entry.getValue()));
            }
            return _lrow;
        }
    }


}
