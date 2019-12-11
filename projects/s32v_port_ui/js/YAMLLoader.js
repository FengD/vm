import {
	FileLoader,
	Loader
} from "./threejs/three.module.js";

var YAMLLoader = function ( manager ) {

	Loader.call( this, manager );

};

YAMLLoader.prototype = Object.assign( Object.create( Loader.prototype ), {

	constructor: YAMLLoader,

	load: function ( url, onLoad, onProgress, onError ) {

		var scope = this;

		var loader = new FileLoader( scope.manager );
		loader.setPath( scope.path );
		loader.load( url, function ( text ) {

			onLoad( scope.parse( text ) );

		}, onProgress, onError );

	},

	parse: function ( yaml ) {
    var json = JSON.parse(JSON.stringify(jsyaml.load(yaml), null, 2));
    return json;
  }

} );

export { YAMLLoader };
