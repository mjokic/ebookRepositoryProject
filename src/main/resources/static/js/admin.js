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
                loadCategories();
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
            '                    <h4 class="panel-title pull-left">' +
            '                        ' + category_name +
            '                    </h4>' +
            '                    <div class="pull-right">' +
            '                       <span class="glyphicon glyphicon-plus-sign add-ebook" data-toggle="modal" data-target="#modalAddEbook"></span>' +
            '                       <span class="glyphicon glyphicon-minus-sign"></span> ' +
            '                       <span class="glyphicon glyphicon-edit"></span> ' +
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

    return '<table id="table' + category_id + '" class="table table-striped">' +
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


function loadCategories() {
    $.ajax({
        type: "GET",
        url: "/category",
        dataType: "json",
        success: function (data) {
            generatePanelCategories(data);
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

            $('#ebookId').val(book['id']);
            $('#ebookTitle').val(book['title']);
            $('#ebookAuthor').val(book['author']);
            $('#ebookKeywords').val(book['keywords']);
            $('#ebookYear').val(book['publicationYear']);
            // $('#ebookLang').text(book['language']['name']);

        }
    });
}

function loadLanguages() {
    $.ajax({
        type: "GET",
        url: "/language",
        dataType: "json",
        success: function (languages) {
            languages.forEach(function (language) {
                console.log(language);
                $('#ebookLanguage').append('<option langId="' + language['id'] + '">' + language['name'] + '</option>');
            });

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
    loadLanguages();
});

$('#modalAddEbook').on('hidden.bs.modal', function () {
    $(this).find("input,select").val('').end();
    $(this).find("img").attr('src', '');
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

    var data = {
        "id": $('#ebookId').val()
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
            alert(response['message']);
        },
        error: function (err) {
            alert(err['message']);
        }
    });

});