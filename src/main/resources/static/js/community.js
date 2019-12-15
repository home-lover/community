/*提交回复*/
function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    if(!commentContent){
        alert("不能回复空内容。。。");
        return;
    }
    $.ajax({
        type:"POST",
        contentType:"application/json",
        url:"/comment" ,
        data:JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "type":1
        }),
        success:function (response) {
            if(response.code == 200){
                window.location.reload();
                $("#comment_section").hide();
            }else {
                if(response.code == 2002){
                    var accept = window.confirm(response.message);
                    if(accept){
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.6d8e3c5f95e3d25a&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else{
                    alert(response.message);
                }
            }
            console.log(response);
        } ,
        datatype:"json"
    });
}
/*展开二级评论*/
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active")
    }else {
        //展开二级评论
        comments.addClass("in");
        e.setAttribute("data-collapse","in");
        e.classList.add("active");
    }
}