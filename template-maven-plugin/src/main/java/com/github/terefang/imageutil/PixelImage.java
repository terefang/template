package com.github.terefang.imageutil;

import lombok.SneakyThrows;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PixelImage extends BufferedImage
{
    public static final int ALPHA_OPAQUE = 0xff000000;
    public static final int ALPHA_MASK = 0xff000000;
    public static final int COLOR_MASK = 0x00ffffff;
    public static final int ALPHA_TRANSPARENT = 0x00000000;

    public static final int WHITE = 0xffffffff;
    public static final int BLACK = 0xffffffff;

    public static final int RED = 0xffff0000;
    public static final int GREEN = 0xff00ff00;
    public static final int BLUE = 0xff0000ff;
    public static final int YELLOW = 0xffffff00;
    public static final int MAGENTA = 0xffff00ff;
    public static final int CYAN = 0xff00ffff;

    public static final int HALF_WHITE = 0x88ffffff;
    public static final int HALF_RED = 0x88ff0000;
    public static final int HALF_GREEN = 0x8800ff00;
    public static final int HALF_BLUE = 0x880000ff;
    public static final int HALF_YELLOW = 0x88ffff00;
    public static final int HALF_MAGENTA = 0x88ff00ff;
    public static final int HALF_CYAN = 0x8800ffff;

    public static final PixelImage create(int _width, int _height)
    {
        return new PixelImage(_width, _height);
    }

    public PixelImage(int _width, int _height)
    {
        super(_width, _height, BufferedImage.TYPE_INT_ARGB);
    }

    public void set(int _x, int _y, int _color)
    {
        if(_x<0 || _y<0 || _x>=this.getWidth() || _y>=this.getHeight()) return;

        this.setRGB(_x, _y, _color);
    }

    public void set(int _x, int _y, float _r, float _g, float _b)
    {
        this.set(_x, _y, ((0xff)<<24) | (((int)(_r*255))<<16) | (((int)(_g*255))<<8) | ((int)(_b*255)));
    }

    public void set(int _x, int _y, float _r, float _g, float _b, float _a)
    {
        this.set(_x, _y, (((int)(_a*255))<<24) | (((int)(_r*255))<<16) | (((int)(_g*255))<<8) | ((int)(_b*255)));
    }

    public void lineXY(int _xf, int _yf,int _xt, int _yt, int _color)
    {
        Graphics _g = this.getGraphics();
        _g.setColor(ImageUtil.createColor(_color));
        _g.drawLine(_xf,_yf,_xt,_yt);
        _g.dispose();
    }

    public void textXY(int _x, int _y, int _color, String _font, int _size, String _text)
    {
        Graphics _g = this.getGraphics();
        _g.setColor(ImageUtil.createColor(_color));
        _g.setFont(ImageUtil.getFont(_font, _size));
        _g.drawString(_text,_x,_y);
        _g.dispose();
    }

    @SneakyThrows
    public void savePng(String _path)
    {
        this.saveAs("png", new File(_path));
    }

    @SneakyThrows
    public void savePng(File _out)
    {
        this.saveAs("png", _out);
    }

    @SneakyThrows
    public void saveAs(String _type, File _out)
    {
        ImageIO.write(this, _type, _out);
    }
}
