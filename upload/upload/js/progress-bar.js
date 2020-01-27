//progress bar 
( function( $ ) {
  
  $(document).bind('scroll', function(ev) {
    
    var $section = $('#skills');
    
    var scrollOffset = $(document).scrollTop();
        
    var containerOffset = $section.offset().top - window.innerHeight;
        
    if (scrollOffset > containerOffset) {
           
      $(".meter > span ").each(function() {
        
        var values = parseInt($(this).attr("data-stop"));
        
        $(this).progressbar({
            value:  values
            
          }), $(".meter .ui-progressbar-value").value
            
            
      });
        
    // unbind event not to load scroll again
    $(document).unbind('scroll');

    }

  });
    
} )( jQuery );