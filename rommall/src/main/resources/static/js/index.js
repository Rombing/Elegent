var category_arr = [];
var goodsMap = {};

$(document).ready(function () {

    queryCategory();
    queryBanner();
    isLogin();
})

function isLogin() {
    $.ajax({
        type: "get",
        url: "/user/isLogin", // /user/isLogin
        data: {},
        dataType: "json",
        async: false,
        success: function (res) {
            if (res.code === 200) {
                $("#nickName").html("你好！" + res.data.nickName);
            }
        }
    });
}

function queryCategory() {
    $.ajax({
        type: "get",
        url: "/category/list",
        data: "",
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                category_arr = res.data;
                let s1 = "";
                let s2 = "";
                for (let val of category_arr) {
                    s1 += `
                    <div class='category' cgId='`+ val.id + `'>
                        <div class='cg_a'><a href='javascript:;'>`+ val.name + `</a></div>
                    </div>
                    `;
                    s2 += `
                    <div class="cc" cgdid='`+ val.id + `'>
                        <div>
                            <h3 class="cc-title">`+ val.name + `</h3>
                        </div>
                    </div>
                    `;
                }
                $("#cg_p").append(s1);
                $("#goodlist").append(s2);

                for (let val of category_arr) {
                    queryGoodsByCategoryId(val.id, val.name, 1, 60);
                }
            }
        }
    })
}

function queryGoodsByCategoryId(categoryId, categoryName, pageNum, pageSize) {
    $.ajax({
        type: "get",
        url: "/product/list",
        data: {
            categoryId: categoryId,
            pageNum: pageNum,
            pageSize: pageSize
        },
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                goodsMap[categoryName] = res.data;
                let s1 = "";
                s1 += `
                <div class="cg_div">
                    <div class="category_detail">`;
                let i = 0;
                for (let val of goodsMap[categoryName]) {
                    if (i == 0) {
                        s1 += `<ul>`;
                    }
                    s1 += `<li class="cd_li">
                                <a href="/good.html?id=`+ val.id + `">` + val.title + `</a>
                            </li>`;
                    i++;
                    if (i == 6) {
                        s1 += `</ul>`;
                        i = 0;
                    }
                }
                if (i != 0) {
                    s1 += `</ul>`;
                }
                s1 +=
                    `</div>
                </div>`

                $("div[cgId='" + categoryId + "']").append(s1);

                $("div[cgId='" + categoryId + "']").on("mouseover", function () {
                    $("div[cgId='" + categoryId + "'] .cg_a").css("background-color", "#e9e9e9");
                    $("div[cgId='" + categoryId + "'] .cg_div").show();
                })

                $("div[cgId='" + categoryId + "']").on("mouseleave", function () {
                    $("div[cgId='" + categoryId + "'] .cg_a").css("background-color", "white");
                    $("div[cgId='" + categoryId + "'] .cg_div").hide();
                });

                listGoods(categoryId, categoryName);

            }
        }
    })
}

function queryBanner() {
    $.ajax({
        type: "get",
        url: "/banner/list",
        data: "",
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                let count = 0;
                let s1 = "";
                let s2 = "";
                for (let val of res.data) {
                    if (count == 0) {
                        s1 += `<li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>`;
                        s2 += `
                        <div class="carousel-item active">
                            <a href="`+ val.url + `" target="_blank">
                                <img src="`+ val.img + `"class="imgfull">
                            </a>
                        </div>
                        `;
                    } else {
                        s1 += `<li data-target="#carouselExampleIndicators" data-slide-to="` + count + `"></li>`;
                        s2 += `
                        <div class="carousel-item">
                            <a href="`+ val.url + `" target="_blank">
                                <img src="`+ val.img + `">
                            </a>
                        </div>
                        `;
                    }
                    count++;
                }
                $("#carouselExampleIndicators .carousel-indicators").append(s1);
                $("#carouselExampleIndicators .carousel-inner").append(s2);
                $("#carouselExampleIndicators").carousel({
                    interval: 2000,
                    ride: "carousel"
                })
            }
        }
    })
}

function listGoods(categoryId, categoryName) {
    let s = "";
    let goodsArr = goodsMap[categoryName];

    s += `
        <div>
            <ul>
    `;

    let count = 0;

    for (let good of goodsArr) {
        if(count == 0){
            s += `<li class="glifirst">`
        }else{
            s  += `<li class="gli">`
        }
        count++;
        if(count == 5){
            count = 0;
        }
        s += `
                <div class="glid1">
                    <div class="glid2">
                        <a href="./good.html?id=`+ good.id +`" target="_blank">
                            <img src="`+ good.img +`">
                        </a>
                    </div>
                    <a href="./good.html?id=`+ good.id +`" target="_blank" class="ga">
                        <div>
                            <div>
                                <p class="gtitle">`+ good.title +`</p>
                            </div>
                            <div>
                                <p class="gdetail">`+ good.description +`</p>
                            </div>
                        </div>
                    </a>
                    <div class="gmoney">
                        <i>￥</i>`+ good.price +`
                    </div>
                </div>
            </li>
        `;
    }

    s += `
            <li class="cls"></li>
        </ul>
    </div>
    `;

    $("div[cgdid='"+ categoryId +"']").append(s);

}

function search() {
    let s = $("#search_id").val()
    if(s.length != 0){
        window.location.href="/search.html?name="+s;
    }else{
        window.location.href="/index.html";
    }
}