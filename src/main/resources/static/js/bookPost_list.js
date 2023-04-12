$(window).on("load", function(){
    //loadList();
});

$("#bookname, #writeremail, #createtime, #viewcount").on("click", function(){
    loadList($(this));
});


function loadList(element){
    var categoryName;
    //console.log($(".categoryName"));
    if($(".categoryName").length != 0){
        categoryName = $(".categoryName").attr("id").toLowerCase();
    }else{
        categoryName = null;
    }
    var page = $(".pagination li.active a").text();
    var order = orderProcess(element);
    var searchRange = $(".search select").val();
    var searchText = $(".search input").val();

    var queryParam = {
        "categoryName":categoryName,
        "page":page,
        "order":order,
        "searchRange":searchRange,
        "searchText":searchText
    };

    $.ajax({
        type:"GET",
        url:"/category/listOrder",
        data: queryParam,
    }).done(function(fragment){
        $("#listTable").replaceWith(fragment);
    });
}

function orderProcess(element){
    var idName = $(element).attr("id");
    var className = $(element).attr("class");
    var span = $($(element).children("span"));
    var order = "";
    if(element == null || element == undefined){
            order = "createtime desc";
    }else{
        if(className == "asc"){
            $(element).removeClass("asc").addClass("desc");
            span.text("↓");
        }
        if(className == "desc"){
            $(element).removeClass("desc").addClass("asc");
            span.text("↑");
        }

        var timeMethod = $("#createtime").attr("class");
        if(idName == "createtime"){
            order = "createtime "+timeMethod;
        }else{
            order = $(element).attr("id")+" "+$(element).attr("class")+", createtime "+timeMethod;
        }
    }
    return order;
}

//===========================================================================
$(".search input").on("keydown", function(key){
    if(key.keyCode == 13){
        $(".search").submit();
    }
});