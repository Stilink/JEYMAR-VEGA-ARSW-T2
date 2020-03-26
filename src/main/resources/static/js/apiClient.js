var ApiClient = (function(){
    let getAllCountries= (callback)=>{
        $.ajax({
            url: '/coronavirus',
            type: 'GET',
            success: function(data){ 
                callback(null,data)
            },
            error: function(data) {
                callback("error",[])
            }
        });
    }
    let getProvincesCountry= (country, callback)=>{
        $.ajax({
            url: '/coronavirus/'+country+'/provinces',
            type: 'GET',
            success: function(data){ 
                callback(null,data)
            },
            error: function(data) {
                callback("error",[])
            }
        });
    }

    let getCountry= (country, callback)=>{
        $.ajax({
            url: '/coronavirus/'+country,
            type: 'GET',
            success: function(data){ 
                callback(null,data)
            },
            error: function(data) {
                callback("error",[])
            }
        });
    }

    let getCountryInformation= (country, callback)=>{
        $.ajax({
            url: '/coronavirus/'+country+'/information',
            type: 'GET',
            success: function(data){ 
                callback(null,data)
            },
            error: function(data) {
                callback("error",[])
            }
        });
    }

    return{
        getCountries:(callback)=>getAllCountries(callback),
        getCountry:(country, callback)=>getCountry(country, callback),
        getProvincesByCountry:(country, callback)=> getProvincesCountry(country, callback),
        getCountryInfo:(country, callback)=> getCountryInformation(country, callback)
    }
})();