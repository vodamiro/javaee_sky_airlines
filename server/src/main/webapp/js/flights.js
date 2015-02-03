function flights_get() {

    httpGetMethodFLights("/flight", "", _flights_fill_table);
 
    return false;
}

function flights_delete(uri) {
    
    httpDelete(uri, flights_get);
    
    return false;
}

function flights_delete_with_confirmation(uri) {
    
    doWithConfirm('Do you really want to delete this entry?',function() {
        httpDelete(uri, flights_get);
    });
    
    return false;
}

function _flights_fill_table(flights) {
    
    var tbody_flights = $("#tbody_flights");
    
    tbody_flights.html("");

    for (i=0 ; i<flights.length ; i++) {
        
        var item = flights[i];
        var departure = new Date();
        departure.setISO8601(item.dateOfDeparture);
        
        var row = "<tr>";
        
        row += "<td>"+item.id+"</td>";
        row += "<td>"+item.name+"</td>";
        row += "<td>"+departure.toLocaleString()+"</td>";
        row += "<td>"+item.distance+" km</td>";
        row += "<td>"+item.price+"</td>";
        row += "<td>"+item.from.name+"</td>";
        row += "<td>"+item.to.name+"</td>";
        row += "<td>"+item.url+"</td>";
        row +="<td>"
        
        // Action buttons
        row += createEditButton('Edit', 'flights_edit(\''+item.url+'\')') + "&nbsp;";
        row += createRemoveButton('Delete', 'flights_delete_with_confirmation(\''+item.url+'\')');
        
        //row += createListButton('Reservations', '');
        
        row += "</td>";
        
        row += "</tr>";
        
        tbody_flights.append(row);
        
    }
}

function flights_edit(uri) {

    httpGet(uri, _flights_fill_edit);
    
    return false;
    
}

function _flights_fill_edit(item) {

    $('#input_edit_flight_id').val(item.id);
    $('#input_edit_flight_url').val(item.url);
    $('#input_edit_flight_id_sec').val(item.id);
    $('#input_edit_flight_url_sec').val(item.url);
    $('#input_edit_flight_name').val(item.name);
    $('#input_edit_flight_departure').val(item.dateOfDeparture);
    $('#input_edit_flight_distance').val(item.distance);
    $('#input_edit_flight_price').val(item.price);
    $('#input_edit_flight_seats').val(item.seats);
    $('#input_edit_flight_from').val(item.from.id);
    $('#input_edit_flight_to').val(item.to.id);
    
}

function add_flight_submit() {
    
    var item = {
            "id": -1,
            "name": $('#input_flight_name').val(),
            "dateOfDeparture": $('#input_flight_departure').val(),
            "distance": $('#input_flight_distance').val(),
            "price": $('#input_flight_price').val(),
            "seats": $('#input_flight_seats').val(),
            "from": $('#input_flight_from').val(),
            "to": $('#input_flight_to').val()
    };
    
    httpPost('/flight', JSON.stringify(item), _add_flight_response); 
    
    return false;
}

function _add_flight_response(data) {
        
    alert("Successfully added.");
    
    flights_get();
    
}

function edit_flight_submit() {
    
    var item = {
            "name": $('#input_edit_flight_name').val(),
            "dateOfDeparture": $('#input_edit_flight_departure').val(),
            "distance": $('#input_edit_flight_distance').val(),
            "price": $('#input_edit_flight_price').val(),
            "seats": $('#input_edit_flight_seats').val(),
            "from": $('#input_edit_flight_from').val(),
            "to": $('#input_edit_flight_to').val()
    };
    
    httpPut($('#input_edit_flight_url').val(), JSON.stringify(item), _edit_flight_response);
    
    return false; 
}

function _edit_flight_response(data) {
        
    alert("Successfully edited.");
    
    flights_get();
    
}