CREATE TABLE video
(
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    titulo    VARCHAR(255) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    url       VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);