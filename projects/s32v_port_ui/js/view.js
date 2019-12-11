import * as THREE from './threejs/three.module.js';
import { OrbitControls } from './threejs/jsm/controls/OrbitControls.js';
import { PCDLoader } from './threejs/jsm/loaders/PCDLoader.js';
import { YAMLLoader } from './YAMLLoader.js';

var View = function () {
  this.camera = undefined;
  this.controls = undefined;
  this.scene = undefined;
  this.renderer = undefined;
  this.init();
};

Object.assign( View.prototype, {
  constructor: View,

  init: function () {
    this.scene = new THREE.Scene();
    this.scene.background = new THREE.Color( 0x000000 );

    this.camera = new THREE.PerspectiveCamera( 15, window.innerWidth / window.innerHeight, 0.01, 10000 );
    this.camera.position.z = 300;
    this.camera.up.set( 0, 0, 1 );

    this.scene.add( this.camera );

    this.renderer = new THREE.WebGLRenderer( { antialias: true } );
    this.renderer.setPixelRatio( window.devicePixelRatio );
    this.renderer.setSize( window.innerWidth, window.innerHeight - 45);
    document.body.appendChild( this.renderer.domElement );

    var container = $("#view").append(this.renderer.domElement);

    this.controls = new OrbitControls( this.camera, this.renderer.domElement );
    this.controls.rotateSpeed = 1.0;
    this.controls.zoomSpeed = 3.0;
    this.controls.panSpeed = 1.0;
    this.controls.staticMoving = true;
    this.controls.minDistance = 0.1;
    this.controls.maxDistance = 0.1 * 10000;
    this.controls.target.set( 0, 0, 0);
  },

  getCamera: function() {
    return this.camera;
  },

  getControls: function() {
    return this.controls;
  },

  getScene: function() {
    return this.scene;
  },

  getRenderer: function() {
    return this.renderer;
  },

  addCloud: function(path, name, check) {
    var geometry = new THREE.BufferGeometry();
    var material = new THREE.PointsMaterial( { size: 1 } );
    var mesh = new THREE.Points( geometry, material );
    mesh.name = name;
    this.scene.add(mesh);

    var that = this;
    setInterval(function() {
      if ($("." + check).is(':checked')) {
        var loader = new PCDLoader();
        loader.load( path, function ( cloud ) {
          var points = that.scene.getObjectByName( name );
          points.geometry = cloud.geometry;
        } );
      }
    }, 100);
  },

  addDetection: function(key, name) {
    switch(key) {
      case 'freespace':
        this.addFreespace(name[0]);
        break;
      case 'signboard':
        this.addSignboard(name[0]);
        break;
      case 'bridge':
        this.addBridge(name[0]);
        break;
      case 'boundary':
        this.addBoundary(name);
        break;
      case 'trailer':
        this.addTrailer(name[0]);
        break;
      case 'container':
        this.addContainer(name[0]);
        break;
    }
  },

  addFreespace: function(name) {
    var geometry = new THREE.Geometry();
    var material = new THREE.MeshBasicMaterial({
      color: 0x00ff00,
      transparent: true,
      opacity: 0.4,
      side: THREE.DoubleSide
    });
    for (var i = 0; i <= 360; i++) {
      geometry.vertices.push(new THREE.Vector3(0, 0, 0));
    }

    for (var i = 0; i < 359; i++) {
      geometry.faces.push(new THREE.Face3(0, i + 1, i + 2));
    }

    geometry.faces.push(new THREE.Face3(0, 360, 1));
    var element = new THREE.Mesh(geometry, material);
    element.name = name;
    element.visible = false;
    this.scene.add(element);
  },

  addSignboard: function(name) {
    var geometry = new THREE.SphereGeometry(0.5, 30, 30);
    var material = new THREE.MeshBasicMaterial({
      color: '#ff0000',
    });
    var element = new THREE.Mesh(geometry, material);
    element.name = name;
    element.visible = false;
    this.scene.add(element);
  },

  addBridge: function(name) {
    var geometry = new THREE.Geometry();
    var material = new THREE.MeshBasicMaterial({
      color: 0x0000ff
    });
    geometry.vertices.push(new THREE.Vector3(0, -5, 0));
    geometry.vertices.push(new THREE.Vector3(0, 5, 0));
    var element = new THREE.Line(geometry, material);
    element.name = name;
    element.visible = false;
    this.scene.add(element);
  },

  addBoundary: function(names) {
    for (var name of names) {
      var geometry = new THREE.Geometry();
      var material = new THREE.MeshBasicMaterial({
        color: 0xff0000
      });
      geometry.vertices.push(new THREE.Vector3(0, 0, 0));
      geometry.vertices.push(new THREE.Vector3(0, 0, 0));
      var element = new THREE.Line(geometry, material);
      element.name = name;
      element.visible = false;
      this.scene.add(element);
    }
  },

  addTrailer: function(name) {
    var geometry = new THREE.Geometry();
    var material = new THREE.MeshBasicMaterial({
      color: 0xff0000,
      transparent: true,
      opacity: 1,
      side: THREE.DoubleSide
    });
    for (var i = 0; i <= 4; i++) {
      geometry.vertices.push(new THREE.Vector3(0, 0, 0));
    }

    for (var i = 0; i < 3; i++) {
      geometry.faces.push(new THREE.Face3(0, i + 1, i + 2));
    }

    geometry.faces.push(new THREE.Face3(0, 4, 1));
    var element = new THREE.Mesh(geometry, material);
    element.name = name;
    element.visible = false;
    this.scene.add(element);
  },

  addContainer: function(name) {
    var geometry = new THREE.Geometry();
    var material = new THREE.MeshBasicMaterial({
      color: 0x0000ff,
      transparent: true,
      opacity: 1,
      side: THREE.DoubleSide
    });
    geometry.vertices.push(new THREE.Vector3(0, 0, 0));
    geometry.vertices.push(new THREE.Vector3(0, -1.45, 0));
    geometry.vertices.push(new THREE.Vector3(0, -1.45, 2));
    geometry.vertices.push(new THREE.Vector3(0, 1.45, 2));
    geometry.vertices.push(new THREE.Vector3(0, 1.45, 0));

    for (var i = 0; i < 3; i++) {
      geometry.faces.push(new THREE.Face3(0, i + 1, i + 2));
    }

    geometry.faces.push(new THREE.Face3(0, 4, 1));
    var element = new THREE.Mesh(geometry, material);
    element.name = name;
    element.visible = false;
    this.scene.add(element);
  },

  updateDetection: function (path) {
    function angPos(r, ang) {
      let _ang = THREE.Math.degToRad(ang);
      let x = r * Math.sin(_ang), y = r * Math.cos(_ang);
      return [x, y];
    }

    function updateFreespace(scene, data) {
      if ($(".freespace_check").is(':checked')) {
        var fs = scene.getObjectByName( 'freespace' );
        for (var i = 0; i < data.length; i++) {
          var p = angPos(data[i], i);
          fs.geometry.vertices[i + 1].set(p[1], p[0], 0);
        }
        fs.geometry.verticesNeedUpdate = true;
      }
    }

    function updateSignboard(scene, data) {
      if ($(".signboard_check").is(':checked')) {
        var signboard = scene.getObjectByName( 'signboard' );
        if (data.type == 0) {
          signboard.visible = false;
        } else {
          signboard.position.x = data.x;
          signboard.position.y = data.y;
          signboard.visible = true;
        }
      }
    }

    function updateBridge(scene, data) {
      if ($(".bridge_check").is(':checked')) {
        var bridge = that.scene.getObjectByName( 'bridge' );
        if (data.flag == 0) {
          bridge.visible = false;
        } else {
          bridge.geometry.vertices[0].set(data.b, -5, 0);
          bridge.geometry.vertices[1].set(data.b, 5, 0);
          bridge.geometry.verticesNeedUpdate = true;
          bridge.visible = true;
        }
      }
    }

    function updateBoundary(scene, data, name) {
      if ($(".boundary_check").is(':checked')) {
        var boundary = that.scene.getObjectByName( name );
        if (data.flag == 0) {
          boundary.visible = false;
        } else {
          var x1 = 10, x2 = -10;
          var y1 = data.k * x1 + data.b;
          var y2 = data.k * x2 + data.b;
          boundary.geometry.vertices[0].set(x1, y1, 0);
          boundary.geometry.vertices[1].set(x2, y2, 0);
          boundary.geometry.verticesNeedUpdate = true;
          boundary.visible = true;
        }
      }
    }

    function updateTrailer(scene, data) {
      if ($(".trailer_check").is(':checked')) {
        var trailer = scene.getObjectByName( 'trailer' );
        if (data.flag == 0) {
          trailer.visible = false;
        } else {
          var k = Math.tan(data.k * Math.PI / 180.0);
          var trailerBack = -18;
          var trailerFront = -5.055;
          var halfTrunkWidth = 1.8;
          var left = k * (trailerBack - trailerFront) + halfTrunkWidth;
          var right = k * (trailerBack - trailerFront) - halfTrunkWidth;
          trailer.geometry.vertices[0].set(trailerFront, 0, 0.2);
          trailer.geometry.vertices[1].set(trailerFront, halfTrunkWidth, 0.2);
          trailer.geometry.vertices[2].set(trailerBack, left, 0.2)
          trailer.geometry.vertices[3].set(trailerBack, right, 0.2)
          trailer.geometry.vertices[4].set(trailerFront, -halfTrunkWidth, 0.2)
          trailer.geometry.verticesNeedUpdate = true;
          trailer.visible = true;
        }
      }
    }

    function updateContainer(scene, data) {
      if ($(".container_check").is(':checked')) {
        var container = scene.getObjectByName( 'container' );
        if (data.flag == 0) {
          container.visible = false;
        } else {
          container.geometry.vertices[0].x = data.dis1;
          container.geometry.vertices[1].x = data.dis1;
          container.geometry.vertices[2].x = data.dis1;
          container.geometry.vertices[3].x = data.dis1;
          container.geometry.vertices[4].x = data.dis1;
          container.geometry.verticesNeedUpdate = true;
          container.visible = true;
        }
      }
    }

    var that = this;
    setInterval(function() {
      var loader = new YAMLLoader();
      loader.load( path, function ( data ) {
        updateFreespace(that.scene, data.fs);
        updateSignboard(that.scene, data.signboard);
        updateBridge(that.scene, data.bridge);
        updateBoundary(that.scene, data.left_boundary, "left_boundary");
        updateBoundary(that.scene, data.right_boundary, "right_boundary");
        updateTrailer(that.scene, data.trailer);
        updateContainer(that.scene, data.container);
      } );
    }, 50);
  }
});

export { View };
