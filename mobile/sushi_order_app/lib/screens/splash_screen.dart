import 'package:flutter/material.dart';

// スプラッシュ画面用のStatefulWidget
// StatefulWidgetは状態を持つ（変更される）ウィジェット
class SplashScreen extends StatefulWidget {

  const SplashScreen({Key? key}) : super(key: key);

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

// スプラッシュ画面の状態を管理するState
class _SplashScreenState extends State<SplashScreen> {
  @override
  // initStateはウィジェットが作成された時に一度だけ呼ばれる
  void initState() {
    super.initState();
    // 3秒後に自動的にログイン画面に遷移
    _navigateToLogin();
  }

  // ログイン画面への遷移処理
  Future<void> _navigateToLogin() async {
    // Future.delayed で3秒間待機
    await Future.delayed(const Duration(seconds: 3));
    // ログイン画面に遷移（戻るボタンでスプラッシュ画面に戻れないようにする）
    if (mounted) {  // Widgetがまだ有効かチェック
      Navigator.of(context).pushReplacementNamed('/login');
    }
  }

  @override
  Widget build(BuildContext context) {
    // Scaffoldはマテリアルデザインの基本的な画面レイアウト
    return Scaffold(
      backgroundColor: Colors.white,
      body: Center(
        // Columnで縦方向にウィジェットを配置
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            // ロゴ画像
            Image.asset(
              'assets/sushi_logo.png',
              width: 200,
              height: 200,
            ),
            const SizedBox(height: 24),
            // アプリ名
            const Text(
              'Sushi Order App',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.black87,
              ),
            ),
            const SizedBox(height: 16),
            // ローディングインジケータ
            const CircularProgressIndicator(
              valueColor: AlwaysStoppedAnimation<Color>(Colors.orange),
            ),
          ],
        ),
      ),
    );
  }
}