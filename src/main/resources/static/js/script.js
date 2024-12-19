const toggleSidebar = () => {
    if ($(".sidebar").is(":visible")){
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0")
    } else {
        $(".sidebar").css("display", "block");
        $(".content").css("margin-left", "20%")
    }
};

function deleteContact(id){
    Swal.fire({
        title: "Are you sure?",
        text: "You won't be able to revert this!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Yes, delete it!"
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire({
                title: "Deleted!",
                text: "Your file has been deleted.",
                icon: "success"
            });
            window.location = '/user/delete/'+id+'/contact'
        } else {
            Swal.fire({
                //title: "Deleted!",
                text: "Your Contact is safe!",
                icon: "info"
            });
        }
    });
}

const search = () => {
    const query = document.getElementById("search-input").value;
    if (query.length > 0){
        let url = window.location.origin+'/user/search/'+query;
        fetch(url).then(response => {
            return response.json();
        }).then(data => {
            let text = `<div class='list-group'>`;
            data.forEach((contact) => {
                text += `<a href='/user/${contact.id}/contact' class='list-group-item list-group-item-action'>${contact.name} </a>`;
            });
            text+= `</div>`;
            $('.search-result').html(text);
            $('.search-result').show();
        })
    }else {
        $('.search-result').hide()
    }

}