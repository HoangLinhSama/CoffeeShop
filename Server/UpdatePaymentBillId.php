<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$orderId = $_POST["orderId"];
$paymentBillId = $_POST["paymentBillId"];
$query = "UPDATE orderr SET payment_bill_id = '$paymentBillId' WHERE id = '$orderId'";
try {
    $data = mysqli_query($connect, $query);
    if ($data) {
        $response = [
            "status" => "success",
            "result" => [true]
        ];
    } else {
        $response = [
            "status" => "fail",
            "result" => []
        ];
    }
} catch (mysqli_sql_exception $e) {
    $response = [
        "status" => "error: " . $e->getMessage(),
        "result" => []
    ];
}
echo json_encode($response);
