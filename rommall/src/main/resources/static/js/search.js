sessionStorage = window.sessionStorage;

$(document).ready(function () {
    isLogin();
    getGoods(1,10);
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
                sessionStorage.setItem("userId", res.data.id);
            }
        }
    });
}

function getGoods(pageNum, pageSize) {
    let params = new URLSearchParams(window.location.search);
    let name = params.get("name");
    $.ajax({
        type: "get",
        url: "/product/list",
        data: {
            name: name,
            pageNum: pageNum,
            pageSize: pageSize
        },
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                goods = res.data;
                let s = "";
                for (let good of goods) {
                    s +=
                        `<div class="cartd4">
                    <ul class="cartul2">
                        <li class="cartli2">
                            <div class="fl">
                                <img src="`+ good.img +`">
                            </div>
                            <div class="fl cartd5">`+ good.title +`</div>
                            <div class="cls"></div>
                        </li> 
                        <li class="cartli3">￥`+ good.price +`</li> 
                        <li class="cartli6">
                            <a href="./good.html?id=`+ good.id +`" target="_blank">
                                <button class="btt">前往该商品详情页</button>
                            </a>
                        </li>
                    </ul>
                </div>`
                }
                $("#search_list").append(s);
            }
        }
    })
}

function search() {
    let s = $("#search_id").val()
    if(s.length != 0){
        window.location.href="/search.html?name="+s;
    }else{
        window.location.href="/index.html";
    }
}