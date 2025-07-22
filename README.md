## REST API 작성 가이드<br>
클라이언트가 항상 동일한 응답 구조를 받을 수 있도록 아래 규칙을 지켜주세요.
### 1. 성공 응답처리
- 필요한 경우 `SuccessCode` enum에 **코드 번호**와 **메시지**를 추가.
- API 컨트롤러에서는 응답 데이터가 있는 경우 `ResponseEntity.ok(ApiResponse.success(SuccessCode, data))` 형태로 응답 / 응답 데이터가 없는 경우 `ResponseEntity.ok(ApiResponse.success(SuccessCode))` 형태로 응답.
```java
// 예시
@GetMapping("/api/member")
public ResponseEntity<ApiResponse<MemberResponse>> getMember(@RequestParam Long id) {
    return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_FETCH_SUCCESS, memberService.getMemberById(id)));
}

@PostMapping("/api/member/register")
public ResponseEntity<ApiResponse<Void>> registerMember(@RequestBody @Valid MemberRegisterRequest memberRegisterRequest) {
    memberService.registerMember(memberRegisterRequest);
    return ResponseEntity.ok(ApiResponse.success(SuccessCode.MEMBER_CREATE_SUCCESS));
}
```

### 2. 예외 처리
- 예외가 필요한 경우 `ErrorCode` enum에 **코드 번호**, **메시지**, **HttpStatus**를 추가.
- Service 계층에서 예외 상황 발생 여부를 확인하고, `throw new CustomException(ErrorCode.XXX)` 형식으로 처리.
```java
// 예시
public List<ProductResponse> getAllProducts() {
    List<Product> products = productRepository.findAll();
    if (products.isEmpty()) throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
    else {
        return products.stream()
                .map(product -> new ProductResponse(product))
                .toList();
    }

}
```
### 3. 성공 응답 결과 예시
```json
{
    "success": true,
    "code": 100,
    "message": "회원 조회 성공",
    "data": {
        "id": 1,
        "name": "최성보",
        "nickname": "최성보",
        "phone": "010-1234-5678",
        "address": "서울특별시",
        "updatedAt": "2025-05-12T14:12:51.229661",
        "status": false,
        "authority": 1,
        "email": "cseongbo17@naver.com",
        "joinDate": "2025-05-12T14:12:51.229682"
    }
}
```
### 4. 실패 응답 결과 예시
```json
{
    "success": false,
    "code": 1002,
    "message": "이미 사용 중인 이메일입니다.",
    "data": null
}
```