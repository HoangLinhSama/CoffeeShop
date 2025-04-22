<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$firstName = $_POST["firstName"];
$lastName = $_POST["lastName"];
$phone = $_POST["phone"];
$address = $_POST["address"];
$image = $_POST["image"];
if ($image == "") $image = null;
$query = "INSERT INTO user VALUES (null, '$firstName', '$lastName', '$phone','$address',0,3,null,'$image')";
$result = array();
try {
    $data = mysqli_query($connect, $query);
    if ($data) {
        echo "success";
    } else {
        echo "fail";
    }
} catch (mysqli_sql_exception $e) {
    echo "error: " . $e->getMessage();
}
