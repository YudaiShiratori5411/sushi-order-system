import 'package:flutter/material.dart';
import 'package:sushi_order_app/main.dart';
import 'package:sushi_order_app/models/order.dart';
import '../models/cart.dart';
import '../widgets/cart_item.dart';
import '../services/api_service.dart';

class CartScreen extends StatefulWidget {
  const CartScreen({Key? key}) : super(key: key);

  @override
  State<CartScreen> createState() => _CartScreenState();
}

class _CartScreenState extends State<CartScreen> {
  final Cart _cart = Cart();
  bool _isLoading = false;
  final _apiService = apiService;

  // 注文確定処理
  Future<void> _placeOrder() async {
    if (_cart.items.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text('カートが空です'),
          backgroundColor: Colors.red,
        ),
      );
      return;
    }

    setState(() {
      _isLoading = true;
    });

    try {
      // CartItemからOrderItemへの変換
      final orderItems = _cart.items.map((cartItem) => OrderItem(
        id: 0,
        item: cartItem.item,
        quantity: cartItem.quantity,
        price: cartItem.item.price,
      )).toList();

      // 注文をAPIに送信
      final order = await _apiService.createOrder(orderItems);
      
      // カートをクリア
      setState(() {
        _cart.clear();
      });

      // 成功メッセージを表示
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(
            content: Text('注文が完了しました'),
            backgroundColor: Colors.green,
          ),
        );
        // 注文確認画面に遷移
        Navigator.of(context).pushReplacementNamed(
          '/order_confirmation',
          arguments: order,
        );
      }
    } catch (e) {
      if (mounted) {
        ScaffoldMessenger.of(context).showSnackBar(
          SnackBar(
            content: Text('注文に失敗しました: ${e.toString()}'),
            backgroundColor: Colors.red,
          ),
        );
      }
    } finally {
      if (mounted) {
        setState(() {
          _isLoading = false;
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('カート'),
        backgroundColor: Colors.orange,
      ),
      body: _cart.items.isEmpty
          ? const Center(
              child: Text(
                'カートは空です',
                style: TextStyle(fontSize: 18),
              ),
            )
          : ListView.builder(
              itemCount: _cart.items.length,
              itemBuilder: (context, index) {
                final cartItem = _cart.items[index];
                return CartItemWidget(
                  cartItem: cartItem,
                  onQuantityChanged: (quantity) {
                    setState(() {
                      cartItem.quantity = quantity;
                    });
                  },
                  onRemove: () {
                    setState(() {
                      _cart.removeItem(cartItem.item.id);
                    });
                  },
                );
              },
            ),
      bottomNavigationBar: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisSize: MainAxisSize.min,
            children: [
              // 合計金額表示
              Padding(
                padding: const EdgeInsets.symmetric(horizontal: 16.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    const Text(
                      '合計:',
                      style: TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    Text(
                      '¥${_cart.totalAmount.toStringAsFixed(0)}',
                      style: const TextStyle(
                        fontSize: 20,
                        fontWeight: FontWeight.bold,
                        color: Colors.green,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 16),
              // 注文ボタン
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: _isLoading ? null : _placeOrder,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.orange,
                    padding: const EdgeInsets.symmetric(vertical: 16),
                  ),
                  child: _isLoading
                      ? const CircularProgressIndicator(color: Colors.white)
                      : const Text(
                          '注文を確定する',
                          style: TextStyle(fontSize: 18),
                        ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}