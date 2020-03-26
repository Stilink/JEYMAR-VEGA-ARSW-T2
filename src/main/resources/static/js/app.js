var App = (function(){
    let drawTable= ()=>{
        $("#countriesList").empty();
        ApiClient.getCountries((error, data)=>{
            if(error){
                console.log(error);
            }else{
                let countries = data;
                $('#countriesList').append("<thead><tr> <th>Country</th>  <th>Num deaths</th>    <th>Num infected</th>    <th>Num cured</th> <th>Information</th> </tr></thead> <tbody></tbody>");
                countries.forEach(element => {
                    let row = "<tr><td>" + element.nombre + "</td><td>" + element.muertos + "</td><td> "+ element.infectados + "</td><td>"+ element.curados + "</td><td> <button type='button' class='btn btn-success btnCountry' id='"+element.nombre+"'>Information</button> </td></tr>";
                    $('#countriesList tbody').append(row);
                });
                $(".btnCountry").click(function(){
                    App.provincesTable(this.id);
                });
            }
        })
    }

    let drawCountryTable= (country)=>{
        ApiClient.getCountry(country, (error, data)=>{
            $("#countryInfo").empty();
            if(error){
                console.log(error);
            }else{
                let country = data;
                App.drawMap(country.nombre);
                $("#countryInfo").append("<thead><tr> <th>Country</th>  <th>Num deaths</th>    <th>Num infected</th>    <th>Num cured</th></tr></thead> <tbody></tbody>");
                $("#countryInfo tbody").append("<tr><td>" + country.nombre + "</td><td>" + country.muertos + "</td><td> "+ country.infectados + "</td><td>"+ country.curados + "</td></tr>")
            }
        });
    }

    let drawCountryMapTable= (country)=>{
        ApiClient.getCountryInfo(country, (error, data)=>{
            if(error){
                console.log(error);
            }else{
                let countryInfo = data;
                let latitud = countryInfo[0];
                let longitud = countryInfo[1];
                let market = { latitud, longitud};
                plotMarkers(market);
            }
        })
    }

    let drawProvincesTable= (country)=>{
        App.drawCountry(country);
        ApiClient.getProvincesByCountry(country, (error, data)=>{
            $("#provincesList").empty();
            if(error){
                console.log(error);
            }else{
                let provinces = data;
                $("#provincesList").append("<thead><tr> <th>Region</th>  <th>Num deaths</th>    <th>Num infected</th>    <th>Num cured</th></tr></thead> <tbody></tbody>");
                provinces.forEach(element => {
                    let row = "<tr><td>" + element.nombre + "</td><td>" + element.muertos + "</td><td> "+ element.infectados + "</td><td>"+ element.curados + "</td></tr>";
                    $("#provincesList tbody").append(row);
                });
            }
        })
    }
    return{
        draw: ()=>drawTable(),
        provincesTable:(country)=>drawProvincesTable(country),
        drawCountry: (country)=>drawCountryTable(country),
        drawMap: (country)=> drawCountryMapTable(country)
    }
})();