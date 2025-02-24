class SushiItem {
  final int id;
  final String name;
  final String description;
  final double price;
  final String imageUrl;
  final bool available;

  // コンストラクタ
  SushiItem({
    required this.id,
    required this.name,
    required this.description,
    required this.price,
    required this.imageUrl,
    this.available = true,  // デフォルト値として true を設定
  });

  // JSONからオブジェクトを生成
  factory SushiItem.fromJson(Map<String, dynamic> json) {
    return SushiItem(
      id: json['id'] as int,
      name: json['name'] as String,
      description: json['description'] as String,
      price: (json['price'] as num).toDouble(), // APIから数値が整数で来る可能性も考慮
      imageUrl: json['image_url'] as String,
      available: json['available'] as bool? ?? true, // null の場合は true とする
    );
  }

  // オブジェクトをJSONに変換
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'description': description,
      'price': price,
      'image_url': imageUrl,
      'available': available,
    };
  }

  // オブジェクトのコピーを作成し、特定のフィールドを更新するメソッド
  SushiItem copyWith({
    int? id,
    String? name,
    String? description,
    double? price,
    String? imageUrl,
    bool? available,
  }) {
    return SushiItem(
      id: id ?? this.id,
      name: name ?? this.name,
      description: description ?? this.description,
      price: price ?? this.price,
      imageUrl: imageUrl ?? this.imageUrl,
      available: available ?? this.available,
    );
  }
}