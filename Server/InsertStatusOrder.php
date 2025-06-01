<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$orderId = $_POST["orderId"];
$statusId = $_POST["statusId"];
date_default_timezone_set("Asia/Ho_Chi_Minh");
$time = date("Y-m-d H:i:s");
$query = "INSERT INTO order_status_order VALUES (null, '$orderId', $statusId,'$time')";
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
