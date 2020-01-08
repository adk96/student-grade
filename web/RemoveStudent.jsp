<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <title>fSchool</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link rel="stylesheet" href="css/RemoveStudent.css">
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link rel="stylesheet" href="css/menu.css">
        <script src="js/jquery1111.min.js" type="text/javascript"></script>
        <script src="js/script.js"></script>
    </head>

    <body class="home-page">
        <header class="wrap-body">
            <div id="cssmenu">
                <ul>
                    <li class="active"><a href="index.html"><span>FSchool</span></a></li>
                    <li class="has-sub"><a href="#"><span>Students</span></a>
                        <ul>
                                <li class="has-sub"><a href="MyServlet?actionname=ShowAllStudent"><span>Show All Students</span></a>
                                    
                                   <li><a href="addstudent.jsp"><span>Add</span></a></li>
                                 <li><a href="RemoveStudent.jsp"><span>Remove</span></a></li>
                                    
                                
                            </ul>
                        </li>
                    <li><a href="archive.html"><span>Media</span></a></li>
                    <li><a href="single.html"><span>Skills</span></a></li>
                    <li class="last"><a href="contact.html"><span>About</span></a></li>
                </ul>
            </div>
        </header>

        <div class="RemoveStudent">
            <h1>Remove Student</h1>
            <form action="MyServlet" method="Post">
                id : <input type="number" name="id"><br><br>

                <input type="hidden" value="RemoveStudent" name="actionname">
                <input type="submit" value="Send"><br>
            </form>
        </div>
    </body>
</html>
