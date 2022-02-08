/* ----------------------------------------------------------
Make unable to input the date if open schedule is selected at the article registration page
---------------------------------------------------------- */
$(function() {
  var switchBtn = $('input[name=select_fix]');
  var switchItems = $('.form-date-registration-03');
  var inputField = 'input, select';

  switchBtn.on('change', function(){

    var inputValue = $(this).val();

    if(inputValue === 'open_date'){
//      switchItems.hide();
      switchItems.find(inputField).each(function(){
        $(this).prop('readonly', true);
      });
    }else{
//      switchItems.show();
      switchItems.find(inputField).each(function(){
        $(this).prop('readonly', false);
      });
    }
  });
});