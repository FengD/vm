//tile Size
var tileSize = 100;
//tile image url
var serverURL = "http://localhost/hds_ui/source/rastertiles/voyager/";
//center position
// var centerLng = 0,
//   centerLat = 0;

function lonLat2WebGL(lng, lat, level) {
  var webMercator = lonLat2WebMercator(lng, lat);
  var tilePos = webMercator2TileimageInfo(webMercator.x, webMercator.y, level).position;
  var centerWM = lonLat2WebMercator(centerLng, centerLat);
  var centerTP = webMercator2TileimageInfo(centerWM.x,centerWM.y, level);
  // 相对偏移修正（以centerLng,centerLat所在点tile中心点为原点，导致的偏移）
  var x = (tilePos.x - centerTP.position.x + (centerTP.offset.x - 256/2) )*tileSize/256;
  var y = (tilePos.y - centerTP.position.y + (-centerTP.offset.y + 256/2))*tileSize/256;
  var result = {
    x: -x,
    y: y
  };
  return result;
}
/**
 * loda Image tile
 * @param {Intager} xno tile编号x
 * @param {Intager} yno tile编号y
 * @param {Intager} level level
 * @param {Object} callback
 */
function loadImageTile(xno, yno, level, callback) {
  var url = serverURL + level + "/" + xno + "/" + yno + ".png";
  var loader = new THREE.TextureLoader();
  //跨域加载图片
  loader.crossOrigin = true;
  loader.load(url, function(texture) {
    var geometry = new THREE.PlaneGeometry(tileSize, tileSize, 1);
    var material = new THREE.MeshBasicMaterial({
      map: texture,
      transparent: true,
      side: THREE.DoubleSide
    });
    var mesh = new THREE.Mesh(geometry, material);
    callback(mesh);
  });
}
/**
 * 将加载的切图放到scene
 * @param {Object} mesh
 * @param {Object} x坐标  WebGL坐标
 * @param {Object} y坐标
 */
function addTileToScene(mesh, x, y) {
  //mesh的中心位置
  mesh.position.x = x;
  mesh.position.y = y;
  scene.add(mesh);
}
/**
 * 辅助函数，用于计算tile应该放在何处
 * @param {Object} dx  tile间相对位置，也就是编号差
 * @param {Object} dy
 */
function addTileToSceneHelper(dx, dy) {
  var x = tileSize * dx;
  var y = -tileSize * dy;
  return function(mesh) {
    addTileToScene(mesh, x, y);
  };
}
/**
 * 加载地图
 * @param {Object} centerX 地图中间的切图编号
 * @param {Object} centerY 地图中间的切图编号
 */
function loadMap(centerX, centerY, level) {
  var radius = 65;
  for (var i = centerX - radius; i <= centerX + radius; i++) {
    for (var j = centerY - radius; j <= centerY + radius; j++) {
      if(i > centerX + radius - 2 || j > centerY + radius - 2){
        loadImageTile(i, j, level, addTileToSceneHelper(i - centerX, j - centerY));
      }

    }
  }
}
