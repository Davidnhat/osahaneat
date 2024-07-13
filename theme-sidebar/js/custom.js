$(document).ready(function(){
    $("#btn-signin").click(function(){

        var email = $("#email").val()
        var password = $("#password").val()

        $.ajax({
            method: "POST",
            url: "http://localhost:8080/login/signin",
            data: { 
                username: email, 
                password: password 
            }
          })
            .done(function( msg ) {
                console.log(msg)
                if(msg.success){
                    localStorage.setItem("token",msg.data) //lưu trữ lại token
                    window.location.href = './index.html'
                } else{
                    alert("Fail!")
                }        
            });
    })
})