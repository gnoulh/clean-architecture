INSERT INTO users (id, email, first_name, last_name, password, role, status, created_at, updated_at)
VALUES ('user1', 'test@example.com', 'John', 'Doe', 'Password1!', 'USER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       ('user2', 'pm@example.com', 'Jane', 'Smith', 'Password1!', 'PROJECT_MANAGER', 'ACTIVE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO projects (id, name, description, owner_id, status, start_date, end_date, created_at, updated_at)
VALUES 
  ('project1', 'Test Project', 'Sample project', 'user2', 'PLANNING', CURRENT_TIMESTAMP, 
   DATEADD('DAY', 10, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);