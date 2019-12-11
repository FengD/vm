import { View } from './view.js';

(function($) {
	var	$window = $(window);
	$window.on('load', function() {
		$.getJSON("source/jsons/config.json",function(data) {
			$('#homepage').createNavHtml(data.nav);
			$('#homepage').createViewHtml();
			var view = new View();
			animate();
			createClouds();
			createDetections();
			view.updateDetection(data.view.detections.path);

		  function animate () {
		    requestAnimationFrame( animate );
		    view.getControls().update();
		    view.getRenderer().render( view.getScene(), view.getCamera() );
		  }

			function createClouds() {
				$.each(data.view.clouds, function(index, cloud) {
					view.addCloud(cloud.path, cloud.name, cloud.check);
					checkAction([cloud.name], cloud.check);
				});
			}

			function createDetections() {
				for (var element of data.view.detections.elements) {
					view.addDetection(element.key, element.name);
					checkAction(element.name, element.check);
				}
			}

			function checkAction(names, check) {
				$("." + check).change(function() {
					if($(this).is(':checked')) {
						for (var name of names) {
							view.getScene().getObjectByName( name ).visible = true;
						}
					} else {
						for (var name of names) {
							view.getScene().getObjectByName( name ).visible = false;
						}
					}
				});
			}

	    window.addEventListener( 'resize', function() {
	      view.getCamera().aspect = window.innerWidth / window.innerHeight;
	      view.getCamera().updateProjectionMatrix();
	      view.getRenderer().setSize( window.innerWidth, window.innerHeight - 45);
	    }, false );

	    window.addEventListener( 'keypress', function(ev) {

				function changeCloudColor(points, color) {
					if ($("." + points.name + "_check").is(':checked')) {
						points.material.color.setHex( color );
						points.material.needsUpdate = true;
		      }
				}

				function changeCloudSize(points, scale) {
					if ($("." + points.name + "_check").is(':checked')) {
						points.material.size *= scale;
						points.material.needsUpdate = true;
						if (points.material.size > 5) {
							points.material.size = 5;
						}
						if (points.material.size < 0.2) {
							points.material.size = 0.2;
						}
					}
				}

				function changeCloudsColor (color) {
					changeCloudColor(left_points, color);
					changeCloudColor(right_points, color);
					changeCloudColor(top_points, color);
				}

				function changeCloudsSize (scale) {
					changeCloudSize(left_points, scale);
					changeCloudSize(right_points, scale);
					changeCloudSize(top_points, scale);
				}

	      var left_points = view.getScene().getObjectByName( 'left_cloud' );
				var right_points = view.getScene().getObjectByName( 'right_cloud' );
				var top_points = view.getScene().getObjectByName( 'top_cloud' );

	      switch ( ev.key || String.fromCharCode( ev.keyCode || ev.charCode ) ) {
	        case '+':
	          changeCloudsSize(1.2);
	          break;
	        case '-':
						changeCloudsSize(0.8);
	          break;
	        case 'c':
						changeCloudsColor(Math.random() * 0xffffff);
	          break;
					case 'C':
						changeCloudsColor(0xffffff);
						break;
	      }
	    });
    });
	});
})(jQuery);
