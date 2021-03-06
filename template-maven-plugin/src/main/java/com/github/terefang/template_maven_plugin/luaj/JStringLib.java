package com.github.terefang.template_maven_plugin.luaj;

import org.codehaus.plexus.util.StringUtils;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;

import java.text.MessageFormat;

public class JStringLib extends TwoArgFunction
{
    public JStringLib()
    {
    }

    @Override
    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaTable string = new LuaTable();
        string.set("split", new _split());
        string.set("explode", new _explode());
        string.set("implode", new _implode());
        string.set("join", new _implode());
        string.set("concat", new _concat());
        string.set("mformat", new _mformat());
        string.set("sformat", new _sformat());

        env.set("jstring", string);

        if (!env.get("package").isnil()) env.get("package").get("loaded").set("jstring", string);

        return string;
    }

    static final class _split extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            String[] _r = null;
            String _sep = _args.checkjstring(1);
            String _s = _args.checkjstring(2);
            if(_args.narg()==2)
            {
                _r = _s.split(_sep);
            }
            else
            if(_args.narg()==3)
            {
                int _limit = _args.checkint(3);
                _r = _s.split(_sep, _limit);
            }

            LuaValue[] v = new LuaValue[_r.length];
            for (int i=0; i<_r.length; i++)
                v[i] = LuaString.valueOf(_r[i]);

            return LuaTable.listOf(v);
        }
    }

    static final class _explode extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            String[] _r = null;
            String _sep = _args.checkjstring(1);
            if(_args.narg()==1)
            {
                _r = StringUtils.split(_sep);
            }
            else
            if(_args.narg()==2)
            {
                String _s = _args.checkjstring(2);
                _r = StringUtils.split(_s, _sep);
            }
            else
            if(_args.narg()==3)
            {
                String _s = _args.checkjstring(2);
                int _limit = _args.checkint(3);
                _r = StringUtils.split(_s, _sep, _limit);
            }

            LuaValue[] v = new LuaValue[_r.length];
            for (int i=0; i<_r.length; i++)
                v[i] = LuaString.valueOf(_r[i]);

            return LuaTable.listOf(v);
        }
    }

    static final class _implode extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            StringBuilder _sb = new StringBuilder();
            String _sep = _args.checkjstring(1);

            if(_args.narg()==2)
            {
                LuaTable _t = _args.checktable(2);

                String _s = _t.get(1).tojstring();
                _sb.append(_s);

                for (int i = 2; i <= _t.length(); i++) {
                    _s = _t.get(i).tojstring();
                    _sb.append(_sep);
                    _sb.append(_s);
                }
            }
            else
            {
                String _s = _args.checkjstring(2);
                _sb.append(_s);

                for (int i = 3; i <= _args.narg(); i++) {
                    _s = _args.checkjstring(i);
                    _sb.append(_sep);
                    _sb.append(_s);
                }
            }
            return LuaString.valueOf(_sb.toString());
        }
    }

    static final class _concat extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            StringBuilder _sb = new StringBuilder();
            if(_args.narg()==1)
            {
                LuaTable _t = _args.checktable(1);

                for (int i = 1; i <= _t.length(); i++) {
                    String _s = _t.get(i).tojstring();
                    _sb.append(_s);
                }
            }
            else
            {
                for (int i = 1; i <= _args.narg(); i++) {
                    String _s = _args.tojstring(i);
                    _sb.append(_s);
                }
            }
            return LuaString.valueOf(_sb.toString());
        }
    }

    static final class _mformat extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            String _pattern = _args.checkjstring(1);
            Object[] _objs = new Object[_args.narg()-1];

            for (int i = 2; i <= _args.narg(); i++)
            {
                if(_args.isstring(i))
                {
                    _objs[i-2] = _args.checkjstring(i);
                }
                else
                if(_args.isnumber(i) && _args.checknumber(i).isint())
                {
                    _objs[i-2] = _args.checkint(i);
                }
                else
                if(_args.isnumber(i) && _args.checknumber(i).islong())
                {
                    _objs[i-2] = _args.checklong(i);
                }
                else
                if(_args.isnumber(i) && _args.checknumber(i).isboolean())
                {
                    _objs[i-2] = _args.checkboolean(i);
                }
                else
                if(_args.isnumber(i))
                {
                    _objs[i-2] = _args.checkdouble(i);
                }
                else
                {
                    _objs[i-2] = _args.tojstring(i);
                }
            }

            return LuaString.valueOf(MessageFormat.format(_pattern, _objs));
        }
    }

    static final class _sformat extends VarArgFunction {
        @Override
        public Varargs invoke(Varargs _args)
        {
            String _pattern = _args.checkjstring(1);
            Object[] _objs = new Object[_args.narg()-1];

            for (int i = 2; i <= _args.narg(); i++)
            {
                if(_args.isstring(i))
                {
                    _objs[i-2] = _args.checkjstring(i);
                }
                else
                if(_args.isnumber(i) && _args.checknumber(i).isint())
                {
                    _objs[i-2] = _args.checkint(i);
                }
                else
                if(_args.isnumber(i) && _args.checknumber(i).islong())
                {
                    _objs[i-2] = _args.checklong(i);
                }
                else
                if(_args.isnumber(i) && _args.checknumber(i).isboolean())
                {
                    _objs[i-2] = _args.checkboolean(i);
                }
                else
                if(_args.isnumber(i))
                {
                    _objs[i-2] = _args.checkdouble(i);
                }
                else
                {
                    _objs[i-2] = _args.tojstring(i);
                }
            }

            return LuaString.valueOf(String.format(_pattern, _objs));
        }
    }
}
