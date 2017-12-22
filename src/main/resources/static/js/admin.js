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
                loadCategories(true, 0);
            } else {
                window.location.replace("/books.html");
            }
        },
        error: function () {
            window.location.replace("/books.html");
        }
    });
}

function generatePanelCategories(categories) {
    categories.forEach(function (category) {
        var categoryId = category['id'];
        var category_name = category['name'];

        var collapseId = 'collapse' + categoryId;

        var panel = '<div class="panel-group col-lg-4 col-md-6">' +
            '            <div id="pan' + categoryId + '" class="panel panel-default">' +
            '                <!-- Default panel contents -->' +
            '                <div id="' + categoryId + '" onclick="onCategoryClick(this.id)" class="panel-heading clearfix" data-toggle="collapse" href="#' + collapseId + '">' +
            '                    <h4 class="panel-title pull-left">' + category_name + '</h4>' +
            '                    <div class="pull-right">' +
            '                       <span class="glyphicon glyphicon-plus-sign add-ebook" data-toggle="modal" data-target="#modalAddEbook"></span>' +
            '                       <span class="glyphicon glyphicon-edit edit-category" data-toggle="modal" data-target="#modalEditCategory"></span> ' +
            '                       <span class="glyphicon glyphicon-minus-sign remove-category"></span> ' +
            '                    </div>' +
            '                </div>' +
            '                <div id="' + collapseId + '" class="panel-collapse collapse">' +
            '                    <div class="panel-body">' +
            '                        <table id="table' + categoryId + '" class="table table-striped">' +
            '' +
            '                        </table>' +
            '                    </div>' +
            '                </div>' +
            '            </div>' +
            '        </div>';

        $('#main').append(panel);

    })
}

function generatePanelCategoriesForSearch(categories) {
    categories.forEach(function (category) {
        var categoryId = category['id'];
        var category_name = category['name'];

        var collapseId = 'collapse' + categoryId;

        var panel = '<div class="panel-group col-lg-4 col-md-6">' +
            '            <div id="pan' + categoryId + '" class="panel panel-default">' +
            '                <!-- Default panel contents -->' +
            '                <div id="' + categoryId + '" class="panel-heading clearfix" data-toggle="collapse" href="#' + collapseId + '">' +
            '                    <h4 class="panel-title pull-left">' +
            '                        ' + category_name +
            '                    </h4>' +
            '                </div>' +
            '                <div id="' + collapseId + '" class="panel-collapse collapse">' +
            '                    <div class="panel-body">' +
            '                        <table id="table' + categoryId + '" class="table table-striped">' +
            '' +
            '                        </table>' +
            '                    </div>' +
            '                </div>' +
            '            </div>' +
            '        </div>';

        $('#main').append(panel);

    })
}

function generateTable(category_id, books) {
    var rows = '';

    books.forEach(function (book) {
        rows += '' +
            '                <tr class="clickable-row" data-toggle="modal" data-target="#modalEditEbook">' +
            '                    <td class="filterable-cell">' + book.id + '</td>' +
            '                    <td class="filterable-cell">' + book.title + '</td>' +
            '                    <td class="filterable-cell">' + book.author + '</td>' +
            '                    <td class="filterable-cell">' + book.publicationYear + '</td>' +
            '                </tr>';
    });

    return '<table id="table' + category_id + '" class="table table-striped table_books">' +
        '<thead>' +
        '                <tr>' +
        '                    <th>Id</th>' +
        '                    <th>Title</th>' +
        '                    <th>Author</th>' +
        '                    <th>Year</th>' +
        '                </tr>' +
        '            </thead>' +
        '            <tbody>' +
        rows +
        '            </tbody>' +
        '        </table>';
}


function onCategoryClick(category_id) {
    loadBooks(category_id);
}


function loadCategories(generateCats, category) {
    $.ajax({
        type: "GET",
        url: "/category",
        dataType: "json",
        success: function (categories) {
            if (generateCats){
                generatePanelCategories(categories);
            }else {
                categories.forEach(function (category) {
                    // console.log(category);
                    $('#ebookCategoryEdit').append('<option catId="' + category['id'] + '">' + category['name'] + '</option>');
                });
                $('option[catid='+category['id']+']').prop('selected',true);
            }
        }
    });
}

function loadLanguages(selectElement, l) {
    $.ajax({
        type: "GET",
        url: "/language",
        dataType: "json",
        success: function (languages) {
            languages.forEach(function (language) {
                // console.log(language);
                selectElement.append('<option langId="' + language['id'] + '">' + language['name'] + '</option>');
            });

            if (l){
                $('option[langid='+l['id']+']').prop('selected',true);
            }
        }
    });
}

function loadBooks(category_id) {
    $.ajax({
        type: "GET",
        url: "/ebook/category/" + category_id,
        dataType: "json",
        success: function (books) {

            var myTable = generateTable(category_id, books);

            $('#table' + category_id).replaceWith(myTable);

        }
    });
}

function loadBook(bookId) {
    $.ajax({
        type: "GET",
        url: "/ebook/" + bookId,
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (book) {

            $('#ebookIdEdit').val(book['id']);
            $('#ebookTitleEdit').val(book['title']);
            $('#ebookAuthorEdit').val(book['author']);
            $('#ebookKeywordsEdit').val(book['keywords']);
            $('#ebookYearEdit').val(book['publicationYear']);

            loadLanguages($('#ebookLanguageEdit'), book['language']);
            loadCategories(false, book['category']);
        }
    });
}


$('body').on('click', 'tr.clickable-row', function () {
    var ebookId = $(this).children("td.filterable-cell").first().text();
    loadBook(ebookId);
});

$('body').on('click', 'span.add-ebook', function () {
    var categoryId = $(this).closest('div.panel-heading').attr('id');
    $('#ebookCategoryAdd').val(categoryId);
    loadLanguages($('#ebookLanguage'), null);
});

$('body').on('click', 'span.edit-category', function () {
    var categoryId = $(this).closest('div.panel-heading').attr('id');
    var categoryName = $(this).closest('div.panel-heading').find('h4.panel-title').text();
    $('#categoryIdEdit').val(categoryId);
    $('#categoryNameEdit').val(categoryName);
});

$('body').on('click', 'span.remove-category', function () {
    var categoryId = $(this).closest('div.panel-heading').attr('id');

    if (confirm('Are you sure you want do delete this category?')) {
        $.ajax({
            type: 'DELETE',
            url: '/category/' + categoryId,
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (response) {
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

$('#modalAddEbook').on('hidden.bs.modal', function () {
    $(this).find("input,select").val('').end();
    $(this).find("img").attr('src', '');
    $(this).find('select').empty();
    $("#ebook-add-form :input").attr("disabled", true);
});

$('#modalEditEbook').on('hidden.bs.modal', function () {
    $(this).find("input,select").val('').end();
    $(this).find('select').empty();
});

$('#file-upload-form').submit(function (e) {
    e.preventDefault();

    $('#upload-indicator').css('display', 'block');
    $('#upload-indicator').attr('src', 'images/loading.gif');

    var formData = new FormData();
    formData.append('file', $('#file')[0].files[0]);

    $.ajax({
        url: $(this).attr("action"),
        type: 'PUT',
        data: formData,
        processData: false,  // tell jQuery not to process the data
        contentType: false,  // tell jQuery not to set contentType
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            $('#upload-indicator').attr('src', 'images/check_mark.png');

            var filename = data['fileName'];
            var title = data['title'];
            var author = data['author'];
            var keywords = data['keywords'];

            $('#ebookFilenameAdd').val(filename);
            $('#ebookTitleAdd').val(title);
            $('#ebookAuthorAdd').val(author);
            $('#ebookKeywordsAdd').val(keywords);

            $("#ebook-add-form :input").attr("disabled", false);
        },
        error: function (err) {
            $('#upload-indicator').attr('src', 'images/error_mark.png');
            var json = err.responseJSON;
            alert(json.message);
        }
    });

});

$('#ebook-add-form').submit(function (e) {
    e.preventDefault();

    var languageId = $('#ebookLanguage').find(":selected").attr('langId');
    var categoryId = $('#ebookCategoryAdd').val();

    var data = {
        "title": $('#ebookTitleAdd').val(),
        "author": $('#ebookAuthorAdd').val(),
        "keywords": $('#ebookKeywordsAdd').val(),
        "publicationYear": $('#ebookYearAdd').val(),
        "mimeType": 'application/pdf',
        "languageId": languageId,
        "categoryId": categoryId,
        "fileName": $('#ebookFilenameAdd').val()
    };

    $.ajax({
        url: '/ebook',
        type: 'PUT',
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (data) {
            $('#modalAddEbook').modal('toggle');
            alert(data['message']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#ebook-edit-form').submit(function (e) {
    e.preventDefault();

    var languageId = $('#ebookLanguageEdit').find(":selected").attr('langid');
    var categoryId = $('#ebookCategoryEdit').find(":selected").attr("catId");

    var data = {
        "id": $('#ebookIdEdit').val(),
        "title": $('#ebookTitleEdit').val(),
        "author": $('#ebookAuthorEdit').val(),
        "keywords": $('#ebookKeywordsEdit').val(),
        "publicationYear": $('#ebookYearEdit').val(),
        "languageId": languageId,
        "categoryId": categoryId,
        "mimeType": "application/pdf"
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            $('#modalEditEbook').modal('toggle');
            alert(response['message']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#category-add-form').submit(function (e) {
    e.preventDefault();

    var categoryName = $('#categoryNameAdd').val();

    var data = {
        "name": categoryName
    };

    $.ajax({
        type: 'PUT',
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            $('#modalAddCategory').modal('toggle');
            refresh();
            alert(response['message']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#category-edit-form').submit(function (e) {
    e.preventDefault();

    var categoryId = $('#categoryIdEdit').val();
    var categoryName = $('#categoryNameEdit').val();


    var data = {
        "id": categoryId,
        "name": categoryName
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        beforeSend: function (request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (response) {
            $('#modalEditCategory').modal('toggle');
            refresh();
            alert(response['message']);
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

$('#delete_ebook').click(function () {
    var ebookId = $('#ebookIdEdit').val();

    if (confirm('Are you sure you want do delete this ebook?')) {
        $.ajax({
            type: 'DELETE',
            url: '/ebook/' + ebookId,
            contentType: "application/json",
            beforeSend: function (request) {
                request.setRequestHeader("Authorization", token);
            },
            success: function (response) {
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


$('#search_form').submit(function (e) {
    e.preventDefault();

    var title = $('#title').val();
    var titleCb = $('#titleCb').prop('checked');

    var author = $('#author').val();
    var authorCb = $('#authorCb').prop('checked');

    var keywords = $('#keywords').val();
    var keywordsCb = $('#keywordsCb').prop('checked');

    var content = $('#content').val();
    var contentCb = $('#contentCb').prop('checked');

    var language = $('#language').val();
    var languageCb = $('#languageCb').prop('checked');

    var type = $("input[name='searchType']:checked").val();

    var data = {
        "title": {
            "value": title,
            "searchType": titleCb
        },
        "author": {
            "value": author,
            "searchType": authorCb
        },
        "keywords": {
            "value": keywords,
            "searchType": keywordsCb
        },
        "content": {
            "value": content,
            "searchType": contentCb
        },
        "language": {
            "value": language,
            "searchType": languageCb
        },
        "type": type
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (response) {
            $('.panel-group').remove(); // removing old results

            var categories = extract_categories(response);
            generatePanelCategoriesForSearch(categories);

            var lista = groupBy(response);

            lista.forEach(function (books) {
                var category_id = books[0]['category']['id'];
                var myTable = generateTable(category_id, books);
                $('#table' + category_id).replaceWith(myTable);
            })

            $('#modalSearch').modal('toggle');
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});

function refresh() {
    $('.panel-group').remove(); // removing old results
    loadCategories(true, 0);
}

function extract_categories(ebooks) {
    var categories = {};

    ebooks.forEach(function (ebook) {
        var category = ebook['category'];
        categories[category['id']] = category;
    });


    var result = [];
    for (var key in categories) {
        result.push(categories[key]);
    }

    return result;
}

function groupBy(collection) {
    var i = 0, val, index, values = [], result = [];

    for (; i < collection.length; i++) {
        val = collection[i]['category']['id'];
        index = values.indexOf(val);
        if (index > -1)
            result[index].push(collection[i]);
        else {
            values.push(val);
            result.push([collection[i]]);
        }
    }
    return result;
}

$('#modalSearch').on('hidden.bs.modal', function () {
    $(this).find("input[type=text]").val('').end();
});


function setUserData(user) {
    $('#username').val(user['username']);
    $('#firstName').val(user['firstName']);
    $('#lastName').val(user['lastName']);
}

$('#modalSearch').on('hidden.bs.modal', function () {
    $(this).find("input[type=text]").val('').end();
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
