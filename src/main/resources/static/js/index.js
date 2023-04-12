$(".search input").on("keydown", function(key){
    if(key.keyCode == 13){
        $(".search").submit();
    }
});
