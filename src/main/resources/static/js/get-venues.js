$(document).ready(function(){

    /*const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    });*/

    (function(){
        $.ajax({
            type : "GET",
            url : "/venues",
            success: function(response){

                if (response.object && response.object.length == 0) {
                    $('#venuesTable tbody').append("<tr><td class='alert alert-warning text-center' colspan='10'>No records found</td></tr>");
                }

                $.each(response.object, (i, venue) => {
                
                    /*  <button type="button" class="btn btn-danger btn_delete" data-toggle="modal" data-target="#myModal">
                    Open modal
                    </button>*/
                    
                    let id = venue.id;
                    
                    // let deleteButton = '<button ' +
                    //                         'id=' +
                    //                         '\"' + 'btn_delete_' + id + '\"'+
                    //                         ' type="button" class="btn btn-danger btn_delete" data-toggle="modal" data-target="#delete-modal"' +
                    //                         '>&times</button>';
                    
                    // let get_More_Info_Btn = '<button' +
                    //                             ' id=' + '\"' + 'btn_id_' + id + '\"' +
                    //                             ' type="button" class="btn btn-info btn_id">' +
                    //                             id +
                    //                             '</button>';

                    // let edit_Btn = '<button' +
                    //                             ' id=' + '\"' + 'btn_id_' + id + '\"' +
                    //                             ' type="button" class="btn btn-info btn_id">Edit</button>';
                    
                    let tr_id = 'tr_' + id;
                    let venueRow = '<tr id=\"' + tr_id + "\"" + '>' +
                              // '<td>' + get_More_Info_Btn + '</td>' +
                              '<td class=\"td_id\">' + venue.id + '</td>' +
                              '<td class=\"td_name\">' + venue.name + '</td>' +
                              '<td class=\"td_location\">' + venue.location + '</td>';
                    
                    // venueRow += '<td>' + edit_Btn + '</td>' +
                    //           '</tr>';
                    $('#venuesTable tbody').append(venueRow);
                });
            },
            error : function(e) {
              alert("ERROR: ", e);
              console.log("ERROR: ", e);
            }
        });
    })();

    (function(){
        let pathname = window.location.pathname;

        if (pathname == "/venues-list") {
            $(".nav .nav-item a[href='/venues-list']").addClass("active");
        }
    })();
});