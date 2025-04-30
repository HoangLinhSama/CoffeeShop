<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$page = $_POST["page"];
$limit = $_POST["pageSize"];
$offset = ($page - 1) * $limit;
$query = "SELECT drink.id,
    drink.name,
    picture,
    ROUND(AVG(feedback.star), 1) AS star,
    COUNT(DISTINCT feedback.id) AS countReview,
    description,
    GROUP_CONCAT(DISTINCT CONCAT(IFNULL(size, ' '), ':', drink_price.price)
        ORDER BY CASE size
            WHEN 'Nhỏ' THEN 1
            WHEN 'Vừa' THEN 2
            WHEN 'Lớn' THEN 3
        END ASC
    ) AS priceSize,
    GROUP_CONCAT(DISTINCT CONCAT(topping.name, ':', topping.price)) AS toppingPrice
FROM drink 
JOIN drink_price ON drink.id = drink_price.drink_id
LEFT JOIN drink_topping ON drink.id = drink_topping.drink_id
LEFT JOIN topping ON drink_topping.topping_id = topping.id
LEFT JOIN feedback ON drink.id = feedback.drink_id
GROUP BY drink.id
LIMIT $limit OFFSET $offset;
";
try {
    $data = mysqli_query($connect, $query);
    if ($data) {
        $result = array();
        while ($row = mysqli_fetch_assoc($data)) {
            $row['priceSize'] = $row['priceSize'] ? explode(',', $row['priceSize']) : [];
            $row['toppingPrice'] = $row['toppingPrice'] ? explode(',', $row['toppingPrice']) : [];
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
