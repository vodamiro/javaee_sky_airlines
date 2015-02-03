function reservations_get() {

    httpGet("/reservation", _reservations_fill_table);
    
    return false;
}

function reservations_delete(uri) {
    
    httpDelete(uri, reservations_get);
    
    return false;
}

function reservations_delete_with_confirmation(uri) {
    
    
    doWithConfirm('Do you really want to delete this entry?',function() {
        httpDelete(uri, reservations_get);
    });
    
    return false;
}

function _reservations_fill_table(reservations) {
    
    var tbody_reservations = $("#tbody_reservations");
    
    tbody_reservations.html("");

    for (i=0 ; i<reservations.length ; i++) {
        
        var item = reservations[i];
        var created = new Date();
        created.setISO8601(item.created);
        
        var row = "<tr>";
        
        row += "<td>"+item.id+"</td>";
        row += "<td>"+item.flight.id+"</td>";
        row += "<td>"+created.toLocaleString()+"</td>";
        row += "<td>"+item.password+"</td>";
        row += "<td>"+item.seats+"</td>";
        row += "<td>"+item.state+"</td>";
        row += "<td>"+item.url+"</td>";
        row +="<td>"
        
        // Action buttons
        row += createEditButton('Edit', 'reservations_edit(\''+item.url+'\')') + "&nbsp;";
        row += createRemoveButton('Delete', 'reservations_delete_with_confirmation(\''+item.url+'\')') + "&nbsp;";
        row += createEmail('E-mail', '/MsgReservationSender?id='+item.id);
        //row += createListButton('Reservations', '');
        
        row += "</td>";
        
        row += "</tr>";
        
        tbody_reservations.append(row);
        
    }
}

function reservations_edit(uri) {

    httpGet(uri, _reservations_fill_edit);
    
    return false;
    
}

function _reservations_fill_edit(item) {
    
    $('#input_edit_reservation_id').val(item.id);
    $('#input_edit_reservation_url').val(item.url);
    $('#input_edit_reservation_id_sec').val(item.id);
    $('#input_edit_reservation_url_sec').val(item.url);
    $('#input_edit_reservation_state').val(item.state);
    $('#input_reservation_seats_sec').val(item.seats);
    
}

function add_reservation_submit() {
    
    var item = {
            "flight": $('#input_reservation_flight').val(),
            "seats": $('#input_reservation_seats').val()
    };
    
    httpPost('/reservation', JSON.stringify(item), _add_reservation_response); 
    
    return false;
}

function _add_reservation_response(data) {
        
    alert("Successfully added.");
    
    reservations_get();
    
}

function edit_reservation_submit() {
    
    var item = {
            "state": $('#input_edit_reservation_state').val(),
            "seats": $('#input_reservation_seats_sec').val()
    };
    
    httpPut($('#input_edit_reservation_url').val(), JSON.stringify(item), _edit_reservation_response);
    
    return false; 
}

function _edit_reservation_response(data) {
        
    alert("Successfully edited.");
    
    reservations_get();
    
}