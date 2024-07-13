$(document).ready(function () {

    let searchParams = new URLSearchParams(window.location.search)
    var restid = searchParams.get("id") //lấy id bên backend

    var linkRestaurant = "http://localhost:8080/restaurant"
    var linkMenu = "http://localhost:8080/menu"
    var token = localStorage.getItem("token")
    console.log(restid)

    $.ajax({
        method: "GET",
        url: `${linkRestaurant}/detail?id=${restid}`, //dấu huyền mới hoạt động
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .done(function (msg) {
        console.log(msg)
        if (msg.success) {
            var value = msg.data
            //load detail restaurant
            var html = `<div class="mt-n5 bg-white p-3 mb-4 rounded shadow position-relative">
                                <div class="osahan-single-top-info d-flex">
                                    <img src="${linkRestaurant}/file/${value.image}" class="img-fluid border p-2 mb-auto rounded brand-logo shadow-sm">
                                    <div class="ml-3">
                                        <h3 class="mb-0 font-weight-bold">${value.title} <small><i class="mdi mdi-silverware-fork-knife ml-2 mr-1"></i> ${value.subTitle}</small></h3>
                                        <div class="restaurant-detail mt-2 mb-3">
                                            <span class="badge badge-light"><i class="mdi mdi-truck-fast-outline"></i> Free delivery</span>
                                            <span class="badge badge-info"><i class="mdi mdi-clock-outline"></i> ${value.openDate}</span>
                                    </div>
                                    <p class="text-muted p-0 mt-2 mb-2">${value.desc}</p>
                                    <p class="mb-0 small">
                                        <i class="mdi mdi-star text-warning"></i> 
                                        <span class="font-weight-bold text-dark">${value.rating.toFixed(2)}</span> - 500+ Ratings
                                    </p>
                                </div>
                            </div>
                        </div>`

            var itemCategoryHtml = ""
            var menuHtml = ""

            //load category
            $.each(value.categories, function (index, data) {
                itemCategoryHtml += `<li class="nav-item mr-2" role="presentation">
                                        <a class="nav-link ${index == 0 ? `active` : ``} border-0 btn btn-light" id="popular-tab" data-toggle="tab"
                                            href="#tab-${index}" role="tab" aria-controls="popular" aria-selected="${index == 0 ? `true` : ``}">${data.name}</a>
                                    </li>`
                //load menu
                var itemMenuHtml = ""
                $.each(data.menus, function (index, itemMenu) {
                    itemMenuHtml += `<a href="#" class="btn-food text-decoration-none text-dark col-lg-3 col-md-6 mb-4" data-toggle="modal" data-target="#myitemsModal" 
                                      data-id="${itemMenu.id}" data-title="${itemMenu.title}" data-desc="${itemMenu.desc}" data-price="${itemMenu.price}" data-image="${itemMenu.image}">
                                        <img src="${linkMenu}/file/${itemMenu.image}" class="img-fluid rounded">
                                        <div class="d-flex align-items-center mt-3 mb-2">
                                            <p class="text-black h6 m-0">${itemMenu.title}</p>
                                            <span class="badge badge-light ml-auto"><i class="mdi mdi-truck-fast-outline"></i> Free delivery</span>
                                        </div>
                                    </a>`
                })

                //load detail category
                menuHtml += `<div class="tab-pane fade ${index == 0 ? `show active` : ``}" id="tab-${index}" role="tabpanel" aria-labelledby="tab-${index}-tab">
                                        <div class="row">
                                            ${itemMenuHtml}
                                        </div>
                                    </div>`
            })

            var categoryHtml = `<ul class="nav nav-tabs border-0 mb-4" id="myTab" role="tablist">
                                    ${itemCategoryHtml}
                                </ul>`

            $("#c-detail").append(html)
            $("#c-detail").append(categoryHtml)
            $("#c-detail").append(`<div class="tab-content" id="myTabContent">${menuHtml} </div>`)
        }
    });

    $("body").on("click",".btn-food",function(){
        var id = $(this).attr("data-id")
        var title = $(this).attr("data-title")
        var desc = $(this).attr("data-desc")
        var price = $(this).attr("data-price")
        var image = $(this).attr("data-image")

        $('#titleFoodModal').html(title)
        $('#descFoodModal').html(desc)
        $('#imgFoodModal').attr('src',`${linkMenu}/file/${image}`)
        $('#priceFoodModal').html(`Add $(${price})`)
        $('#priceFoodModal').attr('id',id)
    })
})