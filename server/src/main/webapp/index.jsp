<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
      <head>
            <title>SkyAirlines</title>
            <meta content="">
            <meta charset="utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">

            <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
            
            <link rel="stylesheet" href="./css/custom.css">
            
            <link rel="stylesheet" href="./bootstrap/css/bootstrap.min.css">
            <link rel="stylesheet" href="./bootstrap/css/bootstrap-theme.min.css">
            <script src="./bootstrap/js/bootstrap.min.js"></script>
            
            <script src="./js/main.js"></script>
            <script src="./js/flights.js"></script>
            <script src="./js/destinations.js"></script>
            <script src="./js/reservations.js"></script>
      </head>
      <body>
      
      <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
            <div class="container">
                  <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                              <span class="sr-only">Toggle navigation</span>
                              <span class="icon-bar"></span>
                              <span class="icon-bar"></span>
                              <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">SkyAirlines</a>
                  </div>
            </div>
      </div>

      <div class="bigspacer"></div>
      
      <div class="container">
          Active sessions: <span id="sessionsCounter"></span><br/>
           
            <div class="row" id="row_flights">
            
                  <h2 id="h2_flights"><span class="glyphicon glyphicon-list"></span> Flights</h2>
                  
                  <div class="spacer"></div>
                  
                  <p>
                        <span class="btn btn-primary pull-right" href="" onclick="flights_get();" role="button"><span class="glyphicon glyphicon-refresh"></span> Refresh</span>
                  </p>
                  
                  <table class="table table-striped">
                        <thead id="thead_flights">
                              <tr> 
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Departure</th>
                                    <th>Distance</th> 
                                    <th>Price</th>
                                    <th>From</th>
                                    <th>To</th>
                                    <th>URL</th>
                                    <th>Actions</th>
                              </tr>
                        </thead>
                        <tbody id="tbody_flights">
                             
                        </tbody>
                  </table>
                        
                  </div>
                  <div class="row">
                  <div class="col-md-3 block" id="div_filter">
                        <h3>Filter and sorting</h3>
                        
                             <div class="form-group">
                                    <label for="input_filter_currency">Currency</label>
                                    <input type="text" class="form-control" id="input_filter_currency" placeholder="" value="CZK">
                              </div>   
                        
                              <div class="form-group">
                                    <label for="input_filter_offset">Offset</label>
                                    <input type="text" class="form-control" id="input_filter_offset" placeholder="" >
                              </div>   
                              
                              <div class="form-group">
                                    <label for="input_filter_base">Base</label>
                                    <input type="text" class="form-control" id="input_filter_base" placeholder="" >
                              </div>   
                        
                              <div class="form-group">
                                    <label for="input_filter_order">Order</label>
                                    <select class="form-control" id="input_filter_order" >
                                        <option value="name:desc">name:desc</option>
                                        <option value="name:asc">name:asc</option>
                                        <option value="dateOfDeparture:desc">dateOfDeparture:desc</option>
                                        <option value="dateOfDeparture:asc">dateOfDeparture:asc</option>
                                    </select>
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_filter_date_from">Date from</label>
                                    <input type="text" class="form-control" id="input_filter_date_from" placeholder="" value="2014-02-01T01:23">
                              </div>   
                        
                              <div class="form-group">
                                    <label for="input_filter_date_to">Date to</label>
                                    <input type="text" class="form-control" id="input_filter_date_to" placeholder="" value="2016-02-01T01:23">
                              </div>   
                        
                               <span class="btn btn-primary pull-right" href="" onclick="flights_get();" role="button"><span class="glyphicon glyphicon-refresh"></span> Refresh</span>
                        
                  </div>
                  
                  <div class="col-md-4 block" id="div_add_flights">
                        <h3>Add new flight</h3>
                        <form role="form" id="form_flight_add" onsubmit="return add_flight_submit()">
                        
                              <div class="form-group">
                                    <label for="input_flight_name">Flight name</label>
                                    <input type="text" class="form-control" id="input_flight_name" placeholder="Enter unique flight name">
                              </div>                  

                              <div class="form-group">
                                    <label for="input_flight_departure">Departure date</label>
                                    <input type="text" class="form-control" id="input_flight_departure" placeholder="">
                              </div>      
     
                              <div class="form-group">
                                    <label for="input_flight_seats">Seats</label>
                                    <input type="text" class="form-control" id="input_flight_seats" placeholder="Enter seat count">
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_flight_from">From ID</label>
                                    <input type="text" class="form-control" id="input_flight_from" placeholder="Enter from ID">
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_flight_to">To ID</label>
                                    <input type="text" class="form-control" id="input_flight_to" placeholder="Enter to ID">
                              </div>
                              
                              <button type="submit" class="btn btn-success pull-right" ><span class="glyphicon glyphicon-plus"></span> Add</button>
                        </form>
                  </div>
                  
                  <div class="col-md-4 block" id="div_edit_flights nonvisible">
                        <h3>Edit flight</h3>
                        <form role="form" id="form_flight_edit" onsubmit="return edit_flight_submit()">
                        
                              <input type="hidden" id="input_edit_flight_id" name="input_edit_flight_id" value="-1">
                              <input type="hidden" id="input_edit_flight_url" name="input_edit_flight_url" value="-1">
                        
                              <div class="form-group">
                                    <label for="input_edit_flight_id_sec">ID</label>
                                    <input type="text" class="form-control" id="input_edit_flight_id_sec" placeholder="" readonly>
                              </div>   
                              
                              <div class="form-group">
                                    <label for="input_edit_flight_url_sec">URL</label>
                                    <input type="text" class="form-control" id="input_edit_flight_url_sec" placeholder="" readonly>
                              </div>   
                        
                              <div class="form-group">
                                    <label for="input_edit_flight_name">Flight name</label>
                                    <input type="text" class="form-control" id="input_edit_flight_name" placeholder="Enter unique flight name">
                              </div>                  

                              <div class="form-group">
                                    <label for="input_edit_flight_departure">Departure date</label>
                                    <input type="text" class="form-control" id="input_edit_flight_departure" placeholder="">
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_edit_flight_distance">Distance</label>
                                    <input type="text" class="form-control" id="input_edit_flight_distance" placeholder="Enter distance in km">
                                    Note: Distance change won't change price value
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_edit_flight_price">Price</label>
                                    <input type="text" class="form-control" id="input_edit_flight_price" placeholder="Enter price in CZK">
                              </div>      
                              
                                                            
                              <div class="form-group">
                                    <label for="input_edit_flight_seats">Seats</label>
                                    <input type="text" class="form-control" id="input_edit_flight_seats" placeholder="Enter seat count">
                              </div>      
                              
                              
                              <div class="form-group">
                                    <label for="input_edit_flight_from">From ID</label>
                                    <input type="text" class="form-control" id="input_edit_flight_from" placeholder="Enter from ID">
                                    Note: From ID change won't change distance value
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_edit_flight_to">To ID</label>
                                    <input type="text" class="form-control" id="input_edit_flight_to" placeholder="Enter to ID">
                                    Note: To ID change won't change distance value
                              </div>
                              
                              <button type="submit" class="btn btn-warning pull-right" ><span class="glyphicon glyphicon-save"></span> Save</button>
                        </form>
                  </div>
             
                   
                  
            </div> <!--row_flights -->
            
            <hr>
            
            
            
            
            
            
            
            
            
            
            
                <div class="row" id="row_destinations">
            
                  <h2 id="h2_destinations"><span class="glyphicon glyphicon-list"></span> Destinations</h2>
                  
                  <div class="spacer"></div>
                  
                  <p>
                        <span class="btn btn-primary pull-right" href="" onclick="destinations_get();" role="button"><span class="glyphicon glyphicon-refresh"></span> Refresh</span>
                  </p>
                  
                  <table class="table table-striped">
                        <thead id="thead_destinations">
                              <tr> 
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Latitude</th>
                                    <th>Longitude</th>
                                    <th>URL</th>
                                    <th>Actions</th>
                              </tr>
                        </thead>
                        <tbody id="tbody_destinations">
                             
                        </tbody>
                  </table>
                        
                  
                  <div class="block col-md-4" id="div_add_destinations">
                        <h3>Add new destination</h3>
                        <form role="form" id="form_destination_add" onsubmit="return add_destination_submit()">
                        
                              <div class="form-group">
                                    <label for="input_destination_name">Destination name</label>
                                    <input type="text" class="form-control" id="input_destination_name" placeholder="Enter unique destination name">
                              </div>                  

                            <!--  <div class="form-group">
                                    <label for="input_destination_lat">Latitude</label>
                                    <input type="text" class="form-control" id="input_destination_lat" placeholder="">
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_destination_lon">Longitude</label>
                                    <input type="text" class="form-control" id="input_destination_lon" placeholder="">
                              </div>-->
                              
                              <button type="submit" class="btn btn-success pull-right" ><span class="glyphicon glyphicon-plus"></span> Add</button>
                        </form>
                  </div>
                  
                  <div class="block col-md-4" id="div_edit_destinations nonvisible">
                        <h3>Edit destination</h3>
                        <form role="form" id="form_destination_edit" onsubmit="return edit_destination_submit()">
                        
                              <input type="hidden" id="input_edit_destination_id" name="input_edit_destination_id" value="-1">
                              <input type="hidden" id="input_edit_destination_url" name="input_edit_destination_url" value="-1">
                        
                              <div class="form-group">
                                    <label for="input_edit_destination_id_sec">ID</label>
                                    <input type="text" class="form-control" id="input_edit_destination_id_sec" placeholder="" readonly>
                              </div>   
                              
                              <div class="form-group">
                                    <label for="input_edit_destination_url_sec">URL</label>
                                    <input type="text" class="form-control" id="input_edit_destination_url_sec" placeholder="" readonly>
                              </div>   
                        
                              <div class="form-group">
                                    <label for="input_edit_destination_name">Destination name</label>
                                    <input type="text" class="form-control" id="input_edit_destination_name" placeholder="Enter unique destination name">
                              </div>                  

                              <div class="form-group">
                                    <label for="input_edit_destination_lat">Latitude</label>
                                    <input type="text" class="form-control" id="input_edit_destination_lat" placeholder="">
                              </div>      
                              
                              <div class="form-group">
                                    <label for="input_edit_destination_lon">Longitude</label>
                                    <input type="text" class="form-control" id="input_edit_destination_lon" placeholder="">
                              </div>
                              
                              <button type="submit" class="btn btn-warning pull-right" ><span class="glyphicon glyphicon-save"></span> Save</button>
                        </form>
                  </div>
                  
            </div> <!--row_destinations -->
            
            <hr>
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
             <div class="row" id="row_reservations">
            
                  <h2 id="h2_reservations"><span class="glyphicon glyphicon-list"></span> Reservations</h2>
                  
                  <div class="spacer"></div>
                  
                  <p>
                        <span class="btn btn-primary pull-right" href="" onclick="reservations_get();" role="button"><span class="glyphicon glyphicon-refresh"></span> Refresh</span>
                  </p>
                  
                  <table class="table table-striped">
                        <thead id="thead_reservations">
                              <tr> 
                                    <th>ID</th>
                                    <th>Flight ID</th>
                                    <th>Created</th>
                                    <th>Password</th> 
                                    <th>Seats</th>
                                    <th>State</th>
                                    <th>URL</th>
                                    <th>Actions</th>
                              </tr>
                        </thead>
                        <tbody id="tbody_reservations">
                             
                        </tbody>
                  </table>
                        
                  
                  <div class="block col-md-4" id="div_add_reservations">
                        <h3>Add new reservation</h3>
                        <form role="form" id="form_reservation_add" onsubmit="return add_reservation_submit()">
                        
                              <div class="form-group">
                                    <label for="input_reservation_flight">Flight ID</label>
                                    <input type="text" class="form-control" id="input_reservation_flight" placeholder="">
                              </div>                  

                              <div class="form-group">
                                    <label for="input_reservation_seats">Seats</label>
                                    <input type="text" class="form-control" id="input_reservation_seats" placeholder="">
                              </div>      
                              
                              <button type="submit" class="btn btn-success pull-right" ><span class="glyphicon glyphicon-plus"></span> Add</button>
                        </form>
                  </div>
                  
                  <div class="block col-md-4" id="div_edit_reservations nonvisible">
                        <h3>Edit reservation</h3>
                        <form role="form" id="form_reservation_edit" onsubmit="return edit_reservation_submit()">
                        
                              <input type="hidden" id="input_edit_reservation_id" name="input_edit_reservation_id" value="-1">
                              <input type="hidden" id="input_edit_reservation_url" name="input_edit_reservation_url" value="-1">
                        
                              <div class="form-group">
                                    <label for="input_edit_reservation_id_sec">ID</label>
                                    <input type="text" class="form-control" id="input_edit_reservation_id_sec" placeholder="" readonly>
                              </div>   
                              
                              <div class="form-group">
                                    <label for="input_edit_reservation_url_sec">URL</label>
                                    <input type="text" class="form-control" id="input_edit_reservation_url_sec" placeholder="" readonly>
                              </div>   
                        
                              <div class="form-group">
                                    <label for="input_edit_reservation_state">State</label>
                                    <input type="text" class="form-control" id="input_edit_reservation_state" placeholder="">
                              </div>     
                              
                              <div class="form-group">
                                    <label for="input_reservation_seats">Seats</label>
                                    <input type="text" class="form-control" id="input_reservation_seats_sec" placeholder="" readonly>
                              </div>   
                              
                              <button type="submit" class="btn btn-warning pull-right" ><span class="glyphicon glyphicon-save"></span> Save</button>
                        </form>
                  </div>
                  
            </div> <!--row_reservations -->
            
            <hr>
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            <footer>
            <p>&copy; Petr Rubeš &amp; Simon Slováček &amp; Miroslav Voda 2014</p>
            </footer>
      </div> <!-- /container -->

      
      </body>
</html>