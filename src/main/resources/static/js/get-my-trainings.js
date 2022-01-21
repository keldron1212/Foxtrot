$(document).ready(function(){

    let username = userInfo.username;
    let trainingIdToUnlink = -1;
    let allTrainings = [];

    getAllTrainings();
    getUserInfo();
    registerEvents();

    function getUserInfo() {
        $.ajax({
            type: "GET",
            url: "/users?username=" + username,
            success: function (response) {


                if (response.object && response.object.trainings && response.object.trainings.length == 0) {
                    $('#trainingsTable tbody').append("<tr><td class='alert alert-warning text-center' colspan='10'>No trainings found</td></tr>");
                    return;
                }

                $('#trainingsTable tbody').empty();

                $.each(response.object.trainings, (i, trainingId) => {

                    let training = allTrainings.find(training => training.id == trainingId);

                    let tr_id = 'tr_' + trainingId;
                    let trainingRow = '<tr id=\"' + tr_id + "\"" + '>' +
                        '<td>' + trainingId + '</td>' +
                        '<td>' + training.name + '</td>' +
                        '<td>' + training.startDate.split("T")[0] + '</td>' +
                        '<td>' + training.endDate.split("T")[0] + '</td>' +
                        '<td>' + training.price + '</td>' +
                        '<td>' + training.maxRegistrations + '</td>' +
                        '<td>' + training.trainer.name + '</td>';

                    // if admin then show participants
                    if (hasAdminRights()) {
                        trainingRow += '<td>';

                        $.each(training.participants, function (i, participant) {
                            trainingRow += '<div>' + participant + '</div>';
                        });

                        trainingRow += '</td>';
                    }

                    trainingRow += '<td>' + training.trainingType + '</td>' +
                        '<td>' + training.venue.name + '</td>';

                    let unlinkButton = "<button data-toggle='modal' data-target='#unlink-training-modal' " +
                        "class='btn btn-primary btn-unlink-training' id='unlink-" + trainingId + "'>Unlink</button>";

                    trainingRow += '<td>' + unlinkButton + '</td></tr>';

                    $('#trainingsTable tbody').append(trainingRow);
                });

                $("#div_user_updating").css({"display": "none"});
            },
            error: function (e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    function getAllTrainings() {
        $.ajax({
            type : "GET",
            url : "/trainings",
            async: false,
            success: function(response){

                if (response.object && response.object.length == 0) {

                    let addTrainingLink = hasAdminRights() ? "<a href='/add-training'>Add Training.</a>" : "";

                    $("#trainings_response").empty();
                    $("#trainings_response").append("No Trainings found. " + addTrainingLink);
                    $("#trainings_response").css({"display": "block"});

                    $("#user_trainings").css({"display": "none"});

                    return;
                }

                let trainingsDropdown = $("#user_trainings");
                trainingsDropdown.empty();

                allTrainings = response.object;

                $.each(response.object, (i, training) => {

                    let id = training.id;

                    trainingsDropdown.append($('<option></option>').attr('value', training.id).text(training.name));

                });

                $("#trainings_response").css({"display": "none"});
                trainingsDropdown.css({"display": "block"});
            },
            error : function(e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);

                $("#trainings_response").empty();
                $("#trainings_response").append("Error fetching Trainings.");
                $("#trainings_response").css({"display": "block"});

                $("#user_trainings").css({"display": "none"});
            }
        });
    }

    function registerEvents() {

        $(document).on("click", "button#link-training", function(){

            $("#response").empty();
            $("#response").css({"display": "none"});
            $("#div_user_updating").css({"display": "block"});
        });

        $(document).on("click", "#trainingsTable button.btn-unlink-training", function(){

            let btn_id = (event.srcElement.id);
            trainingIdToUnlink = btn_id.split("-")[1];

            $("div.modal-body")
                .text("Do you want unlink a training with id: " + trainingIdToUnlink + " ?");
            $("#model-unlink-btn").css({"display": "inline"});
        });

        $(document).on("click", "#model-unlink-btn", function() {

            unlinkTraining();
        });

    }

    function unlinkTraining() {

        if (!trainingIdToUnlink || trainingIdToUnlink < 0) {
            return;
        }

        $.ajax({
            url: '/users/trainings?trainingID=' + trainingIdToUnlink,
            type: 'DELETE',
            success: function(response) {
                $("div.modal-body")
                    .text("Unlinked successfully a training with id = " + trainingIdToUnlink + "!");

                $("#model-unlink-btn").css({"display": "none"});
                $("button.btn.btn-secondary").text("Close");

                // delete the customer row on html page
                let row_id = "tr_" + trainingIdToUnlink;
                $("#" + row_id).remove();
                $("#div_user_updating").css({"display": "none"});
            },
            error: function(error){
                console.log(error);
                $("#div_user_updating").css({"display": "none"});
                alert("Error -> " + error);
            }
        });
    }


    $("#link_training_form").submit(function(evt) {
        evt.preventDefault();

        let selectedTrainingId = $("#user_trainings").val();

        if (!selectedTrainingId) {
            return;
        }

        $.ajax({
            url: '/users/trainings?trainingID=' + selectedTrainingId,
            type: 'POST',
            contentType : "application/json",
            dataType : 'json',
            async: false,
            cache: false,
            success: function (response) {
                let trainingString = "{ id: " + selectedTrainingId + " }";
                let successAlert = '<div class="alert alert-success alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    'Newly linked training\'s Info = ' + trainingString +
                    '</div>';

                $("#response").empty();
                $("#response").append(successAlert);
                $("#response").css({"display": "block"});

                getAllTrainings();
                getUserInfo();
            },
            error: function (response) {

                let errorMsg = response.responseJSON ? (response.responseJSON.desc ? response.responseJSON.desc : ''): '';

                let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                    '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                    '<strong>Failed to link training. ' + errorMsg + '</strong>'+
                    '</div>';
                $("#response").empty();
                $("#response").append(errorAlert);
                $("#response").css({"display": "block"});

            }
        });
    });

    (function(){
        let pathname = window.location.pathname;

        if (pathname == "/my-trainings") {
            $(".nav .nav-item a[href='/my-trainings']").addClass("active");
        }
    })();
});