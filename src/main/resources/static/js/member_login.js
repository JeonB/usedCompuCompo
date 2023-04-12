$(window).on("load", function(){
    var loginhelp = $(".loginHelp");

    if(loginhelp.hasClass("loginHelpErrorEmail")){
        loginhelp.css("display", "block");
        $("#floatingEmail, #floatingPassword").addClass("is-invalid").removeClass("is-valid");
        loginhelp.addClass("invalid-feedback").removeClass("valid-feedback");
    }

    if(loginhelp.hasClass("loginHelpError")){
        loginhelp.css("display", "block");
        $("#floatingEmail").addClass("is-valid").removeClass("is-invalid");
        $("#floatingPassword").addClass("is-invalid").removeClass("is-valid");
        loginhelp.addClass("invalid-feedback").removeClass("valid-feedback");
    }
});