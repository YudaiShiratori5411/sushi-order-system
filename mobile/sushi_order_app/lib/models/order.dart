import 'package:sushi_order_app/models/sushi_item.dart';

// 注文の状態を管理する列挙型を定義
enum OrderStatus {
  pending,    // 注文待ち
  confirmed,  // 注文確定
  preparing,  // 準備中
  ready,      // 準備完了
  completed,  // 受け渡し完了
  cancelled   // キャンセル
}

// 注文の1アイテムを表すクラス
class OrderItem {
  final int id;
  final SushiItem item;
  final int quantity;
  final double price;

  OrderItem({
    required this.id,
    required this.item,
    required this.quantity,
    required this.price,
  });

  // JSONからオブジェクトを生成
  factory OrderItem.fromJson(Map<String, dynamic> json) {
    return OrderItem(
      id: json['id'] as int,
      item: SushiItem.fromJson(json['item'] as Map<String, dynamic>),
      quantity: json['quantity'] as int,
      price: (json['price'] as num).toDouble(),
    );
  }

  // オブジェクトをJSONに変換
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'item': item.toJson(),
      'quantity': quantity,
      'price': price,
    };
  }
}

// 注文全体を表すクラス
class Order {
  final int id;
  final String userId;     // 注文したユーザーのID
  final List<OrderItem> items;  // 注文アイテムのリスト
  final DateTime orderTime;     // 注文時刻
  final OrderStatus status;     // 注文状態
  final double totalAmount;     // 合計金額

  Order({
    required this.id,
    required this.userId,
    required this.items,
    required this.orderTime,
    required this.status,
    required this.totalAmount,
  });

  // JSONからオブジェクトを生成
  factory Order.fromJson(Map<String, dynamic> json) {
    return Order(
      id: json['id'] as int,
      userId: json['user_id'] as String,
      items: (json['items'] as List<dynamic>)
          .map((item) => OrderItem.fromJson(item as Map<String, dynamic>))
          .toList(),
      orderTime: DateTime.parse(json['order_time'] as String),
      status: OrderStatus.values.firstWhere(
          (e) => e.toString() == 'OrderStatus.${json['status']}'),
      totalAmount: (json['total_amount'] as num).toDouble(),
    );
  }

  // オブジェクトをJSONに変換
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'user_id': userId,
      'items': items.map((item) => item.toJson()).toList(),
      'order_time': orderTime.toIso8601String(),
      'status': status.toString().split('.').last,
      'total_amount': totalAmount,
    };
  }
}