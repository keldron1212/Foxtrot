$(document).ready(function(){

    (function(){
        $.ajax({
            type : "GET",
            url : "/trainings",
            success: function(response){

                if (response.object && response.object.length == 0) {
                    $('#trainingsTable tbody').append("<tr><td class='alert alert-warning text-center' colspan='10'>No records found</td></tr>");
                    return;
                }

                $('#trainingsTable tbody').empty();

                $.each(response.object, (i, training) => {

                    let id = training.id;
                    
                    let tr_id = 'tr_' + id;
                    let trainingRow = '<tr id=\"' + tr_id + "\"" + '>' +
                              '<td>' + id + '</td>' +
                              '<td>' + training.name + '</td>' +
                              '<td>' + training.startDate.split("T")[0] + '</td>' +
                              '<td>' + training.endDate.split("T")[0] + '</td>' +
                              '<td>' + training.price + '</td>' +
                              '<td>' + training.maxRegistrations + '</td>' +
                              '<td>' + training.trainer.name + '</td>';

                    // if admin then show participants
                    if (hasAdminRights()) {
                        trainingRow += '<td>';

                        $.each(training.participants, function(i, participant) {
                            trainingRow += '<div>' + participant + '</div>';
                        });

                        trainingRow += '</td>';
                    }

                    trainingRow += '<td>' + training.trainingType + '</td>' +
                        '<td>' + training.venue.name + '</td>';

                    trainingRow += '<td><button class="btn btn-primary btn-link-training" training-name="' + training.name + '" id="link-' + id + '">Register</button></td>';
                    
                    trainingRow += '</tr>';

                    $('#trainingsTable tbody').append(trainingRow);
                });
            },
            error : function(e) {
              alert("ERROR: ", e);
              console.log("ERROR: ", e);
            }
        });
    })();

    $(document).on("click", "table button.btn-link-training", function(){
        let id_of_button = (event.srcElement.id),
            trainingId = id_of_button.split("-")[1],
            trainingName = $(this).attr("training-name");

        linkTraining(trainingId, trainingName);
    });

    function linkTraining(trainingId, trainingName) {

        if (!trainingId) {
            return;
        }

        $.ajax({
            url: '/users/trainings?trainingID=' + trainingId,
            type: 'POST',
            contentType : "application/json",
            dataType : 'json',
            async: false,
            cache: false,
            success: function (response) {

                $("#success-training-name").text(trainingName);

                $("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
                    $("#success-alert").slideUp(500);
                });
            },
            error: function (response) {

                let errorMsg = response.responseJSON ? (response.responseJSON.desc ? response.responseJSON.desc : ''): '';

                $("#failed-training-name").text(trainingName);

                $("#failed-alert").fadeTo(2000, 500).slideUp(500, function() {
                    $("#failed-alert").slideUp(500);
                });

            }
        });
    }

    (function(){
        let pathname = window.location.pathname;

        if (pathname == "/trainings-list") {
            $(".nav .nav-item a[href='/trainings-list']").addClass("active");
        }
    })();
});