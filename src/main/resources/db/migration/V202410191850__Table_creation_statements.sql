CREATE TABLE PURCHASE_AGREEMENTS
(
    ID                  BIGSERIAL PRIMARY KEY,
    CUSTOMER_NAME       VARCHAR(500) NOT NULL,
    AGREEMENT_NUMBER    VARCHAR(150) NOT NULL,
    ADVANCE_DATE        DATE NOT NULL,
    DELIVERY_TERM       DATE NOT NULL,
    STATUS              VARCHAR(20) NOT NULL,
    DELETED_FLAG        BOOLEAN     NOT NULL,
    USER_CREATED        VARCHAR(20)  ,
    USER_MODIFIED       VARCHAR(20)  ,
    DATE_CREATED        TIMESTAMP    NOT NULL,
    DATE_MODIFIED       TIMESTAMP    NOT NULL,
    CONSTRAINT UQ_PurchaseAgreements_Number UNIQUE (AGREEMENT_NUMBER)
);
CREATE SEQUENCE PURCHASE_AGREEMENT_NUMBER_SEQ START 1;
CREATE TABLE PURCHASE_AGREEMENT_ITEMS
(
    ID                      BIGSERIAL PRIMARY KEY,
    PURCHASE_AGREEMENT_ID   BIGINT  NOT NULL,
    NAME                    VARCHAR(500) NOT NULL,
    SUPPLIER                VARCHAR(500) NOT NULL,
    QUANTITY                INT  NOT NULL,
    STATUS                  VARCHAR(20) NOT NULL,
    USER_CREATED            VARCHAR(20)  ,
    USER_MODIFIED           VARCHAR(20)  ,
    DATE_CREATED            TIMESTAMP    NOT NULL,
    DATE_MODIFIED           TIMESTAMP    NOT NULL,
    CONSTRAINT FK_PurchaseAgreements_Items FOREIGN KEY (PURCHASE_AGREEMENT_ID) REFERENCES PURCHASE_AGREEMENTS (ID)
);
CREATE TABLE ITEMS
(
    ID                      BIGSERIAL PRIMARY KEY,
    NAME                    VARCHAR(500) NOT NULL,
    USER_CREATED            VARCHAR(20)  ,
    USER_MODIFIED           VARCHAR(20)  ,
    DATE_CREATED            TIMESTAMP    NOT NULL,
    DATE_MODIFIED           TIMESTAMP    NOT NULL
);
CREATE TABLE USERS
(
    ID                      BIGSERIAL PRIMARY KEY,
    USERNAME                VARCHAR(20) NOT NULL,
    PASSWORD                VARCHAR(100) NOT NULL,
    USER_CREATED            VARCHAR(20)  ,
    USER_MODIFIED           VARCHAR(20)  ,
    DATE_CREATED            TIMESTAMP    NOT NULL,
    DATE_MODIFIED           TIMESTAMP    NOT NULL
);
