INSERT INTO demo_data (name, description)
SELECT 'Spring Boot', 'Application service connected to PostgreSQL'
WHERE NOT EXISTS (SELECT 1 FROM demo_data WHERE name = 'Spring Boot');

INSERT INTO demo_data (name, description)
SELECT 'Docker Compose', 'Application and database run as separate containers'
WHERE NOT EXISTS (SELECT 1 FROM demo_data WHERE name = 'Docker Compose');
