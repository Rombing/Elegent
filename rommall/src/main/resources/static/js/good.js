sessionStorage = window.sessionStorage;
var good
$(document).ready(function () {
    let is = isLogin();
    if (is) {
        getGood();
    }
})

function isLogin() {
    let result = false;
    $.ajax({
        type: "get",
        url: "/user/isLogin", // /user/isLogin
        data: {},
        dataType: "json",
        async: false,
        success: function (res) {
            if (res.code === 200) {
                $("#cartNoLogin").hide();
                $("#cartLogin").show();
                $("#nickName").html("你好！" + res.data.nickName);
                sessionStorage.setItem("userId", res.data.id);
                result = true;
            } else {
                $("#cartNoLogin").show();
                $("#cartLogin").hide();
            }
        }
    });
    return result;
}

function getGood() {
    let params = new URLSearchParams(window.location.search);
    let id = params.get("id");
    $.ajax({
        type: "get",
        url: "/product/admin/"+id,// /product/admin/{id}
        data: {},
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                good = res.data;
                let s = "";
                s += `
                <div class="glid1">
                    <div class="glid2 fl">
                        <img src="`+ good.img + `" class="goodimg">
                    </div>
                    <div class="glid3 fl">
                        <div>
                            <div>
                                <p class="gtitleshow">商品名：</p>
                                <p class="gtitle">`+ good.title + `</p>
                            </div>
                            <div>
                                <p class="gdetailshow">商品介绍：</p>
                                <p class="gdetail">`+ good.description + `</p>
                            </div>
                        </div>
                        <div class="gmoney">
                            <i>￥</i>`+ good.price + `
                        </div>
                        <div>
                            <button class="btt" onclick="checkOut(`+ good.id + `)" >添加到购物车中</button>
                        </div>
                    </div>
                    <div class="cls"></div>
                </div>`
                $('#good').append(s);
            }
        }
    })
}

function checkOut(id) {
    let userId = sessionStorage.getItem("userId");
    $.ajax({
        type: "post",
        url: "/cart/admin/insertOrUpdate",// /cart/admin/insertOrUpdate
        data:JSON.stringify({
            prodId: id,
            num: 1,
            userId: userId
        }),
        dataType: "json",
        contentType:"application/json",
        success: function (res) {
            if(res.code === 200){
                alert("添加成功！")
            }else {
                alert("添加失败")
            }
        }
    })
}

function search() {
    let s = $("#search_id").val()
    console.log(s)
    if(s.length != 0){
        window.location.href="/search.html?name="+s;
    }else{
        window.location.href="/index.html";
    }
}