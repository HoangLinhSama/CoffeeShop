<?php
ini_set('display_errors', 0);
error_reporting(0);
try {
    $filePath = "picture/avatar/";
    $filePath = $filePath . basename($_FILES['pictureAvatar']['name']);
    $result = array();
    if (move_uploaded_file($_FILES['pictureAvatar']['tmp_name'], $filePath)) {
        $result[] = $_FILES['pictureAvatar']['name'];
        $response = [
            "status" => "success",
            "result" => $result
        ];
    } else {
        $response = [
            "status" => "fail",
            "result" => $result
        ];
    }
} catch (Throwable $e) {
    $response = [
        "status" => "error: " . $e->getMessage(),
        "result" => []
    ];
}
echo json_encode($response);
