var fingerprint;
function myInit() {
    if($.cookie("VID") == null){
        fingerprint =  new Fingerprint().get();
        // console.log("第一次登录！")
        $.cookie("VID", fingerprint, {
            expires : 365,
            path: '/'
        });
    }else{
        fingerprint = $.cookie("VID");
    }
}