
//场景对象
var scene = new THREE.Scene();
var group = new THREE.Group();

// 辅助坐标系  参数250表示坐标系大小，可以根据场景大小去设置
var axisHelper = new THREE.AxisHelper(250);
scene.add(axisHelper);


var geometry1 = new THREE.BoxGeometry(38, 6, 44);
var geometry2 = new THREE.BoxGeometry(6, 80, 4);

var materia = new THREE.MeshLambertMaterial({
    color: 0x0000ff
});

var mesh1 = new THREE.Mesh(geometry1, materia); //网格模型对象Mesh
mesh1.translateX(19);
mesh1.translateZ(22);

var mesh3 = new THREE.Mesh(geometry2, materia); //网格模型对象Mesh
var mesh4 = new THREE.Mesh(geometry2, materia); //网格模型对象Mesh
mesh3.translateX(-2);
mesh4.translateX(-2);
mesh4.translateZ(40);

group.add(mesh1);
group.add(mesh3);
group.add(mesh4);
scene.add(group); //网格模型添加到场景中

/**
 * 光源设置
 */
    //点光源
var point = new THREE.PointLight(0xffffff);
point.position.set(400, 200, 100); //点光源位置
scene.add(point); //点光源添加到场景中
//环境光
var ambient = new THREE.AmbientLight(0x444444);

scene.add(ambient);
/**
 * 相机设置
 */
var width = window.innerWidth; //窗口宽度
var height = window.innerHeight; //窗口高度
var k = width / height; //窗口宽高比
var s = 200; //三维场景显示范围控制系数，系数越大，显示的范围越大
//创建相机对象
var camera = new THREE.OrthographicCamera(-s * k*2, s * k*2, s, -s, 1, 1000);
camera.position.set(500, 300, 200); //设置相机位置
camera.lookAt(scene.position); //设置相机方向(指向的场景对象)
/**
 * 创建渲染器对象
 */
var renderer = new THREE.WebGLRenderer();
renderer.setSize(width, height);//设置渲染区域尺寸
renderer.setClearColor(0xb9d3ff, 1); //设置背景颜色
document.body.appendChild(renderer.domElement); //body元素中插入canvas对象

function render(){
    //执行渲染操作   指定场景、相机作为参数
    renderer.render(scene, camera);
}

var controls = new THREE.OrbitControls(camera, renderer.domElement);
controls.addEventListener('change', render);//监听鼠标、键盘事件
