$(document).ready(function () {
    $("#submit").click(function () {
        var n = document.getElementById("name");
        var u = document.getElementById("username");
        var p = document.getElementById("password");
        var a = document.getElementById("address");
        var e = document.getElementById("email");
        var user = {
            "name": n.value,
            "username": u.value,
            "password": p.value,
            "email": e.value,
            "phone": p.value
        };
        saveuserdetails(user);
    });
});


function saveuserdetails(user) {

    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:8080/signup",
        "method": "POST",
        "headers": {
            "Content-Type": "application/json",
            "Cache-Control": "no-cache",
            "Postman-Token": "5593b7cb-0c54-447a-9b19-af198dd00c9d"
        },
        "processData": false,
        // "data": "{\n\t\"name\":\"" + n.value + "\",\n\t\"username\":\" " + u.value +"\",\n\t\"password\":\"" + p.value+  "\",\n\t\"email\":\"" + e.value + "\",\n\t\"phone\":\"" + p.value + "\"\n}"
        "data": user
    }

    $.ajax(settings).done(function (response) {
        console.log(response);
    });


}