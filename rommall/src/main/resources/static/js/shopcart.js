
sessionStorage = window.sessionStorage;
var cartArr={};
let currentPageNum = 1;

$(document).ready(function () {

    let is = isLogin();
    if (is) {
        queryCart(1, 10);
        bindPreNextPage();
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

function queryCart(pageNum, pageSize) {
    let id = sessionStorage.getItem("userId");

    $.ajax({
        type: "get",
        url: "/cart/list",// /cart/list
        data: {
            userId: id,
            pageNum: pageNum,
            pageSize: pageSize,
        },
        dataType: "json",
        success: function (res) {
            if (res.code === 200) {
                let s = "";
                cartArr={};
                for (let record of res.data) {

                    record.ischecked = false;
                    cartArr[record.id] = record;

                    s +=
                        `<div class="cartd4" id="cartgood`+ record.id +`">
                    <ul class="cartul2">
                        <li class="cartli1">
                            <div class="cartd3">
                                <input type="checkbox" onclick='checkOneGood(`+ record.id +`)' class="ipt">
                            </div>
                        </li>
                        <li class="cartli2">
                            <div class="fl">
                                <img src="`+ record.img +`">
                            </div>
                            <div class="fl cartd5">`+ record.prodName +`</div>
                            <div class="cls"></div>
                        </li> 
                        <li class="cartli3">￥`+ record.price +`</li>
                        <li class="cartli4">
                            <button onclick="sub(`+ record.id +`)">-</button>
                            <input readonly="true" type="text" value="`+ record.num +`" id="iid`+ record.id +`"> 
                            <button class="ml" onclick="add(`+ record.id +`)">+</button>
                        </li>
                        <li class="cartli5">
                            ￥<span id="gsum`+ record.id +`">`+ (record.price * record.num) +`</span>
                        </li>
                        <li class="cartli6">
                            <a href="javascript:deleteCartGood(`+ record.id +`)">删除</a>
                        </li>
                    </ul>
                </div>`
                }
                $(".cartd4").remove();
                $("#cartgoodlist").append(s);
                currentPageNum = pageNum;
                if(currentPageNum == 1){
                    $("#prePage").prop("disabled",true);
                }else{
                    $("#prePage").prop("disabled",false);
                }
                totalMoney();
            }
        }
    })
}

function checkAll(){
    let result = $("#selectAll").is(":checked");
    $(".ipt").prop("checked",result);
    for(let key in cartArr){
        if(cartArr.hasOwnProperty(key)){
            cartArr[key].ischecked = result;
        }
    }
    totalMoney();
}

function checkOneGood(id){
    cartArr[id].ischecked = !cartArr[id].ischecked;
    totalMoney();
}

function totalMoney(){
    let total = 0;
    let num = 0;
    for(let key in cartArr){
        if(cartArr.hasOwnProperty(key)){
            let good = cartArr[key];
            if(good.ischecked){
                total += good.price * good.num;
                num += 1;
            }
        }
    }
    $("#totalNum").html(num);
    $("#totalPrice").html(total);
}

function sub(id){
    let snum = $("#iid" + id).val();
    let num = parseInt(snum);
    if(num <= 1){
        showToast("不能更小了");
    }else{
        num = num - 1;
        updateCart(id,num);
    }
}

function add(id){
    let snum = $("#iid" + id).val();
    let num = parseInt(snum) + 1;
    updateCart(id,num);
}

function updateCart(_id,_num){
    $.ajax({
        type:"post",//POST
        url:"/cart/admin/update",// /cart/admin/update
        contentType:"application/json",
        data:JSON.stringify({
            id:_id,
            num:_num
        }),
        dataType:"json",
        success:function(res){
            if(res.code === 200){
                $("#iid" + _id).val(_num);
                cartArr[_id].num = _num;
                $("#gsum" + _id).html(_num * cartArr[_id].price);
                totalMoney();
            }else{
                showToast("更新购物车失败:"+ res.message);
            }
        }
    })
}

function deleteCartGood(id){
    let dataArr = [id];
    $.ajax({
        type:"post",//POST
        url:"/cart/admin/delete",// /cart/admin/delete
        contentType:"application/json",
        data:JSON.stringify(dataArr),
        dataType:"json",
        success:function(res){
            if(res.code === 200){
                delete cartArr[id];
                $("#cartgood" + id).remove();
                totalMoney();
            }else{
                showToast("删除购物车失败:"+ res.message);
            }
        }
    })
}

function bindPreNextPage(){
    $("#prePage").on("click",function(){
        if(currentPageNum <= 1){
            showToast("已经是第一页了");
            return;
        }
        let pageNum = currentPageNum - 1;

        queryCart(pageNum,10);
    })

    $("#nextPage").on("click",function(){

        let pageNum = currentPageNum + 1;

        queryCart(pageNum,10);
    })
}

function showToast(message){
    $("#liveToast").toast('show');
    $("#messageToast").html(message);
}

function checkOut(){
    let checkedGoods = [];
    let total = 0;
    for(let key in cartArr){
        if(cartArr.hasOwnProperty(key) && cartArr[key].ischecked){
            checkedGoods.push(cartArr[key]);
            total += cartArr[key].price * cartArr[key].num;
        }
    }
    if(checkedGoods.length === 0){
        showToast("请先选择要结算的商品");
        return;
    }

    var choose = confirm("确定要结算吗？");
    if(!choose){
        return;
    }

    for(let key in checkedGoods){
        deleteCartGood(checkedGoods[key].id)
    }
    window.location.href = "shopcart.html";
    showToast("结算成功");

    // $.ajax({
    //     type:"get",//POST
    //     url:"./testjson/order.json",//
    //     contentType:"application/json",
    //     data:JSON.stringify(checkedGoods),
    //     dataType:"json",
    //     success:function(res){
    //         if(res.code === 200){
    //             for(let key in checkedGoods){
    //                 deleteCartGood(checkedGoods[key].id)
    //             }
    //             window.location.href = "shopcart.html";
    //             showToast("结算成功");
    //         }else{
    //             showToast("结算失败:"+ res.message);
    //         }
    //     }
    // })
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
