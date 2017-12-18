var token;
$(document).ready(function () {

    token = localStorage.getItem('token');


    loadCategories();

});


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
            '                <tr class="clickable-row" data-toggle="modal" data-target="#myModal">' +
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
        beforeSend: function(request) {
            request.setRequestHeader("Authorization", token);
        },
        success: function (book) {

            $('#ebookTitle').text(book['title']);
            $('#ebookAuthor').text(book['author']);
            $('#ebookKeywords').text(book['keywords']);
            $('#ebookYear').text(book['publicationYear']);
            $('#ebookMimeType').text(book['mimeType']);
            $('#ebookLang').text(book['language']['name']);

            var downloadable = book['downloadable'];
            if (downloadable){
                $('#downloadLink').text("Download")
                    .attr("href", "/ebook/" + book['id'] + "/download");
            }
        }
    });
}

$('body').on('click', 'tr.clickable-row', function () {
    var ebookId = $(this).children("td.filterable-cell").first().text();
    loadBook(ebookId);
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
        }
    };

    $.ajax({
        type: $(this).attr("method"),
        url: $(this).attr("action"),
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function (response) {
           alert("jej");
        },
        error: function (err) {
            var json = err.responseJSON;
            alert(json['message']);
        }
    });

});