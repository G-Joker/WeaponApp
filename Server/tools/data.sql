/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-01 21:29:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `taobao`
-- ----------------------------
DROP TABLE IF EXISTS `taobao`;
CREATE TABLE `taobao` (
`user`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`image`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`price`  double(16,0) NULL DEFAULT NULL ,
`time`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`region`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sign`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`title`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`sign`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of taobao
-- ----------------------------
BEGIN;
INSERT INTO `taobao` VALUES ('透明沙沙_ye', 'http://img.alicdn.com/bao/uploaded/i4/6000000007728/TB2pbO7vShlpuFjSspkXXa1ApXa_!!0-fleamarket.jpg_220x10000.jpg', '120', '1分钟前', '上海 上海', '0cd3d6d7e730d7b6dd6b84c6adc4564e', '全新双立人眉镊和眉剪，使用方便口碑好。各多出一个，转喜欢的朋友……两款一起，可优惠哦……单个120元。'), ('严馨璇', 'http://img.alicdn.com/bao/uploaded/i1/6000000006097/TB2fv1BaMAEF1JjSZFLXXbzNXXa_!!0-fleamarket.jpg_220x10000.jpg', '9500', '1分钟前', '吉林 吉林', '106909cbd8d43f8adcea815a225c846b', '2017年在红星美凯龙联邦米尼买的，17年7月份刚刚入户。因个人原因，忍痛出售，只卖吉林市同城，自取。喜欢就速度下手，同等质量的纯皮沙发，至少售价在2万以上。沙发长3.6米'), ('方氏洛子', 'http://img.alicdn.com/bao/uploaded/i2/6000000007451/TB2HJdhtiC9MuFjSZFoXXbUzFXa_!!0-fleamarket.jpg_220x10000.jpg', '30', '1分钟前', '福建 福州', '1666bb2942d9e97cdc3f49eec891e0e0', '鞋码40码，偏小，我是39的胖脚，穿起来太挤，瘦脚39跟正码38的脚可以穿，鞋子很软，硅胶的，具体可见视频。'), ('123我是大眼', 'http://img.alicdn.com/bao/uploaded/i4/6000000007668/TB2BY2OXT_WJKJjy0FnXXXwwFXa_!!0-fleamarket.jpg_220x10000.jpg', '40', '1分钟前', '湖南 长沙', '27fb8d7844b00e3f53e4d341693a68ec', '尺码是3Y，相当于100码的样子，腰部可以调节大小，国外带回，宝宝不喜欢穿，穿的次数很少，不超过五次！不退不换不议价，不包邮！'), ('快乐小鸟3003', 'http://img.alicdn.com/bao/uploaded/i4/6000000007018/TB275aab_wKL1JjSZFgXXb6aVXa_!!0-fleamarket.jpg_220x10000.jpg', '1680', '1分钟前', '河北 唐山', '2ad1978c7bf25aa6eec2174bff32bcd4', '天然和田玉籽料，个别珠子闪黄沁，单珠8毫米。'), ('梁伟爱刘楠', 'http://img.alicdn.com/bao/uploaded/i2/TB1inNVPVXXXXbxapXXYnqbFpXX_220x10000.jpg', '35', '1分钟前', '吉林 辽源', '5c3400fa6ccf19e5c7125470d61365db', '搬家清仓亏出！十字绣精准印图十字绣，绣了一小部分，没耐心绣下去！尺寸不小1.5米以上，中格11ct，纯棉棉线，已经自刀，不接受议价！不接受议价！不接受议价！重要的事情说三遍！小白磨叽...'), ('迷古董', 'http://img.alicdn.com/bao/uploaded/i4/6000000003312/TB2uRZ2iC3PL1JjSZFtXXclRVXa_!!0-fleamarket.jpg_220x10000.jpg', '80', '1分钟前', '吉林 四平', '5ef913f7769c23c810a1e6946be5c925', '二手威戈85mm老款单层无牙签镊子型，很有收藏价值呦！！实物拍摄品相如图所示，刀刃完好开合正常，毕竟二手物品无法同新的相比追求完美的就不要拍了谢谢！不议价！看好再拍售出不退敬请谅解！点我...'), ('冰儿贝贝512', 'http://img.alicdn.com/bao/uploaded/i4/110545816/TB25As2spXXXXbrXpXXXXXXXXXX_!!110545816.jpg_220x10000.jpg', '255', '1分钟前', '内蒙古 呼和浩特', '60ddb87a31846b69ccfacb4360018d59', '正品，不是外面那些山寨。s码，穿二洗二，但是状态很新，寄出之前会熨烫。真的！非常！非常！新！我用我的节操发誓和我刚买回来的状态没差！嘤嘤嘤！不要觉得洗了两次就不新了呀！穿着的时间也是...'), ('miertao6', 'http://img.alicdn.com/bao/uploaded/i2/6000000001317/TB21lU5hYsTMeJjy1zeXXcOCVXa_!!0-fleamarket.jpg_220x10000.jpg', '45', '1分钟前', '广东 梅州', '69c367b34b4b76fd5d90c4d9d6546d30', '复古系绳花边袖雪纺衫衬衫均码酒红色没穿过没洗过要吃吐了低价转卖'), ('why929929', 'http://img.alicdn.com/bao/uploaded/i4/6000000008020/TB2VKf3be7EWeJjSZFMXXX00FXa_!!0-fleamarket.jpg_220x10000.jpg', '70', '1分钟前', '湖北 武汉', '69d1f072ca3edfdaf93a3830f0b38eab', '闲置ofra脏橘色南瓜色唇釉本人没有试色图当时是看仇仇推荐所以种草了颜色超显白1??余量很多2??不交换不议价'), ('钦涵123', 'http://img.alicdn.com/bao/uploaded/i1/6000000003133/TB29w98bWigSKJjSsppXXabnpXa_!!0-fleamarket.jpg_220x10000.jpg', '80', '1分钟前', '内蒙古 呼和浩特', '8ef7d2c4cac24a31fad7eb130ce05013', '中长款条纹宽松长袖圆领套头卫衣均码：衣长73肩宽54袖长62胸围126【85-140斤左右可以穿】条纹款式属于百搭版，这款宝贝的面料是毛圈的，很柔软，中长款款式可以盖住臀部...'), ('小鹿1998', 'http://img.alicdn.com/bao/uploaded/i3/6000000005067/TB2zYD9camgSKJjSspiXXXyJFXa_!!0-fleamarket.jpg_220x10000.jpg', '1', '1分钟前', '浙江 金华', '93187b4c35fa22a020c5f628fa23dd92', '喜欢私我价格好说'), ('偶爱大熊', 'http://img.alicdn.com/bao/uploaded/i2/6000000007188/TB2VN0gXhSCJuJjSspdXXXy6VXa_!!0-fleamarket.jpg_220x10000.jpg', '79', '1分钟前', '浙江 杭州', '9dbc8f77ffe6aaf8f9a7047cb083dabf', '购于杭州西湖银泰城江博士专柜，有购物小票，买来不到2个月，宝宝脚又长了，就平时出去穿下下，宝宝还不会走路，基本上是新的，21码，鞋内13.5cm，不议价不包邮。'), ('田序欧巴', 'http://img.alicdn.com/bao/uploaded/i1/22125930/TB2vfeEmpXXXXbEXpXXXXXXXXXX_!!22125930.jpg_220x10000.jpg', '40', '1分钟前', '黑龙江 鸡西', 'a60303376698b14d09c5fcc436fcdc8f', '7p三只狗'), ('爱喝萝莉的可乐酱', 'http://img.alicdn.com/bao/uploaded/i2/TB1gDlOPVXXXXcQXpXXH1Cw9pXX_220x10000.jpg', '10', '1分钟前', '重庆 重庆', 'aad05cd747c070a7e6a03bf596fb9751', '转让定金！！！因为身高太矮看卖家秀穿不得，需要的联系我！！！！'), ('虎门商业摄影', 'http://img.alicdn.com/bao/uploaded/i2/6000000001802/TB2WYQkXJXXWeJjSZFvXXa6lpXa_!!0-fleamarket.jpg_220x10000.jpg', '1480', '1分钟前', '湖南 长沙', 'e5b6f1a926d0418011eccf9156024a72', '您的专属小女友！全新实体娃娃非充气全硅胶真人版男用性玩偶成人用品全新全新全新...'), ('8888a54', 'http://img.alicdn.com/bao/uploaded/i2/6000000001216/TB2J72qcu38SeJjSZFPXXc_vFXa_!!0-fleamarket.jpg_220x10000.jpg', '160', '1分钟前', '北京 北京', 'ef22a43d7c456635fbac249c7d76b4ad', '全新。纯羊毛。'), ('oo一天世界oo', 'http://img.alicdn.com/bao/uploaded/i4/6000000006299/TB2HGHCaLxNTKJjy0FjXXX6yVXa_!!0-fleamarket.jpg_220x10000.jpg', '160', '1分钟前', '上海 上海', 'f0d60b0488cc02f32bfa7397a229966c', '每张是20元面值一共10张有效期至2017年12月31日提货券非月饼券除了可以买月饼还能买新雅的熟食包子小菜面点点心等可面交上海可包邮'), ('ys12js', 'http://img.alicdn.com/bao/uploaded/i2/6000000006616/TB2x3KsdO0TMKJjSZFNXXa_1FXa_!!0-fleamarket.jpg_220x10000.jpg', '90', '1分钟前', '辽宁 沈阳', 'f6710b168459deac13e2fabadf051a72', '成色如图，非常新！穿着次数有限。非真假不退不换！'), ('one5555', 'http://img.alicdn.com/bao/uploaded/i3/TB1O1q7PpXXXXc7XFXXXXXXXXXX_!!0-item_pic.jpg_220x10000.jpg', '35', '1分钟前', '广东 珠海', 'ff3b543c71f84650d1658dd06c2ef67e', '#妈妈用品#断奶了还有剩3盒全新，包装都没有拆开的一盒35元');
COMMIT;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
`user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`token`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`uid`  int(4) NOT NULL AUTO_INCREMENT ,
PRIMARY KEY (`uid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=6

;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Auto increment value for `user`
-- ----------------------------
ALTER TABLE `user` AUTO_INCREMENT=6;
