//=============================================================================
 var previewSliders = new Map();
 $('.previewSlider .swiper').each(function(i) {
   var thisID = $(this).attr('id');

   previewSliders.set("previewSlider_"+thisID, new Swiper('#'+thisID, {
        loop: false,
        lazy : {
            loadPrevNext : true // 이전, 다음 이미지는 미리 로딩
        },
    }
    ));
 });


 var previewSliderLists = new Map();
 $('.previewSliderList .swiper').each(function(i) {
   var thisID = $(this).attr('id');

   previewSliderLists.set("previewSliderList_"+thisID, new Swiper('#'+thisID, {
        loop: false,
        navigation: {
            nextEl: '#next_'+thisID,
            prevEl: '#prev_'+thisID,
        },
        lazy : {
            loadPrevNext : true // 이전, 다음 이미지는 미리 로딩
        },
        breakpoints: {
            320: {  slidesPerView: 3,  spaceBetween: 10, slidesPerGroup: 3, },
            500: {  slidesPerView: 'auto',  spaceBetween: 10, slidesPerGroup: 4, },
        },
    }
    ));
 });

//================================================================================

var imgCountMin = 1;
var imgCountMax = 10;
var imgCountMaxMessage = "이미지는 10개까지만 업로드 가능합니다.";
var uploadFileMap = new Map();
var removeFileArray = new Array();

//파일추가==========================================================================
// 파일 개수 확인한 후 파일 선택
$(".addSlideImg").on("click", function(){
    var imgCount = $(".previewSlider.write .slideImg").length;
    //console.log(imgCount+"개 업로드되어있음");
    if(imgCount >= imgCountMax){
        alert(imgCountMaxMessage);
        return;
    }
    $("#fileUploadInput").click();
});

//파일 선택 후 실행됨. 파일 map에 저장
$("#fileUploadInput").on("change", function(event){
    var files = event.target.files; // = $("#fileUploadBtn")[0].files
    /* FileList반환
       [0]: File / length: 1
       name: "캡처.JPG"(파일명), size: 107939(파일사이즈), type: "image/jpeg" (파일형식)
       업로드 후 files는 초기화된다
     */
    var filesArr = Array.prototype.slice.call(files);
    // File로 분리

    var blobsrc = "";
    var count = 0;
    filesArr.forEach(function(file){
        count++;
        if(count > imgCountMax){ } //아무것도안함 }
        if(count <= imgCountMax){
            if(!file.type.match("image.*")){
                alert("이미지 확장자만 가능합니다");
                return;
            }

            blobsrc = URL.createObjectURL(file);
            //Blob 반환. Binary Large Object
            //blob객체의 url주소값으로 이미지를 불러올 수 있게된다.
            //이렇게 생성된 주소는 브라우저의 메모리에 올라가있다.

            uploadFileMap.set(blobsrc, file);
            addSlideDesign(blobsrc);
        }
    });

    if(count > imgCountMax){
        alert(imgCountMaxMessage+"\n10개 이후의 이미지는 삭제되었습니다.");
    }

    blobsrc.onload = function(){
        URL.rejectObjectUrl(blobsrc);
        //이미지 로딩 후 URL 메모리에서 해제
    }
});

//슬라이드 추가
function addSlideDesign(blobsrc){
    //console.log(blobsrc);
    //blob:http://localhost:8080/20e84207-1992-41ab-9b88-7949bb94f05f
    var previewId = $(".previewSlider.write .swiper").attr('id');
    previewSliders.get("previewSlider_"+previewId)
    .prependSlide(`
        <div class="swiper-slide card shadow-sm remove-position">
            <div id="removeSlide-${previewId}" class="removeSlideImg">
                <i class="fas fa-times"></i>
            </div>
            <img class="slideImg" src="${blobsrc}">
        </div>
    `);

    var listID = $(".previewSliderList.write .swiper").attr('id');
    previewSliderLists.get("previewSliderList_"+listID)
    .prependSlide(`
        <div class="swiper-slide card shadow-sm">
            <img class="slideImg" src="${blobsrc}">
        </div>
    `);

    clickSlideShow($(".previewSliderList.write .slideImg").first());
}

//파일보기=========================================================================
$(document).on("click", ".previewSliderList .slideImg" , function() {
     clickSlideShow($(this)); //동적생성객체는 document로 선택해야함
});

function clickSlideShow(thisElement){
    var imgIndex = $(".previewSliderList .slideImg").index($(thisElement));
    var previewId = $(".previewSlider .swiper").attr('id');

    previewSliders.get("previewSlider_"+previewId)
    .slideTo(imgIndex, 1000, false);
}


//파일삭제==========================================================================
$(document).on("click", ".previewSlider.write .removeSlideImg" , function() {
     removeSlide($(this));     //동적생성객체는 document로 선택해야함
});

function removeSlide(thisElement){
    var imgIndex = $(".previewSlider.write .removeSlideImg").index($(thisElement));
    var blobsrc = $(".previewSlider.write .swiper-slide-active img").attr("src");
    uploadFileMap.delete(blobsrc);

    removeFileArray.push($(thisElement).parent().children("img").attr("src"));

    $(".previewSlider.write .swiper-slide").eq(imgIndex).remove();
    $(".previewSliderList.write .swiper-slide").eq(imgIndex).remove();
}


