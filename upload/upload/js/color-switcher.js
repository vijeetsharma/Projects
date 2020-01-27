jQuery(document).ready(function($) {

  jQuery( ".theme-colors ul li a" ).each(function() {

    $(this).click(function(){

      var color_selected = $( this ).attr('id');

      jQuery("#color" ).attr("href", "css/colors/color-"+color_selected+".css");

      return false;

    });

  });

  // picker buttton
  jQuery(".picker_close").click(function(e){

    e.preventDefault();
    
    jQuery("#choose_color").toggleClass("position");
    
  });

  //scroll to demo

  $('a.landing-demo').on('click', function(event) {
    var target = $(this.getAttribute('href'));
    if( target.length ) {
        event.preventDefault();
        $('html, body').stop().animate({
            scrollTop: target.offset().top
        }, 1000);
    }
});
  
});
