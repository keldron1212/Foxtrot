$(document).ready(function(){

    /*const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });*/


    console.log(userInfo);

    let allTrainings = [];

    getAllTrainings();

    let username = hasAdminRights() ? "" : userInfo.username;

    $.ajax({
        type : "GET",
        url : "/users?username=" + username,
        success: function(response){

            let result = $.isArray(response.object) ? response.object : [response.object];

          $.each(result, (i, user) => {

            let id = user.username;


            let editButton = '<button ' +
                                    'id=' +
                                    '\"' + 'btn_edit_' + id + '\"'+
                                    ' type="button" class="btn btn-primary btn_edit">Edit</button>';

            let get_More_Info_Btn = '<button' +
                                        ' id=' + '\"' + 'btn_id_' + id + '\"' +
                                        ' type="button" class="btn btn-info btn_id">' +
                                        id +
                                        '</button>';

            let tr_id = 'tr_' + id;
            let userRow = '<tr id=\"' + tr_id + "\"" + '>' +
                      '<td class=\"td_username\">' + (user.username ? user.username : '') + '</td>' +
                      '<td class=\"td_name\">' + (user.name ? user.name : '') + '</td>' +
                      '<td class=\"td_surname\">' + (user.surname ? user.surname : '') + '</td>';

            if (user.trainings && user.trainings.length > 0) {
                userRow += '<td class="td_training">';

                $.each(user.trainings, (i, trainingId) => {

                    let myTraining = allTrainings.find(training => training.id == trainingId);

                    userRow += '<div training-id="' + trainingId + '">' + myTraining.name + '</div>';
                });
                userRow += '</td>';
            } else {
                userRow += '<td>No training</td>';
            }

            userRow += '<td>' + editButton + '</td>' +
                      '</tr>';
            $('#usersTable tbody').append(userRow);
          });
        },
        error : function(e) {
          alert("ERROR: ", e);
          console.log("ERROR: ", e);
        }
    });

    function getAllTrainings() {
        $.ajax({
            type : "GET",
            url : "/trainings",
            async: false,
            success: function(response){

                if (response.object && response.object.length == 0) {
                    return;
                }

                allTrainings = response.object;
            },
            error : function(e) {
                alert("ERROR: ", e);
                console.log("ERROR: ", e);
            }
        });
    }

    (function(){
        let pathname = window.location.pathname;
        if (pathname == "/hello") {
            $(".nav .nav-item a[href='/hello']").addClass("active");
        }
        if (pathname == "/trainings") {
            $(".nav .nav-item a[href='/trainings']").addClass("active");
        }
    })();
});

function reverseWords(str) {

    var result = "";

    var reversedString = "",
        reversedArr = [];

    for (var i = str.length - 1; i >= 0; i--) {

        reversedString += str[i];
    }

    // split string into array

    var word = "";

    for (var i = 0; i < reversedString.length; i++) {

        if (reversedString[i] === " ") {

            reversedArr.push(word);
            word = "";
        } else {
            word += reversedString[i];
        }
    }

    for (var i = reversedArr.length - 1; i >= 0; i--) {
        result += reversedArr[i];
    }

    return result;
}