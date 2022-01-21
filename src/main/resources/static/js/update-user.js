$(document).ready(function(){

    let usernameToUpdate = null;

    $("#update_user_form").submit(function(evt) {
        evt.preventDefault();
        try {

            // PREPARE FORM DATA
            let formData = {
                username: usernameToUpdate,
                name : $("#user_name").val(),
                surname :  $("#user_surname").val()
                // trainings: $("#user_trainings").val()
            };

            let url = "/users";

            // Admin updating other user
            if (userInfo.username !== usernameToUpdate) {
                url = "/admin/users";
            }

            $.ajax({
                url: url,
                type: 'PUT',
                contentType : "application/json",
                data: JSON.stringify(formData),
                dataType : 'json',
                async: false,
                cache: false,
                success: function (response) {

                    let userString = "{ name: " + formData.name +
                        ", surname: " + formData.surname + " }";
                    let successAlert = '<div class="alert alert-success alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        'Update user\'s Info = ' + userString +
                    '</div>';

                    $("#response").empty();
                    $("#response").append(successAlert);
                    $("#response").css({"display": "block"});

                    resetUploadForm();
                    // change the updated data for user table record
                    $("#tr_" + usernameToUpdate + " td.td_name").text(formData.name);
                    $("#tr_" + usernameToUpdate + " td.td_surname").text(formData.surname);


                    // for trainings
                    /*let trainings = "";

                    $.each(formData.trainings, function (i, trainingId) {

                        let trainingName = $("#user_trainings option[value=" + trainingId + "]").text();
                        trainings += '<div training-id="' + trainingId + '">' + trainingName + '</div>';
                    });

                    if (trainings.length > 0) {
                        $("#tr_" + usernameToUpdate + " td.td_training").html(trainings);
                    } else {
                        $("#tr_" + usernameToUpdate + " td.td_training").text("No training");
                    }*/
                },

                error: function (response) {
                    let errorMsg = response.responseJSON ? (response.responseJSON.desc ? response.responseJSON.desc : ''): '';

                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                        '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                        '<strong>Failed to update user. ' + errorMsg + '</strong>'+
                        '</div>';

                    $("#response").empty();
                    $("#response").append(errorAlert);
                    $("#response").css({"display": "block"});
                }
            });
        } catch(error){
            console.log(error);
            alert(error);
        }
    });

    function resetUploadForm(){
        $("#user_name").val("");
        $("#user_surname").val("");

        $("#div_user_updating").css({"display": "none"});
    }

    $(document).on("click", "table button.btn_edit", function(){
        let id_of_button = (event.srcElement.id);
        usernameToUpdate = id_of_button.split("_")[2];

        let row = $("#tr_" + usernameToUpdate),
            name = $(row).find("td.td_name").text().trim(),
            surname = $(row).find("td.td_surname").text().trim(),
            trainingDivs = $(row).find("td.td_training div");

        // for trainings

        /*let trainingDropdown = $("#user_trainings");
        trainingDropdown.empty();

        $.each(trainingDivs, function (i, trainingDiv) {

            let trainingId = $(trainingDiv).attr("training-id"),
                trainingName = $(trainingDiv).text().trim();

            trainingDropdown.append($('<option selected></option>').attr('value', trainingId).text(trainingName));
        });*/

        $("#user_name").val(name);
        $("#user_surname").val(surname);

        $("#response").empty();
        $("#div_user_updating").css({"display": "block"});
    });
});