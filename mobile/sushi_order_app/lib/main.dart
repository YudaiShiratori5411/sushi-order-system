import 'package:flutter/material.dart';
import 'package:sushi_order_app/services/api_service.dart';
import 'screens/splash_screen.dart';
import 'screens/login_screen.dart';
import 'screens/menu_screen.dart';
import 'screens/item_detail_screen.dart';
import 'screens/cart_screen.dart';
import 'screens/order_history_screen.dart';

final apiService = ApiService();

void main() {
  runApp(const SushiOrderApp());
}

class SushiOrderApp extends StatelessWidget {
  const SushiOrderApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Sushi Order App',
      // アプリ全体のテーマ設定
      theme: ThemeData(
        primarySwatch: Colors.orange,
        // ボタンのスタイル
        elevatedButtonTheme: ElevatedButtonThemeData(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.orange,
            foregroundColor: Colors.white,
          ),
        ),
        // AppBarのスタイル
        appBarTheme: const AppBarTheme(
          backgroundColor: Colors.orange,
          foregroundColor: Colors.white,
        ),
      ),
      // デバッグバナーを非表示
      debugShowCheckedModeBanner: false,
      
      // 初期画面の設定
      initialRoute: '/',
      
      // ルート（画面遷移）の設定
      routes: {
        '/': (context) => const SplashScreen(),
        '/login': (context) => const LoginScreen(),
        '/menu': (context) => const MenuScreen(),
        '/item_detail': (context) => const ItemDetailScreen(),
        '/cart': (context) => const CartScreen(),
        '/order_history': (context) => const OrderHistoryScreen(),
      },
    );
  }
}