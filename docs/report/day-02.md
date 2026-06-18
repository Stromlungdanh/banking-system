# DAY 02 - 

## 1. Hiểu được luồng exception và setup exceptionHandler tập trung

- **Khi có lỗi nghiệp vụ xảy ra trong một method,** 
 1. mình chủ động throw new BusinessException(ErrorCode.xxx). 
 2. Lúc này method hiện tại sẽ dừng xử lý, các dòng code phía sau sẽ không chạy nữa. 
 3. Exception này được ném ra ngoài luồng xử lý hiện tại. 
 4. Spring Boot sẽ tìm GlobalExceptionHandler phù hợp để bắt exception đó. 
 5. Sau đó GlobalExceptionHandler lấy thông tin trong ErrorCode, đóng gói thành ApiResponse có cấu trúc thống nhất và trả về cho FE.


    Service phát hiện lỗi
    ↓
    throw BusinessException(ErrorCode.xxx)
    ↓
    Method dừng lại
    ↓
    Exception được ném ra ngoài
    ↓
    GlobalExceptionHandler bắt lỗi
    ↓
    Convert thành ApiResponse
    ↓
    Trả về FE
---

## 2. Hiểu được luồng đi của chức năng login
- **Thành phần của Spring Security được dùng trong dự án**
1. JwtAuthenticationFilter: nhiệm vụ đọc token từ Header có trong request, validate token, xác định user từ accessToken
2. AuthenticationManager: nhiệm vụ điều phối xác thực. Nhận username/password khi login
3. AuthenticationProvider: nhiệm vụ xử lý cơ chế xác thực cụ thể. Nó là 1 interface cho provider
4. DaoAuthenticationProvider: nhiệm vụ là nhận username/password từ request rồi so sánh với user trong db
5. UserDetailService: nhiêm vụ load user theo username từ dưới db 
6. CustomUserDetailService: nhiệm vụ chuyển object User từ db thành object UserDetail. Vì Spring Security chỉ hiểu được UserDetail
7. UserDetails: là User theo chuẩn của Spring Security. Chứa các trường: username, password, roles, disable.
8. Authentication: đại diện cho user đã xác thực/chưa xác thực. Ở dạng chưa xác thực thì authenticated = false.Ở dạng đã xác thực thì authenticated = true

- **Luồng đi của chức năng login**

Bước 1: FE gọi POST /api/auth/login với username/password.

Bước 2:
Request vẫn đi qua SecurityFilterChain. Vì /api/auth/login được cấu hình permitAll nên request không cần token và được đi tiếp vào Controller.

Bước 3:
AuthController nhận request và gọi hàm login trong AuthServiceImpl.

Bước 4:
AuthServiceImpl tạo UsernamePasswordAuthenticationToken chứa username/password người dùng nhập.

Bước 5:
AuthServiceImpl gọi authenticationManager.authenticate(...) và truyền UsernamePasswordAuthenticationToken vào.

Bước 6:
AuthenticationManager điều phối xác thực và giao cho DaoAuthenticationProvider xử lý vì đây là kiểu đăng nhập username/password.

Bước 7:
DaoAuthenticationProvider gọi CustomUserDetailsService.loadUserByUsername(username).

Bước 8:
CustomUserDetailsService dùng UserRepository để tìm user trong database theo username.

Bước 9:
Nếu không tìm thấy user thì ném UsernameNotFoundException, quá trình xác thực thất bại.

Bước 10:
Nếu tìm thấy user thì CustomUserDetailsService chuyển entity Users thành UserDetails. UserDetails gồm username, password đã mã hóa, trạng thái enabled/disabled và authorities/roles.

Bước 11:
DaoAuthenticationProvider nhận UserDetails, sau đó dùng PasswordEncoder để so sánh password người dùng nhập với password đã mã hóa trong database.

Bước 12:
Nếu password sai hoặc tài khoản bị disable thì Spring Security ném AuthenticationException.

Bước 13:
Nếu xác thực thành công, AuthenticationManager trả về Authentication đã authenticated = true.

Bước 14:
AuthServiceImpl tiếp tục gọi JwtService để tạo accessToken và refreshToken.

Bước 15:
AuthServiceImpl trả LoginResponse cho Controller.

Bước 16:
Controller đóng gói LoginResponse vào ApiResponse và trả về FE.


