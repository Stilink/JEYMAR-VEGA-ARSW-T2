document.addEventListener('DOMContentLoaded', function () {
    if (document.querySelectorAll('#mapHtml').length > 0)
    {
      if (document.querySelector('html').lang)
        lang = document.querySelector('html').lang;
      else
        lang = 'en';
  
      var js_file = document.createElement('script');
      js_file.type = 'text/javascript';
      js_file.src = 'https://maps.googleapis.com/maps/api/js?key=AIzaSyDklz_vypQM1veXLWkmvUjEu8NIRtDZ9kM&callback=initMap&signed_in=true&language=' + lang;
      document.getElementsByTagName('head')[0].appendChild(js_file);
    }
  });
  
  var map;
  
  function initMap()
  {
    map = new google.maps.Map(document.getElementById('mapHtml'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 1
    });
  
    fetch('https://raw.githubusercontent.com/jayshields/google-maps-api-template/master/markers.json')
      .then(function(response){return response.json()})
      .then(plotMarkers);
  }
  
  var markers;
  var bounds;
  
  function plotMarkers(marker)
  {
    markers = [];
    bounds = new google.maps.LatLngBounds();
    var position = new google.maps.LatLng(marker.lat, marker.lng);
  
    markers.push(
      new google.maps.Marker({
        position: position,
        map: map,
        animation: google.maps.Animation.DROP
      })
    );
  
    bounds.extend(position);
  
    map.fitBounds(bounds);
  }