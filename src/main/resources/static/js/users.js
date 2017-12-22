var token;

$(document).ready(function () {

    token = localStorage.getItem('token');

    if (!token) {
        window.location.replace("/books.html");
    }

    checkPrivs();

});

function checkPrivs() {
    $.ajax({
        type: "GET",
        url: "/user/me",
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (me) {
            var type = (me['type']);
            if (type === 'administrator') {
                loadUsers();
            } else {
                window.location.replace("/books.html");
            }
        },
        error: function () {
            window.location.replace("/books.html");
        }
    });
}

function setUserData(user) {
    $('#username').val(user['username']);
    $('#firstName').val(user['firstName']);
    $('#lastName').val(user['lastName']);
}

function loadUsers() {
    $.ajax({
        type: "GET",
        url: "/user",
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (users) {
            $('#table').append(generateTable(users));
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });
}

function loadUser(userId) {
    $.ajax({
        type: "GET",
        url: "/user/" + userId,
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (user) {

            $('#firstNameEdit').val(user['firstName']);
            $('#lastNameEdit').val(user['lastName']);
            $('#usernameEdit').val(user['username']);

            loadCategories('userCategoryEdit', user['categoryId']);
            setupUserType('userTypeEdit', user['type']);
        }
    });
}

function loadCategories(userCategoryId, selectedValue) {
    $.ajax({
        type: "GET",
        url: "/category",
        dataType: "json",
        success: function (categories) {
            $('#' + userCategoryId).append('<option value="">All</option>');
            categories.forEach(function (category) {
                $('#' + userCategoryId).append('<option value="' + category['id'] + '">' + category['name'] + '</option>');
            });

            if (selectedValue){
                $('option[value='+selectedValue+']').prop('selected',true);
            }
        }
    });
}

function generateTable(users) {
    var rows = '';

    users.forEach(function (user) {
        rows += '' +
            '                <tr class="clickable-row" data-toggle="modal" data-target="#modalEditUser">' +
            '                    <td class="filterable-cell">' + user['id'] + '</td>' +
            '                    <td class="filterable-cell">' + user['firstName'] + '</td>' +
            '                    <td class="filterable-cell">' + user['lastName'] + '</td>' +
            '                    <td class="filterable-cell">' + user['username'] + '</td>' +
            '                    <td class="filterable-cell">' + user['type'] + '</td>' +
            '                    <td class="filterable-cell">' + user['categoryId'] + '</td>' +
            '                </tr>';
    });

    return '<table id="table_languages" class="table table-striped table-bordered table_users">' +
        '<thead>' +
        '                <tr>' +
        '                    <th>Id</th>' +
        '                    <th>First Name</th>' +
        '                    <th>Last Name</th>' +
        '                    <th>Username</th>' +
        '                    <th>Type</th>' +
        '                    <th>Category</th>' +
        '                </tr>' +
        '            </thead>' +
        '            <tbody>' +
        rows +
        '            </tbody>' +
        '        </table>';
}

function setupUserType(userType, selectedValue) {
    $('#' + userType).append('<option value="administrator">Administrator</option>');
    $('#' + userType).append('<option value="subscriber">Subscriber</option>');

    if (selectedValue){
        $('option[value='+selectedValue+']').prop('selected',true);
    }
}

function refresh() {
    $('#table').empty();
    loadUsers();
}

$('body').on('click', 'span.glyphicon-user', function () {
    $.ajax({
        type: "GET",
        url: "/user/me",
        dataType: "json",
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (user) {
            setUserData(user);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });
});

$('body').on('click', 'button.my-float', function () {
    setupUserType('userType', null);
    loadCategories('userCategory', null);
});

$('#modalAddUser').on('hidden.bs.modal', function () {
    $(this).find("input,select").val('').end();
    $(this).find('select').empty();
});

$('#modalEditUser').on('hidden.bs.modal', function () {
    $(this).find("input,select").val('').end();
    $(this).find('select').empty();
});

$('body').on('click', 'tr.clickable-row', function () {
    var userId = $(this).children("td.filterable-cell").first().text();
    $('#userId').val(userId);
    loadUser(userId);
});


$('#personal_info_form').submit(function (e) {
    e.preventDefault();

    var firstName = $('#firstName').val();
    var lastName = $('#lastName').val();

    var data = {
        "firstName": firstName,
        "lastName": lastName
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            alert(response['message']);
            $('#modalPersonal').modal('toggle');
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#personal_password_form').submit(function (e) {
    e.preventDefault();

    var pass1 = $('#password1').val();
    var pass2 = $('#password2').val();

    if (pass1 !== pass2){
        alert("Passwords doesn't match!");
        return;
    }

    var data = {
        "password": pass1
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            alert(response['message']);
            $('#modalPersonal').modal('toggle');
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#form-user-add').submit(function (e) {
    e.preventDefault();

    var firstname = $('#firstNameAdd').val();
    var lastname = $('#lastNameAdd').val();
    var username = $('#usernameAdd').val();
    var password = $('#passwordAdd').val();
    var userType = $('#userType').val();
    var userCat = $('#userCategory').val();

    var data = {
        'firstName': firstname,
        'lastName': lastname,
        'username': username,
        'password': password,
        'type': userType,
        'categoryId': userCat
    };

    $.ajax({
        type: "PUT",
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            $('#modalAddUser').modal('toggle');
            refresh();
            alert(response['message']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#form-user-edit').submit(function (e) {
    e.preventDefault();

    var userId = $('#userId').val();
    var firstname = $('#firstNameEdit').val();
    var lastname = $('#lastNameEdit').val();
    var password = $('#passwordEdit').val();
    var categoryId = $('#userCategoryEdit').val();
    var type = $('#userTypeEdit').val();

    var data = {
        'id': userId,
        'firstName': firstname,
        'lastName': lastname,
        'categoryId': categoryId,
        'type': type
    };

    if (password !== ""){
        data['password'] = password;
    }

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            $('#modalEditUser').modal('toggle');
            refresh();
            alert(response['message']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#delete_user').click(function () {
    var userId = $('#userId').val();

    if (confirm('Are you sure you want do delete this user?')) {
        $.ajax({
            type: 'DELETE',
            url: '/user/' + userId,
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (response) {
                $('#modalEditUser').modal('toggle');
                refresh();
                alert(response['message']);
            },
            error: function (err) {
                var json = err.responseJSON;
                alert(json['message']);
            }
        });
    } else {
        // Do nothing!
    }

});
