function history(){
//    if ( document.referrer && document.referrer.indexOf("naver.com") != -1 ) {
//        //뒤로갈 히스토리가 있으면,
//    	history.back();    	// 뒤로가기
//    }
//    else {
//        // 히스토리가 없으면,
//    	location.href = "http://localhost:8080";// 메인 페이지로
//    }
}


//============================================================================================================================

$(window).on("load", function(){
    loadComment();
});

function loadComment(){
    var bookPostId = $("#bookPostId").val();
    $.ajax({
        type:"POST",
        url:"/bookPost/detail/commentList",
        contentType: 'application/json',
        data: JSON.stringify({
            "bookPostId":bookPostId,
        }),
        success: function(lists){
            //console.log(lists);
            lists.forEach(list => {
                if(list.reid == 0){
                    $("#commentList").append(commentDesign(list));
                }
            });

            lists.forEach(list => {
                if(list.reid != 0){
                    $("#comt"+(list.reid)+"-"+(list.retype-1)).append(replyCommentDesign(list));
                }
            });
        }
    });
}



function commentDesign(data){
    var createTime = data.createTime.replace(".0", "").substring(2, 19);
    var parent = `
         <div class="commentWrapper mb-2" id="comt${data.id}-${data.retype}">
             <div class="row commentInfo">
                 <div class="col">
                     <p class="mb-2">
                         <i class="fas fa-user"></i> <span style="font-size:0.8em;">${data.writer}</span>
                         <span class="ms-3" style="font-size:0.7em;">${createTime}</span>
                     </p>
                     <p class="mb-1 commentContent">${data.content}</p>
                 </div>
             </div>
             <div class="row pt-1 commentBtn">
                 <div class="col">
                     <span class="commentLink comtReply" onclick="replyOpen(event)">답글</span>
                 </div>
                 <div class="col text-end">
                     <span class="commentLink text-end comtUpdate" onclick="updateOpen(event)">수정</span>
                     <span class="commentLink text-end comtRemove" onclick="deleteComment(event)">삭제</span>
                 </div>
             </div>
         </div>
     `;
    return parent;
}

function replyCommentDesign(data){
    var createTime = data.createTime.replace(".0", "").substring(2, 19);
    var padding = 1 * (data.retype);
    //console.log(padding);
    var result = "";

    var replyLink = `
        <div class="col">
          <span class="commentLink comtReply" onclick="replyOpen(event)">답글</span>
      </div>
    `;

    if(data.retype == 3){
        replyLink = "";
    }

    var child = `
          <div class="commentWrapper mb-2" id="comt${data.id}-${data.retype}" style="padding-left:${padding}rem!important;">
              <div class="row commentInfo" style="background-color:#e9e9e9;">
                  <div class="col">
                      <p class="mb-2">
                          <i class="fas fa-user"></i> <span style="font-size:0.8em;">${data.writer}</span>
                          <span class="ms-3" style="font-size:0.7em;">${createTime}</span>
                      </p>
                      <p class="mb-1 commentContent">${data.content}</p>
                  </div>
              </div>
              <div class="row pt-1 commentBtn" style="background-color:#e9e9e9;">
                  ${replyLink}
                  <div class="col text-end">
                      <span class="commentLink text-end comtUpdate" onclick="updateOpen(event)">수정</span>
                      <span class="commentLink text-end comtRemove" onclick="deleteComment(event)">삭제</span>
                  </div>
              </div>
          </div>
    `;
    return child;
}



function writeCommentDesign(updateText){
    var string = `
      <p class="mb-1 mt-0 text-end comtUpdateWrapper commentWrite">
          <textarea class="w-100" rows="3"; name="content">${updateText}</textarea>
          <button type="button" style="font-size:0.8em;"
              class="btn btn-primary btn-sm comtUpdateOk">수정</button>
          <button type="button" style="font-size:0.8em;"
              class="btn btn-primary btn-sm comtUpdateCancel">취소</button>
      </p>`;
    return string;
}





function commentSubmit(){
    var bookPostId = $("#bookPostId").val();
    var content = $.trim($(".mainComtWrapper textarea").val());
    if(content == ""){
        alert("댓글내용을 입력해주세요");
        return
    }

    $.ajax({
        type:"POST",
        url:"/comment/write",
        contentType: 'application/json',
        data: JSON.stringify({
            "bookPostId":bookPostId,
            "content":content
        }),
        success: function(data){
            if(data != null){
                $("#commentList").append(commentDesign(data));
            }else{  //data가 null일때
                alert("댓글내용을 입력해주세요");
            }
        }
    });
}


function deleteComment(event){
    var wrapper = $(event.target).parents(".commentWrapper")[0];
    var commentId = $(wrapper).attr("id").replace("comt", "").split("-")[0];

    var result = confirm("삭제하시겠습니까?");
    if(result){    //yes
        $.ajax({
            type:"POST",
            url:"/comment/delete",
            contentType: 'application/json',
            data: JSON.stringify({
                "commentId":commentId
            }),
            success: function(data){
                if(data == 1){
                    wrapper.remove();
                }else{
                    alert("삭제되지않았습니다.");
                }
            }
        });
    }else{          //no
        return
    }
}

var updateTarget = "";
function updateOpen(event){
    if(updateTarget == ""){
        updateTarget = event.target;
    }
    if(event.target != updateTarget){
        comtUpdateCancel(updateTarget);
    }
    updateTarget = event.target;

    var wrapper = $(event.target).parents(".commentWrapper")[0];
    var commentId = $(wrapper).attr("id").replace("comt", "").split("-")[0];
    var content = $(wrapper).find(".commentContent")[0];
    var contentText = $(content).text();

    $(content).replaceWith(writeCommentDesign(contentText));
    $(wrapper).find(".commentBtn").hide();
}

$(document).on("click", ".comtUpdateOk", function(){
    var wrapper = $(event.target).parents(".commentWrapper")[0];
    var commentId = $(wrapper).attr("id").replace("comt", "").split("-")[0];
    var contentText = $(event.target).parent().find("textarea").val();

    var content = $.trim(contentText);
    if(content == ""){
        alert("댓글내용을 입력해주세요");
        return
    }

    $.ajax({
        type:"POST",
        url:"/comment/update",
        contentType: 'application/json',
        data: JSON.stringify({
            "id":commentId,
            "content":content
        }),
        success: function(data){
            if(data != null){
                if(data.reid == 0){
                    $(wrapper).replaceWith(commentDesign(data));
                }else{
                    $(wrapper).replaceWith(replyCommentDesign(data));
                }
            }else{  //data가 null일때
                alert("댓글내용을 입력해주세요");
            }
        }
    });
});

$(document).on("click", ".comtUpdateCancel", function(){
    comtUpdateCancel(event.target);
});

function comtUpdateCancel(element){
    var wrapper = $(element).parents(".commentWrapper")[0]; //commentWrapper
    var contentWrapper = $(wrapper).find(".comtUpdateWrapper")[0];
    var contentText = $(contentWrapper).find("textarea").text();

    $(contentWrapper).replaceWith(`
        <p class="mb-1 commentContent" >${contentText}</p>
    `);

    $(wrapper).find(".commentBtn").show();
}


//============================================================================================================================

var replyTarget = "";
function replyOpen(){
    var appendTarget = $(event.target).parents(".commentWrapper")[0];
    //var write = writeCommentDesign("");
    var string = `
        <div class="commentReplyWrapper mb-2 ps-3">
             <div class="row commentInfo">
                 <div class="col">
                     <p class="mb-2">
                         <i class="fas fa-user"></i> <span style="font-size:0.8em;">익명답글@admin</span>
                     </p>
                     <p class="mb-1 mt-0 text-end commentWrite">
                        <textarea class="w-100" rows="3"; name="content"></textarea>
                        <button type="button" style="font-size:0.8em;"
                           class="btn btn-primary btn-sm comtReplyOk">작성</button>
                        <button type="button" style="font-size:0.8em;"
                           class="btn btn-primary btn-sm comtReplyCancel">취소</button>
                    </p>
                 </div>
             </div>
         </div>
     `;
    $(string).appendTo(appendTarget);
}

$(document).on("click", ".comtReplyOk", function(){
    var bookPostId = $("#bookPostId").val();
    var wrapper = $(event.target).parents(".commentWrapper")[0];
    var commentId = $(wrapper).attr("id").replace("comt", "").split("-")[0];
    var retype = $(wrapper).attr("id").replace("comt", "").split("-")[1] //0;
    //console.log(retype);
    var contentText = $(wrapper).find(".commentReplyWrapper textarea").val();

    var replyWrapper = $(event.target).parents(".commentReplyWrapper")[0];

    var content = $.trim(contentText);
    if(content == ""){
        alert("댓글내용을 입력해주세요");
        return
    }

    $.ajax({
        type:"POST",
        url:"/comment/write",
        contentType: 'application/json',
        data: JSON.stringify({
            "bookPostId":bookPostId,
            "content":content,
            "reid":commentId,
            "retype":parseInt(retype)+1
        }),
        success: function(data){
            if(data != null){
                $(replyWrapper).before(replyCommentDesign(data));
                //$(replyCommentDesign(data)).appendTo(wrapper);

                $(replyWrapper).remove();
            }else{  //data가 null일때
                alert("댓글내용을 입력해주세요");
            }
        }
    });
});

$(document).on("click", ".comtReplyCancel", function(){
    comtReplyCancel(event.target);
});

function comtReplyCancel(element){
    var wrapper = $(element).parents(".commentWrapper")[0]; //commentWrapper
    var contentWrapper = $(wrapper).find(".commentReplyWrapper")[0];
    console.log(contentWrapper);
    $(contentWrapper).remove();
}