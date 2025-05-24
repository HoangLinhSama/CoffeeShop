<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$phone = $_POST["phone"];
$query1 = "SELECT membership, collected_bean AS collectedBean FROM user WHERE phone ='$phone'";
try {
    $data1 = mysqli_query($connect, $query1);
    if ($data1) {
        $row1 = mysqli_fetch_assoc($data1);
        $collectedBean = $row1["collectedBean"];
        if ($collectedBean != null) {
            $query2 = "SELECT bean FROM membership WHERE bean > $collectedBean ORDER BY bean ASC LIMIT 1";
            $data2 = mysqli_query($connect, $query2);
            if ($data2) {
                $row2 = mysqli_fetch_assoc($data2);
                $rankBean = $row2["bean"];
                if ($rankBean != null) {
                    $result = array();
                    $result[] = $rankBean - $collectedBean;
                    $response = [
                        "status" => "success",
                        "result" => $result
                    ];
                } else {
                    $response = [
                        "status" => "fail: unknown error",
                        "result" => []
                    ];
                }
            }
        } else {
            $response = [
                "status" => "fail: unknown error",
                "result" => []
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
