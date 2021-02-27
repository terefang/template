package com.github.terefang.imageutil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil
{
    public static final Map<String, Font> FONT_MAP = new HashMap<>();
    public static Color createColor(int _color)
    {
        return new Color(_color);
    }

    public static Font getFont(String _name, int _size)
    {
        String _l = String.format("%s/%d", _name, _size);
        if(!FONT_MAP.containsKey(_l))
        {
            FONT_MAP.put(_l,Font.getFont(_name).deriveFont((float)_size));
        }
        return FONT_MAP.get(_l);
    }

}
