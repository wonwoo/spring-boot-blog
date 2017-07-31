/**
 * Created by wonwoo on 2017. 2. 26..
 */
$(function() {
    $("img").each(function() {
        var oImgWidth = $(this).width();
        var oImgHeight = $(this).height();
        $(this).css({
            'max-width':oImgWidth+'px',
            'max-height':oImgHeight+'px',
            'width':'100%',
            'height':'100%'
        });
    });
});