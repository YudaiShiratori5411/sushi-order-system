class User {

  final int id;
  final String email;
  final String name;
  final String? phone;    // ?はnull許容を表す

  // コンストラクタ
  // {}で囲むことで名前付き引数になり、呼び出し時にパラメータ名を指定できる
  // required ＝ パラメータ必須
  User({
    required this.id,
    required this.email,
    required this.name,
    this.phone,
  });

  // JSONからUserオブジェクトを作成するファクトリメソッド
  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'] as int,
      email: json['email'] as String,
      name: json['name'] as String,
      phone: json['phone'] as String?,
    );
  }

  // UserオブジェクトをJSONに変換するメソッド
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'email': email,
      'name': name,
      'phone': phone,
    };
  }
}