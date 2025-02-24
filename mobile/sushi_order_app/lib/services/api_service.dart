import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user.dart';
import '../models/sushi_item.dart';
import '../models/order.dart';

class ApiService {
  // APIのベースURL
  static const String baseUrl = 'http://10.0.2.2:8080/api';

  // APIトークンを保持
  String? _token;

  static final ApiService _instance = ApiService._internal();

    factory ApiService() {
    return _instance;
  }

  ApiService._internal();

  // トークンのセッター
  void setToken(String token) {
    _token = token;
  }

  // 認証用ヘッダーを生成
  Map<String, String> _getHeaders() {
    final headers = {
      'Content-Type': 'application/json',
      if (_token != null) 'Authorization': 'Bearer $_token',
    };
    print('Generated headers: $headers');
    return headers;
  }

  // ログイン
  Future<void> login(String email, String password) async {
    try {
      print('Attempting login with email: $email');  // 認証情報確認

      final loginBody = jsonEncode({
        'email': email,
        'password': password,
      });
      print('Login request body: $loginBody');  // リクエストボディ確認

      final response = await http.post(
        Uri.parse('$baseUrl/auth/login'),
        headers: {'Content-Type': 'application/json'},
        body: loginBody,
      );

      print('Login response status: ${response.statusCode}');
      print('Login response body: ${response.body}');

      if (response.statusCode == 200) {
        final data = jsonDecode(response.body);
        _token = data['token'];
        print('Token set successfully: $_token');
      } else {
        print('Login failed with status: ${response.statusCode}');
        throw Exception('Login failed');
      }
    } catch (e) {
      print('Login error: $e');
      throw Exception('Login failed');
    }
  }

  // メニュー一覧の取得
  Future<List<SushiItem>> getMenuItems() async {
    final response = await http.get(
      Uri.parse('$baseUrl/sushi-items'),
      headers: _getHeaders(),
    );

    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((json) => SushiItem.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load menu items');
    }
  }

  // 注文の作成
  Future<Order> createOrder(List<OrderItem> items) async {
    try {
      // リクエストボディの作成
      final Map<String, dynamic> requestData = {
        'customerName': 'テストユーザー',  // TODO: 実際のユーザー名を使用
        'phoneNumber': '09012345678',    // TODO: 実際の電話番号を使用
        'items': items.map((item) => {
          'sushiItemId': item.item.id,
          'quantity': item.quantity,
        }).toList(),
      };

      // デバッグ用のログ出力
      print('Request headers: ${_getHeaders()}');
      print('Request body: ${jsonEncode(requestData)}');

      // POSTリクエストの送信
      final response = await http.post(
        Uri.parse('$baseUrl/orders'),
        headers: _getHeaders(),
        body: jsonEncode(requestData),
      );

      // レスポンスのログ出力
      print('Response status: ${response.statusCode}');
      print('Response body: ${response.body}');

      if (response.statusCode == 201 || response.statusCode == 200) {
        return Order.fromJson(jsonDecode(response.body));
      } else {
        throw Exception('Failed to create order: ${response.body}');
      }
    } catch (e) {
      print('Error creating order: $e');
      throw Exception('Failed to create order');
    }
  }

  // 注文履歴の取得
  Future<List<Order>> getOrderHistory() async {
    final response = await http.get(
      Uri.parse('$baseUrl/orders'),
      headers: _getHeaders(),
    );

    if (response.statusCode == 200) {
      final List<dynamic> data = jsonDecode(response.body);
      return data.map((json) => Order.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load order history');
    }
  }
}