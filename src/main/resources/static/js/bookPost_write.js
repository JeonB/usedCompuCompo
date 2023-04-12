var inputs = $("#bookName, #bookCategory, #bookPrice, #bookDescription");
var imgCountMin = 1;
var imgCountMax = 10;
var imgCountMaxMessage = "이미지는 10개까지만 업로드 가능합니다.";

inputs.on("click", function(){
    if($(this).hasClass("is-invalid")){
        $(this).removeClass("is-invalid");
        var thisid = $(this).attr("id");
        $("#"+thisid+"Help").removeClass("ani");
    }
});




var validCheck = 0;
$("#editorFormSubmit").click(function(event){
    var imgCount = $(".previewSlider.write .slideImg").length;
    if(imgCount < imgCountMin){
        alert("이미지는 최소 1개 이상 업로드해야합니다.");
        return false;
    }

    var form = $("#editorForm")[0];
    var formData = new FormData(form);
    uploadFileMap.forEach(function(v, k){
        formData.append("fileList", v);
    });

    removeFileArray.forEach(function(v){
        formData.append("removeFileList", v);
    });
//    console.log(uploadFileMap);
//    console.log($("#fileUploadBtn")[0].files);
//    for (var pair of formData.entries()) {
//        console.log(pair[0]+ ', ' + pair[1]);
//    }

    var url = "/bookPost/write";
    if($(location).attr('href').includes("/edit") == true){
        url = "/bookPost/edit"
    }

    $.ajax({
        url: url,
        type: "POST",
        data: formData,    //formData는 반드시 ajax랑 사용@!!
        contentType: false,
        processData: false,
        success : function(result){
            if(result.status == "validPhoto"){
                alert(imgCountMaxMessage);
            }

            if(result.status == "valid"){
                result.response.forEach(e => {
                    if(e.status == false){
                        $("#"+e.field).addClass("is-invalid");
                        $("#"+e.field+"Help").addClass("ani");
                        $("#"+e.field+"Help").html(""+e.message);
                    }
                    setTimeout(function(){
                        $("#"+e.field+"Help").removeClass("ani");
                    }, 2000);
                });
            }

            if(result.status == "saveOk"){
                //alert(result.response);
                window.location.replace(result.response);
            }

        },
        error: function(error){
            alert(error);
        }
    });
});


