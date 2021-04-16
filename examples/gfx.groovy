
def _im = _.svg(200, 200);

def _font = _im.getTTFont('/data/fredo/_fontz/_pub/Arimo/Arimo-Regular.ttf', (int)10);

_im.beginGroup('id-group-1');
_im.gDashedLine(0,0,200,200,(float)4, (int)0x55000000, (float)5);

_im.gString(_font, 50,50, 'Hello World !',(int)0xffff0000);


def _sub = _im.getSub('id-dashed-group-1');
_sub.gDashedLine(0,0,200,200,(float)4, (int)0x55000000, (float)5);
_sub.dispose();

_im.endGroup();

def _sub1 = _im.getSub('id-dashed-group-2');
def _sub2 = _sub1.getSub('id-dashed-group-3');
_sub2.gDashedLine(0,0,200,200,(float)4, (int)0x55000000, (float)5);
_sub2.dispose();
_sub1.dispose();

// _im.savePng(_out_dir+'/gfx-'+_id+'.png');

return _im;