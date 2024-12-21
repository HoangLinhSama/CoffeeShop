<?php
include "Connect.php";
$page = $_POST["page"];
$limit = $_POST["pageSize"];
$offset = ($page - 1) * $limit;
$query = "SELECT name FROM drink_category LIMIT $limit OFFSET $offset";
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
                "status" => "fail: no data found !",
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
