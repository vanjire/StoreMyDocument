<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Create User</title>
</head>

<body>

<form onsubmit="saveUser(event)">

    <b>Username:</b>
    <input type="text" id="name">
    <br>
    <p id="um"></p>

    <b>Password:</b>
    <input type="password" id="password">
    <br>
    <p id="pm"></p>

    <button type="submit">Submit</button>

    <p id="msg"></p>

</form>

<script>

function saveUser(event) {

    // Stop normal form submission
    event.preventDefault();

    let username =
        document.getElementById("name").value;

    let password =
        document.getElementById("password").value;


    fetch("/auth/register", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            username: username,
            password: password
        })
    })

    .then(response => {

        /*
         * 400 BAD REQUEST
         */
        if (response.status === 400) {

            return response.json()
                .then(error => {
                	document.getElementById("um")
                    .innerHTML =
                    "";
                	 document.getElementById("pm")
                     .innerHTML ="";
                	 document.getElementById("msg")
                     .innerHTML = "";
                    if (error.username != null) {

                        document.getElementById("um")
                            .innerHTML =
                            error.username;
                    }

                    if (error.password != null) {

                        document.getElementById("pm")
                            .innerHTML =
                            error.password;
                    }
                });
        }


        /*
         * 409 CONFLICT
         */
        if (response.status === 409) {
        	document.getElementById("um")
            .innerHTML =
            "";
        	 document.getElementById("pm")
             .innerHTML ="";
        	 document.getElementById("msg")
             .innerHTML = "";
            return response.text()
                .then(message => {

                    document.getElementById("msg")
                        .innerHTML = message;
                });
        }


        /*
         * 201 CREATED
         */
        if (response.status === 201) {

            window.location.href = "/login";
            return;
        }

    })

    .catch(error => {

        console.log(error);

    });
}

</script>

</body>
</html>