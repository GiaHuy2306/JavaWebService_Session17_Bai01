### Phần 1: Phân tích

JWT không hợp lệ vì token được kí bằng một Secret Key nhưng lại được xác mih bằng 1 Secret Key khác

Trong đoạn:
```
Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
```

JWT được kí bằng key

Nhưng xác minh bằng:
```
Key differentKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

Jwts.parserBuilder()
    .setSigningKey(differentKey)
    .build()
    .parseClaimsJws(jwtToken);
```

đây là một khóa hoàn toàn mới nên ko khớp với khóa dùng để xác minh

Quy trình JWT hoạt động:
1. Tạo Payload chứa userId, role, subject
2. Ký JWT bằng Secret Key
3. Khi nhân JWT, xác minh bằng cùng một Secret Key đã kí để kiểm tra Signature
4. Nếu khóa xác minh ko khớp với khóa kí -> Signature không hợp lệ -> JWT bị từ chối

=> Chương trình ném lỗi:
```angular2html
Invalid JWT: JWT signature does not match locally computed signature
```