

let role = 'user'
getRole();

const productDOM = document.querySelector('.products');


let products;
getdata();

function getdata() {

    $.ajax({
        url: "/getAll",
        success: function (avilableProducts) {
            $('#emptyProduct').empty();

            avilableProducts.forEach(element => {
                showProducts(element);
            })

            console.log(role);

        }
    })
}

function showProducts(product) {

    productDOM.insertAdjacentHTML('beforeend', `
     <div class="product">
       <h3 class="Id">${product.prductId}</h3>
       <h3 class="category">${product.category}</h3>
       <h3 class="name">${product.productName}</h3>
       <h3 class="price">${product.price}</h3>
       <input id=${product.prductId} type="number" min="1" placeholder="quantity"  value="1">
       <button class="btn btn--primary" onclick="addToCart(${product.prductId})" >Add To Cart</button>
       <button class="btn btn--primary" data-action="forAdmin" onclick="editProduct(${product.prductId})" >Update Product</button>
     </div>`)

}

function editProduct(productId){


    $.ajax({
        type: 'GET',
        url: '/getProductById?productId=' + productId ,
        success: function (product) {
            $('#emptyProduct').empty();

            $('#emptyProduct').append(`
            <div style="margin-left: auto" >
            <table class="table" style="margin-left: 25%">
            <thead>
              <tr>
                <th>Product Id</th>
                <th>${productId}</th>
              </tr>
              <tr>
                <th>Product Name</th>
                <th><input id="modifiedName" type="text" value=${product.productName}></th>
              </tr>
              <tr>
                <th>Product Price</th>
                <th><input  id="modifiedPrice" type="number" value=${product.productPrice}></th>
              </tr>
              
              <tr>
                <th>Product Price</th>
                <th>
                <select id="modifiedCat" style="width: 15%">
                    <option class="btn" value="FOOD">Food</option>
                    <option class="btn" value="BOOK">Book</option>
                    <option class="btn" value="CLOTH">Cloth</option>
                    <option class="btn" value="INSTRUMENT">Instrument</option>
                    <option class="btn" value="ANONYMOUS">Anonymous</option>

                 </select>
                 </th>
              </tr>
              
              <tr>
                <th><button class="btn btn--primary" onclick="updateProduct(${productId})">UPDATE</button></th>
                <th><button class="btn btn--primary" onclick="deleteProduct(${productId})">DELETE</button></th>
              </tr>
              
            </div>
            `)


        }
    })



}
function updateProduct(productId) {
   let modifiedName = document.getElementById('modifiedName').value;
   let modifiedPrice = document.getElementById('modifiedPrice').value;
   let modifiedCat = document.getElementById('modifiedCat').value;

    var jsonProduct = JSON.stringify({
        'productId': productId,
        'name': modifiedName,
        'price' : modifiedPrice,
        'category' : modifiedCat
    });




    $.ajax({
        url: '/modifyProduct',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: jsonProduct,
        success: function () {
            alert("product modified");
            getdata();

        }
    })

}

function deleteProduct(productId) {

    $.ajax({
        type: 'GET',
        url: '/deleteProduct?productId='+ productId,
        success: function () {
            alert("product removed");
            getdata();

        }
    })


}



// // const admin = document.querySelector('[data-action="forAdmin"]');







function addToCart(productId) {

    let quantity = document.getElementById(productId).value;

    $.ajax({
        type: 'GET',
        url: '/addToCart?productId=' + productId + '&quantity=' + quantity,
        success: function () {
            $('#emptyCart').empty();
            getCart();
            alert("added");

        }
    })
}






const cartDOM = document.querySelector('.cart');

getCart();

function getCart() {

    $('#emptyCart').empty();
    $.ajax({
        type: 'GET',
        url: "/getCart",
        success: function (avilableCart) {
            avilableCart.forEach(element => {
                showCart(element);
            })

        }
    })

}

function showCart(cartItem) {

    cartDOM.insertAdjacentHTML('beforeend', `
     <div class="cartItem">
       <h5 class="Id">cart Item Id:- ${cartItem.cartItemId}</h5>
       <h5 class="name">Name:- ${cartItem.product.productName}</h5>
       <h5 class="price">Price:- ${cartItem.product.price}</h5>
       <h5>Quantity <input id=${cartItem.cartItemId} class="quantity" type="number" value=${cartItem.quantity}></h5>
       <h5 class="total">Total:- ${cartItem.amount}</h5>
       <button class="btn btn--primary btn--small" onclick="update(${cartItem.cartItemId} )">update</button>
        <button class="btn btn--primary btn--small" onclick="remove(${cartItem.cartItemId})">remove</button>
       <hr/>
     </div>`)

}

function update(cart) {
    let quantity = document.getElementById(cart).value;
    $.ajax({
        type: 'GET',
        url: '/changeQuantity?cartItemId=' + cart + '&quantity=' + quantity,
        success: function () {
            getCart();
            alert("done");

        }
    })

}

function remove(cartItemId) {
    $.ajax({
        type: 'GET',
        url: '/removeFromCart?cartItemId=' + cartItemId,
        success: function () {
            getCart();
            alert("item removed");

        }
    })
}

function order() {
    $('#emptyCart').empty();
    $.ajax({
        type: 'GET',
        url: '/createOrder',
        success: function () {
            getCart();
            alert("Order Placed");

        }
    })

}

function orderHistory() {


    $.ajax({
        type: 'GET',
        url: '/orderHistory',
        success: function (orderHistory) {
            $('#emptyCart').empty();
            orderHistory.forEach(element => {
                showHistory(element);
            })
        }
    })
}

function showHistory(cartItem) {

    cartDOM.insertAdjacentHTML('beforeend', `
     <div class="cartItem">
       <h5 class="Id">cart Item Id:- ${cartItem.cartItemId}</h5>
       <h5 class="name">Name:- ${cartItem.product.productName}</h5>
       <h5 class="price">Price:- ${cartItem.product.price}</h5>
       <h5>Quantity:- ${cartItem.quantity}</h5>
       <h5 class="total">Total:- ${cartItem.amount}</h5>
       <hr/>
     </div>`)

}

function searchProduct() {

    let search = document.getElementById("search").value;

    $.ajax({
        type: 'GET',
        url: '/search?search=' + search,
        success: function (products) {
            $('#emptyProduct').empty();
            products.forEach(element => {
                showProducts(element);
            })
        }
    })

}

function filterProduct() {
    let min = document.getElementById("min").value;
    let max = document.getElementById("max").value;
    $.ajax({
        type: 'GET',
        url: '/filter?lowest=' + min + '&highest=' + max,
        success: function (products) {
            if (products == null) {
                alert("product not found");
            }
            else {
                $('#emptyProduct').empty();

                products.forEach(element => {
                    showProducts(element);
                })
            }
        }
    })
}


function getRole() {
    let currentRole;
    $.ajax({
        type: 'GET',
        url: '/getProfile',
        success: function (user) {

            role = user.role;

            console.log(user.role);
            console.log(currentRole);

        }
    })
    return currentRole;

}


function user() {

    $.ajax({
        type: 'GET',
        url: '/getProfile',
        success: function (user) {
            let User = "Name:-" + user.name + "\n" +
                "Username:- " + user.username + "\n" +
                "phone:-" + user.phone + "\n" +
                "email:-" + user.email;


            alert(User);

        }
    })

}

function getCat(category) {
    $.ajax({
        type: 'GET',
        url: '/findProductByCategory?category=' + category,
        success: function (product) {

            $('#emptyProduct').empty();
            product.forEach(element => {
                showProducts(element);
            })


        }
    })
}