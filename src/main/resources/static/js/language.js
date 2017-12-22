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
                loadLanguages();
            } else {
                window.location.replace("/books.html");
            }
        },
        error: function () {
            window.location.replace("/books.html");
        }
    });
}

function loadLanguages() {
    $.ajax({
        type: "GET",
        url: "/language",
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (languages) {
            $('#table').append(generateTable(languages));
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });
}

function loadLanguage(languageId) {
    $.ajax({
        type: "GET",
        url: "/language/" + languageId,
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (language) {
            $('#languageNameEdit').val(language['name']);
            $('#languageId').val(language['id']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });
}

function generateTable(languages) {
    var rows = '';

    languages.forEach(function (language) {
        rows += '' +
            '                <tr class="clickable-row" data-toggle="modal" data-target="#modalEditLanguage">' +
            '                    <td class="filterable-cell">' + language['id'] + '</td>' +
            '                    <td class="filterable-cell">' + language['name'] + '</td>' +
            '                </tr>';
    });

    return '<table id="table_languages" class="table table-striped table-bordered table_lang">' +
        '<thead>' +
        '                <tr>' +
        '                    <th>Id</th>' +
        '                    <th>Name</th>' +
        '                </tr>' +
        '            </thead>' +
        '            <tbody>' +
        rows +
        '            </tbody>' +
        '        </table>';
}

function refresh() {
    $('#table').empty();
    loadLanguages();
}

$('body').on('click', 'tr.clickable-row', function () {
    var languageId = $(this).children("td.filterable-cell").first().text();
    loadLanguage(languageId);
});

$('#modalAddLanguage').on('hidden.bs.modal', function () {
    $(this).find("input[type=text]").val('').end();
});


$('#language-add-form').submit(function (e) {
    e.preventDefault();

    var languageName = $('#languageNameAdd').val();

    var data = {
        'name': languageName
    };

    $.ajax({
        type: 'PUT',
        url: $(this).attr("action"),
        dataType: "json",
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            alert(response['message']);
            $('#modalAddLanguage').modal('toggle');
            refresh();
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#language-edit-form').submit(function (e) {
    e.preventDefault();

    var languageId = $('#languageId').val();
    var languageName = $('#languageNameEdit').val();

    var data = {
        'id': languageId,
        'name': languageName
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
            $('#modalEditLanguage').modal('toggle');
            refresh();
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#delete_lang').click(function () {
    var langId = $('#languageId').val();

    if (confirm('Are you sure you want do delete this language?')) {
        $.ajax({
            type: 'DELETE',
            url: '/language/' + langId,
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (response) {
                alert(response['message']);
                $('#modalEditLanguage').modal('toggle');
                refresh();
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

function setUserData(user) {
    $('#username').val(user['username']);
    $('#firstName').val(user['firstName']);
    $('#lastName').val(user['lastName']);
}

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
