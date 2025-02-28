INSERT INTO public.payments
(id, "date", price, status, deadline, user_id)
VALUES(1, '2023-02-09', 250.00, 'ON_TIME'::public."payment_status", '2023-02-10', 10);
INSERT INTO public.payments
(id, "date", price, status, deadline, user_id)
VALUES(4, '2023-03-15', 250.00, 'ON_TIME'::public."payment_status", '2023-04-05', 9);
INSERT INTO public.payments
(id, "date", price, status, deadline, user_id)
VALUES(8, '2023-03-18', 150.00, 'ON_TIME'::public."payment_status", '2023-04-11', 9);
INSERT INTO public.payments
(id, "date", price, status, deadline, user_id)
VALUES(9, '2023-01-04', 370.00, 'ON_TIME'::public."payment_status", '2023-01-10', 9);
INSERT INTO public.payments
(id, "date", price, status, deadline, user_id)
VALUES(11, NULL, 110.00, 'OWE'::public."payment_status", '2023-03-19', 4);
INSERT INTO public.payments
(id, "date", price, status, deadline, user_id)
VALUES(12, NULL, 150.00, 'OWE'::public."payment_status", '2023-04-20', 4);