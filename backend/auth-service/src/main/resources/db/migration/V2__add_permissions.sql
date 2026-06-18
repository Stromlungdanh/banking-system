CREATE TABLE IF NOT EXISTS permissions (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id)
);

INSERT INTO permissions(code, name)
VALUES
    ('AUTH_ME_READ', 'View own profile'),
    ('ACCOUNT_READ_SELF', 'User view own accounts'),
    ('TRANSFER_CREATE', 'User create transfer'),
    ('TRANSFER_CONFIRM', 'User confirm transfer'),
    ('TRANSACTION_READ_SELF', 'User view own transactions'),
    ('ADMIN_USER_READ', 'Admin view users'),
    ('ADMIN_USER_MANAGE', 'Admin manage users'),
    ('ADMIN_TRANSACTION_READ', 'Admin view transactions')
ON CONFLICT (code) DO NOTHING;

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.code = 'ROLE_USER'
AND p.code IN (
    'AUTH_ME_READ',
    'ACCOUNT_READ_SELF',
    'TRANSFER_CREATE',
    'TRANSFER_CONFIRM',
    'TRANSACTION_READ_SELF'
)
ON CONFLICT DO NOTHING;

INSERT INTO role_permissions(role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE r.code = 'ROLE_ADMIN'
AND p.code IN (
    'AUTH_ME_READ',
    'ADMIN_USER_READ',
    'ADMIN_USER_MANAGE',
    'ADMIN_TRANSACTION_READ'
)
ON CONFLICT DO NOTHING;