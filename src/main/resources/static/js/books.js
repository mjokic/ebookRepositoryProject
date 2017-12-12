$(function () {

    loadBooks();

    function generatePanel(books) {
        var category = books[0].categoryName;

        var tabela = '<div class="col-lg-4 col-md-6">' +
            '<div class="panel panel-default">'+
            '            <!-- Default panel contents -->'+
            '            <div class="panel-heading">' + category + '</div>'+
            '            <div class="panel-body">'+
            '                <!-- Table -->'+
            '                <table class="table table-striped">'+
                                generateTable(books) +
            '                </table>'+
            '            </div>'+
            '        </div>' +
            '</div>';


        $('#main').append(tabela);
    }


    function generateTable(books) {
        var rows = '';

        books.forEach(function (value) {
            rows += '' +
                '                <tr>' +
                '                    <td class="filterable-cell">'+value.id+'</td>' +
                '                    <td class="filterable-cell">'+value.title+'</td>' +
                '                    <td class="filterable-cell">'+value.author+'</td>' +
                '                    <td class="filterable-cell">'+value.publicationYear+'</td>' +
                '                </tr>';
        });

        var myTable = '<table class="table">'+
            '            <thead>'+
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

        return myTable;
    }

    function loadBooks() {
        $.ajax({
           type: "GET",
           url: "/ebook",
           dataType: "json",
           success: function (data) {
               var obj = groupBy(data, "categoryName");

               obj.forEach(function (value) {
                    generatePanel(value)
               })
           }
        });

    }

    function groupBy(collection, property) {
        var i = 0, val, index,
            values = [], result = [];
        for (; i < collection.length; i++) {
            val = collection[i][property];
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

});