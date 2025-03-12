INSERT IGNORE INTO users (id, username, email, password, created_at) VALUES
                                                                          (900101, 'johnDoe', 'john@example.com', 'password123', NOW()),
                                                                          (900102,'janeDoe', 'jane@example.com', 'mypassword!', NOW()),
                                                                          (900103,'adminUser', 'admin@example.com', 'adminPass!', NOW());