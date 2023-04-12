$(window).on("load", function(){
    var findHelp = $(".findHelp");
    var findResult = $(".findResult");

    if(findHelp.hasClass("findHelpError")){
        findHelp.css("display", "block");
        $("#floatingEmail, #floatingName").addClass("is-invalid").removeClass("is-valid");
        findHelp.addClass("invalid-feedback").removeClass("valid-feedback");
    }

    if(findResult.hasClass("open")){
        setTimeout(function(){
            findResult.slideDown();
        }, 200);

    }
});