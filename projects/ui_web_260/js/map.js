var container, stats;
var camera, controls, scene, renderer;
var car;
var carTrace, carTraceLine;
var drawCount;
var startPositions = [{
    x: 0,
    y: 0,
    z: 0,
    r: 0,
    p: 0,
    ya: 0
  },
  {
    x: 73.97,
    y: -3.08,
    z: 0,
    r: -0.01,
    p: 0.26,
    ya: 0.03
  },
  {
    x: -14.44,
    y: 12.01,
    z: 0,
    r: -0.04,
    p: 0.23,
    ya: -1.57
  },
  {
    x: -146.07,
    y: -126.05,
    z: 0,
    r: 0,
    p: 0.21,
    ya: 3.07
  },
  {
    x: -12.26,
    y: -235.47,
    z: 0,
    r: -0.05,
    p: 0.24,
    ya: 0
  },
  {
    x: 15.82,
    y: -165.93,
    z: 0,
    r: -0.03,
    p: 0.29,
    ya: 1.61
  }
];

var startPoseSphere = [];
var index = 0;
var car_veyron = {
  name: "Bugatti Veyron",
  url: "models/veyron/VeyronNoUv_bin.js",
  init_rotation: [1.57, 1.57, 0],
  scale: 0.015,
  mmap: {
    0: new THREE.MeshLambertMaterial({
      color: 0x909090
    }), // tires + inside
    1: new THREE.MeshLambertMaterial({
      color: 0x808080
    }), // wheels + extras chrome
    2: new THREE.MeshLambertMaterial({
      color: 0x909090,
      combine: THREE.MultiplyOperation
    }), // back / top / front torso
    3: new THREE.MeshLambertMaterial({
      color: 0x606060,
      opacity: 0.25,
      transparent: true
    }), // glass
    4: new THREE.MeshLambertMaterial({
      color: 0x606060
    }), // sides torso
    5: new THREE.MeshLambertMaterial({
      color: 0xff0000
    }), // engine
    6: new THREE.MeshLambertMaterial({
      color: 0xff0000,
      opacity: 0.5,
      transparent: true
    }), // backlights
    7: new THREE.MeshLambertMaterial({
      color: 0xff0000,
      opacity: 0.5,
      transparent: true
    }) // backsignals
  }
};

function controlsSetup() {
  controls = new THREE.TrackballControls(camera);
  controls.zoomSpeed = 3.0;
  controls.maxDistance = 100000;
  controls.noZoom = false;
  controls.noPan = true;
  controls.noRotate = true;
}

function initPosCreate() {
  // add initPose
  for (var i = 0; i < startPositions.length; i++) {
    var sphereGeometry = new THREE.SphereGeometry(0.5, 30, 30);

    var sphereMaterial = new THREE.MeshBasicMaterial({
      color: 0x00ff00,
      wireframe: false
    });
    var sphere = new THREE.Mesh(sphereGeometry, sphereMaterial);
    sphere.name = "start_pose" + i;
    sphere.position.x = startPositions[i].x;
    sphere.position.y = startPositions[i].y;
    sphere.position.z = startPositions[i].z;
    startPoseSphere.push(sphere);
  }
}

function currentPosCarCreate() {
  var loader = new THREE.BinaryLoader();
  loader.load(car_veyron.url, function(geometry) {
    geometry.sortFacesByMaterialIndex();

    var m = [];

    for (var i in car_veyron.mmap) {

      m[i] = car_veyron.mmap[i];

    }

    car = new THREE.Mesh(geometry, m);

    car.rotation.x = car_veyron.init_rotation[0];
    car.rotation.y = car_veyron.init_rotation[1];
    car.rotation.z = car_veyron.init_rotation[2];

    car.position.x = 0;
    car.position.y = 0;
    car.position.z = 0.5;

    car.scale.x = car.scale.y = car.scale.z = car_veyron.scale;

    scene.add(car);
    car.add(camera);
    camera.position.set(0, 300, -1000);
    // camera.lookAt(car.posiiton);
    currentPosSubCreate();
  });
}

function currentPosSubCreate() {
  var pose_sub = sub_create(ros, '/current_pose', 'geometry_msgs/PoseStamped');

  pose_sub.subscribe(function(message) {
    // console.log(message);
    car.position.x = message.pose.position.x;
    car.position.y = message.pose.position.y;
    car.position.z = message.pose.position.z + 0.5;
    car.rotation.y = message.pose.orientation.z + 1.57;

    var positions = carTraceLine.geometry.attributes.position.array;

    positions[index++] = car.position.x;
    positions[index++] = car.position.y;
    positions[index++] = car.position.z;
    drawCount++;

    if (drawCount > 100000) {
      index = 0;
      drawCount = 0;
    }

  });
}

function posAddtoScene() {
  for (var i = 0; i < startPoseSphere.length; i++) {
    scene.add(startPoseSphere[i]);
  }
}

function init() {

  height = window.innerHeight - 45;
  width = window.innerWidth;

  //create a scene of three
  scene = new THREE.Scene();
  scene.background = new THREE.Color(0x000000);

  // var ambient = new THREE.AmbientLight(0x050505);
  // scene.add(ambient);

  // var directionalLight = new THREE.DirectionalLight(0xffffff, 2);
  // directionalLight.position.set(2, 1.2, 10).normalize();
  // scene.add(directionalLight);
  //
  // var directionalLight = new THREE.DirectionalLight(0xffffff, 1);
  // directionalLight.position.set(-2, 1.2, -10).normalize();
  // scene.add(directionalLight);

  var pointLight = new THREE.PointLight(0x808080, 2);
  pointLight.position.set(2000, 1200, 10000);
  scene.add(pointLight);

  // create a camera of three
  camera = new THREE.PerspectiveCamera(50,
    width / height, 0.01, 1000000);
  camera.up.set(0, 1, 0);
  //create 3d renderer and set its size
  renderer = new THREE.WebGLRenderer();

  // carTrace
  var carTrace = new THREE.BufferGeometry();

  // attributes
  var positions = new Float32Array(100000 * 3); // 3 vertices per point
  carTrace.addAttribute('position', new THREE.BufferAttribute(positions, 3));
  // drawcalls
  drawCount = 0;
  // material
  var material = new THREE.LineBasicMaterial({
    color: 0xffffff,
    linewidth: 1
  });
  // carTraceLine
  carTraceLine = new THREE.Line(carTrace, material);
  scene.add(carTraceLine);

  controlsSetup();
  initPosCreate();


  renderer.setPixelRatio(window.devicePixelRatio);
  renderer.setSize(width, height);

  var loaderPcd = new THREE.PCDLoader();

  loaderPcd.load('models/pcds/zhizhenweishi02v2.pcd', function(mesh) {

    // set points size
    mesh.material.size = 5;
    // set points position
    mesh.position.y = 0;

    mesh.geometry.boundingSphere.center.y = 0;
    scene.add(mesh);
    posAddtoScene();
    currentPosCarCreate();
    var center = mesh.geometry.boundingSphere.center;
    controls.target.set(center.x, center.y, center.z);
    controls.update();
  });

  var canvas = $("#map_top_view").append(renderer.domElement);
  $(renderer.domElement).addClass("center-block");
  //render
  render();

  window.addEventListener('resize', onWindowResize, false);
}

function onWindowResize() {
  height = window.innerHeight;
  width = window.innerWidth;
  camera.aspect = width / height;
  camera.updateProjectionMatrix();
  renderer.setSize(width, height);
  controls.handleResize();
}

function render() {
  renderer.render(scene, camera);

}

function animate() {
  requestAnimationFrame(animate);

  carTraceLine.geometry.setDrawRange(0, drawCount);
  carTraceLine.geometry.attributes.position.needsUpdate = true;

  render();
  controls.update();
}
