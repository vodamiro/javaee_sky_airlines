Date.prototype.setISO8601 = function(dString){
    var regexp = /(\d\d\d\d)(-)?(\d\d)(-)?(\d\d)(T)?(\d\d)(:)?(\d\d)(:)?(\d\d)(\.\d+)?(Z|([+-])(\d\d)(:)?(\d\d))/;
    if (dString.toString().match(new RegExp(regexp))) {
        var d = dString.match(new RegExp(regexp));
        var offset = 0;
        this.setUTCDate(1);
        this.setUTCFullYear(parseInt(d[1],10));
        this.setUTCMonth(parseInt(d[3],10) - 1);
        this.setUTCDate(parseInt(d[5],10));
        this.setUTCHours(parseInt(d[7],10));
        this.setUTCMinutes(parseInt(d[9],10));
        this.setUTCSeconds(parseInt(d[11],10));
        if (d[12]) {
            this.setUTCMilliseconds(parseFloat(d[12]) * 1000);
        }
        else {
            this.setUTCMilliseconds(0);
        }
        if (d[13] != 'Z') {
            offset = (d[15] * 60) + parseInt(d[17],10);
            offset *= ((d[14] == '-') ? -1 : 1);
            this.setTime(this.getTime() - offset * 60 * 1000);
        }
    }
    else {
        this.setTime(Date.parse(dString));
    }
    return this;
};

function IsJsonString(str) {
    try {
        JSON.parse(str);
    } catch (e) {
        return false;
    }
    return true;
}

/////////////////////////////////////////////////////////////////////
// Constants
/////////////////////////////////////////////////////////////////////

var API_URL = "http://"+location.hostname+":"+location.port+"/rest";

/////////////////////////////////////////////////////////////////////
// Dialogs
/////////////////////////////////////////////////////////////////////

function doWithConfirm(text, method) {
	var r = confirm(text);
	if (r == true) {
		method();
	}
	return false;
}

/////////////////////////////////////////////////////////////////////
// AJAX handling
/////////////////////////////////////////////////////////////////////

function httpMethod(method, url, input_data, success_callback) {
       
       console.log(input_data);
    $.ajax({
        type: method,
        url: API_URL+url,
        data: input_data,
        dataType: 'json',
        contentType: "application/json",
        success: function( data ) {
            success_callback(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (IsJsonString(xhr.responseText)) {
                var o = JSON.parse(xhr.responseText);
                
                if (o.fieldViolations.length>0) {
                    alert('Error: \''+o.fieldViolations[0].value+'\' - '+o.fieldViolations[0].message);
                } else if (o.parameterViolations.length>0) {
                    alert('Error: \''+o.parameterViolations[0].value+'\' - '+o.parameterViolations[0].message);
                } else if (o.propertyViolations.length>0) {
                    alert('Error: \''+o.propertyViolations[0].value+'\' - '+o.propertyViolations[0].message);
                } else {
                    alert('Error: '+xhr.responseText);
                }

            } else {
                alert('Error ' + xhr.status +' - ' + thrownError + ': '+xhr.responseText);
            }
        },
    });
    
}

function httpGetMethodFLights(url, input_data, success_callback) {
       
    $.ajax({
        type: "GET",
        url: API_URL+url,
        data: input_data,
        dataType: 'json',
        contentType: "application/json",
        beforeSend: function (request)
            {
                
                if ($('#input_filter_order').val() && $('#input_filter_base').val() && $('#input_filter_offset').val() && $('#input_filter_date_from').val() && $('#input_filter_currency').val() && $('#input_filter_date_to').val()) {
                    var order = $('#input_filter_order').val();
                    var base = $('#input_filter_base').val();
                    var offset = $('#input_filter_offset').val();
                    var filter = "dateOfDepartureFrom="+$('#input_filter_date_from').val()+",dateOfDepartureTo="+$('#input_filter_date_to').val();
                    var currency = $('#input_filter_currency').val();

                    request.setRequestHeader("X-Order", order);
                    request.setRequestHeader("X-Base", base);
                    request.setRequestHeader("X-Offset", offset);
                    request.setRequestHeader("X-Filter", filter);
                    request.setRequestHeader("X-Currency", currency);
                }
               
            },
        success: function( data ) {
            success_callback(data);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (IsJsonString(xhr.responseText)) {
                var o = JSON.parse(xhr.responseText);
                
                if (o.fieldViolations.length>0) {
                    alert('Error: \''+o.fieldViolations[0].value+'\' - '+o.fieldViolations[0].message);
                } else if (o.parameterViolations.length>0) {
                    alert('Error: \''+o.parameterViolations[0].value+'\' - '+o.parameterViolations[0].message);
                } else if (o.propertyViolations.length>0) {
                    alert('Error: \''+o.propertyViolations[0].value+'\' - '+o.propertyViolations[0].message);
                } else {
                    alert('Error: '+xhr.responseText);
                }

            } else {
                alert('Error ' + xhr.status +' - ' + thrownError + ': '+xhr.responseText);
            }
        },
    });
    
}

function httpGet(url, success_callback) {
        
    httpMethod("GET", url, "", success_callback);
    
}

function httpDelete(url, success_callback) {
       
    httpMethod("DELETE", url, "", success_callback);
    
}

function httpPost(url, data, success_callback) {
       
    httpMethod("POST", url, data, success_callback);
    
}

function httpPut(url, data, success_callback) {
       
    httpMethod("PUT", url, data, success_callback);
    
}

/////////////////////////////////////////////////////////////////////
// Buttons generating
/////////////////////////////////////////////////////////////////////

function createEditButton(title, onclick) {
    return '<span class="btn btn-warning btn-sm" onclick="'+onclick+'" role="button"><span class="glyphicon glyphicon-edit"></span> '+title+'</span>';
}

function createRemoveButton(title, onclick) {
    return '<span class="btn btn-danger btn-sm" onclick="'+onclick+'" role="button"><span class="glyphicon glyphicon-remove"></span> '+title+'</span>';
}

function createListButton(title, onclick) {
    return '<span class="btn btn-info btn-sm" onclick="'+onclick+'" role="button"><span class="glyphicon glyphicon-list"></span> '+title+'</span>';
}

function createEmail(title, href) {
    return '<a class="btn btn-success btn-sm" href="'+href+'" role="button"><span class="glyphicon glyphicon-envelope"></span> '+title+'</span>';
}

//create a new WebSocket object.
websocket = new WebSocket('ws://'+location.hostname+':'+location.port+'/sessionsCounter'); 
websocket.onopen = function(evt) {
    console.log('Websocket opened'); 
}; 

websocket.onclose = function(evt) { 
    console.log('Websocket closed'); 
    var domain = location.hostname; 
    var port = 8080;
    if (location.port==8080) {
        port = 8180;
    }
    location.replace('http://'+domain+':'+port+'/');
};

websocket.onmessage = function(evt) { 
    var msg = JSON.parse(evt.data);
    if (msg.action==="count") {
        $('#sessionsCounter').html(msg.content);
    }
    //websocket.close(); 
};

websocket.onerror = function(evt) { 
    console.log('Websocket error'); 
};

