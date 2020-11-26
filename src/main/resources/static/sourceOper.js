function inputUploadClick(){
    document.getElementById('inputUpload').click();
}

//当文件选择框改变时，就上传文件，里面发Ajax请求到处理层
function uploadFile(){
    $.ajaxFileUpload({
        fileElementId:'inputUpload',  //要上传的文件的id
        url:'/source/upload',  //请求的地址，
        type:'post',        //请求的方式
        dataType:'json',    //返回数据时的格式，也可以将Text改为json，这个要看具体需求
        secureuri: false,  //是否需要安全协议，一般设置为false
        async : true,      //是否是异步
        success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
            if($.trim(data) != null){
                alert("上传成功！");
            }
        },
        error: function(data, status, e) {  //提交失败自动执行的处理函数。
            alert("上传失败！");
        }
    });
}

function newFload() {

}