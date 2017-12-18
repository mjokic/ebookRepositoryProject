$(function () {

    $('#login-form-link').click(function (e) {
        $("#login-form").delay(100).fadeIn(100);
        $("#register-form").fadeOut(100);
        $('#register-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });
    $('#register-form-link').click(function (e) {
        $("#register-form").delay(100).fadeIn(100);
        $("#login-form").fadeOut(100);
        $('#login-form-link').removeClass('active');
        $(this).addClass('active');
        e.preventDefault();
    });


    var token = localStorage.getItem("token");
    if (token){
        $('#me').show();
        $('#logout').show();
    }

});


$('#login-form').submit(function (e) {
    e.preventDefault();

    var username = $("#username-log").val();
    var password = $("#password-log").val();

    var data = {
        "username": username,
        "password": password
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (data, textStatus, request) {
            var token = request.getResponseHeader("Authorization");
            var location = request.getResponseHeader("Location");

            localStorage.setItem("token", token);

            if (location){
                window.location.replace(location);
            }else {
                window.location.replace("/books.html")
            }
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json.message);
        }
    })

});


$('#register-form').submit(function (e) {
    e.preventDefault();

    var firstname = $('#firstname').val();
    var lastname = $('#lastname').val();
    var username = $('#username-reg').val();
    var password = $('#password-reg').val();
    var passconf = $('#confirm-password').val();

    if (password !== passconf){
        alert("Passwords doesn't match!");
        return;
    }

    var data = {
        "firstName": firstname,
        "lastName": lastname,
        "username": username,
        "password": password
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            alert(data.message);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json.message);
        }
    });

});


$('#logout').click(function () {
    localStorage.removeItem("token");
    window.location.replace("/")
});
