function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
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