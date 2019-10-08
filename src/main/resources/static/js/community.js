function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);

}

function comment(e) {

    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content);
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {

                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=5668fad92773b48b128c&redirect_uri=http://localhost/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
        },
        javaType: "json"
    });
}

/*
展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    // console.log(id);
    // console.log(comments);
    //获取一下二级评论的展开状态
    //  var collapse=e.getAttribute("data-collapse");
    // if(collapse){
    //
    //
    //     e.removeAttribute("data-collapse");
    //     e.classList.remove("active");
    // }else{
    //     //展开二级评论
    //
    //     //标记二级评论展开状态
    //
    //     e.setAttribute("data-collapse","in");
    //     e.classList.add("active");
    // }
    $(e).toggleClass("active");
    comments.toggleClass("in");
    if ($(e).hasClass("active")) {
        var commentBody = $("#comment-body-" + id);
        var subCommentBody = $("#comment-" + id);
        if (subCommentBody.children().length == 1) {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data, function (key, comment) {
                    var date = new Date(parseInt(comment.gmtCreate));
                    var month = date.getMonth() + 1;
                    subCommentBody.prepend("<div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments\"\n" +
                        "                                \n" +
                        "                                <div class=\"media\">\n" +
                        "                                    <div class=\"media-left\">\n" +
                        "                                        <a href=\"#\">\n" +
                        "                                            <img class=\"media-object img-rounded\" src=" + comment.user.avatarUrl + ">\n" +
                        "                                        </a>\n" +
                        "                                    </div>\n" +
                        "                                    <div class=\"media-body\">\n" +
                        "                                        <h5 class=\"media-heading\">\n" +
                        "                                            <span >" + comment.user.name + "</span>\n" +
                        "                                        </h5>\n" +
                        "                                        <div>" + comment.content + "</div>\n" +
                        "                                        <div class=\"menu\">\n" +
                        "                                            <span class=\"pull-right\"\n" +
                        "                                                  >" + date.getFullYear() + "-" + month + "-" + date.getDate() + "</span>\n" +
                        "                                        </div>\n" +
                        "                                    </div>\n" +
                        "                                </div>\n" +
                        "                            </div>");
                });


            })
        }

    }
}
// $("#tag-text").focus(function () {
//     $("#select-tag").css("display","block");
// })
function selectTag(value) {
    var previous = $("#tag").val();
    if(previous.indexOf(value)==-1){

    if(previous){
        $("#tag").val(previous+','+value);
    }else {
        $("#tag").val(value);
    }
    }
}