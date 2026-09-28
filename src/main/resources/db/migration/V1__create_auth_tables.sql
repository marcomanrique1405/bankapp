CREATE TABLE app_users (
    id UUID NOT NULL,
    email VARCHAR(320) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,

    CONSTRAINT pk_app_users
        PRIMARY KEY (id),

    CONSTRAINT uq_app_users_email
        UNIQUE (email),

    CONSTRAINT ck_app_users_email_lowercase
        CHECK (email = lower(email)),

    CONSTRAINT ck_app_users_status
        CHECK (status IN ('ACTIVE', 'BLOCKED', 'DISABLED'))

);


CREATE TABLE user_roles (
    user_id UUID NOT NULL,
    role VARCHAR(20) NOT NULL,

    CONSTRAINT pk_user_roles
        PRIMARY KEY (user_id, role),

    CONSTRAINT fk_user_roles_user
        FOREIGN KEY (user_id)
        REFERENCES app_users(id),

    CONSTRAINT ck_user_roles_role
        CHECK (role IN ('CLIENTE', 'EMPLEADO', 'ADMIN'))

);


CREATE TABLE refresh_tokens (
   id UUID NOT NULL,
   user_id UUID NOT NULL,
   token_hash VARCHAR(255) NOT NULL,
   expires_at TIMESTAMPTZ NOT NULL,
   revoked_at TIMESTAMPTZ,
   created_at TIMESTAMPTZ NOT NULL,

   CONSTRAINT pk_refresh_tokens
       PRIMARY KEY (id),

   CONSTRAINT fk_refresh_tokens_user
       FOREIGN KEY (user_id)
       REFERENCES app_users(id),

   CONSTRAINT uq_refresh_tokens_token_hash
       UNIQUE (token_hash)
);

CREATE INDEX ix_refresh_tokens_user_id
    ON refresh_tokens(user_id);