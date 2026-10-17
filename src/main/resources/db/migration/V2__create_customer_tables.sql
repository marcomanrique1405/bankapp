CREATE TABLE customers (
                           id UUID NOT NULL,
                           user_id UUID,
                           first_name VARCHAR(100) NOT NULL,
                           last_name VARCHAR(100) NOT NULL,
                           phone VARCHAR(20),
                           date_of_birth DATE NOT NULL,
                           status VARCHAR(20) NOT NULL,
                           created_at TIMESTAMPTZ NOT NULL,
                           updated_at TIMESTAMPTZ NOT NULL,

                           CONSTRAINT pk_customers PRIMARY KEY (id),
                           CONSTRAINT uq_customers_user_id UNIQUE (user_id),
                           CONSTRAINT fk_customers_user
                               FOREIGN KEY (user_id)
                                   REFERENCES app_users(id),
                           CONSTRAINT ck_customers_status
                               CHECK (status IN ('ACTIVE', 'INACTIVE'))
);