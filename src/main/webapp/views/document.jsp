
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="UTF-8">

    <title>My Documents</title>

    <style>

        .document {
            border: 1px solid #ccc;
            padding: 15px;
            margin: 10px 0;
            width: 500px;
        }

        .document img {
            width: 200px;
            display: block;
            margin-top: 10px;
        }

    </style>

</head>

<body>

    <a href="/store/upload">Upload Document</a>

    <h1>My Documents</h1>

    <div id="documents"></div>


    <script>

        fetch("/user/documents")

            .then(function(response) {

                if (!response.ok) {
                    throw new Error("Failed to load documents");
                }

                return response.json();

            })

            .then(function(data) {

                console.log(data);

                var container =
                    document.getElementById("documents");


                if (data.length === 0) {

                    container.innerHTML =
                        "<p>No documents found.</p>";

                    return;
                }


                data.forEach(function(doc) {

                    var div =
                        document.createElement("div");

                    div.className = "document";


                    // Document name

                    var heading =
                        document.createElement("h3");

                    heading.textContent = doc.name;

                    div.appendChild(heading);


                    // View file

                    var viewLink =
                        document.createElement("a");

                    viewLink.href =
                        "/user/documents/" + doc.id + "/view";

                    viewLink.textContent =
                        "View Document";

                    viewLink.target = "_blank";

                    div.appendChild(viewLink);


                    container.appendChild(div);

                });

            })

            .catch(function(error) {

                console.error(
                    "Error loading documents:",
                    error
                );

                document.getElementById("documents").innerHTML =
                    "<p>Unable to load documents.</p>";

            });

    </script>

</body>

</html>
