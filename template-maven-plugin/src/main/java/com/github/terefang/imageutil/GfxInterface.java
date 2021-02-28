package com.github.terefang.imageutil;

import java.awt.*;

public interface GfxInterface
{
    public Graphics2D getG2d();

    public void gSet(int _x, int _y, int _color);
    public int gGet(int _x, int _y);

    default void gLine(int _x1, int _y1, int _x2, int _y2, int _color)
    {
        gLine(_x1, _y1, _x2, _y2, 1, _color);
    }

    default void gLine(int _x1, int _y1, int _x2, int _y2, float _lineWidth, int _color)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        Stroke _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
        _g.setStroke(_s);
        _g.drawLine(_x1, _y1, _x2, _y2);
        _g.dispose();
    }

    default void gDashedLine(int _x1, int _y1, int _x2, int _y2, int _color, float... _shape)
    {
        gDashedLine(_x1, _y1, _x2, _y2, 1, _color, _shape);
    }

    default void gDashedLine(int _x1, int _y1, int _x2, int _y2, float _lineWidth, int _color, float... _shape)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        Stroke _dashed = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _shape, 0);
        _g.setStroke(_dashed);
        _g.drawLine(_x1, _y1, _x2, _y2);
        _g.dispose();
    }

    default void gRectangle(int _x1, int _y1, int _x2, int _y2, int _color)
    {
        gRectangle(false, _x1, _y1, _x2, _y2, 1, _color, null);
    }

    default void gRectangle(int _x1, int _y1, int _x2, int _y2, float _lineWidth, int _color, float[] _dash)
    {
        gRectangle(false, _x1, _y1, _x2, _y2, _lineWidth, _color, null);
    }

    default void gRectangle(boolean _fill, int _x1, int _y1, int _x2, int _y2, float _lineWidth, int _color, float[] _dash)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        if(!_fill)
        {
            Stroke _s = null;
            if(_dash == null || _dash.length==0)
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
            }
            else
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
            }
            _g.setStroke(_s);
            _g.drawRect(_x1,_y1, _x2-_x1, _y2-_y1);
        }
        else
        {
            _g.fillRect(_x1,_y1, _x2-_x1, _y2-_y1);
        }
        _g.dispose();
    }

    default void gFilledRectangle(int _x1, int _y1, int _x2, int _y2, int _color)
    {
        gRectangle(true, _x1, _y1, _x2, _y2, 1, _color, null);
    }

    default void gFilledRectangle(int _x1, int _y1, int _x2, int _y2, float _lineWidth, int _color)
    {
        gRectangle(true, _x1, _y1, _x2, _y2, _lineWidth, _color, null);
    }

    default void gPolygon(int _color, int... _points)
    {
        gPolygon(1, _color, null, _points);
    }

    default void gPolygon(float _lineWidth, int _color, float[] _dash, int... _points)
    {
        this.gPolygon(false,_lineWidth, _color, _dash, _points);
    }

    default void gPolygon(boolean _fill, float _lineWidth, int _color, float[] _dash, int... _points)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        Polygon _p = new Polygon();
        for(int _i = 0; _i<_points.length; _i+=2)
        {
            _p.addPoint(_points[_i], _points[_i+1]);
        }
        if(!_fill)
        {
            Stroke _s = null;
            if(_dash == null || _dash.length==0)
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
            }
            else
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
            }
            _g.setStroke(_s);
            _g.drawPolygon(_p);
        }
        else
        {
            _g.fillPolygon(_p);
        }
        _g.dispose();
    }

    default void gFilledPolygon(int _color, int... _points)
    {
        gFilledPolygon(1, _color, _points);
    }

    default void gFilledPolygon(float _lineWidth, int _color, int... _points)
    {
        this.gPolygon(true,_lineWidth, _color, null, _points);
    }

    default void gPolyline(int _color, int... _points)
    {
        gPolyline(1, _color, null, _points);
    }

    default void gPolyline(float _lineWidth, int _color, int... _points)
    {
        gPolyline(_lineWidth, _color, null, _points);
    }

    default void gPolyline(float _lineWidth, int _color, float[] _dash, int... _points)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        Stroke _s = null;
        if(_dash == null || _dash.length==0)
        {
            _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
        }
        else
        {
            _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
        }
        _g.setStroke(_s);
        int _lx = _points[0];
        int _ly = _points[1];
        for(int _i = 2; _i<_points.length; _i+=2)
        {
            _g.drawLine(_lx,_ly,_points[_i], _points[_i+1]);
            _lx = _points[_i];
            _ly = _points[_i+1];
        }
        _g.dispose();
    }

    default void gCircle(int _x, int _y, int _r, int _color)
    {
        this.gOval(_x, _y, _r, _r, 1, _color, null);
    }

    default void gCircle(int _x, int _y, int _r, float _lineWidth, int _color)
    {
        this.gOval(_x, _y, _r, _r, _lineWidth, _color, null);
    }

    default void gCircle(int _x, int _y, int _r, float _lineWidth, int _color, float[] _dash)
    {
        this.gOval(false, _x, _y, _r, _r, _lineWidth, _color, _dash);
    }

    default void gCircle(boolean _fill, int _x, int _y, int _r, float _lineWidth, int _color, float[] _dash)
    {
        this.gOval(_fill, _x, _y, _r, _r, _lineWidth, _color, _dash);
    }

    default void gFilledCircle(int _x, int _y, int _r, int _color)
    {
        this.gCircle(true, _x, _y, _r, 1, _color, null);
    }

    default void gOval(int _x, int _y, int _ra, int _rb, int _color)
    {
        this.gOval(_x, _y, _ra, _rb, 1, _color, null);
    }

    default void gOval(int _x, int _y, int _ra, int _rb, float _lineWidth, int _color, float[] _dash)
    {
        this.gOval(false, _x, _y, _ra, _rb, 1, _color, null);
    }

    default void gOval(boolean _fill, int _x, int _y, int _ra, int _rb, float _lineWidth, int _color, float[] _dash)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        if(!_fill)
        {
            Stroke _s = null;
            if(_dash == null || _dash.length==0)
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
            }
            else
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
            }
            _g.setStroke(_s);
            _g.drawOval(_x-_ra, _y-_rb, _ra*2,_rb*2);
        }
        else
        {
            _g.fillOval(_x-_ra, _y-_rb,_ra*2,_rb*2);
        }
        _g.dispose();
    }

    default void gFilledOval(int _x, int _y, int _ra, int _rb, int _color)
    {
        this.gOval(true, _x, _y, _ra, _rb, 1, _color, null);
    }

    default void gArc(int _x, int _y, int _ra, int _rb, int _as, int _ae, int _color)
    {
        gArc(false, _x, _y, _ra, _rb, _as, _ae,1, _color, null);
    }

    default void gArc(boolean _fill, int _x, int _y, int _ra, int _rb, int _as, int _ae, float _lineWidth, int _color, float[] _dash)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        if(!_fill)
        {
            Stroke _s = null;
            if(_dash == null || _dash.length==0)
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0);
            }
            else
            {
                _s = new BasicStroke(_lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, _dash, 0f);
            }
            _g.setStroke(_s);
            _g.drawArc(_x-_ra, _y-_rb, _ra*2,_rb*2, _as, _ae-_as);
        }
        else
        {
            _g.fillArc(_x-_ra, _y-_rb, _ra*2,_rb*2, _as, _ae-_as);
        }
        _g.dispose();
    }

    default void gFilledArc(int _x, int _y, int _ra, int _rb, int _as, int _ae, int _color)
    {
        gArc(true, _x, _y, _ra, _rb, _as, _ae,1, _color, null);
    }

    default void gString(String _font, int _size, int _x, int _y, String _s, int _color)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        _g.setFont(ImageUtil.getFont(_font, _size));
        _g.drawString(_s, _x, _y);
        _g.dispose();
    }

    default void gString(Font _font, int _x, int _y, String _s, int _color)
    {
        Graphics2D _g = (Graphics2D) this.getG2d();
        _g.setPaint(ImageUtil.createColor(_color));
        _g.setFont(_font);
        _g.drawString(_s, _x, _y);
        _g.dispose();
    }

}
