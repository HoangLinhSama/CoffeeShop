<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$firstName = $_POST["firstName"];
$lastName = $_POST["lastName"];
$phone = $_POST["phone"];
$address = $_POST["address"];
$image = $_POST["image"];
$storeId = $_POST["storeId"];
if ($image == "") {
    $image = 'null';
} else {
    $image = "'" . mysqli_real_escape_string($connect, $image) . "'";
}
if ($storeId == "") {
    $storeId = 'null';
} else {
    $storeId = "'" . mysqli_real_escape_string($connect, $storeId) . "'";
}
$query1 = "INSERT INTO user VALUES (null, '$firstName', '$lastName', '$phone','$address',0,3,$storeId,$image)";
$query2 = "SELECT id FROM user WHERE phone ='$phone'";
$result = array();
try {
    $data1 = mysqli_query($connect, $query1);
    if ($data1) {
        if ($storeId == 'null') {
            $data2 = mysqli_query($connect, $query2);
            if ($data2) {
                $row = mysqli_fetch_assoc($data2);
                $userId = $row["id"];
                $query3 = "INSERT INTO cart VALUES (null,'$userId')";
                $data3 = mysqli_query($connect, $query3);
                if ($data3) {
                    echo "success";
                }
            }
        }
    } else {
        echo "fail";
    }
} catch (mysqli_sql_exception $e) {
    echo "error: " . $e->getMessage();
}
