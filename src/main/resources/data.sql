-- Users
INSERT INTO public.users (username, password) VALUES ('cpats', '$2a$10$gkmlWZfU1.w9vwI0UltgFei2OV3XEmFAr.jlbqcG0lkbtoeRiJeRO') ON CONFLICT (username) DO NOTHING;
INSERT INTO public.users (username, password) VALUES ('user', '$2a$10$eFp7CMa6oTWURxosJa58Xu06GjuRlGEi5PMD0Gy/E14wx7dC96hhu') ON CONFLICT (username) DO NOTHING;

-- Authorities
INSERT INTO public.authorities SELECT 'cpats', 'ROLE_USER' WHERE NOT EXISTS (SELECT 1 FROM public.authorities WHERE username='cpats' AND authority='ROLE_USER');
INSERT INTO public.authorities SELECT 'cpats', 'ROLE_ADMIN' WHERE NOT EXISTS (SELECT 1 FROM public.authorities WHERE username='cpats' AND authority='ROLE_ADMIN');
INSERT INTO public.authorities SELECT 'user', 'ROLE_USER' WHERE NOT EXISTS (SELECT 1 FROM public.authorities WHERE username='user' AND authority='ROLE_USER');

-- Todos
INSERT INTO public.todo (description, target_date, done, users) SELECT 'Buy a new car','2022-08-26', FALSE, 'cpats' WHERE NOT EXISTS(SELECT 1 FROM public.todo WHERE id=1);
INSERT INTO public.todo (description, target_date, done, users) SELECT 'Find a Job','2021-12-22', TRUE, 'cpats' WHERE NOT EXISTS(SELECT 1 FROM public.todo WHERE id=2);
INSERT INTO public.todo (description, target_date, done, users) SELECT 'Learn Jasper','2022-09-21', FALSE, 'cpats' WHERE NOT EXISTS(SELECT 1 FROM public.todo WHERE id=3);
INSERT INTO public.todo (description, target_date, done, users) SELECT 'Learn Everything','2222-02-02', FALSE, 'cpats' WHERE NOT EXISTS(SELECT 1 FROM public.todo WHERE id=4);
INSERT INTO public.todo (description, target_date, done, users) SELECT 'Find a better name for the description','2021-11-10', FALSE, 'user' WHERE NOT EXISTS(SELECT 1 FROM public.todo WHERE id=5);
INSERT INTO public.todo (description, target_date, done, users) SELECT 'Go on a trip','2022-07-18', FALSE, 'user' WHERE NOT EXISTS(SELECT 1 FROM public.todo WHERE id=6);