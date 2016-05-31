jQuery(document).ready(function($) {
  var fbTemplate = document.getElementById('fb-template'),
    formContainer = document.getElementById('rendered-form'),
    $fBInstance = $(document.getElementById('edit-form')),
    formRenderOpts = {
      container: $('form', formContainer)
    };

  $(fbTemplate).formBuilder();

  $('.fb-save').click(function() {
    $fBInstance.toggle();
    $(formContainer).toggle();
    $(fbTemplate).formRender(formRenderOpts);
  });

  $('.edit-form', formContainer).click(function() {
    $fBInstance.toggle();
    $(formContainer).toggle();
  });
});