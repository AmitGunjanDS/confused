const name = document.querySelector('#name');
const phone = document.querySelector('#phone');
const email = document.querySelector('#email');
userDetail();

function userDetail() {

    $.ajax({
        type: 'GET',
        url: '/getProfile',
        success: function (user) {
            name.setAttribute('value', user.name);
            phone.setAttribute('value', user.phone);
            email.setAttribute('value', user.email);

        }
    })

}