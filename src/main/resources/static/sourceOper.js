// function inputUploadClick(){
//     document.getElementById('inputUpload').click();
// }

layui.use('upload', function(){
    var upload = layui.upload;

    //执行实例
    var uploadInst = upload.render({
        elem: '#uploadFileButton' //绑定对应的按钮id(指向容器选择器)
        ,url: '/source/upload' //上传接口
        ,data: {path:$('#curPath').val()} //请求上传接口的额外参数
        ,accept: 'file' //所有文件
        ,done: function(res){
            layui.use('layer', function(){
                var layer = layui.layer;
                layer.alert(res.desc, function (alertIndex) {
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.location.reload(); //刷新父页面
                    layer.close(alertIndex); //关闭alert窗口
                });
            });
        }
        ,error: function(){
            //请求异常回调
        }
    });
});

//当文件选择框改变时，就上传文件，里面发Ajax请求到处理层(替换成layui的上传文件组件)
function uploadFile(path){
    // $.ajaxFileUpload({
    //     fileElementId:'inputUpload',  //要上传的文件的id
    //     url:'/source/upload?path='+path,  //请求的地址，
    //     type:'post',        //请求的方式
    //     dataType:'json',    //返回数据时的格式，也可以将Text改为json，这个要看具体需求
    //     secureuri: false,  //是否需要安全协议，一般设置为false
    //     async : true,      //是否是异步
    //     success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
    //         if($.trim(data) != null){
    //             alert("上传成功！");
    //         }
    //     },
    //     error: function(data, status, e) {  //提交失败自动执行的处理函数。
    //         alert("上传失败！");
    //     }
    // });
}

function newFolder(path){
    layui.use('layer', function(){
        var layer = layui.layer;
        layer.open({
            type: 1, //页面层
            content: $("#newFolderWindow"),
            shadeClose: true, //通过点击弹窗外部区域来关闭弹窗
            scrollbar: false, //不允许出现滚动条
            title: ['创建新文件夹'],
            area: ['320px'],
            btn: ['确定', '取消'],
            btnAlign: 'c', //居中对其
            anim: 1, //弹出动画：从上掉落
            yes: function (index, layero) {
                $.getJSON('/source/newFolder', { //传递json请求
                    path: path,
                    folderName: $('#folderName').val()
                },
                function(data) {
                    //layer.alert(content, options, yes) - 普通信息框
                    layer.alert(data.desc, function (alertIndex) {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.location.reload(); //刷新父页面
                        layer.close(alertIndex); //关闭alert窗口
                        layer.close(index); //关闭弹出层
                    });
                });
            }
        });
    });
}