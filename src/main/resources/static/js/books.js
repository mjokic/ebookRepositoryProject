$(function () {

    loadCategories();

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

    function loadBooks() {
        $.ajax({
           type: "GET",
           url: "/ebook",
           dataType: "json",
           success: function (data) {
               var obj = groupBy(data, "category");

               obj.forEach(function (value) {
                    generatePanel(value)
               })
           }
        });
    }


    function groupBy(collection, property) {
        var i = 0, val, index, values = [], result = [];

        for (; i < collection.length; i++) {
            val = collection[i][property]["id"];
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


    function generatePanelCategories(categories) {
        categories.forEach(function (category) {
            var categoryId = category['id'];
            var category_name = category['name'];

            var collapseId = 'collapse' + categoryId;

            var panel = '<div class="panel-group col-lg-4 col-md-6">'+
                '            <div class="panel panel-default">'+
                '                <!-- Default panel contents -->'+
                '                <div id="'+categoryId+'" onclick="onCategoryClick(this.id)" class="panel-heading" data-toggle="collapse" href="#'+collapseId+'">'+
                '                    <h4 class="panel-title">'+
                '                        '+ category_name +
                '                    </h4>'+
                '                </div>'+
                '                <div id="'+collapseId+'" class="panel-collapse collapse">'+
                '                    <div class="panel-body">'+
                '                        <table class="table table-striped">'+
                ''+
                '                        </table>'+
                '                    </div>'+
                '                </div>'+
                '            </div>'+
                '        </div>';

            $('#main').append(panel);

        })
    }

});


function onCategoryClick(category_id) {
    alert(category_id);
}
