# DAY 01 - SETUP NỀN MÓNG PROJECT DIGITAL BANKING SYSTEM

## 1. Mục tiêu ngày 1

Mục tiêu của ngày đầu tiên là xây dựng nền móng ban đầu cho project **Digital Banking System**, bao gồm cấu trúc thư mục, Git repository, Docker Compose, PostgreSQL, Redis và tài liệu thiết kế ban đầu.

Ngày 1 chưa tập trung vào code nghiệp vụ như chuyển tiền, OTP hay thanh toán hóa đơn. Thay vào đó, mục tiêu chính là chuẩn bị môi trường phát triển ổn định để các ngày sau có thể bắt đầu xây dựng backend service một cách bài bản.

---

## 2. Thông tin môi trường phát triển

Môi trường hiện tại:

```text
Java: 21.0.5 LTS
NVM: 1.2.2
Docker: 29.3.1
Git: 2.48.1.windows.1
Database Tool: pgAdmin 4, Navicat Premium 16
OS: Windows
```

Công nghệ chính của project:

```text
Backend:
- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA
- Microservices
- Redis
- Kafka
- Docker
- Swagger/OpenAPI

Frontend:
- ReactJS
- Axios
- React Router
- Bootstrap hoặc Tailwind CSS

Database:
- PostgreSQL
```

---

## 3. Cấu trúc thư mục hiện tại

Sau khi setup ban đầu, project có cấu trúc như sau:

```text
banking-system
│
├── .git
├── backend
├── docs
├── frontend
├── .gitignore
└── docker-compose.yml
```

Ý nghĩa từng thư mục:

| Thư mục / File       | Ý nghĩa                                                    |
| -------------------- | ---------------------------------------------------------- |
| `.git`               | Thư mục quản lý Git repository                             |
| `backend`            | Chứa toàn bộ source code backend Spring Boot               |
| `frontend`           | Chứa source code frontend ReactJS                          |
| `docs`               | Chứa tài liệu thiết kế, API, database, test case           |
| `.gitignore`         | Khai báo các file/folder không đưa lên Git                 |
| `docker-compose.yml` | Cấu hình chạy PostgreSQL, Redis và các service bằng Docker |

---

## 4. Cấu trúc thư mục backend dự kiến

Trong các ngày tiếp theo, thư mục `backend` sẽ chứa các service sau:

```text
backend
│
├── api-gateway
├── auth-service
├── account-service
├── transfer-service
├── payment-service
├── card-service
├── admin-service
└── notification-service
```

Tuy nhiên, ở giai đoạn đầu không tạo toàn bộ service cùng lúc. Thứ tự triển khai hợp lý là:

```text
1. auth-service
2. account-service
3. transfer-service
4. payment-service
5. card-service
6. admin-service
7. notification-service
8. api-gateway
```

Lý do không tạo hết service từ đầu là để tránh bị rối về cấu hình port, database, security, service communication, Kafka và Docker.

---

## 5. Cấu trúc thư mục docs dự kiến

Thư mục `docs` dùng để lưu tài liệu của project.

Cấu trúc đề xuất:

```text
docs
│
├── architecture
│   └── system-architecture.md
│
├── database
│   └── database-design-v1.md
│
├── api
│   └── api-document.md
│
├── sequence-diagram
│   └── transfer-flow.md
│
├── test-case
│   └── test-case-v1.md
│
└── day-01-setup.md
```

Ý nghĩa:

| Thư mục            | Mục đích                          |
| ------------------ | --------------------------------- |
| `architecture`     | Tài liệu kiến trúc hệ thống       |
| `database`         | Tài liệu thiết kế database        |
| `api`              | Tài liệu API request/response     |
| `sequence-diagram` | Mô tả luồng xử lý nghiệp vụ       |
| `test-case`        | Test case và test script          |
| `day-01-setup.md`  | Tài liệu ghi lại công việc ngày 1 |

---

## 6. Docker Compose ngày 1

Ngày 1 cần chạy trước:

```text
PostgreSQL
Redis
```

PostgreSQL dùng để lưu dữ liệu chính của hệ thống.

Redis dùng cho các chức năng sau này như:

```text
- Lưu OTP có TTL
- Cache dữ liệu
- Rate limit
- Session storage nếu cần
```

File `docker-compose.yml` đề xuất:

```yaml
version: "3.9"

services:
  postgres:
    image: postgres:16
    container_name: banking-postgres
    restart: always
    environment:
      POSTGRES_USER: banking_user
      POSTGRES_PASSWORD: banking_password
      POSTGRES_DB: banking_db
    ports:
      - "5432:5432"
    volumes:
      - banking_postgres_data:/var/lib/postgresql/data

  redis:
    image: redis:7
    container_name: banking-redis
    restart: always
    ports:
      - "6379:6379"

volumes:
  banking_postgres_data:
```

---

## 7. Lệnh chạy Docker

Tại thư mục root của project:

```text
C:\Users\Quan Nguyen\banking-system
```

Chạy lệnh:

```bash
docker compose up -d
```

Kiểm tra container:

```bash
docker ps
```

Kết quả mong muốn:

```text
banking-postgres
banking-redis
```

Nếu cần dừng container:

```bash
docker compose down
```

Nếu muốn xóa luôn volume dữ liệu local:

```bash
docker compose down -v
```

Lưu ý: lệnh `docker compose down -v` sẽ xóa dữ liệu PostgreSQL local.

---

## 8. Thông tin kết nối PostgreSQL

Dùng pgAdmin 4 hoặc Navicat Premium 16 để kết nối database.

Thông tin kết nối:

```text
Host: localhost
Port: 5432
Database: banking_db
Username: banking_user
Password: banking_password
```

Nếu kết nối thành công nghĩa là PostgreSQL đã chạy ổn.

---

## 9. Database core cần thiết kế ban đầu

Ngày 1 chưa cần thiết kế toàn bộ database. Chỉ cần thiết kế bản core để phục vụ các chức năng đầu tiên:

```text
1. users
2. roles
3. user_roles
4. accounts
5. transactions
6. otp_requests
7. audit_logs
```

Các bảng này phục vụ cho các nghiệp vụ chính:

```text
- Đăng ký / đăng nhập
- Phân quyền USER / ADMIN
- Quản lý tài khoản ngân hàng
- Chuyển tiền
- OTP
- Lịch sử giao dịch
- Ghi log thao tác
```

---

## 10. Thiết kế bảng roles

Bảng `roles` dùng để lưu quyền trong hệ thống.

```sql
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

Dữ liệu mẫu:

```sql
INSERT INTO roles(code, name)
VALUES 
('ROLE_USER', 'Customer User'),
('ROLE_ADMIN', 'Administrator');
```

Ý nghĩa:

| Field        | Ý nghĩa                               |
| ------------ | ------------------------------------- |
| `id`         | Khóa chính                            |
| `code`       | Mã quyền, ví dụ ROLE_USER, ROLE_ADMIN |
| `name`       | Tên hiển thị                          |
| `created_at` | Thời gian tạo                         |

---

## 11. Thiết kế bảng users

Bảng `users` lưu thông tin người dùng.

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
```

Ý nghĩa:

| Field        | Ý nghĩa              |
| ------------ | -------------------- |
| `id`         | Khóa chính           |
| `username`   | Tên đăng nhập        |
| `password`   | Mật khẩu đã mã hóa   |
| `full_name`  | Họ tên người dùng    |
| `email`      | Email                |
| `phone`      | Số điện thoại        |
| `status`     | Trạng thái tài khoản |
| `deleted`    | Cờ xóa mềm           |
| `created_at` | Thời gian tạo        |
| `updated_at` | Thời gian cập nhật   |

Các trạng thái user:

```text
ACTIVE
LOCKED
INACTIVE
```

---

## 12. Xóa mềm trong database

Trong hệ thống ngân hàng, không nên xóa vật lý dữ liệu quan trọng.

Không nên dùng:

```sql
DELETE FROM users WHERE id = 1;
```

Nên dùng xóa mềm:

```sql
UPDATE users
SET deleted = true
WHERE id = 1;
```

Khi truy vấn dữ liệu, cần lọc:

```sql
SELECT *
FROM users
WHERE deleted = false;
```

Lý do dùng xóa mềm:

```text
- Giữ lại lịch sử dữ liệu
- Phục vụ audit log
- Có thể khôi phục dữ liệu nếu cần
- Tránh mất dữ liệu quan trọng
- Phù hợp với hệ thống tài chính/ngân hàng
```

---

## 13. Thiết kế bảng user_roles

Bảng `user_roles` dùng để mapping user với role.

```sql
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user 
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role 
        FOREIGN KEY (role_id) REFERENCES roles(id)
);
```

Một user có thể có nhiều role.

Ví dụ:

```text
user01 có ROLE_USER
admin01 có ROLE_ADMIN
```

---

## 14. Thiết kế bảng accounts

Bảng `accounts` dùng để lưu tài khoản ngân hàng của khách hàng.

```sql
CREATE TABLE accounts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    account_no VARCHAR(30) NOT NULL UNIQUE,
    account_type VARCHAR(30) NOT NULL,
    balance NUMERIC(19, 2) NOT NULL DEFAULT 0,
    currency VARCHAR(10) NOT NULL DEFAULT 'VND',
    status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_accounts_user 
        FOREIGN KEY (user_id) REFERENCES users(id)
);
```

Ý nghĩa:

| Field          | Ý nghĩa              |
| -------------- | -------------------- |
| `user_id`      | Chủ sở hữu tài khoản |
| `account_no`   | Số tài khoản         |
| `account_type` | Loại tài khoản       |
| `balance`      | Số dư                |
| `currency`     | Loại tiền tệ         |
| `status`       | Trạng thái tài khoản |
| `deleted`      | Cờ xóa mềm           |

Trạng thái account:

```text
ACTIVE
LOCKED
CLOSED
```

---

## 15. Thiết kế bảng transactions

Bảng `transactions` lưu thông tin giao dịch.

```sql
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    transaction_ref VARCHAR(50) NOT NULL UNIQUE,
    from_account_no VARCHAR(30),
    to_account_no VARCHAR(30),
    amount NUMERIC(19, 2) NOT NULL,
    fee NUMERIC(19, 2) NOT NULL DEFAULT 0,
    content VARCHAR(255),
    transaction_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    error_code VARCHAR(50),
    error_message TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);
```

Loại giao dịch:

```text
TRANSFER_INTERNAL
BILL_PAYMENT
CARD_PAYMENT
TOPUP
```

Trạng thái giao dịch:

```text
PENDING_OTP
PROCESSING
SUCCESS
FAILED
EXPIRED
CANCELLED
```

Ý nghĩa trạng thái:

| Status        | Ý nghĩa                                 |
| ------------- | --------------------------------------- |
| `PENDING_OTP` | Đã tạo giao dịch, đang chờ xác thực OTP |
| `PROCESSING`  | Đang xử lý giao dịch                    |
| `SUCCESS`     | Giao dịch thành công                    |
| `FAILED`      | Giao dịch thất bại                      |
| `EXPIRED`     | Giao dịch hết hạn                       |
| `CANCELLED`   | Giao dịch bị hủy                        |

---

## 16. Thiết kế bảng otp_requests

Bảng `otp_requests` dùng để ghi nhận lịch sử yêu cầu OTP.

OTP thực tế sẽ lưu trong Redis với TTL. Bảng này chỉ dùng để lưu log.

```sql
CREATE TABLE otp_requests (
    id BIGSERIAL PRIMARY KEY,
    transaction_ref VARCHAR(50) NOT NULL,
    otp_type VARCHAR(50) NOT NULL,
    receiver VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    expired_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

Không nên lưu OTP plain text trong database.

Nếu cần lưu thì chỉ nên lưu dạng hash.

Lý do:

```text
- OTP là thông tin nhạy cảm
- Tránh lộ OTP nếu database bị truy cập trái phép
- Redis đã hỗ trợ TTL để tự hết hạn OTP
```

---

## 17. Thiết kế bảng audit_logs

Bảng `audit_logs` lưu lịch sử thao tác của user/admin.

```sql
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    actor_id BIGINT,
    actor_type VARCHAR(50),
    action VARCHAR(100) NOT NULL,
    module VARCHAR(100) NOT NULL,
    ip_address VARCHAR(100),
    request_data TEXT,
    response_data TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

Ví dụ action:

```text
LOGIN
REGISTER
CREATE_ACCOUNT
INIT_TRANSFER
CONFIRM_TRANSFER
LOCK_CARD
UNLOCK_CARD
ADMIN_VIEW_TRANSACTION
```

Lý do cần audit log:

```text
- Truy vết ai đã thao tác
- Biết thao tác xảy ra lúc nào
- Biết thao tác thuộc module nào
- Hỗ trợ điều tra lỗi
- Hỗ trợ kiểm tra bảo mật
- Phù hợp với hệ thống ngân hàng
```

---

## 18. Luồng nghiệp vụ đầu tiên cần hướng tới

Sau khi setup xong ngày 1, các ngày tiếp theo sẽ xây dựng luồng sau:

```text
User đăng ký / đăng nhập
        |
        v
User có tài khoản ngân hàng
        |
        v
User khởi tạo giao dịch chuyển tiền
        |
        v
Hệ thống tạo transaction_ref
        |
        v
Hệ thống sinh OTP và lưu Redis
        |
        v
User nhập OTP
        |
        v
Hệ thống xác thực OTP
        |
        v
Trừ tiền tài khoản nguồn
        |
        v
Cộng tiền tài khoản nhận
        |
        v
Cập nhật transaction SUCCESS
        |
        v
Ghi audit log
        |
        v
Gửi notification
```

---

## 19. Git workflow ngày 1

Khởi tạo Git:

```bash
git init
```

Kiểm tra trạng thái:

```bash
git status
```

Add file:

```bash
git add .
```

Commit:

```bash
git commit -m "day 1 setup banking system foundation"
```

Quy tắc commit message:

```text
- Viết ngắn gọn
- Mô tả đúng nội dung thay đổi
- Không commit file rác
- Không commit password production
```

Ví dụ commit tốt:

```bash
git commit -m "setup docker compose for postgres and redis"
git commit -m "add initial database design document"
git commit -m "init banking system structure"
```

---

## 20. Checklist ngày 1

Checklist cần hoàn thành:

```text
[x] Tạo folder banking-system
[x] Tạo thư mục backend
[x] Tạo thư mục frontend
[x] Tạo thư mục docs
[x] Tạo file .gitignore
[x] Tạo file docker-compose.yml
[x] Khởi tạo Git repository
[ ] Chạy PostgreSQL bằng Docker
[ ] Chạy Redis bằng Docker
[ ] Kết nối PostgreSQL bằng pgAdmin/Navicat
[ ] Tạo tài liệu database-design-v1.md
[ ] Tạo auth-service Spring Boot
[ ] Cấu hình application.yml
[ ] Tạo Flyway migration V1
[ ] Tạo API health check
[ ] Commit code cuối ngày
```

---

## 21. Kiến thức học kèm ngày 1

Trong lúc làm ngày 1, cần ôn các kiến thức sau:

### 21.1. Local environment là gì?

Local environment là môi trường chạy trên máy cá nhân của lập trình viên.

Ví dụ:

```text
- Database chạy bằng Docker trên máy cá nhân
- Backend chạy bằng IntelliJ
- Frontend chạy bằng npm trên máy cá nhân
```

Local dùng để developer code và test nhanh trước khi đưa lên môi trường chung.

---

### 21.2. Docker container là gì?

Container là môi trường đóng gói ứng dụng và dependency để chạy độc lập.

Trong project này:

```text
PostgreSQL chạy trong container banking-postgres
Redis chạy trong container banking-redis
```

Lợi ích:

```text
- Không cần cài PostgreSQL trực tiếp vào máy
- Dễ setup lại môi trường
- Các thành viên team chạy cùng một cấu hình
- Dễ chuyển sang staging/production hơn
```

---

### 21.3. Docker volume là gì?

Volume dùng để lưu dữ liệu ra ngoài container.

Nếu không có volume, khi xóa container thì dữ liệu có thể mất.

Trong file docker-compose:

```yaml
volumes:
  banking_postgres_data:
```

Volume này giúp dữ liệu PostgreSQL không bị mất khi restart container.

---

### 21.4. PostgreSQL dùng để làm gì?

PostgreSQL là relational database dùng để lưu dữ liệu chính của hệ thống.

Trong project banking, PostgreSQL lưu:

```text
- User
- Role
- Account
- Transaction
- OTP request log
- Audit log
- Card
- Bill payment
```

---

### 21.5. Redis dùng để làm gì?

Redis là in-memory database.

Trong project này Redis dùng cho:

```text
- Lưu OTP với TTL
- Cache dữ liệu
- Rate limit
- Session storage nếu cần
```

Ví dụ OTP:

```text
Key: OTP:TRX202606150001
Value: 123456
TTL: 5 phút
```

---

## 22. Khi không connect được database thì xử lý thế nào?

Khi Spring Boot hoặc tool DB không connect được PostgreSQL, xử lý theo thứ tự sau:

### Bước 1: Kiểm tra container có chạy không

```bash
docker ps
```

Nếu không thấy container PostgreSQL:

```bash
docker compose up -d
```

---

### Bước 2: Kiểm tra port

Kiểm tra port 5432:

```bash
netstat -ano | findstr :5432
```

Nếu port bị trùng, có thể đổi port trong `docker-compose.yml`:

```yaml
ports:
  - "5433:5432"
```

Khi đó cấu hình kết nối sẽ là:

```text
Host: localhost
Port: 5433
Database: banking_db
Username: banking_user
Password: banking_password
```

---

### Bước 3: Kiểm tra username, password, database

Cấu hình trong Docker:

```yaml
POSTGRES_USER: banking_user
POSTGRES_PASSWORD: banking_password
POSTGRES_DB: banking_db
```

Thông tin kết nối phải khớp:

```text
Username: banking_user
Password: banking_password
Database: banking_db
```

---

### Bước 4: Đọc lỗi cụ thể

Một số lỗi thường gặp:

```text
Connection refused
```

Nguyên nhân thường gặp:

```text
- PostgreSQL chưa chạy
- Sai port
- Docker container bị stop
```

---

```text
password authentication failed
```

Nguyên nhân thường gặp:

```text
- Sai username
- Sai password
```

---

```text
database "banking_db" does not exist
```

Nguyên nhân thường gặp:

```text
- Sai database name
- Container dùng volume cũ
- Database chưa được tạo
```

---

```text
relation "users" does not exist
```

Nguyên nhân thường gặp:

```text
- Chưa tạo bảng
- Flyway chưa chạy
- Chạy sai database
```

---

### Bước 5: Nếu Docker đang giữ volume cũ

Nếu đã đổi `POSTGRES_DB`, `POSTGRES_USER`, `POSTGRES_PASSWORD` nhưng database không thay đổi, có thể do Docker volume cũ vẫn còn.

Xử lý ở môi trường local:

```bash
docker compose down -v
docker compose up -d
```

Lưu ý: lệnh này xóa dữ liệu local.

---

## 23. Bài học ngày 1

Sau ngày 1, cần nắm được:

```text
1. Biết tạo cấu trúc project backend/frontend/docs
2. Biết khởi tạo Git repository
3. Biết tạo .gitignore
4. Biết dùng Docker Compose để chạy PostgreSQL và Redis
5. Biết kết nối PostgreSQL bằng pgAdmin/Navicat
6. Biết vì sao cần thiết kế database trước khi code
7. Biết ý nghĩa của soft delete
8. Biết các bước xử lý khi không connect được DB
9. Biết vai trò của Redis trong project banking
10. Biết cách ghi tài liệu kỹ thuật cho project
```

---

## 24. Câu trả lời phỏng vấn sau ngày 1

Nếu nhà tuyển dụng hỏi:

```text
Em bắt đầu project banking này như thế nào?
```

Có thể trả lời:

```text
Em bắt đầu bằng việc xác định phạm vi MVP của hệ thống, sau đó tạo cấu trúc project gồm backend, frontend và docs. Em dùng Docker Compose để chạy PostgreSQL và Redis trên local, giúp môi trường dễ setup và thống nhất. Tiếp theo em thiết kế database core gồm users, roles, accounts, transactions, otp_requests và audit_logs. Em cũng thiết kế sẵn cơ chế soft delete bằng field deleted để tránh xóa vật lý dữ liệu quan trọng, phù hợp với hệ thống tài chính/ngân hàng.
```

Nếu nhà tuyển dụng hỏi:

```text
Nếu không connect được database thì em xử lý như thế nào?
```

Có thể trả lời:

```text
Đầu tiên em kiểm tra database container có đang chạy không bằng docker ps. Sau đó em kiểm tra port mapping, username, password và database name có khớp với cấu hình datasource không. Tiếp theo em đọc log lỗi cụ thể, ví dụ connection refused thì thường là DB chưa chạy hoặc sai port, password authentication failed thì là sai tài khoản, còn relation does not exist thì có thể migration chưa chạy. Nếu dùng Docker mà env không đổi do volume cũ thì ở local em có thể down volume rồi tạo lại.
```

Nếu nhà tuyển dụng hỏi:

```text
Tại sao em dùng soft delete?
```

Có thể trả lời:

```text
Vì trong hệ thống ngân hàng dữ liệu rất quan trọng, không nên xóa vật lý ngay. Soft delete giúp giữ lại lịch sử dữ liệu, phục vụ audit log, tra soát và có thể khôi phục khi cần. Khi query dữ liệu thì hệ thống sẽ lọc theo deleted = false.
```

---

## 25. Kết quả mong muốn cuối ngày 1

Cuối ngày 1, project cần đạt được:

```text
- Có cấu trúc thư mục rõ ràng
- Có Git repository
- Có docker-compose.yml chạy PostgreSQL và Redis
- Connect được PostgreSQL bằng pgAdmin/Navicat
- Có tài liệu database core ban đầu
- Hiểu được vai trò của Docker, PostgreSQL, Redis, soft delete
- Sẵn sàng tạo auth-service ở ngày 2
```

---

## 26. Kế hoạch ngày 2

Ngày 2 sẽ bắt đầu tạo `auth-service`.

Nội dung cần làm:

```text
1. Tạo Spring Boot project auth-service
2. Thêm dependencies:
   - Spring Web
   - Spring Security
   - Spring Data JPA
   - PostgreSQL Driver
   - Validation
   - Lombok
   - Flyway
   - Swagger/OpenAPI

3. Cấu hình application.yml
4. Tạo Flyway migration V1
5. Tạo entity User, Role
6. Tạo Register API
7. Tạo Login API
8. Tạo JWT
9. Test API bằng Postman
10. Viết unit test cơ bản
```

Mục tiêu ngày 2:

```text
- Đăng ký user thành công
- Login thành công
- Trả về JWT token
- Có phân quyền USER/ADMIN cơ bản
```
