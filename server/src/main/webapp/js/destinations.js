function destinations_get() {

    httpGet("/destination", _destinations_fill_table);
    
    return false;
}

function destinations_delete(uri) {
    
    httpDelete(uri, destinations_get);
    
    return false;
}

function destinations_delete_with_confirmation(uri) {
    
    
    doWithConfirm('Do you really want to delete this entry?',function() {
        httpDelete(uri, destinations_get);
    });
    
    return false;
}

function _destinations_fill_table(destinations) {
    
    var tbody_destinations = $("#tbody_destinations");
    
    tbody_destinations.html("");

    for (i=0 ; i<destinations.length ; i++) {
        
        var item = destinations[i];
        
        var row = "<tr>";
        
        row += "<td>"+item.id+"</td>";
        row += "<td>"+item.name+"</td>";
        row += "<td>"+item.lat+"</td>";
        row += "<td>"+item.lon+"</td>";
        row += "<td>"+item.url+"</td>";
        row +="<td>"
        
        // Action buttons
        row += createEditButton('Edit', 'destinations_edit(\''+item.url+'\')') + "&nbsp;";
        row += createRemoveButton('Delete', 'destinations_delete_with_confirmation(\''+item.url+'\')') + "&nbsp;";
        //row += createListButton('Reservations', '');
        
        row += "</td>";
        
        row += "</tr>";
        
        tbody_destinations.append(row);
        
    }
}

function destinations_edit(uri) {

    httpGet(uri, _destinations_fill_edit);
    
    return false;
    
}

function _destinations_fill_edit(item) {

    $('#input_edit_destination_id').val(item.id);
    $('#input_edit_destination_url').val(item.url);
    $('#input_edit_destination_id_sec').val(item.id);
    $('#input_edit_destination_url_sec').val(item.url);
    $('#input_edit_destination_name').val(item.name);
    $('#input_edit_destination_lat').val(item.lat);
    $('#input_edit_destination_lon').val(item.lon);
    
}

function add_destination_submit() {
    
    var item = {
            "name": $('#input_destination_name').val()//,
            //"lat": $('#input_destination_lat').val(),
           // "lon": $('#input_destination_lon').val(),
    };
    
    httpPost('/destination', JSON.stringify(item), _add_destination_response); 
    
    return false;
}

function _add_destination_response(data) {
        
    alert("Successfully added.");
    
    destinations_get();
    
}

function edit_destination_submit() {
    
    var item = {
            "name": $('#input_edit_destination_name').val(),
            "lat": $('#input_edit_destination_lat').val(),
            "lon": $('#input_edit_destination_lon').val(),
    };
    
    httpPut($('#input_edit_destination_url').val(), JSON.stringify(item), _edit_destination_response);
    
    return false; 
}

function _edit_destination_response(data) {
        
    alert("Successfully edited.");
    
    destinations_get();
    
}