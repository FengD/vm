$(function() {
  $(".show_info_btn").bind( "click" ,function() {
    if($(this).text() == "Show Info") {
      $(this).text("Hide Info");
      $(".info-display").show();
    } else if($(this).text() == "Hide Info") {
      $(this).text("Show Info");
      $(".info-display").hide();
    }
  });

  $(".show_camera_btn").bind( "click" ,function() {
    if($(this).text() == "Show Camera") {
      $(this).text("Hide Camera");
      $(".camera-container").show();
    } else if($(this).text() == "Hide Camera") {
      $(this).text("Show Camera");
      $(".camera-container").hide();
    }
  });



});
