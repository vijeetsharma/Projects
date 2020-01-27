(function($) {
    "use strict";

    // Go to top.
    var $scroll_obj = $( '#btn-gotop' );

    $(window).on('scroll', function() {
      if ( $( this ).scrollTop() > 100 ) {
        $scroll_obj.fadeIn();
      } else {
        $scroll_obj.fadeOut();
      }
    });

    $scroll_obj.on('click', function(e){
      $( 'html, body' ).animate( { scrollTop: 0 }, 600 );
      return false;
    });

    // Tabs and Accordian Call
    $( "#tabs" ).tabs();
    $( "#accordion" ).accordion();

    // Mean Menu
    $('.main-navigation').meanmenu({
      meanMenuContainer: '.bottom-header',
      meanScreenWidth:"850"
    });

    //for gallery of home page
    $('#portfolio').lightGallery();

    // Portfolio filter

    $('#portfolio').mixitup({
      targetSelector: '.portfolio-item',
      transitionSpeed: 450
    });

    //counters
    $('.count').counterUp({
      delay: 10,
      time: 4000
    });

    //main slider
    $('.slick-main-slider').slick({
      dots: true,
      infinite: true,
      speed: 600,
      arrows:true,
      autoplay: true,
      responsive: [
        {
          breakpoint: 768,
          settings: {
            arrows : false
          }
        }
      ]
    });

    //product slider
    $('.products-slider').slick({
      dots: true,
      infinite: true,
      speed: 600,
      slidesToShow: 4,
      arrows:false,
      slidesToScroll: 2,
      autoplay:true,
      responsive: [
        {
          breakpoint: 1024,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 2,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 551,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
      ]
    });

    //Home Page One Testimonial
    $('.testimonial-slider, .testimonial-slider-three').slick({
      dots: true,
      infinite: true,
      speed: 600,
      slidesToShow: 3,
      arrows:false,
      slidesToScroll: 1,
      autoplay:false,
      responsive: [
        {
          breakpoint: 1024,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 1,
            infinite: true,
            dots: false
          }
        },
        {
          breakpoint: 768,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        },
        {
          breakpoint: 481,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
      ]
    });

    //Home Page Two Testimonial
    $('.testimonial-slider-two').slick({
      dots: true,
      infinite: false,
      speed: 300,
      fade: true,
      arrows:false,
      autoplay: true     
    });

    //Single product slider
    $('.product-gallery-slider').slick({
      slidesToShow: 1,
      slidesToScroll: 1,
      autoplay:true,
      arrows: false,
      fade: true,
      asNavFor: '.product-gallery-nav'
    });

    $('.product-gallery-nav').slick({
      slidesToShow: 3,
      slidesToScroll: 1,
      asNavFor: '.product-gallery-slider',
      dots: false,
      centerMode: true,
      focusOnSelect: true
    });

})(jQuery);