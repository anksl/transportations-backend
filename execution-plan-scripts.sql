SELECT name from users u inner join payments p on u.id=p.user_id;
SELECT name from users u inner join transportations t on u.id=t.user_id;
SELECT c.id as cargo_id, t.id as transportation_id  from cargos c inner join transportations t on t.id=t.cargo_id;
SELECT width from cargos c inner join sizes s on c.id=s.id;
SELECT price from transportations t inner join payments p on t.id=p.id;
SELECT date from transportations t left join deliveries l on t.loading_id=l.id;
SELECT date from transportations t left join deliveries l on t.landing_id=l.id;
SELECT house from deliveries d inner join addresses a on d.id=a.id;
SELECT countries.name as country_name, c.name as city_name, a.street as street
from countries inner join cities c on countries.id=c.country_id
inner join addresses a on c.id=a.city_id;
select users.name as username, roles.role as role
from users inner join users_roles on users.id=users_roles.user_id
inner join roles on users_roles.role_id = roles.id