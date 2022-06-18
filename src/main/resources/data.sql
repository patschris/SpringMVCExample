INSERT INTO public.users (username, password)
VALUES
    ('cpats', '$2a$10$gkmlWZfU1.w9vwI0UltgFei2OV3XEmFAr.jlbqcG0lkbtoeRiJeRO'),
    ('user', '$2a$10$eFp7CMa6oTWURxosJa58Xu06GjuRlGEi5PMD0Gy/E14wx7dC96hhu')

INSERT INTO public.authorities
VALUES
    ('cpats', 'ROLE_USER'),
    ('cpats', 'ROLE_ADMIN'),
    ('user', 'ROLE_USER')

INSERT INTO public.todo (description, target_date, done, users)
VALUES
    ('Buy a new car','2022-08-26', FALSE, 'cpats'),
    ('Find a Job','2021-12-22', TRUE, 'cpats'),
    ('Learn Jasper','2022-09-21', FALSE, 'cpats'),
    ('Learn Everything','2222-02-02', FALSE, 'cpats'),
    ('Find a better name for the description','2021-11-10', FALSE, 'user'),
    ('Go on a trip','2022-07-18', FALSE, 'user')