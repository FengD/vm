// -------------Gradient color generation----------------------
/**
  * domicial to hexial
  * @param {string} c
  * @return {string}
  */
function hex (c) {
  var s = "0123456789abcdef";
  var i = parseInt (c);
  if (i == 0 || isNaN (c)) {
    return "00";
  }

  i = Math.round (Math.min (Math.max (0, i), 255));
  return s.charAt ((i - i % 16) / 16) + s.charAt (i % 16);
}
/**
  * Convert an RGB triplet to a hex string
  * @param {string} rgb
  * @return {string}
  */
function convertToHex (rgb) {
  return hex(rgb[0]) + hex(rgb[1]) + hex(rgb[2]);
}

/**
  * Remove '#' in color hex string if exists
  * @param {string} s
  * @return {string}
  */
function trim (s) {
  return (s.charAt(0) == '#') ? s.substring(1, 7) : s;
}

/**
  * Convert a hex string to an RGB triplet
  * @param {string} hex
  * @return {string}
  */
function convertToRGB (hex) {
  var color = [];
  color[0] = parseInt ((trim(hex)).substring (0, 2), 16);
  color[1] = parseInt ((trim(hex)).substring (2, 4), 16);
  color[2] = parseInt ((trim(hex)).substring (4, 6), 16);
  return color;
}

/**
  * Generate gradient color by start color, end color and the steps
  * @param {string} colorStart
  * @param {string} colorEnd
  * @param {string} steps
  * @return {Array}
  */
function generateColor(colorStart, colorEnd, steps){
  // The beginning of your gradient
  var start = convertToRGB (colorStart);
  // The end of your gradient
  var end   = convertToRGB (colorEnd);
  // The number of colors to compute
  var len = steps;
  //Alpha blending amount
  var alpha = 0.0;
  var saida = [];
  for (i = 0; i < len; i++) {
    var c = [];
    alpha += (1.0 / len);
    c[0] = start[0] * alpha + (1 - alpha) * end[0];
    c[1] = start[1] * alpha + (1 - alpha) * end[1];
    c[2] = start[2] * alpha + (1 - alpha) * end[2];
    saida.push(convertToHex (c));
  }
  return saida;
}

// -------------------------------------------------------------------------
// Radium Earth 6378137m
// half equator PI*R
var EQUATORHALF = 20037508.3427892;
/**
  * WGS84 coordiate to web mercator
  * reference：http://www.opengsc.com/archives/137
  * https://blog.csdn.net/mygisforum/article/details/13295223
  * @param {double} lng
  * @param {double} lat
  * @return {Object} web mercator project x, y, unit meters
*/
function lonLat2WebMercator (lng, lat) {
  // x range [-EQUATORHALF,EQUATORHALF]
  var x = (lng / 180.0) * EQUATORHALF;
  // y range angles[-85.05112877980659，85.05112877980659]
  var y;
  if (lat > 85.05112877980659) {
    lat = 85.05112877980659;
  }
  if (lat < -85.05112877980659) {
    lat = -85.05112877980659;
  }

  var tmp = Math.PI / 4.0 + (Math.PI / 2.0) * lat / 180.0;
  // y range meters[-EQUATORHALF,EQUATORHALF]
  y = EQUATORHALF * Math.log(Math.tan(tmp)) / Math.PI;
  var result = {
    x: x,
    y: y
  };
  return result;
}

/**
  * webMercator position to tile image info
  * @param {double} x webMeracator
  * @param {double} y webMeracator
  * @param {intager} level level zoom
  * @return {Object} tile image info
*/

function webMercator2TileimageInfo (x, y, level){
  y = EQUATORHALF - y;
  x = EQUATORHALF + x;
  var size = Math.pow(2, level) * 256;
  var imgx = x * size / (EQUATORHALF * 2);
  var imgy = y * size / (EQUATORHALF * 2);
  //current position tile image index in world map
  var col = Math.floor(imgx / 256);
  var row = Math.floor(imgy / 256);
  //current position in tile image position
  var imgdx = imgx % 256;
  var imgdy = imgy % 256;
  //pixal positon
  var position = {
    x : imgx,
    y : imgy
  };
  //tile index
  var tileinfo = {
    x : col,
    y : row,
    level : level
  };
  // tile offset
  var offset = {
    x : imgdx,
    y : imgdy
  };

  var result = {
    position : position,
    tileinfo : tileinfo,
    offset : offset
  };
  return result;
}
// -----------------------------------------------info unpack

function unpack560(msg) {
  var lat = ((msg[0]<<24>>>0) + (msg[1]<<16) + (msg[2]<<8) + (msg[3])) * 0.0000001 - 180.0;
  var lng = ((msg[4]<<24>>>0) + (msg[5]<<16) + (msg[6]<<8) + (msg[7])) * 0.0000001 - 180.0;
  return {
    lat: lat,
    lng: lng
  };
}

function unpack561(msg) {
  var counter = (msg[0]>>>4);
  var flagUse = (15 & msg[0])>>>1;
  var  distance = (msg[1]) * 10;
  var heading = ((msg[2]<<8) + (msg[3])) * 0.010986 - 360;
  return {
    counter: counter,
    flagUse: flagUse,
    distance: distance,
    heading: heading
  };
}

function unpackLatLng(msg) {
  var xCoordH = ((msg[0]<<8) + (msg[1])) / 100.0;
  var xCoordL = ((msg[2]<<9) + (msg[3]<<1) + (msg[4]>>>7)) / 10000000.0;
  var yCoordH = (((127 & msg[4])<<14) + (msg[5]<<6) + (msg[6]>>>2)) / 10000.0;
  var yCoordL = (((3 & msg[6])<<8) + (msg[7])) / 10000000.0;
  // console.log(xCoordH,xCoordL,yCoordH,yCoordL);
  // console.log(xCoordH+xCoordL, yCoordH+yCoordL);
  return {
    lat: yCoordH + yCoordL,
    lng: xCoordH + xCoordL
  };
}

function unpack552(msg) {
  // console.log(msg);
  // console.log(msg[0]<<3, (msg[1]>>>5), (msg[0]<<3 + (msg[1]>>>5)) / 1.0);
  // console.log((31 & msg[1])<<6 , (msg[2]>>>2), (((31 & msg[1])<<6) + (msg[2]>>>2)) / 1.0);
  // console.log((3 & msg[2]<<9) , (msg[3]<<1) , (msg[4]>>>7), ((3 & msg[2]<<9) + (msg[3]<<1) + (msg[4]>>>7)) / 1.0);
  var nxtOpSegDis = (msg[0]<<3) + (msg[1]>>>5);
  var nxtPlDis = ((31 & msg[1])<<6) + (msg[2]>>>2);
  var nxtRampDis = ((3 & msg[2])<<9) + (msg[3]<<1) + (msg[4]>>>7);
  var nxtSpmrDis = ((127 & msg[4])<<4) + (msg[5]>>>4);
  var nxtTnlDis = ((15 & msg[5])<<7) + (msg[6]>>>1);
  var hsOpSeg = (1 & msg[6]);
  var hsPl = (msg[7]>>>7);
  var hsRamp = ((64 & msg[7])>>>6);
  var hsSpmr = ((32 & msg[7])>>>5);
  var hsTnl = ((16 & msg[7])>>>4);
  var nxtSpmrType = ((14 & msg[7])>>>1);
  return {
    nxtOpSegDis: hsOpSeg ? nxtOpSegDis : 'N/A',
    nxtPlDis: hsPl ? nxtPlDis : 'N/A',
    nxtRampDis: hsRamp ? nxtRampDis : 'N/A',
    nxtSpmrDis: hsSpmr ? nxtSpmrDis : 'N/A',
    nxtTnlDis: hsTnl ? nxtTnlDis : 'N/A'
    // hsOpSeg: hsOpSeg,
    // hsPl: hsPl,
    // hsRamp: hsRamp,
    // hsSpmr: hsSpmr,
    // hsTnl: hsTnl,
    // nxtSpmrType: nxtSpmrType
  };
}

function unpack553(msg) {
  var nxtBrgDis = (msg[0]<<3) + (msg[1]>>>5);
  var nxtDdrkDis = ((31 & msg[1])<<6) + (msg[2]>>>2);
  var nxtDnStatusDis = ((3 & msg[2])<<9) + (msg[3]<<1) + (msg[4]>>>7);
  var nxtJpFlgDis = ((127 & msg[4])<<4) + (msg[5]>>>4);
  var nxtLnNmChgDis = ((15 & msg[5])<<7) + (msg[6]>>>1);
  var hsBrg = (1 & msg[6]);
  var hsDdrk = (msg[7]>>>7);
  var hsDnStatus = ((64 & msg[7])>>>6);
  var nxtDnStatusType = ((48 & msg[7])>>>4);
  var hsJpFlg = ((8 & msg[7])>>>3);
  var isLnNmChg = ((4 & msg[7])>>>2);
  var nxtTlgtType = (3 & msg[7]);
  return {
    nxtBrgDis: hsBrg ? nxtBrgDis : 'N/A',
    nxtDdrkDis: hsDdrk ? nxtDdrkDis : 'N/A',
    nxtDnStatusDis: hsDnStatus ? nxtDnStatusDis : 'N/A',
    nxtJpFlgDis: hsJpFlg ? nxtJpFlgDis : 'N/A',
    nxtLnNmChgDis: isLnNmChg ? nxtLnNmChgDis : 'N/A'
    // hsBrg: hsBrg,
    // hsDdrk: hsDdrk,
    // hsDnStatus: hsDnStatus,
    // nxtDnStatusType: nxtDnStatusType,
    // hsJpFlg: hsJpFlg,
    // isLnNmChg: isLnNmChg,
    // nxtTlgtType: nxtTlgtType
  };
}

function unpack554(msg) {
  var nxtTfcLtDis = (msg[0]<<3) + (msg[1]>>>5);
  var nxtTfSgnDis = ((31 & msg[1])<<6) + (msg[2]>>>2);
  var nxtTlgtDis = ((3 & msg[2])<<9) + (msg[3]<<1) + (msg[4]>>>7);
  var nxtSmblDis = ((127 & msg[4])<<4) + (msg[5]>>>4);
  var nxtSplmtChgDis = ((15 & msg[5])<<7) + (msg[6]>>>1);
  var hsTlgt = (1 & msg[6]);
  var hsTfSgn = (msg[7]>>>7);
  var hsTfcLt = ((64 & msg[7])>>>6);
  var hsSmbl = ((32 & msg[7])>>>5);
  var isSplmtChg = ((16 & msg[7])>>>4);
  var nxtSpmrLnNum = (15 & msg[7]);
  return {
    nxtTfcLtDis: hsTlgt ? nxtTfcLtDis : 'N/A',
    nxtTfSgnDis: hsTfSgn ? nxtTfSgnDis : 'N/A',
    nxtTlgtDis: hsTfcLt ? nxtTlgtDis : 'N/A',
    nxtSmblDis: hsSmbl ? nxtSmblDis : 'N/A',
    nxtSplmtChgDis: isSplmtChg ? nxtSplmtChgDis : 'N/A'
    // hsTlgt: hsTlgt,
    // hsTfSgn: hsTfSgn,
    // hsTfcLt: hsTfcLt,
    // hsSmbl: hsSmbl,
    // isSplmtChg: isSplmtChg,
    // nxtSpmrLnNum: nxtSpmrLnNum
  };
}

function updateInfo(v) {
  for (var key in v) {

    if($("." + key).text() != v[key]) {
      $("." + key).text(v[key]);
      $("." + key).addClass("backgroundColorRed");
    }else {
      $("." + key).removeClass("backgroundColorRed");
    }

  }
}
