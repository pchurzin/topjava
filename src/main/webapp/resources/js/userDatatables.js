var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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
                "asc"
            ]
        ]
    });
    makeEditable();
});

$(function () {
   $("#datatable").find("input[type='checkbox']")
       .on("click", function () {
           var id = $($(this).parents("tr[id]")[0]).attr("id");
           var d = {
               enabled : this.checked
           };
           $.ajax({
               url: ajaxUrl + id,
               data: d,
               method: "POST",
               success: function () {
                   successNoty("Updated");
                   updateTable();
               }
           });
       })
});