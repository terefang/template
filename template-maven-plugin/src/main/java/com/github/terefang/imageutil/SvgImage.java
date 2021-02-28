package com.github.terefang.imageutil;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.codehaus.plexus.util.IOUtil;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;

public class SvgImage implements GfxInterface
{
    SVGGraphics2D g2d;
    public static final SvgImage create(int _width, int _height) {
        return new SvgImage(_width, _height);
    }

    public SvgImage(int width, int height)
    {
        super();
        g2d = new SVGGraphics2D(width, height);
    }

    @Override
    public Graphics2D getG2d() {
        return (Graphics2D) this.g2d.create();
    }

    @Override
    public void gSet(int _x, int _y, int _color) {
        Graphics2D _g = this.getG2d();
        _g.setColor(ImageUtil.createColor(_color));
        _g.fillRect(_x, _y, 1, 1);
        _g.dispose();
    }

    @Override
    public int gGet(int _x, int _y) {
        return 0;
    }

    @SneakyThrows
    public void save(String _path) {
        this.save(new File(_path));
    }

    @SneakyThrows
    public void save(File _out)
    {
        FileWriter _fh = new FileWriter(_out);
        IOUtil.copy(this.g2d.getSVGDocument(), _fh);
        IOUtil.close(_fh);
    }
}
