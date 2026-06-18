Giai đoạn 1: Auth + CRUD cơ bản

Bạn học kèm:

- Java Core
- OOP
- Spring Boot cơ bản
- CRUD
- Spring Data JPA
- REST API
- Validation
- Exception handling
- JWT
- Spring Security
- Unit test
- Swagger
- Git pull request
- Self review code

Module làm:

auth-service
account-service
Giai đoạn 2: Account + Transaction

Bạn học kèm:

- Database transaction
- @Transactional
- SQL
- Join nhiều bảng
- Lazy/Eager loading
- Index
- Soft delete
- Logging
- Audit log
- Report cơ bản

Module làm:

account-service
transfer-service
transaction history
Giai đoạn 3: Chuyển tiền + OTP

Bạn học kèm:

- Redis TTL
- Idempotency
- Duplicate request
- Race condition
- Locking
- Transaction status
- Business logic đúng/sai
- Unit test nghiệp vụ
- Integration test

Module làm:

transfer-service
otp flow
Giai đoạn 4: Payment + Card

Bạn học kèm:

- Mock provider
- External API
- Timeout
- Retry
- Wrong business logic
- Log request/response
- Batch chạy ban đêm
- Report phức tạp

Module làm:

payment-service
card-service
Giai đoạn 5: Admin web

Bạn học kèm:

- Role ADMIN/USER
- Authorization
- Dashboard
- Search/filter/paging
- Export report
- Review code nhanh
- Giao tiếp QA/PM
- Jira bug classification

Module làm:

admin-service
admin-web
Giai đoạn 6: Microservice nâng cao

Bạn học kèm:

- API Gateway
- Service Registry
- Kafka
- Distributed transaction
- Saga pattern
- Docker
- Docker Compose
- Kubernetes cơ bản
- Performance test
- Security

Module làm:

api-gateway
notification-service
Kafka event
Docker full system
-------------------------------------------------
########################################## Ngày 1
Setup project
Docker PostgreSQL + Redis
Thiết kế DB core
Tạo auth-service
Kết nối DB
Flyway migration
Health API
########################################## Ngày 2
Register API
Login API
JWT
PasswordEncoder
Spring Security config
Swagger
Unit test register/login
########################################## Ngày 3
Tạo account-service
Tạo bảng accounts
CRUD account cơ bản
Soft delete account
Validate account status
########################################## Ngày 4
API xem danh sách account theo user
API xem số dư
API admin xem account
Phân quyền USER/ADMIN
########################################## Ngày 5
Tạo transfer-service
Thiết kế bảng transactions
API init transfer
Validate số tiền > 0
Validate tài khoản nguồn/đích
Check số dư
########################################## Ngày 6
Redis OTP
Sinh OTP
Lưu OTP TTL 5 phút
API confirm transfer
Verify OTP
########################################## Ngày 7
@Transactional
Debit/Credit
Update transaction SUCCESS/FAILED
Audit log
Test case chuyển tiền
########################################## Ngày 8
Idempotency
Chống bấm nhiều lần
TransactionRef unique
PROCESSING/SUCCESS handling
########################################## Ngày 9
Transaction history
Search/filter by date/status/type
Pagination
Sort
########################################## Ngày 10
Admin transaction management
Xem chi tiết giao dịch
Tra cứu transactionRef
Audit log admin
########################################## Ngày 11
Payment bill mock provider
Query bill
Init bill payment
Confirm OTP
########################################## Ngày 12
Card service
List card
Lock/unlock card
Change limit
########################################## Ngày 13
Kafka
Publish TransferSuccessEvent
Notification consume event
Send email giả lập/log
########################################## Ngày 14
React customer web
Login
Account list
Transfer form
OTP confirm screen