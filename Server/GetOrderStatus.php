<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$orderId = $_POST["orderId"];
try {
  $query1 = "SELECT 
      orderr.is_delivery AS isDelivery,
      orderr.name,
      orderr.phone,
      orderr.address,
      orderr.sub_total AS subTotal,
      orderr.delivery_fee AS deliveryFee,
      orderr.total_price AS totalPrice,
      orderr.payment_method AS methodPayment,
      orderr.quantity_bean_use AS quantityBeanUse,
      store.name AS shopName,
      store.address AS shopAddress,
      voucher.name AS voucherName,
      voucher.value
    FROM orderr
    LEFT JOIN store ON store.id = orderr.store_id
    LEFT JOIN voucher ON voucher.id = orderr.voucher_id
    WHERE orderr.id = $orderId";
  $data1 = mysqli_query($connect, $query1);
  if ($data1) {
    $row1 = mysqli_fetch_assoc($data1);
    $value = $row1['value'];
    $quantityBeanUse = $row1['quantityBeanUse'];
    $subTotal = $row1['subTotal'];
    $row1['isDelivery'] = (bool)$row1['isDelivery'];
    if ($subTotal !== null) {
      if ($value == null && $quantityBeanUse == null) {
        $row1['discount'] = null;
      } else {
        if ($value != null) {
          if ($value < 1) {
            $row1['discount'] = $value * $subTotal;
          } else {
            $row1['discount'] = $value;
          }
        }
        if ($quantityBeanUse !== null) {
          $row1['discount'] = $quantityBeanUse * 1000;
        }
      }
      unset($row1['value']);
      $query2 = "SELECT 
          status_order.name,
          status_order.description,
          status_order.image,
          order_status_order.time AS dateTime
        FROM order_status_order
        JOIN status_order ON status_order.id = order_status_order.status_id
        WHERE order_status_order.order_id = $orderId
        ORDER BY order_status_order.time ASC";
      $data2 = mysqli_query($connect, $query2);
      $result2 = [];
      while ($row2 = mysqli_fetch_assoc($data2)) {
        $result2[] = $row2;
      }
      $query3 = "SELECT 
          order_drink.id AS orderDrinkId,
          drink.id,
          drink.picture,
          drink.name,
          drink_category.name AS drinkCategory,
          order_drink.size,
          order_drink.note,
          order_drink.quantity AS count,
          drink_price.price AS priceSize
        FROM order_drink
        JOIN drink ON drink.id = order_drink.drink_id
        JOIN drink_category ON drink.category_id = drink_category.id
        LEFT JOIN drink_price 
          ON drink_price.drink_id = drink.id 
          AND (
            (drink_price.size IS NULL AND order_drink.size IS NULL) 
            OR (drink_price.size = order_drink.size)
          )
        WHERE order_drink.order_id = $orderId";
      $data3 = mysqli_query($connect, $query3);
      $result3 = [];
      while ($row3 = mysqli_fetch_assoc($data3)) {
        $orderDrinkId = $row3['orderDrinkId'];
        if ($orderDrinkId != null) {
          $count = $row3['count'];
          if ($count != null) {
            $priceSize = $row3['priceSize'];
            if ($priceSize != null) {
              $query4 = "SELECT 
            topping.name, 
            topping.price
          FROM order_drink_topping
          JOIN topping ON topping.id = order_drink_topping.topping_id
          WHERE order_drink_topping.order_drink_id = $orderDrinkId";
              $data4 = mysqli_query($connect, $query4);
              $result4 = [];
              $totalToppingPrice = 0;
              while ($row4 = mysqli_fetch_assoc($data4)) {
                $result4[] = $row4['name'];
                $totalToppingPrice += $row4['price'];
              }
              $row3['listTopping'] = $result4;
              $row3['price'] = ($priceSize + $totalToppingPrice) * $count;
              unset($row3['orderDrinkId']);
              unset($row3['priceSize']);
              $result3[] = $row3;
            } else {
              $response = [
                "status" => "fail: unknown error",
                "result" => []
              ];
            }
          } else {
            $response = [
              "status" => "fail: unknown error",
              "result" => []
            ];
          }
        } else {
          $response = [
            "status" => "fail: unknown error",
            "result" => []
          ];
        }
      }
      $row1['listStatus'] = $result2;
      $row1['listDrinkOrder'] = $result3;
      $response = [
        "status" => "success",
        "result" => [$row1]
      ];
    } else {
      $response = [
        "status" => "fail: unknown error",
        "result" => []
      ];
    }
  }
} catch (Exception $e) {
  $response = [
    "status" => "error: " . $e->getMessage(),
    "result" => []
  ];
}
echo json_encode($response);
