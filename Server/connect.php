<?php
$hostname = "localhost";
$username = "root";
$password = "";
$database = "coffee_shop";
$connect = mysqli_connect($hostname, $username, $password, $database);
mysqli_query($connect, "SET NAMES 'utf8'");
?>