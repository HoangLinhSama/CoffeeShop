<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$page = $_POST["page"];
$limit = $_POST["pageSize"];
$offset = ($page - 1) * $limit;
$query = "SELECT code,
    DATE_FORMAT(start_date, '%e/%c') AS startDate,
    DATE_FORMAT(expiration_date, '%e/%c') AS expirationDate,
    voucher.name,
    description,
    value,
    freeship,
    conditions,
    picture,
    type,
    GROUP_CONCAT(DISTINCT drink_category.name) AS categoryDrink
FROM voucher
JOIN voucher_drink_category ON voucher.id = voucher_drink_category.voucher_id
JOIN voucher_membership  ON voucher.id = voucher_membership.voucher_id
JOIN drink_category ON voucher_drink_category.drink_category_id = drink_category.id
WHERE CURDATE() BETWEEN start_date AND expiration_date
GROUP BY voucher.id
LIMIT $limit OFFSET $offset";
try {
    $data = mysqli_query($connect, $query);
    if ($data) {
        $result = array();
        while ($row = mysqli_fetch_assoc($data)) {
            $row['categoryDrink'] = $row['categoryDrink'] ? explode(',', $row['categoryDrink']) : [];
            $result[] = $row;
        }
        if (!empty($result)) {
            $response = [
                "status" => "success",
                "result" => $result
            ];
        } else {
            if ($page == 1) {
                $response = [
                    "status" => "fail: no data found",
                    "result" => $result
                ];
            } else {
                $response = [
                    "status" => "fail: no more data",
                    "result" => $result
                ];
            }
        }
    }
} catch (mysqli_sql_exception $e) {
    $response = [
        "status" => "error: " . $e->getMessage(),
        "result" => []
    ];
}
echo json_encode($response);
