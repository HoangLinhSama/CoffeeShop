-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 17, 2025 lúc 06:59 PM
-- Phiên bản máy phục vụ: 10.4.25-MariaDB
-- Phiên bản PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `coffee_shop`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `drink`
--

CREATE TABLE `drink` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `category_id` int(11) NOT NULL,
  `description` text NOT NULL,
  `picture` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `drink`
--

INSERT INTO `drink` (`id`, `name`, `category_id`, `description`, `picture`) VALUES
(1, 'Bạc Xỉu', 1, 'Bạc xỉu chính là \"Ly sữa trắng kèm một chút cà phê\". Thức uống này rất phù hợp những ai vừa muốn trải nghiệm chút vị đắng của cà phê vừa muốn thưởng thức vị ngọt béo ngậy từ sữa.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/bacxiu.png'),
(2, 'Phin Sữa Tươi Bánh Flan', 1, 'Tỉnh tức thì cùng cà phê Robusta pha phin đậm đà và bánh flan núng nính. Uống là tỉnh, ăn là dính, xứng đáng là highlight trong ngày của bạn.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/phinsuatuoibanhflan.png'),
(4, 'Trà Xanh Espresso Marble', 4, 'Cho ngày thêm tươi, tỉnh, êm, mượt với Trà Xanh Espresso Marble. Đây là sự mai mối bất ngờ giữa trà xanh Tây Bắc vị mộc và cà phê Arabica Đà Lạt. Muốn ngày thêm chút highlight, nhớ tìm đến sự bất ngờ này bạn nhé!', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/traxanhespressomarble.jpg'),
(5, 'Đường Đen Sữa Đá', 1, 'Nếu chuộng vị cà phê đậm đà, bùng nổ và thích vị đường đen ngọt thơm, Đường Đen Sữa Đá đích thị là thức uống dành cho bạn. Không chỉ giúp bạn tỉnh táo buổi sáng, Đường Đen Sữa Đá còn hấp dẫn đến ngụm cuối cùng bởi thạch cà phê giòn dai, nhai cực cuốn. - Khuấy đều trước khi sử dụng', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/duongdensuada.png'),
(7, 'Cà Phê Sữa Đá', 1, 'Cà phê Đắk Lắk nguyên chất được pha phin truyền thống kết hợp với sữa đặc tạo nên hương vị đậm đà, hài hòa giữa vị ngọt đầu lưỡi và vị đắng thanh thoát nơi hậu vị.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/cafesuada.png'),
(8, 'Cà Phê Sữa Nóng', 1, 'Cà phê được pha phin truyền thống kết hợp với sữa đặc tạo nên hương vị đậm đà, hài hòa giữa vị ngọt đầu lưỡi và vị đắng thanh thoát nơi hậu vị.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/cafesuanong.png'),
(9, 'Bạc Sỉu Nóng', 1, 'Bạc sỉu chính là \"Ly sữa trắng kèm một chút cà phê\". Thức uống này rất phù hợp những ai vừa muốn trải nghiệm chút vị đắng của cà phê vừa muốn thưởng thức vị ngọt béo ngậy từ sữa.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/bacsiunong.jpg'),
(10, 'Cà Phê Đen Đá', 1, 'Không ngọt ngào như Bạc sỉu hay Cà phê sữa, Cà phê đen mang trong mình phong vị trầm lắng, thi vị hơn. Người ta thường phải ngồi rất lâu mới cảm nhận được hết hương thơm ngào ngạt, phảng phất mùi cacao và cái đắng mượt mà trôi tuột xuống vòm họng.\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/cafedenda.jpg'),
(11, 'Cà Phê Đen Nóng', 1, 'Không ngọt ngào như Bạc sỉu hay Cà phê sữa, Cà phê đen mang trong mình phong vị trầm lắng, thi vị hơn. Người ta thường phải ngồi rất lâu mới cảm nhận được hết hương thơm ngào ngạt, phảng phất mùi cacao và cái đắng mượt mà trôi tuột xuống vòm họng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/cafedennong.png'),
(13, 'Caramel Macchiato Đá', 1, 'Khuấy đều trước khi sử dụng Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/caramelmacchiatoda.png'),
(14, 'Caramel Macchiato Nóng', 1, 'Caramel Macchiato sẽ mang đến một sự ngạc nhiên thú vị khi vị thơm béo của bọt sữa, sữa tươi, vị đắng thanh thoát của cà phê Espresso hảo hạng và vị ngọt đậm của sốt caramel được gói gọn trong một tách cà phê.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/caramelmacchiatonong.jpg'),
(15, 'Latte Đá', 1, 'Một sự kết hợp tinh tế giữa vị đắng cà phê Espresso nguyên chất hòa quyện cùng vị sữa nóng ngọt ngào, bên trên là một lớp kem mỏng nhẹ tạo nên một tách cà phê hoàn hảo về hương vị lẫn nhãn quan.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/latteda.jpg\r\n'),
(16, 'Latte Nóng', 1, 'Một sự kết hợp tinh tế giữa vị đắng cà phê Espresso nguyên chất hòa quyện cùng vị sữa nóng ngọt ngào, bên trên là một lớp kem mỏng nhẹ tạo nên một tách cà phê hoàn hảo về hương vị lẫn nhãn quan.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/lattenong.png'),
(17, 'Americano Đá', 1, 'Americano được pha chế bằng cách pha thêm nước với tỷ lệ nhất định vào tách cà phê Espresso, từ đó mang lại hương vị nhẹ nhàng và giữ trọn được mùi hương cà phê đặc trưng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/americanoda.png'),
(18, 'Americano Nóng', 1, 'Americano được pha chế bằng cách pha thêm nước với tỷ lệ nhất định vào tách cà phê Espresso, từ đó mang lại hương vị nhẹ nhàng và giữ trọn được mùi hương cà phê đặc trưng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/americanonong.png'),
(19, 'Cappuccino Đá', 1, 'Capuchino là thức uống hòa quyện giữa hương thơm của sữa, vị béo của bọt kem cùng vị đậm đà từ cà phê Espresso. Tất cả tạo nên một hương vị đặc biệt, một chút nhẹ nhàng, trầm lắng và tinh tế.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/cappuccinoda.png\r\n'),
(20, 'Cappuccino Nóng', 1, 'Capuchino là thức uống hòa quyện giữa hương thơm của sữa, vị béo của bọt kem cùng vị đậm đà từ cà phê Espresso. Tất cả tạo nên một hương vị đặc biệt, một chút nhẹ nhàng, trầm lắng và tinh tế', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/cappuccinonong.png'),
(21, 'Espresso Đá', 1, 'Một tách Espresso nguyên bản được bắt đầu bởi những hạt Arabica chất lượng, phối trộn với tỉ lệ cân đối hạt Robusta, cho ra vị ngọt caramel, vị chua dịu và sánh đặc.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/espressoda.png'),
(22, 'Espresso Nóng', 1, 'Một tách Espresso nguyên bản được bắt đầu bởi những hạt Arabica chất lượng, phối trộn với tỉ lệ cân đối hạt Robusta, cho ra vị ngọt caramel, vị chua dịu và sánh đặc.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/espressonong.png'),
(23, 'Cold Brew Phúc Bồn Tử', 1, 'Vị chua ngọt của trái phúc bồn tử, làm dậy lên hương vị trái cây tự nhiên vốn sẵn có trong hạt cà phê, hòa quyện thêm vị đăng đắng, ngọt dịu nhẹ nhàng của Cold Brew 100% hạt Arabica Cầu Đất để mang đến một cách thưởng thức cà phê hoàn toàn mới, vừa thơm lừng hương cà phê quen thuộc, vừa nhẹ nhàng và thanh mát bởi hương trái cây đầy thú vị.\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/coldbrewphucbontu.png'),
(24, 'Cold Brew Sữa Tươi', 1, 'Thanh mát và cân bằng với hương vị cà phê nguyên bản 100% Arabica Cầu Đất cùng sữa tươi thơm béo cho từng ngụm tròn vị, hấp dẫn.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/coldbrewsuatuoi.png'),
(25, 'Cold Brew Truyền Thống', 1, 'Tại The Coffee House, Cold Brew được ủ và phục vụ mỗi ngày từ 100% hạt Arabica Cầu Đất với hương gỗ thông, hạt dẻ, nốt sô-cô-la đặc trưng, thoang thoảng hương khói nhẹ giúp Cold Brew giữ nguyên vị tươi mới.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/coldbrewtruyenthong.png'),
(26, 'Oolong Tứ Quý Kim Quất Trân Châu', 2, 'Đậm hương trà, sảng khoái du xuân cùng Oolong Tứ Quý Kim Quất Trân Châu. Vị nước cốt kim quất tươi chua ngọt, thêm trân châu giòn dai.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/oolongtuquykimquattranchau.png'),
(27, 'Oolong Tứ Quý Vải', 2, 'Đậm hương trà, thanh mát sắc xuân với Oolong Tứ Quý Vải. Cảm nhận hương hoa đầu mùa, hòa quyện cùng vị vải chín mọng căng tràn sức sống.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/oolongtuquyvai.png'),
(28, 'Trà Long Nhãn Hạt Sen', 2, 'Thức uống mang hương vị của nhãn, của sen, của trà Oolong đầy thanh mát cho tất cả các thành viên trong dịp Tết này. An lành, thư thái và đậm đà chính là những gì The Coffee House mong muốn gửi trao đến bạn và gia đình.\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/tralongnhanhatsen.png'),
(29, 'Trà Đào Cam Sả Đá', 2, 'Vị thanh ngọt của đào, vị chua dịu của Cam Vàng nguyên vỏ, vị chát của trà đen tươi được ủ mới mỗi 4 tiếng, cùng hương thơm nồng đặc trưng của sả chính là điểm sáng làm nên sức hấp dẫn của thức uống này.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/tradaocamsada.png'),
(30, 'Trà Đào Cam Sả Nóng', 2, 'Vị thanh ngọt của đào, vị chua dịu của Cam Vàng nguyên vỏ, vị chát của trà đen tươi được ủ mới mỗi 4 tiếng, cùng hương thơm nồng đặc trưng của sả chính là điểm sáng làm nên sức hấp dẫn của thức uống này.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/tradaocamsanong.png'),
(33, 'Trà Đen Macchiato', 3, 'Trà đen được ủ mới mỗi ngày, giữ nguyên được vị chát mạnh mẽ đặc trưng của lá trà, phủ bên trên là lớp Macchiato \"homemade\" bồng bềnh quyến rũ vị phô mai mặn mặn mà béo béo.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/tradenmacchiato.png'),
(34, 'Hồng Trà Sữa Trân Châu', 3, 'Thêm chút ngọt ngào cho ngày mới với hồng trà nguyên lá, sữa thơm ngậy được cân chỉnh với tỉ lệ hoàn hảo, cùng trân châu trắng dai giòn có sẵn để bạn tận hưởng từng ngụm trà sữa ngọt ngào thơm ngậy thiệt đã.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hongtrasuatranchau.png'),
(35, 'Hồng Trà Sữa Nóng', 3, 'Từng ngụm trà chuẩn gu ấm áp, đậm đà beo béo bởi lớp sữa tươi chân ái hoà quyện. Trà đen nguyên lá âm ấm dịu nhẹ, quyện cùng lớp sữa thơm béo khó lẫn - hương vị ấm áp chuẩn gu trà, cho từng ngụm nhẹ nhàng, ngọt dịu lưu luyến mãi nơi cuống họng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hongtrasuanong.png'),
(36, 'Trà sữa Oolong Nướng Trân Châu', 3, 'Hương vị chân ái đúng gu đậm đà với trà oolong được “sao” (nướng) lâu hơn cho hương vị đậm đà, hòa quyện với sữa thơm béo mang đến cảm giác mát lạnh, lưu luyến vị trà sữa đậm đà nơi vòm họng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/trasuaoolongnuongtranchau.png'),
(44, 'Hi Tea Đào Kombucha', 2, 'Trà hoa Hibiscus 0% caffeine chua nhẹ, kết hợp cùng trà lên men Kombucha hoàn toàn tự nhiên và Đào thanh mát tạo nên Hi-Tea Đào Kombucha chua ngọt cực cuốn. Đặc biệt Kombucha Detox giàu axit hữu cơ, Đào nhiều chất xơ giúp thanh lọc cơ thể và hỗ trợ giảm cân hiệu quả. Lưu ý: Khuấy đều trước khi dùng', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hiteadaokombucha.png'),
(45, 'Hi Tea Yuzu Kombucha', 2, 'Trà hoa Hibiscus 0% caffeine thanh mát, hòa quyện cùng trà lên men Kombucha 100% tự nhiên và mứt Yuzu Marmalade (quýt Nhật) mang đến hương vị chua chua lạ miệng. Đặc biệt, Hi-Tea Yuzu Kombucha cực hợp cho team thích detox, muốn sáng da nhờ Kombucha Detox nhiều chất chống oxy hoá cùng Yuzu giàu vitamin C. Lưu ý: Khuấy đều trước khi dùng', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hiteayuzukombucha.png'),
(46, 'Hi Tea Yuzu Trân Châu', 2, 'Không chỉ nổi bật với sắc đỏ đặc trưng từ trà hoa Hibiscus, Hi-Tea Yuzu còn gây ấn tượng với topping Yuzu (quýt Nhật) lạ miệng, kết hợp cùng trân châu trắng dai giòn sần sật, nhai vui vui.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hiteayuzutranchau.png'),
(47, 'Hi Tea Vải', 2, 'Chút ngọt ngào của Vải, mix cùng vị chua thanh tao từ trà hoa Hibiscus, mang đến cho bạn thức uống đúng chuẩn vừa ngon, vừa healthy.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hiteavai.png'),
(48, 'Hi Tea Đào', 2, 'Sự kết hợp ăn ý giữa Đào cùng trà hoa Hibiscus, tạo nên tổng thể hài hoà dễ gây “thương nhớ” cho team thích món thanh mát, có vị chua nhẹ.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/hiteadao.png'),
(51, 'Trà Xanh Latte', 4, 'Không cần đến Tây Bắc mới cảm nhận được sự trong trẻo của núi rừng, khi Trà Xanh Latte từ Nhà được chắt lọc từ những búp trà xanh mướt, ủ mình trong sương sớm. Trà xanh Tây Bắc vị thanh, chát nhẹ hoà cùng sữa tươi nguyên kem ngọt béo tạo nên hương vị dễ uống, dễ yêu. Đây là thức uống healthy, giúp bạn tỉnh táo một cách êm mượt, xoa dịu những căng thẳng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/traxanhlatte.png'),
(52, 'Trà Xanh Latte (Nóng)', 4, 'Trà Xanh Latte (Nóng) là phiên bản rõ vị trà nhất. Nhấp một ngụm, bạn chạm ngay vị trà xanh Tây Bắc chát nhẹ hoà cùng sữa nguyên kem thơm béo, đọng lại hậu vị ngọt ngào, êm dịu. Không chỉ là thức uống tốt cho sức khoẻ, Trà Xanh Latte (Nóng) còn là cái ôm ấm áp của đồng bào Tây Bắc gửi cho người miền xuôi.\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/traxanhlattenong.png'),
(53, 'Trà Xanh Đường Đen', 4, 'Trà Xanh Đường Đen với hiệu ứng phân tầng đẹp mắt, như phác hoạ núi đồi Tây Bắc bảng lảng trong sương mây, thấp thoáng những nương chè xanh ngát. Từng ngụm là sự hài hoà từ trà xanh đậm đà, đường đen ngọt ngào, sữa tươi thơm béo. Khuấy đều trước khi dùng, để thưởng thức đúng vị\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/traxanhduongden.png'),
(54, 'Chocolate Nóng', 5, 'Bột chocolate nguyên chất hoà cùng sữa tươi béo ngậy. Vị ngọt tự nhiên, không gắt cổ, để lại một chút đắng nhẹ, cay cay trên đầu lưỡi.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/chocolatenong.png'),
(55, 'Chocolate Đá', 5, 'Bột chocolate nguyên chất hoà cùng sữa tươi béo ngậy, ấm nóng. Vị ngọt tự nhiên, không gắt cổ, để lại một chút đắng nhẹ, cay cay trên đầu lưỡi.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/chocolateda.png'),
(57, 'Frosty Phin Gato', 6, 'Đá Xay Frosty Phin-Gato là lựa chọn không thể bỏ lỡ cho tín đồ cà phê. Cà phê nguyên chất pha phin truyền thống, thơm đậm đà, đắng mượt mà, quyện cùng kem sữa béo ngậy và đá xay mát lạnh. Nhân đôi vị cà phê nhờ có thêm thạch cà phê đậm đà, giòn dai. Thức uống khơi ngay sự tỉnh táo tức thì. Lưu ý: Khuấy đều phần đá xay trước khi dùng', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostyphingato.png'),
(58, 'Frosty Cà Phê Đường Đen', 6, 'Đá Xay Frosty Cà Phê Đường Đen mát lạnh, sảng khoái ngay từ ngụm đầu tiên nhờ sự kết hợp vượt chuẩn vị quen giữa Espresso đậm đà và Đường Đen ngọt thơm. Đặc biệt, whipping cream beo béo cùng thạch cà phê giòn dai, đậm vị nhân đôi sự hấp dẫn, khơi bừng sự hứng khởi trong tích tắc.\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostycafeduongden.png'),
(59, 'Frosty Bánh Kem Dâu', 6, 'Bồng bềnh như một đám mây, Đá Xay Frosty Bánh Kem Dâu vừa ngon mắt vừa chiều vị giác bằng sự ngọt ngào. Bắt đầu bằng cái chạm môi với lớp kem whipping cream, cảm nhận ngay vị beo béo lẫn sốt dâu thơm lừng. Sau đó, hút một ngụm là cuốn khó cưỡng bởi đá xay mát lạnh quyện cùng sốt dâu ngọt dịu. Lưu ý: Khuấy đều phần đá xay trước khi dùng', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostybanhkemdau.png'),
(60, 'Frosty Choco Chip', 6, 'Đá Xay Frosty Choco Chip, thử là đã! Lớp whipping cream bồng bềnh, beo béo lại có thêm bột sô cô la và sô cô la chip trên cùng. Gấp đôi vị ngon với sô cô la thật xay với đá sánh mịn, đậm đà đến tận ngụm cuối cùng.\r\n\r\n', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostychocochip.png'),
(61, 'Choco Sữa Hạnh Nhân Kem Cheese', 5, 'Cacao sữa hạnh nhân thơm bùi sánh đôi cùng kem cheese beo béo, ngọt ngào gấp bội. Mùa hội, HẠNH phúc NHÂN đôi khi thưởng thức bên người thương bạn nhé!', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostysuahanhnhankem.png'),
(62, 'Frosty Caramel Arabica', 6, 'Caramel ngọt thơm quyện cùng cà phê Arabica Cầu Đất đượm hương gỗ thông, hạt dẻ và nốt sô cô la đặc trưng tạo nên Đá Xay Frosty Caramel Arabica độc đáo chỉ có tại Nhà. Cực cuốn với lớp whipping cream béo mịn, thêm thạch cà phê giòn ngon bắt miệng.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostycaramelarbica.png'),
(63, 'Frosty Trà Xanh', 4, 'Đá Xay Frosty Trà Xanh như lời mời mộc mạc, ghé thăm Tây Bắc vào những ngày tiết trời se lạnh, núi đèo mây phủ. Vị chát dịu, ngọt thanh của trà xanh Tây Bắc kết hợp đá xay sánh mịn, whipping cream bồng bềnh và bột trà xanh trên cùng thêm đậm vị. Đây không chỉ là thức uống mát lạnh bật mood, mà còn tốt cho sức khoẻ nhờ giàu vitamin và các chất chống oxy hoá.', 'http://192.168.1.8/CoffeeShop/Server/picture/drink/frostytraxanh.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `drink_category`
--

CREATE TABLE `drink_category` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `drink_category`
--

INSERT INTO `drink_category` (`id`, `name`) VALUES
(1, 'Cafe'),
(2, 'Trà trái cây'),
(3, 'Trà sữa'),
(4, 'Trà xanh'),
(5, 'Chocolate'),
(6, 'Đá xay');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `drink_price`
--

CREATE TABLE `drink_price` (
  `id` int(11) NOT NULL,
  `drink_id` int(11) NOT NULL,
  `size` varchar(255) DEFAULT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `drink_price`
--

INSERT INTO `drink_price` (`id`, `drink_id`, `size`, `price`) VALUES
(1, 1, 'Nhỏ', 29000),
(2, 1, 'Vừa', 39000),
(3, 1, 'Lớn', 45000),
(4, 2, 'Nhỏ', 49000),
(6, 2, 'Vừa', 55000),
(7, 2, 'Lớn', 65000),
(8, 4, 'Nhỏ', 49000),
(9, 4, 'Vừa', 55000),
(10, 4, 'Lớn', 65000),
(11, 5, 'Nhỏ', 45000),
(12, 5, 'Vừa', 49000),
(13, 5, 'Lớn', 55000),
(17, 7, 'Nhỏ', 29000),
(18, 7, 'Vừa', 39000),
(19, 7, 'Lớn', 45000),
(20, 8, NULL, 39000),
(21, 9, NULL, 39000),
(22, 10, 'Nhỏ', 29000),
(23, 10, 'Vừa', 39000),
(24, 10, 'Lớn', 45000),
(25, 11, NULL, 39000),
(28, 13, 'Nhỏ', 55000),
(29, 13, 'Vừa', 59000),
(30, 14, 'Nhỏ', 55000),
(31, 14, 'Vừa', 59000),
(32, 15, 'Nhỏ', 55000),
(33, 15, 'Vừa', 59000),
(34, 16, 'Nhỏ', 55000),
(35, 16, 'Vừa', 59000),
(36, 17, 'Nhỏ', 45000),
(37, 17, 'Vừa', 49000),
(38, 18, 'Nhỏ', 45000),
(39, 18, 'Vừa', 49000),
(40, 19, 'Nhỏ', 55000),
(41, 19, 'Vừa', 59000),
(42, 20, 'Nhỏ', 55000),
(43, 20, 'Vừa', 59000),
(44, 21, NULL, 49000),
(45, 22, 'Nhỏ', 45000),
(46, 22, 'Vừa', 49000),
(47, 23, 'Vừa', 49000),
(48, 23, 'Lớn', 55000),
(49, 24, 'Vừa', 49000),
(50, 24, 'Lớn', 55000),
(51, 25, 'Vừa', 45000),
(52, 25, 'Lớn', 49000),
(53, 26, 'Nhỏ', 49000),
(54, 26, 'Vừa', 59000),
(55, 26, 'Lớn', 65000),
(56, 27, 'Nhỏ', 49000),
(57, 27, 'Vừa', 59000),
(58, 27, 'Lớn', 65000),
(59, 28, 'Nhỏ', 49000),
(60, 28, 'Vừa', 59000),
(61, 28, 'Lớn', 65000),
(62, 29, 'Nhỏ', 49000),
(63, 29, 'Vừa', 55000),
(64, 29, 'Lớn', 65000),
(65, 30, NULL, 59000),
(70, 33, 'Vừa', 55000),
(71, 33, 'Lớn', 59000),
(72, 34, 'Vừa', 55000),
(73, 34, 'Lớn', 59000),
(74, 35, NULL, 55000),
(75, 36, 'Vừa', 55000),
(76, 36, 'Lớn', 59000),
(91, 44, 'Nhỏ', 59000),
(92, 44, 'Vừa', 65000),
(93, 44, 'Lớn', 69000),
(94, 45, 'Nhỏ', 59000),
(95, 45, 'Vừa', 65000),
(96, 45, 'Lớn', 69000),
(97, 46, 'Nhỏ', 49000),
(98, 46, 'Vừa', 59000),
(99, 46, 'Lớn', 65000),
(100, 47, 'Nhỏ', 49000),
(101, 47, 'Vừa', 59000),
(102, 47, 'Lớn', 65000),
(103, 48, 'Nhỏ', 49000),
(104, 48, 'Vừa', 59000),
(105, 48, 'Lớn', 65000),
(111, 51, 'Nhỏ', 45000),
(112, 51, 'Vừa', 49000),
(113, 51, 'Lớn', 55000),
(114, 52, 'Nhỏ', 45000),
(115, 52, 'Vừa', 49000),
(116, 53, 'Nhỏ', 55000),
(117, 53, 'Vừa', 65000),
(118, 53, 'Lớn', 69000),
(119, 54, NULL, 55000),
(120, 55, NULL, 55000),
(125, 57, 'Nhỏ', 55000),
(126, 57, 'Vừa', 65000),
(127, 57, 'Lớn', 69000),
(128, 58, 'Nhỏ', 59000),
(129, 58, 'Vừa', 65000),
(130, 58, 'Lớn', 69000),
(131, 59, 'Nhỏ', 59000),
(132, 59, 'Vừa', 65000),
(133, 59, 'Lớn', 69000),
(134, 60, 'Nhỏ', 59000),
(135, 60, 'Vừa', 65000),
(136, 60, 'Lớn', 69000),
(138, 61, NULL, 59000),
(139, 62, 'Nhỏ', 59000),
(140, 62, 'Vừa', 65000),
(141, 62, 'Lớn', 69000),
(142, 63, 'Nhỏ', 59000),
(143, 63, 'Vừa', 65000),
(144, 63, 'Lớn', 69000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `drink_topping`
--

CREATE TABLE `drink_topping` (
  `id` int(11) NOT NULL,
  `drink_id` int(11) NOT NULL,
  `topping_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `drink_topping`
--

INSERT INTO `drink_topping` (`id`, `drink_id`, `topping_id`) VALUES
(1, 1, 8),
(2, 1, 1),
(3, 1, 9),
(4, 2, 8),
(5, 2, 1),
(6, 2, 9),
(7, 4, 8),
(8, 4, 1),
(9, 4, 9),
(10, 5, 8),
(11, 5, 1),
(12, 5, 9),
(13, 7, 8),
(14, 7, 1),
(15, 7, 9),
(16, 8, 8),
(17, 8, 1),
(18, 8, 9),
(22, 9, 8),
(23, 9, 1),
(24, 9, 9),
(25, 10, 8),
(26, 10, 1),
(27, 10, 9),
(28, 11, 8),
(29, 11, 1),
(30, 11, 9),
(34, 13, 8),
(35, 13, 1),
(36, 13, 9),
(37, 14, 8),
(38, 14, 1),
(39, 14, 9),
(40, 15, 8),
(41, 15, 1),
(42, 15, 9),
(43, 16, 8),
(44, 16, 1),
(45, 16, 9),
(46, 17, 8),
(47, 17, 1),
(48, 17, 9),
(49, 18, 8),
(50, 18, 1),
(51, 18, 9),
(52, 19, 8),
(53, 19, 1),
(54, 19, 9),
(55, 20, 8),
(56, 20, 1),
(57, 20, 9),
(58, 21, 8),
(59, 21, 1),
(60, 21, 9),
(61, 22, 8),
(62, 22, 1),
(63, 22, 9),
(64, 23, 8),
(65, 23, 1),
(66, 23, 9),
(67, 24, 8),
(68, 24, 1),
(69, 24, 9),
(70, 25, 8),
(71, 25, 1),
(72, 25, 9),
(73, 26, 3),
(74, 26, 7),
(75, 26, 1),
(76, 27, 3),
(77, 27, 7),
(78, 27, 1),
(79, 28, 3),
(80, 28, 7),
(81, 28, 1),
(82, 29, 3),
(83, 29, 7),
(84, 29, 1),
(85, 30, 3),
(86, 30, 7),
(87, 30, 1),
(94, 33, 8),
(95, 33, 3),
(96, 33, 7),
(97, 33, 7),
(98, 33, 1),
(99, 34, 8),
(100, 34, 3),
(101, 34, 7),
(102, 34, 1),
(103, 34, 9),
(104, 35, 8),
(105, 35, 1),
(106, 36, 8),
(107, 36, 3),
(108, 36, 7),
(109, 36, 1),
(110, 36, 9),
(136, 44, 3),
(137, 44, 7),
(138, 44, 1),
(139, 45, 3),
(140, 45, 7),
(141, 45, 1),
(142, 46, 3),
(143, 46, 7),
(144, 46, 1),
(145, 47, 3),
(146, 47, 7),
(147, 47, 1),
(148, 48, 3),
(149, 48, 7),
(150, 48, 1),
(157, 51, 8),
(158, 51, 1),
(159, 52, 8),
(160, 52, 1),
(161, 53, 8),
(162, 53, 1),
(163, 55, 1),
(167, 54, 1),
(168, 54, 1),
(169, 61, 9),
(170, 61, 8),
(171, 61, 7),
(172, 61, 1),
(173, 63, 8),
(174, 63, 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `favorite_drink`
--

CREATE TABLE `favorite_drink` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `drink_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `content` text NOT NULL,
  `star` int(11) NOT NULL,
  `drink_id` int(11) NOT NULL,
  `date_time` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `feedback`
--

INSERT INTO `feedback` (`id`, `user_id`, `content`, `star`, `drink_id`, `date_time`) VALUES
(1, 19, 'Rất ngon, giá cả phù hợp', 5, 1, '2025-04-03');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `membership`
--

CREATE TABLE `membership` (
  `id` int(11) NOT NULL,
  `rank` varchar(255) NOT NULL,
  `bean` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `membership`
--

INSERT INTO `membership` (`id`, `rank`, `bean`) VALUES
(1, 'new', 0),
(2, 'bronze', 200),
(3, 'silver', 500),
(4, 'gold', 1000),
(5, 'platinum', 2000),
(7, 'diamond', 5000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `onboarding`
--

CREATE TABLE `onboarding` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `onboarding`
--

INSERT INTO `onboarding` (`id`, `title`, `description`, `image`) VALUES
(1, 'Khám phá hương vị yêu thích', 'Từ cà phê đậm đà, trà xanh thanh mát đến trà sữa béo ngậy,... chúng tôi có tất cả để bạn lựa chọn', 'http://192.168.1.8/CoffeeShop/Server/picture/onboarding/onboarding1.png'),
(2, 'Thanh toán tiện lợi, bảo mật cao', 'Hỗ trợ nhiều phương thức thanh toán điện tử giúp bạn dễ dàng hoàn tất đơn hàng chỉ trong vài giây', 'http://192.168.1.8/CoffeeShop/Server/picture/onboarding/onboarding2.png'),
(3, 'Đặt hàng dễ dàng, nhanh chóng', 'Chỉ với vài thao tác, đồ uống thơm ngon sẽ được giao tận tay bạn ngay lập tức', 'http://192.168.1.8/CoffeeShop/Server/picture/onboarding/onboarding3.png');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderr`
--

CREATE TABLE `orderr` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address` text NOT NULL,
  `date_time` datetime NOT NULL,
  `status` varchar(255) NOT NULL,
  `bean_use` int(11) NOT NULL,
  `payment` varchar(255) NOT NULL,
  `store_id` int(11) DEFAULT NULL,
  `delivery` tinyint(1) DEFAULT NULL,
  `delivery_fee` float NOT NULL,
  `total_price` float NOT NULL,
  `amount_due` float NOT NULL,
  `voucher_id` int(11) NOT NULL,
  `payment_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_detail`
--

CREATE TABLE `order_detail` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `drink_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `note` text NOT NULL,
  `size` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `order_drink_topping_detail`
--

CREATE TABLE `order_drink_topping_detail` (
  `id` int(11) NOT NULL,
  `order_detail_id` int(11) NOT NULL,
  `topping_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `policy`
--

CREATE TABLE `policy` (
  `id` int(11) NOT NULL,
  `title` text NOT NULL,
  `content` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `policy`
--

INSERT INTO `policy` (`id`, `title`, `content`) VALUES
(1, '1. Chính sách bảo mật', 'Ứng dụng thu thập thông tin như họ tên, số điện thoại, email, địa chỉ khi khách hàng đăng ký tài khoản hoặc đặt hàng.\r\nCác thông tin này chỉ được sử dụng để phục vụ nhu cầu mua hàng, giao hàng, chăm sóc khách hàng và cải thiện dịch vụ.\r\nDữ liệu cá nhân của khách hàng được mã hóa và lưu trữ an toàn.\r\nỨng dụng không chia sẻ thông tin người dùng với bên thứ ba mà không có sự đồng ý của họ.'),
(2, '2. Chính sách đặt hàng và thanh toán', 'Người dùng có thể đặt hàng trực tiếp qua ứng dụng bằng cách chọn sản phẩm, xác nhận giỏ hàng và thanh toán.\r\nSau khi đặt hàng, hệ thống sẽ gửi xác nhận qua email hoặc thông báo trong ứng dụng.\r\nHỗ trợ thanh toán bằng tiền mặt khi nhận hàng (COD), thẻ ngân hàng, ví điện tử (Momo, ZaloPay, VNPay, v.v.).\r\nCác giao dịch trực tuyến được mã hóa để đảm bảo an toàn cho người dùng.\r\nNgười dùng có thể hủy đơn trước khi quán bắt đầu pha chế.\r\nHoàn tiền áp dụng với các trường hợp đơn hàng bị lỗi hoặc không đúng yêu cầu.'),
(5, '3. Chính sách giao hàng', 'Giao hàng trong vòng 30 – 60 phút tùy khu vực.\r\nThời gian có thể thay đổi vào giờ cao điểm hoặc thời tiết xấu.\r\nPhí giao hàng được tính theo khoảng cách từ quán đến địa chỉ nhận hàng.'),
(6, '4. Chính sách khách hàng thân thiết', 'Mỗi lần mua hàng, khách hàng nhận điểm thưởng để đổi quà hoặc giảm giá.\r\nCấp bậc thành viên: gold, silver, new, diamond, platinum với ưu đãi tương ứng.\r\nGiảm giá cho khách hàng mới, sinh nhật hoặc vào các dịp đặc biệt.\r\nThông báo khuyến mãi sẽ được gửi qua app hoặc email.');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `store`
--

CREATE TABLE `store` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `picture` text NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(255) NOT NULL,
  `operating_hours` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `store`
--

INSERT INTO `store` (`id`, `name`, `picture`, `address`, `phone`, `operating_hours`) VALUES
(1, 'HCM Nguyễn Ảnh Thủ', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_nguyenanhthu.jpg', '93/5 Nguyễn Ảnh Thủ, Huyện Hóc Môn, Hồ Chí Minh, Việt Nam', '0968674279', '07:00 - 21:30'),
(2, 'HCM Trung Sơn', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_trungson.jpg', '114 Đường số 9A, Bình Hưng, Bình Chánh, Hồ Chí Minh', '0968674279', '07:00 - 20:30'),
(3, 'HCM Nguyễn Gia Trí', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_nguyengiatri.jpg', '157 Nguyễn Gia Trí, Phường 25, Bình Thạnh, Thành phố Hồ Chí Minh, Vietnam', '0968674279', '07:00 - 21:30'),
(4, 'HCM Đường số 22', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_duongso22.jpg', '9-11 Đường số 22, Q. Bình Tân, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(5, 'HCM Phan Văn Trị', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_phanvantri.jpg', '771 Phan Văn Trị, Phường 7, Gò Vấp, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(6, 'HCM Huỳnh Văn Bánh', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_huynhvanbanh.jpg', '68 Huỳnh Văn Bánh, Phường 15, Phú Nhuận, Hồ Chí Minh', '0968674279', '07:00 - 20:30'),
(7, 'HCM Nguyễn Thái Bình', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_nguyenthaibinh.jpg', '141 Nguyễn Thái Bình, Quận 1, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(8, 'HCM Sư Vạn Hạnh', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_suvanhanh.jpg', '798 Sư Vạn Hạnh, Phường 12, Quận 10, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(9, 'HCM Cư Xá Bình Thới', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_cuxabinhthoi.jpg', '45 Đường Số 5 - Cư xá Bình Thới, Phường 8, Quận 11, Hồ Chí Minh\r\n\r\n\r\n', '0968674279', '07:00 - 21:30'),
(10, 'HCM Ngô Thời Nhiệm', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_ngothoinhiem.jpg', '6A Ngô Thời Nhiệm, Quận 3, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(11, 'HCM Đường 41', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_duong41.jpg', '35-37 Đường 41, Quận 4, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(12, 'HCM Trần Hưng Đạo', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_tranhungdao.jpg', '682 Trần Hưng Đạo, Phường 2, Quận 5, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(13, 'HCM Hậu Giang', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_haugiang.jpg', '178 Hậu Giang, Quận 6, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(14, 'HCM Huỳnh Tấn Phát', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_huynhtanphat.jpg', '400A Huỳnh Tấn Phát, Quận 7, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(15, 'HCM Homyland Q2', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_homyland.jpg', 'SH2, Tầng 1 Dự án Chung cư cao cấp Homyland Riverside, Đ. Nguyễn Duy Trinh, P. Bình Trưng Tây, Quận 2, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(16, 'HCM Đỗ Xuân Hợp', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_doxuanhop.jpg', '359 Đỗ Xuân Hợp, Phước Long B, Quận 9, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(17, 'HCM Hoàng Diệu 2', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_hoangdieu2.jpg', '66E Hoàng Diệu 2, P.Linh Trung, Thủ Đức, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(18, 'HCM Lê Văn Sỹ', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_levansy.jpg', '281 Lê Văn Sỹ, Q. Tân Bình, Hồ Chí Minh', '0968674279', '07:00 - 21:30'),
(19, 'HCM Tân Sơn Nhì', 'http://192.168.1.8/CoffeeShop/Server/picture/shop/hcm_tansonnhi.jpg', '141-143 Tân Sơn Nhì, Q. Tân Phú, Hồ Chí Minh', '0968674279', '07:00 - 21:30');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `topping`
--

CREATE TABLE `topping` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `topping`
--

INSERT INTO `topping` (`id`, `name`, `price`) VALUES
(1, 'Trân châu trắng', 10000),
(2, 'Hạt sen', 10000),
(3, 'Trái vải', 10000),
(4, 'Kem Phô Mai Macchiato', 10000),
(5, 'Thạch Cà Phê', 10000),
(6, 'Bánh Flan', 15000),
(7, 'Đào Miếng', 10000),
(8, 'Shot Espresso', 10000),
(9, 'Sốt Caramel', 10000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `address` text NOT NULL,
  `current_bean` int(11) NOT NULL,
  `collected_bean` int(11) NOT NULL,
  `membership` int(11) DEFAULT NULL,
  `store_id` int(11) DEFAULT NULL,
  `image` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `first_name`, `last_name`, `phone`, `address`, `current_bean`, `collected_bean`, `membership`, `store_id`, `image`) VALUES
(19, 'Anh', 'Nguyễn', '+84968674272', 'Quận 12, Hồ Chí Minh', 0, 0, 1, NULL, 'http://192.168.1.8/CoffeeShop/Server/picture/avatar/avatar_1744433372114.jpg'),
(20, 'Hoàng', 'Lê', '+84968674273', 'Gò Vấp, Hồ Chí Minh', 0, 0, 1, NULL, 'http://192.168.1.8/CoffeeShop/Server/picture/avatar/avatar_1744433535809.jpg'),
(63, 'Linh', 'Hoàng', '+84968674274', 'Quận 12, Hồ Chí Minh', 5, 10, 1, NULL, 'http://192.168.1.8/CoffeeShop/Server/picture/avatar/avatar_1745666969309.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `voucher`
--

CREATE TABLE `voucher` (
  `id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `expiration_date` date NOT NULL,
  `name` text NOT NULL,
  `value` float NOT NULL,
  `description` text NOT NULL,
  `freeship` tinyint(1) NOT NULL,
  `conditions` int(255) NOT NULL,
  `picture` text DEFAULT NULL,
  `type` varchar(255) NOT NULL,
  `qr_code` text NOT NULL,
  `remain_quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `voucher`
--

INSERT INTO `voucher` (`id`, `code`, `start_date`, `expiration_date`, `name`, `value`, `description`, `freeship`, `conditions`, `picture`, `type`, `qr_code`, `remain_quantity`) VALUES
(1, 'TUNGBUNG30', '2025-04-01', '2025-05-31', 'Giảm 30K Đơn 99K', 30000, 'Giảm 30K cho đơn từ 99K\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho coffee, trà trái cây, trà sữa.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.', 0, 99000, 'http://192.168.1.8/CoffeeShop/Server/picture/promotion/promotion_1.png', 'delivery', 'http://192.168.1.8/CoffeeShop/Server/picture/voucher_qr_code/tungbung30.png', 300),
(3, 'RONRANG20', '2025-04-01', '2025-05-31', 'Giảm 20K Đơn 60K', 20000, 'Giảm 20K cho đơn từ 60K\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho trà trái cây, trà sữa.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.', 0, 60000, 'http://192.168.1.8/CoffeeShop/Server/picture/promotion/promotion_2.png', 'delivery', 'http://192.168.1.8/CoffeeShop/Server/picture/voucher_qr_code/ronrang20.png', 300),
(4, 'NHANHGON', '2025-04-01', '2025-05-31', 'Giảm 10% Đơn 2 Ly', 0.1, 'Giảm 10% cho đơn từ 2 ly\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho trà xanh, trà sữa.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.', 0, 2, 'http://192.168.1.8/CoffeeShop/Server/picture/promotion/promotion_3.png', 'takeaway', 'http://192.168.1.8/CoffeeShop/Server/picture/voucher_qr_code/nhanhgon.png', 300),
(5, 'FREESHIPVUI', '2025-04-01', '2025-05-31', 'Freeship Đơn 30K', 0, 'Freeship cho đơn từ 30K\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho coffee, trà trái cây, trà sữa.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.', 1, 30000, 'http://192.168.1.8/CoffeeShop/Server/picture/promotion/promotion_4.png', 'delivery', 'http://192.168.1.8/CoffeeShop/Server/picture/voucher_qr_code/freeshipvui.png', 300),
(6, 'CUNGVUI30', '2025-04-01', '2025-05-31', 'Giảm 30% + Freeship Đơn 3 Ly', 0.3, 'Giảm 30% + Freeship cho đơn từ 3 ly\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho đá xay, coffee, trà xanh.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.', 1, 3, 'http://192.168.1.8/CoffeeShop/Server/picture/promotion/promotion_5.png', 'delivery', 'http://192.168.1.8/CoffeeShop/Server/picture/voucher_qr_code/cungvui30.png', 300),
(7, 'VUIHOI20', '2025-04-01', '2025-05-31', 'Giảm 20% Đơn 2 Ly', 0.2, 'Giảm 20% cho đơn từ 2 ly\r\n1/ Áp dụng dịch vụ Giao hàng (Delivery) khi đặt hàng qua App/Web Coffee Shop trên toàn quốc.\r\n2/ Áp dụng cho chocolate, đá xay, trà xanh.\r\n3/ Không áp dụng song song các chương trình khác.\r\n4/ Chương trình có thể kết thúc sớm hơn dự kiến nếu hết số lượng ưu đãi.', 0, 2, 'http://192.168.1.8/CoffeeShop/Server/picture/promotion/promotion_6.png', 'delivery', 'http://192.168.1.8/CoffeeShop/Server/picture/voucher_qr_code/vuihoi20.png', 300);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `voucher_drink_category`
--

CREATE TABLE `voucher_drink_category` (
  `id` int(11) NOT NULL,
  `voucher_id` int(11) NOT NULL,
  `drink_category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `voucher_drink_category`
--

INSERT INTO `voucher_drink_category` (`id`, `voucher_id`, `drink_category_id`) VALUES
(3, 1, 3),
(5, 1, 1),
(6, 1, 2),
(9, 3, 2),
(10, 3, 3),
(11, 4, 4),
(12, 4, 3),
(13, 5, 1),
(14, 5, 2),
(15, 5, 3),
(16, 6, 6),
(17, 6, 1),
(18, 6, 4),
(19, 7, 5),
(20, 7, 6),
(21, 7, 4);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `voucher_membership`
--

CREATE TABLE `voucher_membership` (
  `id` int(11) NOT NULL,
  `voucher_id` int(11) NOT NULL,
  `membership_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `voucher_membership`
--

INSERT INTO `voucher_membership` (`id`, `voucher_id`, `membership_id`) VALUES
(1, 1, 5),
(7, 3, 5),
(8, 3, 4),
(9, 3, 1),
(10, 3, 2),
(11, 3, 3),
(12, 4, 7),
(13, 4, 5),
(14, 4, 4),
(15, 4, 3),
(16, 5, 7),
(17, 5, 5),
(18, 5, 1),
(19, 5, 2),
(20, 5, 3),
(21, 6, 7),
(26, 7, 7),
(27, 7, 5),
(28, 7, 4),
(29, 7, 3),
(30, 5, 4),
(31, 3, 7),
(32, 1, 7),
(33, 7, 1),
(34, 7, 2),
(36, 4, 2),
(37, 4, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `drink`
--
ALTER TABLE `drink`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Chỉ mục cho bảng `drink_category`
--
ALTER TABLE `drink_category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `drink_price`
--
ALTER TABLE `drink_price`
  ADD PRIMARY KEY (`id`),
  ADD KEY `drink_id` (`drink_id`);

--
-- Chỉ mục cho bảng `drink_topping`
--
ALTER TABLE `drink_topping`
  ADD PRIMARY KEY (`id`),
  ADD KEY `topping_id` (`topping_id`),
  ADD KEY `drink_id` (`drink_id`);

--
-- Chỉ mục cho bảng `favorite_drink`
--
ALTER TABLE `favorite_drink`
  ADD PRIMARY KEY (`id`),
  ADD KEY `drink_id` (`drink_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `drink_id` (`drink_id`);

--
-- Chỉ mục cho bảng `membership`
--
ALTER TABLE `membership`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `onboarding`
--
ALTER TABLE `onboarding`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orderr`
--
ALTER TABLE `orderr`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`),
  ADD KEY `store_id` (`store_id`),
  ADD KEY `voucher_id` (`voucher_id`);

--
-- Chỉ mục cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `drink_id` (`drink_id`),
  ADD KEY `order_id` (`order_id`);

--
-- Chỉ mục cho bảng `order_drink_topping_detail`
--
ALTER TABLE `order_drink_topping_detail`
  ADD PRIMARY KEY (`id`),
  ADD KEY `topping_id` (`topping_id`),
  ADD KEY `order_detail_id` (`order_detail_id`);

--
-- Chỉ mục cho bảng `policy`
--
ALTER TABLE `policy`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `store`
--
ALTER TABLE `store`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `topping`
--
ALTER TABLE `topping`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `store_id` (`store_id`),
  ADD KEY `membership` (`membership`);

--
-- Chỉ mục cho bảng `voucher`
--
ALTER TABLE `voucher`
  ADD PRIMARY KEY (`id`,`code`);

--
-- Chỉ mục cho bảng `voucher_drink_category`
--
ALTER TABLE `voucher_drink_category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `drink_category_id` (`drink_category_id`),
  ADD KEY `voucher_id` (`voucher_id`);

--
-- Chỉ mục cho bảng `voucher_membership`
--
ALTER TABLE `voucher_membership`
  ADD PRIMARY KEY (`id`),
  ADD KEY `membership_id` (`membership_id`),
  ADD KEY `voucher_id` (`voucher_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `drink`
--
ALTER TABLE `drink`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT cho bảng `drink_category`
--
ALTER TABLE `drink_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `drink_price`
--
ALTER TABLE `drink_price`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=145;

--
-- AUTO_INCREMENT cho bảng `drink_topping`
--
ALTER TABLE `drink_topping`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=175;

--
-- AUTO_INCREMENT cho bảng `favorite_drink`
--
ALTER TABLE `favorite_drink`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `membership`
--
ALTER TABLE `membership`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `onboarding`
--
ALTER TABLE `onboarding`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `orderr`
--
ALTER TABLE `orderr`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `order_drink_topping_detail`
--
ALTER TABLE `order_drink_topping_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `policy`
--
ALTER TABLE `policy`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `store`
--
ALTER TABLE `store`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT cho bảng `topping`
--
ALTER TABLE `topping`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=71;

--
-- AUTO_INCREMENT cho bảng `voucher`
--
ALTER TABLE `voucher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `voucher_drink_category`
--
ALTER TABLE `voucher_drink_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT cho bảng `voucher_membership`
--
ALTER TABLE `voucher_membership`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `drink`
--
ALTER TABLE `drink`
  ADD CONSTRAINT `drink_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `drink_category` (`id`);

--
-- Các ràng buộc cho bảng `drink_price`
--
ALTER TABLE `drink_price`
  ADD CONSTRAINT `drink_price_ibfk_1` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`);

--
-- Các ràng buộc cho bảng `drink_topping`
--
ALTER TABLE `drink_topping`
  ADD CONSTRAINT `drink_topping_ibfk_1` FOREIGN KEY (`topping_id`) REFERENCES `topping` (`id`),
  ADD CONSTRAINT `drink_topping_ibfk_2` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`);

--
-- Các ràng buộc cho bảng `favorite_drink`
--
ALTER TABLE `favorite_drink`
  ADD CONSTRAINT `favorite_drink_ibfk_1` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`),
  ADD CONSTRAINT `favorite_drink_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Các ràng buộc cho bảng `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`);

--
-- Các ràng buộc cho bảng `orderr`
--
ALTER TABLE `orderr`
  ADD CONSTRAINT `orderr_ibfk_2` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `orderr_ibfk_3` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
  ADD CONSTRAINT `orderr_ibfk_4` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`);

--
-- Các ràng buộc cho bảng `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `order_detail_ibfk_1` FOREIGN KEY (`drink_id`) REFERENCES `drink` (`id`),
  ADD CONSTRAINT `order_detail_ibfk_3` FOREIGN KEY (`order_id`) REFERENCES `orderr` (`id`);

--
-- Các ràng buộc cho bảng `order_drink_topping_detail`
--
ALTER TABLE `order_drink_topping_detail`
  ADD CONSTRAINT `order_drink_topping_detail_ibfk_2` FOREIGN KEY (`topping_id`) REFERENCES `topping` (`id`),
  ADD CONSTRAINT `order_drink_topping_detail_ibfk_3` FOREIGN KEY (`order_detail_id`) REFERENCES `order_detail` (`id`);

--
-- Các ràng buộc cho bảng `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `user_ibfk_1` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`),
  ADD CONSTRAINT `user_ibfk_2` FOREIGN KEY (`membership`) REFERENCES `membership` (`id`);

--
-- Các ràng buộc cho bảng `voucher_drink_category`
--
ALTER TABLE `voucher_drink_category`
  ADD CONSTRAINT `voucher_drink_category_ibfk_2` FOREIGN KEY (`drink_category_id`) REFERENCES `drink_category` (`id`),
  ADD CONSTRAINT `voucher_drink_category_ibfk_3` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`);

--
-- Các ràng buộc cho bảng `voucher_membership`
--
ALTER TABLE `voucher_membership`
  ADD CONSTRAINT `voucher_membership_ibfk_1` FOREIGN KEY (`membership_id`) REFERENCES `membership` (`id`),
  ADD CONSTRAINT `voucher_membership_ibfk_2` FOREIGN KEY (`voucher_id`) REFERENCES `voucher` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
