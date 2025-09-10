-- seed a couple people because typing is hard
INSERT INTO person (id, name, email) VALUES
  (1, 'Alice', 'alice@example.com'),
  (2, 'Bob',   'bob@example.com')
ON DUPLICATE KEY UPDATE name=VALUES(name);
