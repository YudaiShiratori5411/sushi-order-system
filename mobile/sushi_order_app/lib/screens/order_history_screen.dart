import 'package:flutter/material.dart';
import '../models/order.dart';
import '../services/api_service.dart';

class OrderHistoryScreen extends StatefulWidget {
  const OrderHistoryScreen({Key? key}) : super(key: key);

  @override
  State<OrderHistoryScreen> createState() => _OrderHistoryScreenState();
}

class _OrderHistoryScreenState extends State<OrderHistoryScreen> {

  final _apiService = ApiService();
  List<Order> _orders = [];
  bool _isLoading = true;
  String? _error;

  @override
  void initState() {
    super.initState();
    _loadOrders();
  }

  // 注文履歴の取得
  Future<void> _loadOrders() async {
    try {
      final orders = await _apiService.getOrderHistory();
      setState(() {
        _orders = orders;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = '注文履歴の取得に失敗しました';
        _isLoading = false;
      });
    }
  }

  // 注文状態に応じた色を返す
  Color _getStatusColor(OrderStatus status) {
    switch (status) {
      case OrderStatus.pending:
        return Colors.orange;
      case OrderStatus.confirmed:
        return Colors.blue;
      case OrderStatus.preparing:
        return Colors.purple;
      case OrderStatus.ready:
        return Colors.green;
      case OrderStatus.completed:
        return Colors.grey;
      case OrderStatus.cancelled:
        return Colors.red;
    }
  }

  // 注文状態の日本語表示
  String _getStatusText(OrderStatus status) {
    switch (status) {
      case OrderStatus.pending:
        return '注文受付中';
      case OrderStatus.confirmed:
        return '注文確定';
      case OrderStatus.preparing:
        return '準備中';
      case OrderStatus.ready:
        return 'お受け取り可能';
      case OrderStatus.completed:
        return '完了';
      case OrderStatus.cancelled:
        return 'キャンセル';
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('注文履歴'),
        backgroundColor: Colors.orange,
      ),
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _error != null
              ? Center(child: Text(_error!))
              : _orders.isEmpty
                  ? const Center(child: Text('注文履歴がありません'))
                  : RefreshIndicator(
                      onRefresh: _loadOrders,
                      child: ListView.builder(
                        itemCount: _orders.length,
                        itemBuilder: (context, index) {
                          final order = _orders[index];
                          return Card(
                            margin: const EdgeInsets.symmetric(
                              horizontal: 16,
                              vertical: 8,
                            ),
                            child: Padding(
                              padding: const EdgeInsets.all(16.0),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  // 注文日時と状態
                                  Row(
                                    mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                    children: [
                                      Text(
                                        order.orderTime.toString().split('.')[0],
                                        style: const TextStyle(
                                          color: Colors.grey,
                                        ),
                                      ),
                                      Chip(
                                        label: Text(
                                          _getStatusText(order.status),
                                          style: const TextStyle(
                                            color: Colors.white,
                                          ),
                                        ),
                                        backgroundColor:
                                            _getStatusColor(order.status),
                                      ),
                                    ],
                                  ),
                                  const Divider(),

                                  // 注文内容
                                  ...order.items.map((item) => Padding(
                                        padding: const EdgeInsets.symmetric(
                                          vertical: 4,
                                        ),
                                        child: Row(
                                          mainAxisAlignment:
                                              MainAxisAlignment.spaceBetween,
                                          children: [
                                            Text(
                                              '${item.item.name} × ${item.quantity}',
                                            ),
                                            Text(
                                              '¥${(item.price * item.quantity).toStringAsFixed(0)}',
                                            ),
                                          ],
                                        ),
                                      )),
                                  const Divider(),

                                  // 合計金額
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.end,
                                    children: [
                                      const Text(
                                        '合計: ',
                                        style: TextStyle(
                                          fontWeight: FontWeight.bold,
                                        ),
                                      ),
                                      Text(
                                        '¥${order.totalAmount.toStringAsFixed(0)}',
                                        style: const TextStyle(
                                          fontWeight: FontWeight.bold,
                                          color: Colors.green,
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            ),
                          );
                        },
                      ),
                    ),
    );
  }
}