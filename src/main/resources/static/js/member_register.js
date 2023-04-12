$(window).on("load", function(){  //회원가입 저장버튼 눌렀을 경우 기존입력값에 대한 메시지 출력
  if($("#email").val().length != 0){    dataCheck("email");  }
    if($("#name").val().length != 0){    dataCheck("name");  }
});


$("#email, #name, #password").on("change paste input", function(){

  if($("#email").val().length != 0){    dataCheck("email");  }
  if($("#name").val().length != 0){    dataCheck("name");  }
  dataCheck();
});


function dataCheck(param){
  var elementId = $("*:focus").attr("id");  //현재 focus된 객체 id가져오기
  if(param != null){
    elementId = param;
  }

  var member = {};
  member.email = $("#email").val();
  member.name = $("#name").val();
  member.password = $("#password").val();

  $.ajax({
    type:"POST",
    url:"/register/check",
    contentType: 'application/json',
    data: JSON.stringify(member),
    success: function(data) {
      var inputs = "#"+elementId;
      var helps = "#"+elementId+"Help";
      $(helps).css("display", "block");

      data.forEach(e => {
        if(elementId == e.field){
          if(e.status == true){
            $(inputs).addClass("is-valid").removeClass("is-invalid");
            $(helps).addClass("valid-feedback").removeClass("invalid-feedback");
            $(helps).html(""+e.message);
          }else{
            $(inputs).addClass("is-invalid").removeClass("is-valid");
            $(helps).addClass("invalid-feedback").removeClass("valid-feedback");
            $(helps).html(""+e.message);
          }
        }
      });

    },
    error: function(){

    }
  });
}



$("#passwordConfirm, #password").on("change keyup", function(){

  var password = $("#password").val().trim();
  var passwordConfirm = $("#passwordConfirm").val().trim();

  if(password != null){

    $("#passwordConfirmHelp").css("display", "block");

    if(password != passwordConfirm){
      $("#passwordConfirm").addClass("is-invalid").removeClass("is-valid");
      $("#passwordConfirmHelp").addClass("invalid-feedback").removeClass("valid-feedback");
      $("#passwordConfirmHelp").html("비밀번호가 일치하지 않습니다");
    }else{
      $("#passwordConfirm").addClass("is-valid").removeClass("is-invalid");
      $("#passwordConfirmHelp").addClass("valid-feedback").removeClass("invalid-feedback");
      $("#passwordConfirmHelp").html("비밀번호가 일치합니다");
    }
  }
});


function register(){
  var validchk = $(".is-invalid").length;
//  console.log(validchk);

  if(validchk == 0){
    $("#registerForm").submit();
  }else{
    $(".is-invalid").focus();
  }
}