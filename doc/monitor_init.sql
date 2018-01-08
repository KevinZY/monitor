CREATE TABLE topic (
  id        BIGINT(20)   AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(20) NOT NULL UNIQUE,
  active    CHAR(1)     NOT NULL DEFAULT 1,
  processor VARCHAR(255) DEFAULT NULL,
  configs   LONGTEXT DEFAULT NULL ,
  pollInterval int(11) DEFAULT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE topic_partition (
  id           BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
  topic        VARCHAR(20) NOT NULL,
  partitionId INT(11)     NOT NULL,
  topicId     BIGINT(20)  NOT NULL,
  CONSTRAINT fk_topic_id FOREIGN KEY (topicId) REFERENCES topic (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE topic_partition_aspect (
  id                 BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
  messages           LONGTEXT,
  startOffset       BIGINT(20)   NOT NULL,
  endOffset         BIGINT(20)   NOT NULL,
  status             VARCHAR(255) NOT NULL,
  topicPartitionId BIGINT(20)   NOT NULL,
  CONSTRAINT fk_topic_partition_id FOREIGN KEY (topicPartitionId) REFERENCES topic_partition (id)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;