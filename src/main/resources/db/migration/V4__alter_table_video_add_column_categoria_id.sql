ALTER TABLE video
    ADD COLUMN categoria_id BIGINT,
    ADD CONSTRAINT fk_video_categoria FOREIGN KEY (categoria_id) REFERENCES categoria(id);