<body class= "background" >
<?php
session_start();
include 'styles.css' ;
$link =
mysqli_connect( "localhost" , "root" , "" , "lib
rary1" )
or die (
mysqli_connect_error( "Connection
Failed" ));
$username= $_POST[ "member_id" ];
$password= $_POST[ "Password" ];
if (! empty ($username)){
if (! empty ($password)){
$sql= "SELECT * FROM members where
member_id = '$username' " ;
$query = mysqli_query($link, $sql)
or die ( mysqli_connect_error());
while ($row= mysqli_fetch_array
($query)){
$DB_username= $row[ "member_id" ];
$DB_password= $row[ "Password" ];
if ($username==$DB_username &&
$password== $DB_password){
echo "<h2> Hello " . $row[ "F_name" ]
. "</h2>" ;
echo "<div id='rcorners1'> Your
LOGIN details: " ;
echo "<table
class='cinereousTable'>
<thead><tr>
<th> First Name </th>
<th> Last Name</th>
<th> Type </th>
<th> email </th>
<th> DOB </th>
<th> privilege NO. </th>
</tr></thead>" ;
$sql= "SELECT * FROM members" ;
$query = mysqli_query($link, $sql)
or die ( mysqli_connect_error());
while ($row= mysqli_fetch_array
($query)){
echo "<tbody> <tr> <td> <h4>
" .$row[ "F_name" ]. "</h4> </td>" ;
echo "<td> <h4> " .$row[ "L_name" ].
"</h4></td>" ;
echo "<td> <h4>
" .$row[ "member_type" ]. "</h4></td>" ;
echo "<td> <h4> " .$row[ "email" ].
"</h4></td>" ;
echo "<td> <h4> " .$row[ "DOB" ].
"</h4></td>" ;
echo "<td> <h4>
" .$row[ "privilege_id" ].
"</h4></td></tr></tbody>" ; }
echo "</table >" ;
echo "<br><br><br> Available books" ;
echo "<table class='cinereousTable'>
<thead><tr>
<th> book ID </th>
<th> book Name</th>
<th> Type </th>
<th> catagory </th>
<th> author name </th>
</tr></thead>" ;
$sql= "SELECT * FROM `vBookStatus`" ;
$query = mysqli_query($link, $sql)
or die ( mysqli_connect_error());
while ($row= mysqli_fetch_array
($query)){
echo "<tbody> <tr> <td> <h4>
" .$row[ "book_id" ]. "</h4> </td>" ;
echo "<td> <h4> " .$row[ "book_name" ].
"</h4></td>" ;
echo "<td> <h4> " .$row[ "type" ].
"</h4></td>" ;
echo "<td> <h4> " .$row[ "catagory" ].
"</h4></td>" ;
echo "<td> <h4> " .$row[ "author_name" ].
"</h4></td>" ;
}
echo "</table>" ;
echo "</div>" ;
}
else { echo "error in login" ; }
}
}
else { echo "Enter a password" ;}
}
else { echo "Enter ID number & password" ;}
?>
</body>