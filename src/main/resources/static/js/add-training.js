$(document).ready(function() {

    /*const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });*/

    getVenues();

    function getVenues() {
        $.ajax({
            type : "GET",
            url : "/venues",
            success: function(response){

                if (response.object && response.object.length == 0) {
                    var errorAlert = "<div class='alert alert-warning text-center'>No venues found. " +
                        "<a href='/add-venue'>Create Venue.</a>" +
                        "</div>";

                    $("#response").append(errorAlert);
                    $("#response").css({"display": "block"});
                    $("#add_new_training").css({"display": "none"});

                    return;
                }

                var dropdown = $('#venue-dropdown');

                $.each(response.object, (i, venue) => {

                    dropdown.append($('<option></option>').attr('value', venue.id).text(venue.name));
                });
            },
            error : function(e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    $("#add_new_training").submit(function(evt) {
        evt.preventDefault();

        let trainer = {
            username: userInfo.username
        };

        // PREPARE FORM DATA
        let formData = {
            name : $("#name").val(),
            startDate : $("#startDate").val() + " 00:00:00",
            endDate : $("#endDate").val() + " 00:00:00",
            price : $("#price").val(),
            maxRegistrations : $("#maxRegistrations").val(),
            trainingType : $("#trainingType").val(),
            venueId : $("#venue-dropdown").val(),
            trainer: trainer
        };

        $.ajax({
            url: '/trainings',
            type: 'POST',
            contentType : "application/json",
            data: JSON.stringify(formData),
            dataType : 'json',
            async: false,
            cache: false,
            success: function (response) {
                let trainingString = "{ name: " + formData.name +
                    ", price: " + formData.price + " }";
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    'Newly added training\'s Info = ' + trainingString +
                '</div>';

                $("#response").empty();
                $("#response").append(successAlert);
                $("#response").css({"display": "block"});

                resetUploadForm();
            },
            error: function (response) {

                let errorMsg = response.responseJSON ? (response.responseJSON.desc ? response.responseJSON.desc : ''): '';

                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Failed to create training. ' + errorMsg + '</strong>'+
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
        if(pathname === "/add-training"){
            $(".nav .nav-item a[href='/trainings-list']").addClass("active");
        }
    })();
});