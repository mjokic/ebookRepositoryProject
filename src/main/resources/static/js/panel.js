$(function () {



});


$('#logout').click(function () {
    localStorage.removeItem("token");
    alert("Logout successful");

    window.location.replace("/")
});