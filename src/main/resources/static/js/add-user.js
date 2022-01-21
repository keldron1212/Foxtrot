$(document).ready(function() {

    /*const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });*/

    $("#add_new_user").submit(function(evt) {
        evt.preventDefault();

        // PREPARE FORM DATA
        let formData = {
            username : $("#username").val(),
            password : $("#password").val(),
            name : $("#name").val(),
            surname :  $("#surname").val()
        };

        $.ajax({
            url: '/users',
            type: 'POST',
            contentType : "application/json",
            data: JSON.stringify(formData),
            dataType : 'json',
            async: false,
            cache: false,
            success: function (response) {
                let user = response.object;
                let userString = "{ name: " + formData.name + " " + formData.surname +
                    ", username: " + formData.username + " }";
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>' + response.message + '</strong> User\'s Info = ' + userString;
                '</div>'
                $("#response").append(successAlert);
                $("#response").css({"display": "block"});

                resetUploadForm();
            },
            error: function (response) {
                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>' + response.message + '</strong>' + ' ,Error: ' + response.error +
                    '</div>'
                $("#response").append(errorAlert);
                $("#response").css({"display": "block"});

                resetUploadForm();
            }
        });
    });

    function resetUploadForm(){
        $("#name").val("");
        $("#surname").val("");
        $("#username").val("");
        $("#password").val("");
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/hello"){
            $(".nav .nav-item a[href='/hello']").addClass("active");
        } else if (pathname == "/trainings-list") {
            $(".nav .nav-item a[href='/trainings-list']").addClass("active");
        }
    })();
});