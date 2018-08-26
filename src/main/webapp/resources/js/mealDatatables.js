var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ]
    });
    makeEditable();
});

$(function () {
    var form = $("#filterForm");
    form.on("submit", function () {
        updateTable();
        return false;
    });

    $("#reset").on("click", function () {
        form.find(":input").val("");
        updateTable();
        return false;
    })
});
$(function () {
    var oldUpdateTable = updateTable;
    var form = $("#filterForm");

    updateTable = function () {
        $.ajax({
            url: ajaxUrl,
            data: form.serialize(),
            success: function (data) {
                datatableApi.clear().rows.add(data).draw();
            }
        });
    }
});