$(document).ready(function() {

    /*const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });*/

    $("#add_new_venue").submit(function(evt) {
        evt.preventDefault();

        // PREPARE FORM DATA
        let formData = {
            name : $("#name").val(),
            location : $("#location").val()
        };

        $.ajax({
            url: '/venues',
            type: 'POST',
            contentType : "application/json",
            data: JSON.stringify(formData),
            dataType : 'json',
            async: false,
            cache: false,
            success: function (response) {
                let venue = response.object;
                let venueString = "{ name: " + formData.name +
                    ", location: " + formData.location + " }";
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    'Newly added venue\'s Info = ' + venueString;
                '</div>'
                $("#response").empty();
                $("#response").append(successAlert);
                $("#response").css({"display": "block"});

                resetUploadForm();
            },
            error: function (response) {
                let errorMsg = response.responseJSON ? (response.responseJSON.desc ? response.responseJSON.desc : ''): '';

                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Failed to create Venue. ' + errorMsg + '</strong>'+
                    '</div>';
                $("#response").empty();
                $("#response").append(errorAlert);
                $("#response").css({"display": "block"});

                resetUploadForm();
            }
        });
    });

    function resetUploadForm(){
        $("#name").val("");
        $("#location").val("");
    }

    (function(){
        let pathname = window.location.pathname;
        if(pathname === "/add-venue"){
            $(".nav .nav-item a[href='/venues-list']").addClass("active");
        }
    })();
});