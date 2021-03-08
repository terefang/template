package com.github.terefang.template_maven_plugin.util;

import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.soulwing.snmp.*;

import java.util.Collections;
import java.util.List;

public class SnmpHelper {
    @SneakyThrows
    public static void main(String[] args) {
        SimpleSnmpV2cTarget _target = new SimpleSnmpV2cTarget();
        _target.setAddress("192.168.0.66");
        _target.setCommunity("public");
        _target.setPort(161);
        SnmpContext _snmp = SnmpFactory.getInstance().newContext(_target);
        SnmpWalker<VarbindCollection> _res = _snmp.walk(".1.3.6");
        SnmpResponse<VarbindCollection> _n;
        boolean doIt=true;
        while(doIt)
        {
            try {
                _n = _res.next();
                for (Varbind _vb : _n.get().asList())
                {
                    System.out.format("%s = %s\n", _vb.getName(), vbToString(_vb));
                }
            }
            catch (SnmpException _se)
            {
                doIt = false;
            }
        }
    }

    static String vbToString(Varbind _vb)
    {
        switch (_vb.getSyntax())
        {
            case 2:
            case 65:
            case 66:
            case 67:
            case 70:
                return ""+_vb.asLong();
            case 4:
                String _str = "'"+new String((byte[])_vb.toObject())+"'";
                if(!StringUtils.isAsciiPrintable(_str)) _str="["+Hex.encodeHexString((byte[])_vb.toObject())+"]";
                return _str;
            case 64:
                return "["+Hex.encodeHexString((byte[])_vb.toObject())+"]";
            default:
                return _vb.getSyntax()+":"+_vb.toObject().getClass().getName()+":"+_vb.toString();
        }
    }
}
