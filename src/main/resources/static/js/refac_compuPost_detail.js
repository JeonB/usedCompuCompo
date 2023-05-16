window.addEventListener("load", function() {
  loadComment();
});

function loadComment() {
  var compuPostId = document.getElementById("compuPostId").value;
  var xhr = new XMLHttpRequest();
  xhr.open("POST", "/compuPost/detail/commentList");
  xhr.setRequestHeader("Content-Type", "application/json");
  xhr.onreadystatechange = function() {
    if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
      var lists = JSON.parse(this.responseText);
      lists.forEach(function(list) {
        if (list.reid === 0) {
          document.getElementById("commentList").insertAdjacentHTML("beforeend", commentDesign(list));
        }
      });
      lists.forEach(function(list) {
        if (list.reid !== 0) {
          document.getElementById("comt" + list.reid + "-" + (list.retype - 1)).insertAdjacentHTML("beforeend", replyCommentDesign(list));
        }
      });
    }
  };
  var data = JSON.stringify({
    "compuPostId": compuPostId,
  });
  xhr.send(data);
}