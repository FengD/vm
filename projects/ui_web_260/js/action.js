$(function() {
  $(".wheel-speed-check").change(function() {
    if($(this).is(':checked')) {
      $(".wheel-speed-display").show();
    } else {
      $(".wheel-speed-display").hide();
    }
  });

  $(".path-check").change(function() {
    if($(this).is(':checked')) {
      carTraceLine.visible = true;
    } else {
      carTraceLine.visible = false;
    }
  });


});
