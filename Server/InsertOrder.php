<?php
ini_set('display_errors', 0);
error_reporting(0);
include "Connect.php";
$userId = $_POST["userId"];
$name = $_POST["name"];
$phone = $_POST["phone"];
$address = $_POST["address"];
$dateTime = $_POST["dateTime"];
$quantityBeanUse = $_POST["quantityBeanUse"];
$paymentMethod = $_POST["paymentMethod"];
$shopId = $_POST["shopId"];
$isDelivery = $_POST["isDelivery"];
$deliveryFee = $_POST["deliveryFee"];
$subTotal = $_POST["subTotal"];
$totalPrice = $_POST["totalPrice"];
$voucherId = $_POST["voucherId"];
$paymentBillId = $_POST["paymentBillId"];
$listDrinkOrder = $_POST["listDrinkOrder"];

$name = ($name === "null" || $name === null || $name === "") ? "NULL" : "'$name'";
$phone = ($phone === "null" || $phone === null || $phone === "") ? "NULL" : "'$phone'";
$address = ($address === "null" || $address === null || $address === "") ? "NULL" : "'$address'";
$quantityBeanUseSQL = ($quantityBeanUse === "null" || $quantityBeanUse === null || $quantityBeanUse === "") ? "NULL" : "'$quantityBeanUse'";
$shopId = ($shopId === "null" || $shopId === null || $shopId === "") ? "NULL" : "'$shopId'";
$voucherIdSQL = ($voucherId === "null" || $voucherId === null || $voucherId === "") ? "NULL" : "'$voucherId'";
$paymentBillId = ($paymentBillId === "null" || $paymentBillId === null || $paymentBillId === "") ? "NULL" : "'$paymentBillId'";
$isDelivery = ($isDelivery === "true" || $isDelivery === true || $isDelivery == 1) ? 1 : 0;

$allSuccess = true;

$query1 = "INSERT INTO orderr VALUES (null,'$userId',$name,$phone,$address,'$dateTime',$quantityBeanUseSQL,
'$paymentMethod',$shopId,'$isDelivery','$deliveryFee','$subTotal','$totalPrice',$voucherIdSQL,$paymentBillId)";
try {
    $data1 = mysqli_query($connect, $query1);
    if ($data1) {
        $query2 = "SELECT id FROM orderr WHERE customer_id = '$userId' ORDER BY id DESC LIMIT 1";
        $data2 = mysqli_query($connect, $query2);
        if ($data2) {
            $row2 = mysqli_fetch_assoc($data2);
            $orderId = $row2['id'];
            if ($orderId != null) {
                $query7 = "INSERT INTO order_status_order VALUES (null,'$orderId',1,'$dateTime')";
                $data7 = mysqli_query($connect, $query7);
                if ($data7) {
                    $listDrink = json_decode($listDrinkOrder, true);
                    foreach ($listDrink as $drinkOrder) {
                        $note = (!isset($drinkOrder['note']) || $drinkOrder['note'] === null || $drinkOrder['note'] === "null") ? "NULL" : "'" . addslashes($drinkOrder['note']) . "'";
                        $size = (!isset($drinkOrder['size']) || $drinkOrder['size'] === null || $drinkOrder['size'] === "null") ? "NULL" : "'" . addslashes($drinkOrder['size']) . "'";

                        $query3 = "INSERT INTO order_drink VALUES (null,'$orderId',{$drinkOrder['id']},{$drinkOrder['count']},$note,$size)";
                        $data3 = mysqli_query($connect, $query3);
                        if ($data3) {
                            $query4 = "SELECT id FROM order_drink WHERE order_id = '$orderId' ORDER BY id DESC LIMIT 1";
                            $data4 = mysqli_query($connect, $query4);
                            if ($data4) {
                                $row4 = mysqli_fetch_assoc($data4);
                                $orderDrinkId = $row4["id"];
                                if ($orderDrinkId != null) {
                                    $listTopping = $drinkOrder['listTopping'];
                                    if (!empty($listTopping) && is_array($listTopping)) {
                                        foreach ($listTopping as $toppingName) {
                                            $toppingName = addslashes($toppingName);
                                            $query5 = "SELECT id FROM topping WHERE name = '$toppingName' LIMIT 1";
                                            $data5 = mysqli_query($connect, $query5);
                                            if ($data5) {
                                                $row5 = mysqli_fetch_assoc($data5);
                                                $toppingId = $row5["id"];
                                                if ($toppingId != null) {
                                                    $query6 = "INSERT INTO order_drink_topping VALUES (null,'$orderDrinkId','$toppingId')";
                                                    $data6 = mysqli_query($connect, $query6);
                                                    if ($data6) {
                                                    } else {
                                                        $allSuccess = false;
                                                        $response = [
                                                            "status" => "fail: error when insert to order_drink_topping",
                                                            "result" => []
                                                        ];
                                                    }
                                                } else {
                                                    $allSuccess = false;
                                                    $response = [
                                                        "status" => "fail: toppingId = null",
                                                        "result" => []
                                                    ];
                                                }
                                            } else {
                                                $allSuccess = false;
                                                $response = [
                                                    "status" => "fail: error when get toppingId",
                                                    "result" => []
                                                ];
                                            }
                                        }
                                    }
                                } else {
                                    $allSuccess = false;
                                    $response = [
                                        "status" => "fail: error orderDrinkId = null",
                                        "result" => []
                                    ];
                                }
                            } else {
                                $allSuccess = false;
                                $response = [
                                    "status" => "fail: error when get id in order_drink",
                                    "result" => []
                                ];
                            }
                        } else {
                            $allSuccess = false;
                            $response = [
                                "status" => "fail: error when insert into order drink",
                                "result" => []
                            ];
                        }
                    }
                    if ($quantityBeanUse !== null && $quantityBeanUse !== "null" && $quantityBeanUse !== "") {
                        $query9 = "UPDATE user SET current_bean = 0 WHERE id ='$userId'";
                        $data9 = mysqli_query($connect, $query9);
                        if ($data9) {
                        } else {
                            $allSuccess = false;
                            $response = [
                                "status" => "fail: error when update currentBean",
                                "result" => []
                            ];
                        }
                    }
                    if ($voucherId !== null && $voucherId !== "null" && $voucherId !== "") {
                        $query10 = "SELECT remain_quantity FROM voucher WHERE id =$voucherId";
                        $data10 = mysqli_query($connect, $query10);
                        if ($data10) {
                            $row10 = mysqli_fetch_assoc($data10);
                            $remainQuantity = $row10["remain_quantity"];
                            if ($remainQuantity != null) {
                                $query11 = "UPDATE voucher SET remain_quantity = $remainQuantity-1 WHERE id =$voucherId";
                                $data11 = mysqli_query($connect, $query11);
                                if ($data11) {
                                } else {
                                    $allSuccess = false;
                                    $response = [
                                        "status" => "fail: error when update quantity voucher remain",
                                        "result" => []
                                    ];
                                }
                            } else {
                                $allSuccess = false;
                                $response = [
                                    "status" => "fail: error when remain quantity = null",
                                    "result" => []
                                ];
                            }
                        } else {
                            $allSuccess = false;
                            $response = [
                                "status" => "fail: error when get remain_quantity from voucher",
                                "result" => []
                            ];
                        }
                    }
                } else {
                    $allSuccess = false;
                    $response = [
                        "status" => "fail: error when insert into order_status_order",
                        "result" => []
                    ];
                }
            } else {
                $allSuccess = false;
                $response = [
                    "status" => "fail: error when orderId = null",
                    "result" => []
                ];
            }
        } else {
            $allSuccess = false;
            $response = [
                "status" => "fail: error when getId from orderr",
                "result" => []
            ];
        }
    } else {
        $allSuccess = false;
        $response = [
            "status" => "fail: error when insert into orderr",
            "result" => []
        ];
    }
    if ($allSuccess) {
        sleep(3);
        $status = ($paymentMethod == "Tiền mặt") ? 2 : 3;
        date_default_timezone_set("Asia/Ho_Chi_Minh");
        $time = date("Y-m-d H:i:s");
        $query8 = "INSERT INTO order_status_order VALUES (null,'$orderId',$status,'$time')";
        $data8 = mysqli_query($connect, $query8);
        if ($data8) {
            $response = [
                "status" => "success",
                "result" => [(int)$orderId]
            ];
        } else {
            $response = [
                "status" => "fail: error when insert new status",
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
