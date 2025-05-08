<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$phone = $_POST["phone"];
$query = "SELECT id, first_name AS firstName,last_name AS lastName, phone, address, image, membership AS memberShip, current_bean AS currentBean, collected_bean AS collectBean FROM user WHERE phone ='$phone'";
try {
    $data = mysqli_query($connect, $query);
    if ($data) {
        $result = array();
        while ($row = mysqli_fetch_assoc($data)) {
            $result[] = $row;
        }
        if (!empty($result)) {
            $response = [
                "status" => "success",
                "result" => $result
            ];
        } else {
            $response = [
                "status" => "fail: no data found",
                "result" => $result
            ];
        }
    }
} catch (mysqli_sql_exception $e) {
    $response = [
        "status" => "error: " . $e->getMessage(),
        "result" => []
    ];
}
echo json_encode($response);
