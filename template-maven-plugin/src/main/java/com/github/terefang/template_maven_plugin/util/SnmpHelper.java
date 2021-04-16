package com.github.terefang.template_maven_plugin.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.soulwing.snmp.*;

import java.util.List;
import java.util.Vector;

@Slf4j
public class SnmpHelper {

    public static SnmpContext connectV1(String _host, int _port, String _comm)
    {
        SimpleSnmpV1Target _target = new SimpleSnmpV1Target();
        _target.setAddress(_host);
        _target.setCommunity(_comm);
        _target.setPort(_port);
        return SnmpFactory.getInstance().newContext(_target);
    }

    public static SnmpContext connectV2(String _host, int _port, String _comm)
    {
        SimpleSnmpV2cTarget _target = new SimpleSnmpV2cTarget();
        _target.setAddress(_host);
        _target.setCommunity(_comm);
        _target.setPort(_port);
        return SnmpFactory.getInstance().newContext(_target);
    }

    public static List<Object[]> walk(SnmpContext _snmp, String _oid)
    {
        List<Object[]> _ret = new Vector<>();
        SnmpWalker<VarbindCollection> _res = _snmp.walk(1,_oid);
        SnmpResponse<VarbindCollection> _n;
        boolean doIt=true;
        while(doIt)
        {
            try {
                _n = _res.next();
                for (Varbind _vb : _n.get().asList())
                {
                    _ret.add(new Object[]{ _vb.getName(), vbToType(_vb), vbToString(_vb)});
                }
            }
            catch (SnmpException _se)
            {
                doIt = false;
                log.warn(_se.getMessage());
            }
        }
        return _ret;
    }

    public static List<Object[]> walkNext(SnmpContext _snmp, String _oid)
    {
        List<Object[]> _ret = new Vector<>();
        boolean doIt=true;
        while(doIt)
        {
            try {
                SnmpResponse<VarbindCollection> _n = _snmp.getNext(_oid);
                for (Varbind _vb : _n.get().asList())
                {
                    _ret.add(new Object[]{ _vb.getName(), vbToType(_vb), vbToString(_vb)});
                    _oid = _vb.getName();
                }
            }
            catch (Exception _se)
            {
                doIt = false;
                log.warn(_se.getMessage());
            }
        }
        return _ret;
    }

    @SneakyThrows
    public static void main(String[] args) {
        SnmpContext _snmp = connectV1("192.168.1.48", 161, "public");
        List<Object[]> _vars = walkNext(_snmp, "1.3.6.1.4.1");
        for(Object[] _row : _vars)
        {
            System.out.format("%s (%s)= %s\n", _row[0], _row[1], _row[2]);
        }
    }

    static String vbToType(Varbind _vb)
    {
        switch (_vb.getSyntax())
        {
            case 6:
                return "oid";
            case 2:
            case 65:
            case 66:
            case 67:
            case 70:
                return "long";
            case 4:
                String _str = "string";
                if(!StringUtils.isAsciiPrintable(new String((byte[])_vb.toObject()))) _str="hex";
                return _str;
            case 64:
                return "hex";
            default:
                return Integer.toString(_vb.getSyntax());
        }
    }

    static String vbToString(Varbind _vb)
    {
        switch (_vb.getSyntax())
        {
            case 6:
                return StringUtils.join(((int[])_vb.toObject()), '.');
            case 2:
            case 65:
            case 66:
            case 67:
            case 70:
                return ""+_vb.asLong();
            case 4:
                String _str = new String((byte[])_vb.toObject());
                if(!StringUtils.isAsciiPrintable(_str)) _str=Hex.encodeHexString((byte[])_vb.toObject());
                return _str;
            case 64:
                return Hex.encodeHexString((byte[])_vb.toObject());
            default:
                return _vb.toObject().getClass().getName()+":"+_vb.toString();
        }
    }
}
