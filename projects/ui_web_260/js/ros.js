function initRos(ip, port) {
  var ros = new ROSLIB.Ros({
    url: 'ws://' + ip + ':' + port
  });

  ros.on('connection', function() {
    console.log('Connected to websocket server.');
  });

  ros.on('error', function(error) {
    console.log('Error connecting to websocket server: ', error);
  });

  ros.on('close', function() {
    console.log('Connection to websocket server closed.');
  });

  return ros;
}

function pub_create(ros, topicName, topicType) {
  var pose_pub = new ROSLIB.Topic({
    ros: ros,
    name: topicName,
    messageType: topicType
  });
  return pose_pub;
}

function sub_create(ros, topicName, topicType) {
  var pose_sub = new ROSLIB.Topic({
    ros: ros,
    name: topicName,
    messageType: topicType
  });
  return pose_sub;
}

function msg_create(msg_obj) {
  var msg = new ROSLIB.Message(msg_obj);
  return msg;
}

function service_create(serviceName, serviceType) {
  var service = new ROSLIB.Service({
    ros: ros,
    name: serviceName,
    serviceType: serviceType
  });
}

function request_create(request_obj) {
  var request = new ROSLIB.ServiceRequest(request_obj);
  return request;
}
