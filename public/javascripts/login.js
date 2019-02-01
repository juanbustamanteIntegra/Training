function validateEmail(email) {
  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
  return emailReg.test( email );
}

$(document).ready(function() {
  $(".register-form").submit(function(e) {
    var emailVal = $("#createEmail").val().toLowerCase();
    var nameVal = $("#createName").val().toLowerCase();
    var passVal = $("#createPass").val().toLowerCase();
    var cPassVal = $("#createCPass").val().toLowerCase();
    if(emailVal.length == 0 || nameVal.length == 0 || passVal.length == 0 || cPassVal.length == 0)
    {
      alert("Please enter all the fields.");
      e.preventDefault();
    }
    else if(passVal.length < 6) {
      alert("Password must have at least 6 characters.");
      e.preventDefault();
    }
    else if(passVal !== cPassVal) {
      alert("Passwords must match.");
      e.preventDefault();
    }
    else if(! validateEmail(emailVal))
    {
      alert("Please enter a valid email.");
      e.preventDefault();
    }
  });
})
