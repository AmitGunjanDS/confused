userDetail();


const userDOM = document.querySelector('.user');

function showUser(user) {

    userDOM.insertAdjacentHTML('beforeend', `
     <div style="text-align: center" class="user">
       <h3 id="name">${user.name}</h3>
       <h3 id="phone">${user.phone}</h3>
       <h3 id="email">${user.email}</h3>
       <a class="btn btn--primary" href="/updateUser.html">Update</a>
     </div>`)

}

function userDetail() {

    $.ajax({
        type: 'GET',
        url: '/getProfile',
        success: function (user) {
            showUser(user);


        }
    })

}

function editUser() {

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

function userList() {

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

