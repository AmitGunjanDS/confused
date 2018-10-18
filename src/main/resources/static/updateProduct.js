let productId = localStorage.getItem("productId");

populatingFields(productId);

function populatingFields(productId) {
    $.ajax({
        type: 'GET',
        url: '/getProductById?productId=' + productId,
        success: function (product) {

            document.getElementById("name").setAttribute(value, product.productName);
            document.getElementById("price").setAttribute(value, product.productPrice);
        }
    })

}