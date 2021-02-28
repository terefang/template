<%
import com.github.terefang.imageutil.PixelImage;
import com.github.terefang.imageutil.SvgImage;
import com.github.terefang.imageutil.ImageUtil;

def _font = ImageUtil.getFont('dialog', 10);

SvgImage _im = SvgImage.create(200, 200);

_im.gDashedLine(0,0,200,200,(float)4, (int)0x55000000, (float)5);

_im.gString(_font, 50,50, 'Hello World !',(int)0xffff0000);

// _im.savePng(_out_dir+'/gfx-'+_id+'.png');

_im.save(_out_dir+'/gfx-'+_id+'.svg');
%>

image::gfx-<%=_id%>.svg[]