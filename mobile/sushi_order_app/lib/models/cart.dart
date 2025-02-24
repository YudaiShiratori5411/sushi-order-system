import 'sushi_item.dart';

class CartItem {
  final SushiItem item;
  int quantity;

  CartItem({
    required this.item,
    this.quantity = 1,
  });
}

class Cart {
  // シングルトンパターンの実装
  static final Cart _instance = Cart._internal();
  
  factory Cart() {
    return _instance;
  }
  
  Cart._internal();

  // カート内のアイテムリスト
  final List<CartItem> items = [];

  // カートに商品を追加
  void addItem(SushiItem item, {int quantity = 1}) {
    final existingItem = items.firstWhere(
      (element) => element.item.id == item.id,
      orElse: () => CartItem(item: item, quantity: 0),
    );

    if (existingItem.quantity == 0) {
      items.add(CartItem(item: item, quantity: quantity));
    } else {
      existingItem.quantity += quantity;
    }
  }

  // 合計金額の計算
  double get totalAmount {
    return items.fold(0, (sum, item) => sum + (item.item.price * item.quantity));
  }

  // 商品の削除
  void removeItem(int itemId) {
    items.removeWhere((item) => item.item.id == itemId);
  }

  // カートをクリア
  void clear() {
    items.clear();
  }
}