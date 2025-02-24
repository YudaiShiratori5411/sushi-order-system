import 'package:flutter/material.dart';
import '../models/sushi_item.dart';
import '../services/api_service.dart';
import '../widgets/sushi_card.dart';

class MenuScreen extends StatefulWidget {
  const MenuScreen({Key? key}) : super(key: key);

  @override
  State<MenuScreen> createState() => _MenuScreenState();
}

class _MenuScreenState extends State<MenuScreen> {

  final _apiService = ApiService();  // APIサービスのインスタンス
  List<SushiItem> _menuItems = [
    SushiItem(
      id: 1,
      name: 'まぐろ',
      description: '新鮮な本まぐろを使用',
      price: 500,
      imageUrl: 'assets/images/tuna.jpg', // 仮のURL
      available: true,
    ),
    SushiItem(
      id: 2,
      name: 'サーモン',
      description: 'ノルウェー産の上質なサーモン',
      price: 400,
      imageUrl: 'assets/images/salmon.jpg',
      available: true,
    ),
    SushiItem(
      id: 3,
      name: 'いか',
      description: '新鮮なイカを使用',
      price: 300,
      imageUrl: 'assets/images/squid.jpg',
      available: true,
    ),
    SushiItem(
      id: 4,
      name: 'えび',
      description: '大ぶりの海老',
      price: 350,
      imageUrl: 'assets/images/shrimp.jpg',
      available: true,
    ),
  ];
  bool _isLoading = true;  // ローディング状態
  String? _error;  // エラーメッセージ

  @override
  void initState() {
    super.initState();
    // 初期データを表示
    setState(() {
      _isLoading = false;  // ローディング終了
    });
    // APIからのデータ取得は後で行う
    //_loadMenuItems();
  }

  // メニューデータの取得
  Future<void> _loadMenuItems() async {
    try {
      final items = await _apiService.getMenuItems();
      setState(() {
        _menuItems = items;
        _isLoading = false;
      });
    } catch (e) {
      setState(() {
        _error = 'メニューの読み込みに失敗しました';
        _isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('メニュー'),
        backgroundColor: Colors.orange,
        actions: [
          // カートボタン
          IconButton(
            icon: const Icon(Icons.shopping_cart),
            onPressed: () {
              Navigator.of(context).pushNamed('/cart');
            },
          ),
        ],
      ),
      
      body: _isLoading
          ? const Center(child: CircularProgressIndicator())
          : _error != null
              ? Center(child: Text(_error!))
              : RefreshIndicator(
                  onRefresh: _loadMenuItems,
                  child: GridView.builder(
                    padding: const EdgeInsets.all(8.0),
                    gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: 2,  // 1行に2つのカード
                      childAspectRatio: 0.8,  // カードの縦横比
                      crossAxisSpacing: 8.0,   // 横方向の間隔
                      mainAxisSpacing: 8.0,    // 縦方向の間隔
                    ),
                    itemCount: _menuItems.length,
                    itemBuilder: (context, index) {
                      final item = _menuItems[index];
                      return SushiCard(
                        item: item,
                        onTap: () {
                          // 商品詳細画面への遷移
                          Navigator.of(context).pushNamed(
                            '/item_detail',
                            arguments: item,
                          );
                        },
                      );
                    },
                  ),
                ),
                
      // フローティングアクションボタン（注文履歴）
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          Navigator.of(context).pushNamed('/order_history');
        },
        backgroundColor: Colors.orange,
        child: const Icon(Icons.history),
      ),
    );
  }
}