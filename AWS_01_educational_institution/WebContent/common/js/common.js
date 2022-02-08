/**
 * common.js
 *
 */

 $(function(){
	 $("#sp-menu").on("click", function() {
		 $(this).next().slideToggle();
	 });
 });
/* ----------------------------------------------------------
 init
---------------------------------------------------------- */
$(function(){
  // Smooth scroll
  pageScroll();
  // Image roll over
  rollover();
  // Local navigation current
  localNav();
  //Confirmation the image when it will be uploaded
  confirmImage();
  //Reminder the missing required items at new user registration.
  adminRegisterUser();
  //Reminder the missing required items at new article registration.
  articleRegisterWarning();
  //Confirmation to upload the eyecatch image.
  confirmEyecatch();
  //Confirmation to upload the article image.
  confirmarticleImg();
  //Confirmation to upload the article main visual.
  confirmMainVisual();
  //
  checkUserRegistrationInput();
  //Keep the application button above the footer
  scrollBottomEvent();
  //Force to select "Release"
  checkUserReleaseInput();
  //Change the title up to the image size at the author page
  userSearchTitleChanger();
  //Indicate the balloon to show editing and duplicate buttons
  baloonInMyPage();
  //
  baloonInAdminMyPage();
  //
  baloonForReleaseIndicator();
  //Indicate the baloon to show article release information at user my page
  baloonForarticleReleaseIndicator();
  //Count how many character in the text area
  indicatingNumberOfCharacter();
  //Count the characters of the user's profile
  indicatingNumberOfCharacterOfUserProfile();
  //
  indicatingNumberOfCharacterOfUserCatchPhrase();
;})
/* ----------------------------------------------------------
confirm main visual
---------------------------------------------------------- */
var confirmMainVisual = function(){
	var flag = false;
	var fileSize = 0;
	$(".ttl-register-warning").css({"display":"none"});

	  //Register the event to image file pre-view
	  $('#form-register-mv').on('change', 'input[type="file"]', function(e) {

	    var file = e.target.files[0],
	        reader = new FileReader(),
	        $preview = $(".img-preview-02");
	        t = this;

	    // Do nothing if the file is not image.
	    if(file.type.indexOf("image") < 0){
	      return false;
	    }

	    // Register the event after finishing to read the file
	    reader.onload = (function(file) {
	      return function(e) {
	        //Remove the existing pre-view
	        $preview.empty();
	        // Add the image tag to show the image loaded in the .preview domain
	        $preview.append($('<img class="img-confirm-eyecatch">').attr({
	                  src: e.target.result,
	                  width: "904px",
	                  height: "372px",
	                  class: "img-preview-02",
	                  title: file.name
	              }));
	      };
	    })(file);
	    reader.readAsDataURL(file);

	  //if the image size is more than 100Mb cannot move on to next page
	    $("#form-register-mv").submit(function(){
			 if (fileSize > 104857600) {
			     flag = false;
			}
			else {
				 flag = true;
			}
			 return flag
			}
		);
	    return flag
 });
};
/* ----------------------------------------------------------
Count how many character in the text area @ user profile
---------------------------------------------------------- */
//var indicatingNumberOfCharacterOfUserCatchPhrase = function ShowLength( str ) {
//
//	 var numberOfCharactor = str.length;
//     document.getElementById("catch_copy_inputlength").innerHTML = "Current number of characters: " + numberOfCharactor;
//
//	 if(numberOfCharactor > 100){
//		 $("#catch_copy_charactor_number_warning").css({"display":"inline"});
//	 }
//	 else {
//		 $("#catch_copy_charactor_number_warning").css({"display":"none"});
//	 }
//}
/* ----------------------------------------------------------
Count how many character in the text area @ user profile
---------------------------------------------------------- */
var indicatingNumberOfCharacterOfUserProfile = function ShowLength( str ) {

	 var numberOfCharactor = str.length;
     document.getElementById("profile_inputlength").innerHTML = "Current number of characters: " + numberOfCharactor;

	 if(numberOfCharactor > 300){
		 $("#profile_charactor_number_warning").css({"display":"inline"});
	 }
	 else {
		 $("#profile_charactor_number_warning").css({"display":"none"});
	 }
}
/* ----------------------------------------------------------
Count how many character in the text area @ article Registration
---------------------------------------------------------- */
var indicatingNumberOfCharacter = function ShowLength( str ) {

	 var numberOfCharactor = str.length;
     document.getElementById("inputlength").innerHTML = "Current number of characters: " + numberOfCharactor;

	 if(numberOfCharactor > 2000){
		 $("#article_introduction_charactor_number_warning").css({"display":"inline"});
	 }
	 else {
		 $("#article_introduction_charactor_number_warning").css({"display":"none"});
	 }
}
/* ----------------------------------------------------------
Indicate the baloon to show editing and duplicate buttons
---------------------------------------------------------- */
var baloonInMyPage = function(){
	$(".list-mypage-edit").on({
		'mouseenter':function(){
			$(this).find(".list-mypage-selection").css('display', 'block');
		},
		'mouseleave':function(){
			$(this).find(".list-mypage-selection").css('display', 'none');
		}
	});
}
/* ----------------------------------------------------------
Indicate the baloon to show release buttons at admin page
---------------------------------------------------------- */
var baloonInAdminMyPage = function(){
	$(".list-mypage-edit").on({
		'mouseenter':function(){
			$(this).find(".list-admin-selection").css('display', 'block');
		},
		'mouseleave':function(){
			$(this).find(".list-admin-selection").css('display', 'none');
		}
	});
}
/* ----------------------------------------------------------
Indicate the baloon to show release buttons at admin page
---------------------------------------------------------- */
var baloonForReleaseIndicator = function(){
	$(".list-search-indicator").on({
		'mouseenter':function(){
			var text = $(this).attr('release');
			$(this).append('<div class="baloon-admin-selection">'+text+'</div>');
		},
		'mouseleave':function(){
			$(this).find(".baloon-admin-selection").remove();
		}
	});
}
/* ----------------------------------------------------------
Indicate the baloon to show article release information at user my page
---------------------------------------------------------- */
var baloonForarticleReleaseIndicator = function(){
	$(".list-mypage-indicator").on({
		'mouseenter':function(){
			var text = $(this).attr('release');
			$(this).append('<div class="baloon-article-activatedmypage">'+text+'</div>');
		},
		'mouseleave':function(){
			$(this).find(".baloon-article-activatedmypage").remove();
		}
	});
}
/* ----------------------------------------------------------
Up to the display size, the title in the user search is changed
---------------------------------------------------------- */
var userSearchTitleChanger = function() {
	$(window).load(function() {
		//Check the width of the window
		var windowWidth = window.innerWidth;

	    //In case of the display width is PC
	    if(windowWidth >= 1240){
		    $("#ttl-user-search").html("Find the author.");
	    }
	    //In case of the display width is SP
	    else {
		    $("#ttl-user-search").html("Find the author.");
	    }
	});
};
/* ----------------------------------------------------------
Set application url indicator fixed @ index page
---------------------------------------------------------- */
var scrollBottomEvent = function getScrollBottom() {
	var scrollBottomEvent = window.document.createEvent('UIEvents');
	scrollBottomEvent.initUIEvent('scrollBottom', true, false, window, 1);
	window.document.addEventListener('scroll', function() {
	  var body = window.document.body;
	  var html = window.document.documentElement;

	  var scrollTop    = body.scrollTop || html.scrollTop;
	  var scrollBottom = html.scrollHeight - html.clientHeight - scrollTop;

	  var windowWidth = window.innerWidth;

	  //In case of the display width is PC
	  if(windowWidth >= 1240){
		  if(scrollBottom <= 347){
			  $("#event-wrapper").css({"position":"relative"});
			  $("#event-application").css({"position":"absolute"});
		  }
		  else if(scrollBottom > 347){
			  $("#event-application").css({"position":"fixed"});
		  }
	  }
	  //In case of the display width is SP
	  else {
		  if(scrollBottom <= 1189){
			  $("#event-wrapper").css({"position":"relative"});
			  $("#event-application").css({"position":"absolute"});
		  }
		  else if(scrollBottom > 1189){
			  $("#event-application").css({"position":"fixed"});
		  }
	  }
	});
};
/* ----------------------------------------------------------
Check the input @ release selection
---------------------------------------------------------- */
var checkUserReleaseInput = function(){
	  $("#release_warning").css({"display":"none"});
	  $("#register_release_warning").css({"display":"none"});

	  $("#register_release").submit(function(){
			 var flag = true;

			 if($('input[name=user_released]:eq(0)').is(':checked') || $('input[name=user_released]:eq(1)').is(':checked')){
				 $("#release_warning").css({"display":"none"});
			 }
			 else {
				 flag = false;
				 $("#release_warning").css({"display":"block"});
			 }

			 if(flag === false){
				 $("#register_release_warning").css({"display":"block"});
			 }
			 else {
				 $("#register_release_warning").css({"display":"none"});
			 }

			 return flag
			}
		);
};
/* ----------------------------------------------------------
Check the input @ practitioner registration
---------------------------------------------------------- */
var checkUserRegistrationInput = function(){
	  $("#gender_profile_warning").css({"display":"none"});
	  $("#profile_warning").css({"display":"none"});
	  $("#profile_charactor_number_warning").css({"display":"none"});
	  $("#register_user_warning").css({"display":"none"});

	  $("#register_user_01").submit(function(){
			 var flag = true;

			 if($("#gender_profile").val() === ""){
				 flag = false;
				 $("#gender_profile_warning").css({"display":"inline"});
			 }
			 else {
				 $("#gender_profile_warning").css({"display":"none"});
			 }

			 if($("#profile").val() === ""){
				 flag = false;
				 $("#profile_warning").css({"display":"inline"});
			 }
			 else {
				 $("#profile_warning").css({"display":"none"});
			 }

			 var numberOfCatchPhraseInput = document.getElementById("catchphrase").value.length;
			 if(numberOfCatchPhraseInput > 100){
				 flag = false;
			 }
			 else {

			 }

			 var numberOfProfileInput = document.getElementById("profile").value.length;
			 if(numberOfProfileInput > 300){
				 flag = false;
			 }
			 else {

			 }

			 if(flag === false){
				 $("#register_user_warning").css({"display":"block"});
			 }
			 else {
				 $("#register_user_warning").css({"display":"none"});
			 }

			 return flag
	  		}
	  );
};
/* ----------------------------------------------------------
Check input is set @ admin Register user
---------------------------------------------------------- */
var adminRegisterUser = function(){
	  $(".hidden").css({"display":"none"});

	  $("#user_last_name_warning").css({"display":"none"});
	  $("#user_first_name_warning").css({"display":"none"});
	  $("#user_email_warning").css({"display":"none"});
	  $("#register_warning").css({"display":"none"});

	  $("#register_user").submit(function(){
		 var flag = true;

		 if($("#user_last_name").val() === ""){
			 flag = false;
			 $("#user_last_name_warning").css({"display":"inline"});
		 }
		 else {
			 $("#user_last_name_warning").css({"display":"none"});
		 }

		 if($("#user_first_name").val() === ""){
			 flag = false;
			 $("#user_first_name_warning").css({"display":"inline"});
		 }
		 else {
			 $("#user_first_name_warning").css({"display":"none"});
		 }

		 if($("#user_mail").val() === ""){
			 flag = false;
			 $("#user_email_warning").css({"display":"inline"});
		 }
		 else {
			 $("#user_email_warning").css({"display":"none"});
		 }

		 if($(flag === false)){
			 $("#register_warning").css({"display":"inline"});
		 }
		 else {
			 $("#register_warning").css({"display":"none"});
		 }

		 return flag
		}
	);
};
/* ----------------------------------------------------------
Check input is set @ article registration
---------------------------------------------------------- */
var articleRegisterWarning = function(){
	  $(".hidden").css({"display":"none"});

	  $("#article_title_warning").css({"display":"none"});
	  $("#date_warning").css({"display":"none"});
	  $("#opening_day_warning").css({"display":"none"});
	  $("#application_url_warning").css({"display":"none"});
	  $("#end_of_applying_warning").css({"display":"none"});
	  $("#article-introduction_warning").css({"display":"none"});
	  $("#register_article_warning").css({"display":"none"});
	  $("#article-introduction_charactor_number_warning").css({"display":"none"});

	  $("#register_article").submit(function(){
		 var flag = 0;

		 if($("#article_title").val() === ""){
			 flag = 1;
			 $("#article_title_warning").css({"display":"inline"});
		 }
		 else {
			 $("#article_title_warning").css({"display":"none"});
		 }

		 if(($("#date").val() === "") && ($('input[name=select_fix]:eq(0)').is(':checked'))){
			 flag = 1;
			 $("#date_warning").css({"display":"inline"});
		 }
		 else {
			 $("#date_warning").css({"display":"none"});
		 }

		 if($("#opening_day").val() === ""){
			 flag = 1;
			 $("#opening_day_warning").css({"display":"inline"});
		 }
		 else {
			 $("#opening_day_warning").css({"display":"none"});
		 }

		 if($("#application_url").val() === ""){
			 flag = 1;
			 $("#application_url_warning").css({"display":"inline"});
		 }
		 else {
			 $("#application_url_warning").css({"display":"none"});
		 }

		 if($("#end_of_applying").val() === ""){
			 flag = 1;
			 $("#end_of_applying_warning").css({"display":"inline"});
		 }
		 else {
			 $("#end_of_applying_warning").css({"display":"none"});
		 }

		 if($("#article-introduction").val() === ""){
			 flag = 1;
			 $("#article-introduction_warning").css({"display":"inline"});
		 }
		 else {
			 $("#article-introduction_warning").css({"display":"none"});
		 }

		 var numberOfInput = document.getElementById("article-introduction").value.length;
		 if(numberOfInput > 3000){
			 flag = 1;
		 }
		 else {

		 }

		 if(flag === 1){
			 $("#register_article_warning").css({"display":"block"});
		 }
		 else {
			 $("#register_article_warning").css({"display":"none"});
		 }

		 if(flag){
				return false; // Cancel the sending
			}
			else{
				return true; // Excecute the sending
			}
		}
	);
};
/* ----------------------------------------------------------
confirm the user profile image
---------------------------------------------------------- */
var confirmImage = function(){
  var flag = false;
  var fileSize = 0;
  $(".ttl-register-warning").css({"display":"none"});

  //Register the event to image file pre-view
  $('#form-register-profile').on('change', 'input[type="file"]', function(e) {
    var file = e.target.files[0],
        reader = new FileReader(),
        $preview = $(".img-preview-01");
        t = this;

    //if the image size is more than 100Mb alert will be issued
	fileSize = e.target.files[0].size;
	if (fileSize > 104857600) {
	     $("#register_profile_warning").css({"display":"block"});
	     flag = false;
	}
	else {
		 $("#register_profile_warning").css({"display":"none"});
		 flag = true;
	}

    // Do nothing if the file is not image.
    if(file.type.indexOf("image") < 0){
      return false;
    }

    // Register the event after finishing to read the file
    reader.onload = (function(file) {
      return function(e) {
        //Remove the existing pre-view
        $preview.empty();
        // Add the image tag to show the image loaded in the .preview domain
        $preview.append($('<img>').attr({
                  src: e.target.result,
                  width: "300px",
                  height: "300px",
                  class: "img-preview-01",
                  title: file.name
              }));
      };
    })(file);
    reader.readAsDataURL(file);

    //if the image size is more than 100Mb cannot move on to next page
    $("#form-register-profile").submit(function(){
		 if (fileSize > 104857600) {
		     flag = false;
		}
		else {
			 flag = true;
		}
		 return flag
		}
	);
   return flag
 });
};
/* ----------------------------------------------------------
confirm eyecatch
---------------------------------------------------------- */
var confirmEyecatch = function(){
	var flag = false;
	var fileSize = 0;
	$(".ttl-register-warning").css({"display":"none"});

	  //Register the event to image file pre-view
	  $('#form-register-eyecatch').on('change', 'input[type="file"]', function(e) {

	    var file = e.target.files[0],
	        reader = new FileReader(),
	        $preview = $(".img-preview-02");
	        t = this;

	    //if the image size is more than 100Mb alert will be issued
	    fileSize = e.target.files[0].size;
	    if (fileSize > 104857600) {
		     $(".ttl-register-must").css({"display":"block"});
		     flag = false;
		}
		else {
			 $(".ttl-register-must").css({"display":"none"});
			 flag = true;
		}

	    // Do nothing if the file is not image.
	    if(file.type.indexOf("image") < 0){
	      return false;
	    }

	    //Event registration if the file input is done.
	    reader.onload = (function(file) {
	      return function(e) {
	        //Remove the existing preview image
	        $preview.empty();
	        // Add the image tag to show the image loaded in the .preview domain
	        $preview.append($('<img class="img-confirm-eyecatch">').attr({
	                  src: e.target.result,
	                  width: "904px",
	                  height: "372px",
	                  class: "img-preview-02",
	                  title: file.name
	              }));
	      };
	    })(file);
	    reader.readAsDataURL(file);

	  //if the image size is more than 100Mb cannot move on to next page
	    $("#form-register-eyecatch").submit(function(){
			 if (fileSize > 104857600) {
			     flag = false;
			}
			else {
				 flag = true;
			}
			 return flag
			}
		);
	    return flag
 });
};
/* ----------------------------------------------------------
confirm article img
---------------------------------------------------------- */
var confirmarticleImg = function(){
  var flag = false;
  var fileSize = 0;
  $(".ttl-register-warning").css({"display":"none"});

  //Register the event to image file pre-view
  //image（1）
  $('#form-register-article-img-01').on('change', function(e) {
    var file = e.target.files[0],
    reader = new FileReader(),
    $preview = $(".img-preview-03");
    t = this;

    //if the image size is more than 50Mb alert will be issued
    fileSize = e.target.files[0].size;
    if (fileSize > 52428800) {
	     $("#register_article_img_01_warning").css({"display":"block"});
	     flag = false;
	}
	else {
		 $("#register_article_img_01_warning").css({"display":"none"});
		 flag = true;
	}

    // Do nothing if the file is not image.
    if(file.type.indexOf("image") < 0){
      return false;
    }

    // Register the event after finishing to read the file
    reader.onload = (function(file) {
      return function(e) {
        //Remove the existing pre-view
        $preview.empty();
        // Add the image tag to show the image loaded in the .preview domain
        $preview.append($('<img>').attr({
          src: e.target.result,
          width: "286px",
          height: "193px",
          class: "img-preview-03",
          title: file.name
        }));
      };
    })(file);
    reader.readAsDataURL(file);

    //if the image size is more than 50Mb cannot move on to next page
    $("#form-register-article-img").submit(function(){
		 if (fileSize > 52428800) {
		     flag = false;
		}
		else {
			 flag = true;
		}
		 return flag
		}
	);
    return flag
  });

  //image（2）
  $('#form-register-article-img-02').on('change', function(e) {
    var file = e.target.files[0],
    reader = new FileReader(),
    $preview = $(".img-preview-05");
    t = this;

    //if the image size is more than 50Mb alert will be issued
    fileSize = e.target.files[0].size;
    if (fileSize > 52428800) {
	     $("#register_article_img_02_warning").css({"display":"block"});
	     flag = false;
	}
	else {
		 $("#register_article_img_02_warning").css({"display":"none"});
		 flag = true;
	}

    // Do nothing if the file is not image.
    if(file.type.indexOf("image") < 0){
      return false;
    }

    // Register the event after finishing to read the file
    reader.onload = (function(file) {
      return function(e) {
        //Remove the existing pre-view
        $preview.empty();
        // Add the image tag to show the image loaded in the .preview domain
        $preview.append($('<img>').attr({
          src: e.target.result,
          width: "286px",
          height: "193px",
          class: "img-preview-05",
          title: file.name
        }));
      };
    })(file);
    reader.readAsDataURL(file);

    //if the image size is more than 50Mb cannot move on to next page
    $("#form-register-article-img").submit(function(){
		 if (fileSize > 52428800) {
		     flag = false;
		}
		else {
			 flag = true;
		}
		 return flag
		}
	);
    return flag
  });

  //image（3）
  $('#form-register-article-img-03').on('change', function(e) {
    var file = e.target.files[0],
    reader = new FileReader(),
    $preview = $(".img-preview-06");
    t = this;

    //if the image size is more than 50Mb alert will be issued
    fileSize = e.target.files[0].size;
    if (fileSize > 52428800) {
	     $("#register_article_img_03_warning").css({"display":"block"});
	     flag = false;
	}
	else {
		 $("#register_article_img_03_warning").css({"display":"none"});
		 flag = true;
	}

    // Do nothing if the file is not image.
    if(file.type.indexOf("image") < 0){
      return false;
    }

    // Register the event after finishing to read the file
    reader.onload = (function(file) {
      return function(e) {
        //Remove the existing pre-view
        $preview.empty();
        // Add the image tag to show the image loaded in the .preview domain
        $preview.append($('<img>').attr({
          src: e.target.result,
          width: "286px",
          height: "193px",
          class: "img-preview-06",
          title: file.name
        }));
      };
    })(file);
    reader.readAsDataURL(file);

    //if the image size is more than 50Mb cannot move on to next page
    $("#form-register-article-img").submit(function(){
		 if (fileSize > 52428800) {
		     flag = false;
		}
		else {
			 flag = true;
		}
		 return flag
		}
	);
    return flag
  });

  //image（4）
  $('#form-register-article-img-04').on('change', function(e) {
    var file = e.target.files[0],
    reader = new FileReader(),
    $preview = $(".img-preview-07");
    t = this;

    //if the image size is more than 50Mb alert will be issued
    fileSize = e.target.files[0].size;
    if (fileSize > 52428800) {
	     $("#register_article_img_04_warning").css({"display":"block"});
	     flag = false;
	}
	else {
		 $("#register_article_img_04_warning").css({"display":"none"});
		 flag = true;
	}

    // Do nothing if the file is not image.
    if(file.type.indexOf("image") < 0){
      return false;
    }

    // Register the event after finishing to read the file
    reader.onload = (function(file) {
      return function(e) {
        //Remove the existing pre-view
        $preview.empty();
        // Add the image tag to show the image loaded in the .preview domain
        $preview.append($('<img>').attr({
          src: e.target.result,
          width: "286px",
          height: "193px",
          class: "img-preview-07",
          title: file.name
        }));
      };
    })(file);
    reader.readAsDataURL(file);

    //if the image size is more than 50Mb cannot move on to next page
    $("#form-register-article-img").submit(function(){
		 if (fileSize > 52428800) {
		     flag = false;
		}
		else {
			 flag = true;
		}
		 return flag
		}
	);
    return flag
  });
};
/* ----------------------------------------------------------
 pageScroll
---------------------------------------------------------- */
var pageScroll = function(){
  $('.js-scroll').click(function() {
    var speed = 400; // Scroll speed
    var href= $(this).attr("href");
    var target = $(href == "#" || href == "" ? 'html' : href);
    var position = target.offset().top;
    if(href == '#'){
      // Move to the top if the link is #.
      $('body,html').animate({scrollTop:0}, speed, 'swing');
    } else {
      // Ohterwise move to the designated id.
      $('body,html').animate({scrollTop:position}, speed, 'swing');
    }
    return false;
  });
}
/* ----------------------------------------------------------
 rollover
---------------------------------------------------------- */
var rollover = function(){
  var suffix = { normal : '_no.', over   : '_on.'}
  $('.js-over').each(function(){
    var a = null;
    var img = null;
    var elem = $(this).get(0);
    if( elem.nodeName.toLowerCase() == 'a' ){
      a = $(this);
      img = $('img',this);
    }else if( elem.nodeName.toLowerCase() == 'img' || elem.nodeName.toLowerCase() == 'input' ){
      img = $(this);
    }
    var src_no = img.attr('src'); // Get the image
    var src_on = src_no.replace(suffix.normal, suffix.over); // Change to the over image
    /* Image chaning */
    if( elem.nodeName.toLowerCase() == 'a' ){
      a.on("mouseover focus",function(){ img.attr('src',src_on); })
      .on("mouseout blur",  function(){ img.attr('src',src_no); });
    }else if( elem.nodeName.toLowerCase() == 'img' ){
      img.on("mouseover",function(){ img.attr('src',src_on); })
       .on("mouseout", function(){ img.attr('src',src_no); });
    }else if( elem.nodeName.toLowerCase() == 'input' ){
      img.on("mouseover focus",function(){ img.attr('src',src_on); })
       .on("mouseout blur",  function(){ img.attr('src',src_no); });
    }
    /* Get the image. */
    var cacheimg = document.createElement('img');
    cacheimg.src = src_on;
  });
};
/* ----------------------------------------------------------
 localNav
---------------------------------------------------------- */
var localNav = function(){
  var navClass = document.body.className.toLowerCase(),
  	navLocal = $(".nav-local"),
    prefix = 'nav',
    current = 'is-current',
    parent = 'is-parent',
    regex = {
			a  : /l/,
			dp : [
			/l[\d]+-[\d]+-[\d]+-[\d]+/,
			/l[\d]+-[\d]+-[\d]+/,
			/l[\d]+-[\d]+/,
			/l[\d]+/
			]
		},
		route = [],
		i,
		l,
		temp,
		node;

  /* Stop showing the child factors. */
  $("ul ul", parent).hide();
  if( navClass.indexOf("ldef") >= -1 ){
  for(i = 0, l = regex.dp.length; i < l; i++){
    temp = regex.dp[i].exec( navClass );
    if( temp ){
      route[i] = temp[0].replace(regex.a, prefix);
    }
  }

  /* Add the class if it is active. */
  if( route[0] ){
    // depth 4
    node = $("a."+route[0], navLocal);
    node.addClass(current);
    node.next().show();
    node.parent().parent().show()
    .parent().parent().show()
    .parent().parent().show();
    node.parent().parent().prev().addClass(parent);
    node.parent().parent()
    .parent().parent().prev().addClass(parent);
    node.parent().parent()
    .parent().parent()
    .parent().parent().prev().addClass(parent);

    }else if( route[1] ){
      // depth 3
      node = $("a."+route[1], navLocal);
      node.addClass(current);
      node.next().show();
      node.parent().parent().show()
      .parent().parent().show();
      node.parent().parent().prev().addClass(parent);
      node.parent().parent()
      .parent().parent().prev().addClass(parent);

    }else if( route[2] ){
      // depth 2
      node = $("a."+route[2], navLocal);
      node.addClass(current);
      node.next().show();
      node.parent().parent().show();
      node.parent().parent().prev().addClass(parent);

    }else if( route[3] ){
      // depth 1
      node = $("a."+route[3], navLocal);
      node.addClass(current);
      node.next().show();
    }
  }
}