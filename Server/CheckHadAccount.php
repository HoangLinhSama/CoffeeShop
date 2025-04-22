<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$phone = $_POST["phone"];
$query = "SELECT COUNT(*) AS countPhone FROM user WHERE phone = '$phone'";
try {
    $data = mysqli_query($connect, $query);
    if ($data) {
        $row = mysqli_fetch_assoc($data);
        $available = $row['countPhone'] == 1;
        $response = [
            "status" => "success",
            "result" => [$available]
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
