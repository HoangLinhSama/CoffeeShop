<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$page = $_POST["page"];
$limit = $_POST["pageSize"];
$phone = $_POST["phone"];
$offset = ($page - 1) * $limit;
$query1 = "SELECT membership FROM user WHERE phone =$phone";
try {
    $data1 = mysqli_query($connect, $query1);
    if ($data1) {
        $row1 = mysqli_fetch_assoc($data1);
        $memberShipId = $row1["membership"];
        if ($memberShipId != null) {
            $query2 = "SELECT code,
        DATE_FORMAT(start_date, '%e/%c') AS startDate,
        DATE_FORMAT(expiration_date, '%e/%c') AS expirationDate,
        voucher.name,
        description,
        value,
        freeship,
        conditions,
        picture,
        type,
        GROUP_CONCAT(DISTINCT drink_category.name) AS categoryDrink,
        qr_code AS qrCode
    FROM voucher
    JOIN voucher_drink_category ON voucher.id = voucher_drink_category.voucher_id
    JOIN voucher_membership  ON voucher.id = voucher_membership.voucher_id
    JOIN drink_category ON voucher_drink_category.drink_category_id = drink_category.id
    WHERE CURDATE() BETWEEN start_date AND expiration_date AND voucher_membership.membership_id=$memberShipId
    GROUP BY voucher.id
    LIMIT $limit OFFSET $offset";
            $data2 = mysqli_query($connect, $query2);
            if ($data2) {
                $result = array();
                while ($row = mysqli_fetch_assoc($data2)) {
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
