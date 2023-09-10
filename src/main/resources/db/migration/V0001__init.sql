create table holiday
(
  id          int auto_increment primary key,
  name        nvarchar(50) not null,
  description text         not null,
  cover_path  text         null
);
