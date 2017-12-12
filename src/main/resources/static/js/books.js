$(document).ready(function () {

    loadCategories();

});


function generatePanelCategories(categories) {
    categories.forEach(function (category) {
        var categoryId = category['id'];
        var category_name = category['name'];

        var collapseId = 'collapse' + categoryId;

        var panel = '<div class="panel-group col-lg-4 col-md-6">'+
            '            <div id="pan'+categoryId+'" class="panel panel-default">'+
            '                <!-- Default panel contents -->'+
            '                <div id="'+categoryId+'" onclick="onCategoryClick(this.id)" class="panel-heading" data-toggle="collapse" href="#'+collapseId+'">'+
            '                    <h4 class="panel-title">'+
            '                        '+ category_name +
            '                    </h4>'+
            '                </div>'+
            '                <div id="'+collapseId+'" class="panel-collapse collapse">'+
            '                    <div class="panel-body">'+
            '                        <table id="table'+categoryId+'" class="table table-striped">'+
            ''+
            '                        </table>'+
            '                    </div>'+
            '                </div>'+
            '            </div>'+
            '        </div>';

        $('#main').append(panel);

    })
}


function generateTable(category_id, books) {
    var rows = '';

    books.forEach(function (book) {
        rows += '' +
            '                <tr>' +
            '                    <td class="filterable-cell">'+book.id+'</td>' +
            '                    <td class="filterable-cell">'+book.title+'</td>' +
            '                    <td class="filterable-cell">'+book.author+'</td>' +
            '                    <td class="filterable-cell">'+book.publicationYear+'</td>' +
            '                </tr>';
    });

    return '<table id="table'+category_id+'" class="table table-striped">'+
                '<thead>'+
        '                <tr>'+
        '                    <th>Id</th>'+
        '                    <th>Title</th>'+
        '                    <th>Author</th>'+
        '                    <th>Year</th>'+
        '                </tr>'+
        '            </thead>'+
        '            <tbody>'+
                        rows +
        '            </tbody>'+
        '        </table>';
}


function onCategoryClick(category_id) {

    loadBooks(category_id);

    // $('.panel').on('shown.bs.collapse', function (e) {
    //     alert("CALLED!" + e.currentTarget.id);
    // });

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

            $('#table'+category_id).replaceWith(myTable);

        }
    });
}

