-- init img
if _output_type == "svg" then
    _im = _:lvalue(_:svgImage(20,20));
elseif _output_type == "png" then
    _im = _:lvalue(_:pixImage(20,20));
end

_font = _im:getTTFont('/data/fredo/_fontz/_pub/Arimo/Arimo-Regular.ttf', 10);


_im:beginGroup('id-group-1');

_im:gDashedLine(0,0,200,200,4.0f, 0x55000000, { 5.0f } );

_im:gString(_font, 50,50, 'Hello World !',0xffff0000);

_sub = _:lvalue(_im:getSub('id-dashed-group-1'));
_sub:gDashedLine(0,0,200,200,4.0, 0x55000000, 5.0);
_sub:dispose();

_im:endGroup();

_sub1 = _:lvalue(_im:getSub('id-dashed-group-2'));
_sub2 = _:lvalue(_sub1:getSub('id-dashed-group-3'));
_sub2:gDashedLine(0,0,200,200,4.0, 0x55000000, 5.0);
_sub2:dispose();
_sub1:dispose();

return _im;